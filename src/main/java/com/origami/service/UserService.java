package com.origami.service;

import com.origami.config.Constants;
import com.origami.domain.Authority;
import com.origami.domain.Profile;
import com.origami.domain.User;
import com.origami.repository.AuthorityRepository;
import com.origami.repository.ProfileRepository;
import com.origami.repository.UserRepository;
import com.origami.security.AuthoritiesConstants;
import com.origami.security.SecurityUtils;
import com.origami.service.dto.AdminUserDTO;
import com.origami.service.dto.UserDTO;
import com.origami.web.rest.vm.ManagedUserVM;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.security.RandomUtil;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthorityRepository authorityRepository;

    private final CacheManager cacheManager;

    private final ProfileService profileService;

    private final ProfileRepository profileRepository;

    public UserService(
        UserRepository userRepository,
        ProfileRepository profileRepository,
        PasswordEncoder passwordEncoder,
        AuthorityRepository authorityRepository,
        CacheManager cacheManager,
        QRService qrService,
        ProfileService profileService,
        ProfileRepository profileRepository1
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
        this.cacheManager = cacheManager;
        this.profileService = profileService;
        this.profileRepository = profileRepository1;
    }

    public Optional<User> activateRegistration(String key) {
        log.debug("Activating user for activation key {}", key);
        return userRepository
            .findOneByActivationKey(key)
            .map(user -> {
                // activate given user for the registration key.
                user.setActivated(true);
                user.setActivationKey(null);
                this.clearUserCaches(user);
                log.debug("Activated user: {}", user);
                return user;
            });
    }

    public Optional<User> completePasswordReset(String newPassword, String key) {
        log.debug("Reset user password for reset key {}", key);
        return userRepository
            .findOneByResetKey(key)
            .filter(user -> user.getResetDate().isAfter(Instant.now().minus(1, ChronoUnit.DAYS)))
            .map(user -> {
                user.setPassword(passwordEncoder.encode(newPassword));
                user.setResetKey(null);
                user.setResetDate(null);
                this.clearUserCaches(user);
                return user;
            });
    }

    public Optional<User> requestPasswordReset(String mail) {
        return userRepository
            .findOneByEmailIgnoreCase(mail)
            .filter(User::isActivated)
            .map(user -> {
                user.setResetKey(RandomUtil.generateResetKey());
                user.setResetDate(Instant.now());
                this.clearUserCaches(user);
                return user;
            });
    }

    public User registerUser(ManagedUserVM userDTO, String password) {
        userRepository
            .findOneByLogin(userDTO.getLogin().toLowerCase())
            .ifPresent(existingUser -> {
                boolean removed = removeNonActivatedUser(existingUser);
                if (!removed) {
                    throw new UsernameAlreadyUsedException();
                }
            });
        userRepository
            .findOneByEmailIgnoreCase(userDTO.getEmail())
            .ifPresent(existingUser -> {
                boolean removed = removeNonActivatedUser(existingUser);
                if (!removed) {
                    throw new EmailAlreadyUsedException();
                }
            });

        User newUser = new User();
        String encryptedPassword = passwordEncoder.encode(password);
        newUser.setLogin(userDTO.getLogin().toLowerCase());
        // new user gets initially a generated password
        newUser.setPassword(encryptedPassword);
        newUser.setFirstName(userDTO.getFirstName());
        newUser.setLastName(userDTO.getLastName());
        if (userDTO.getEmail() != null) {
            newUser.setEmail(userDTO.getEmail().toLowerCase());
        }
        newUser.setImageUrl(userDTO.getImageUrl());
        newUser.setLangKey(userDTO.getLangKey());
        // new user is not active
        newUser.setActivated(false);
        // new user gets registration key
        newUser.setActivationKey(RandomUtil.generateActivationKey());
        Set<Authority> authorities = new HashSet<>();
        authorityRepository.findById(AuthoritiesConstants.USER).ifPresent(authorities::add);
        newUser.setAuthorities(authorities);
        userDTO.setUserId(userRepository.save(newUser).getId());

        /// Do zastąpienia jest ta linijka jak bedzie przygotowany już 3 etapowy formularz zamiast jednoetapowego
        profileService.createNewProfile(userDTO);

        this.clearUserCaches(newUser);
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

    public void registerZeroForm(ManagedUserVM userDTO) {
        Optional<User> userOptional = userRepository.findOneById(userDTO.getUserId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            profileService.registerZeroForm(userDTO);
        }
    }

    private boolean removeNonActivatedUser(User existingUser) {
        if (existingUser.isActivated()) {
            return false;
        }
        userRepository.delete(existingUser);
        userRepository.flush();
        this.clearUserCaches(existingUser);
        return true;
    }

    public User createUser(AdminUserDTO userDTO) {
        User user = new User();
        user.setLogin(userDTO.getLogin().toLowerCase());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        if (userDTO.getEmail() != null) {
            user.setEmail(userDTO.getEmail().toLowerCase());
        }
        user.setImageUrl(userDTO.getImageUrl());
        if (userDTO.getLangKey() == null) {
            user.setLangKey(Constants.DEFAULT_LANGUAGE); // default language
        } else {
            user.setLangKey(userDTO.getLangKey());
        }
        String encryptedPassword = passwordEncoder.encode(RandomUtil.generatePassword());
        user.setPassword(encryptedPassword);
        user.setResetKey(RandomUtil.generateResetKey());
        user.setResetDate(Instant.now());
        user.setActivated(true);
        if (userDTO.getAuthorities() != null) {
            Set<Authority> authorities = userDTO
                .getAuthorities()
                .stream()
                .map(authorityRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
            user.setAuthorities(authorities);
        }
        userRepository.save(user);
        this.clearUserCaches(user);
        log.debug("Created Information for User: {}", user);
        return user;
    }

    /**
     * Update all information for a specific user, and return the modified user.
     *
     * @param userDTO user to update.
     * @return updated user.
     */
    public Optional<AdminUserDTO> updateUser(AdminUserDTO userDTO) {
        return Optional
            .of(userRepository.findById(userDTO.getId()))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(user -> {
                this.clearUserCaches(user);
                user.setLogin(userDTO.getLogin().toLowerCase());
                user.setFirstName(userDTO.getFirstName());
                user.setLastName(userDTO.getLastName());
                if (userDTO.getEmail() != null) {
                    user.setEmail(userDTO.getEmail().toLowerCase());
                }
                user.setImageUrl(userDTO.getImageUrl());
                user.setActivated(userDTO.isActivated());
                user.setLangKey(userDTO.getLangKey());
                Set<Authority> managedAuthorities = user.getAuthorities();
                managedAuthorities.clear();
                userDTO
                    .getAuthorities()
                    .stream()
                    .map(authorityRepository::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .forEach(managedAuthorities::add);
                this.clearUserCaches(user);
                log.debug("Changed Information for User: {}", user);
                return user;
            })
            .map(AdminUserDTO::new);
    }

    public void deleteUser(String login) {
        userRepository
            .findOneByLogin(login)
            .ifPresent(user -> {
                userRepository.delete(user);
                this.clearUserCaches(user);
                log.debug("Deleted User: {}", user);
            });
    }

    /**
     * Update basic information (first name, last name, email, language) for the current user.
     *
     */
    public HttpStatus updateUser(ManagedUserVM userDTO) {
        Optional<User> optionalUser = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            userDTO.setUserId(user.getId());
            if (profileService.updateProfile(userDTO)) {
                user.setFirstName(userDTO.getFirstName());
                user.setLastName(userDTO.getLastName());
                if (userDTO.getEmail() != null) {
                    user.setEmail(userDTO.getEmail().toLowerCase());
                }
                user.setLangKey(userDTO.getLangKey());
                user.setImageUrl(userDTO.getImageUrl());
                this.clearUserCaches(user);
                log.debug("Changed Information for User: {}", user);
                return HttpStatus.OK;
            } else {
                return HttpStatus.FORBIDDEN;
            }
        } else return HttpStatus.FORBIDDEN;
    }

    @Transactional
    public void changePassword(String currentClearTextPassword, String newPassword) {
        SecurityUtils
            .getCurrentUserLogin()
            .flatMap(userRepository::findOneByLogin)
            .ifPresent(user -> {
                String currentEncryptedPassword = user.getPassword();
                if (!passwordEncoder.matches(currentClearTextPassword, currentEncryptedPassword)) {
                    throw new InvalidPasswordException();
                }
                String encryptedPassword = passwordEncoder.encode(newPassword);
                user.setPassword(encryptedPassword);
                this.clearUserCaches(user);
                log.debug("Changed password for User: {}", user);
            });
    }

    @Transactional(readOnly = true)
    public Page<AdminUserDTO> getAllManagedUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(AdminUserDTO::new);
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> getAllPublicUsers(Pageable pageable) {
        return userRepository.findAllByIdNotNullAndActivatedIsTrue(pageable).map(UserDTO::new);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthoritiesByLogin(String login) {
        return userRepository.findOneWithAuthoritiesByLogin(login);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities() {
        return SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneWithAuthoritiesByLogin);
    }

    /**
     * Not activated users should be automatically deleted after 3 days.
     * <p>
     * This is scheduled to get fired everyday, at 01:00 (am).
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void removeNotActivatedUsers() {
        userRepository
            .findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(Instant.now().minus(3, ChronoUnit.DAYS))
            .forEach(user -> {
                log.debug("Deleting not activated user {}", user.getLogin());
                userRepository.delete(user);
                this.clearUserCaches(user);
            });
    }

    /**
     * Gets a list of all the authorities.
     * @return a list of all the authorities.
     */
    @Transactional(readOnly = true)
    public List<String> getAuthorities() {
        return authorityRepository.findAll().stream().map(Authority::getName).collect(Collectors.toList());
    }

    private void clearUserCaches(User user) {
        Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE)).evict(user.getLogin());
        if (user.getEmail() != null) {
            Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_EMAIL_CACHE)).evict(user.getEmail());
        }
    }

    public ManagedUserVM getManagedUserVMFromUser(User user) {
        ManagedUserVM managedUserVM = new ManagedUserVM();
        managedUserVM.setUserId(user.getId());
        managedUserVM.setFirstName(user.getFirstName());
        managedUserVM.setLastName(user.getLastName());
        managedUserVM.setLogin(user.getLogin());
        managedUserVM.setActivated(user.isActivated());
        managedUserVM.setAuthorities(user.getAuthorities().stream().map(Authority::getName).collect(Collectors.toSet()));
        managedUserVM.setCreatedBy(user.getCreatedBy());
        managedUserVM.setCreatedDate(user.getCreatedDate());
        managedUserVM.setEmail(user.getEmail());
        managedUserVM.setImageUrl(user.getImageUrl());
        managedUserVM.setLangKey(user.getLangKey());
        managedUserVM.setPassword(user.getPassword());
        managedUserVM.setLastModifiedBy(user.getLastModifiedBy());
        managedUserVM.setLastModifiedDate(user.getLastModifiedDate());
        Optional<Profile> profile = profileService.getProfileByUserID(user.getId());
        if (profile.isPresent()) {
            managedUserVM.setLifeStatus(profile.get().getLifeStatus());
            managedUserVM.setClothes(profile.get().getClothes());
            managedUserVM.setAccessesForRelatives(profile.get().getAccessesForRelatives());
            managedUserVM.setBurialMethod(profile.get().getBurialMethod());
            managedUserVM.setFarewellLetter(profile.get().getFarewellLetter());
            managedUserVM.setFlowers(profile.get().getFlowers());
            managedUserVM.setIfFlowers(profile.get().getIfFlowers());
            managedUserVM.setGraveInscription(profile.get().getGraveInscription());
            managedUserVM.setGuests(profile.get().getGuests());
            managedUserVM.setPurchasedPlace(profile.get().getPurchasedPlace());
            managedUserVM.setIsPurchasedOther(profile.get().getIfPurchasedOther());
            managedUserVM.setNotInvited(profile.get().getNotInvited());
            managedUserVM.setObituary(profile.get().getObituary());
            managedUserVM.setOther(profile.get().getOther());
            managedUserVM.setPhone(profile.get().getPhone());
            managedUserVM.setOpenCoffin(profile.get().isOpenCoffin());
            managedUserVM.setPrefix(profile.get().getPrefix());
            managedUserVM.setPhoto(profile.get().getPhoto());
            managedUserVM.setPlaceOfCeremony(profile.get().getPlaceOfCeremony());
            managedUserVM.setSpeech(profile.get().getSpeech());
            managedUserVM.setSpotify(profile.get().getSpotify());
            managedUserVM.setTestament(profile.get().getTestament());
            managedUserVM.setVideoSpeech(profile.get().getVideoSpeech());
            managedUserVM.setLevelOfForm(profile.get().getLevelOfForm());
            managedUserVM.setEditsLeft(profile.get().getEditsLeft());
            managedUserVM.setIfGraveInscription(profile.get().getIfGraveInscription());
            managedUserVM.setBurialPlace(profile.get().getBurialPlace());
            managedUserVM.setBurialType(profile.get().getBurialType());
            managedUserVM.setIfPhotoGrave(profile.get().getIfPhotoGrave());
            managedUserVM.setMusicType(profile.get().getMusicType());
            managedUserVM.setIfGuests(profile.get().getIfGuests());
            managedUserVM.setIfOther4(profile.get().getIfOther4());
        }
        return managedUserVM;
    }
}
