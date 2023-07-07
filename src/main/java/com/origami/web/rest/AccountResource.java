package com.origami.web.rest;

import com.origami.domain.LifeStatus;
import com.origami.domain.Profile;
import com.origami.domain.User;
import com.origami.repository.ProfileRepository;
import com.origami.repository.UserRepository;
import com.origami.security.SecurityUtils;
import com.origami.service.MailService;
import com.origami.service.ProfileService;
import com.origami.service.UserService;
import com.origami.service.dto.LifeStatusChangeDTO;
import com.origami.service.dto.PasswordChangeDTO;
import com.origami.service.dto.PublicProfileDTO;
import com.origami.web.rest.errors.*;
import com.origami.web.rest.vm.KeyAndPasswordVM;
import com.origami.web.rest.vm.ManagedUserVM;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api")
public class AccountResource {

    private static class AccountResourceException extends RuntimeException {

        private AccountResourceException(String message) {
            super(message);
        }
    }

    private final Logger log = LoggerFactory.getLogger(AccountResource.class);

    private final UserRepository userRepository;

    private final ProfileRepository profileRepository;

    private final UserService userService;

    private final MailService mailService;

    private final ProfileService profileService;

    public AccountResource(
        UserRepository userRepository,
        ProfileRepository profileRepository,
        UserService userService,
        MailService mailService,
        ProfileService profileService
    ) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.userService = userService;
        this.mailService = mailService;
        this.profileService = profileService;
    }

    /**
     * {@code POST  /register} : register the user.
     *
     * @param managedUserVM the managed user View Model.
     * @throws InvalidPasswordException {@code 400 (Bad Request)} if the password is incorrect.
     * @throws EmailAlreadyUsedException {@code 400 (Bad Request)} if the email is already used.
     * @throws LoginAlreadyUsedException {@code 400 (Bad Request)} if the login is already used.
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerAccount(@Valid @RequestBody ManagedUserVM managedUserVM) {
        if (isPasswordLengthInvalid(managedUserVM.getPassword())) {
            throw new InvalidPasswordException();
        }
        User user = userService.registerUser(managedUserVM, managedUserVM.getPassword());
        mailService.sendActivationEmail(user);
    }

    @PostMapping("/register/form")
    public HttpStatus registerAccountFormOne(@Valid @RequestBody ManagedUserVM userDTO) {
        setUserIdIfUserWithThatLoginExists(userDTO);
        if (!profileService.isEditingFinished(userDTO)) {
            if (userDTO.getLevelOfForm() == 0L) {
                userService.registerZeroForm(userDTO);
            }
            if (userDTO.getLevelOfForm() == 1L) {
                profileService.registerFirstForm(userDTO);
            }
            if (userDTO.getLevelOfForm() == 2L) {
                profileService.registerSecondForm(userDTO);
            }
            if (userDTO.getLevelOfForm() == 3L) {
                profileService.registerThirdForm(userDTO);
            }
            return HttpStatus.OK;
        } else {
            return HttpStatus.FORBIDDEN;
        }
    }

    @PostMapping("/qr")
    public HttpStatus getProfileFromQRCode(@Valid @RequestBody String codeQR) {
        Optional<Profile> profileOptional = profileRepository.findOneByCodeQR(codeQR);
        if (profileOptional.isEmpty()) return HttpStatus.BAD_REQUEST;
        if (profileOptional.get().getLifeStatus().equals(LifeStatus.UNKNOWN)) return HttpStatus.BAD_REQUEST;

        LifeStatusChangeDTO lifeStatusChangeDTO = new LifeStatusChangeDTO();
        lifeStatusChangeDTO.setCodeQR(codeQR);
        lifeStatusChangeDTO.setLifeStatus(LifeStatus.UNKNOWN);
        profileService.startQRCountdown(lifeStatusChangeDTO);
        return HttpStatus.ACCEPTED;
    }

    @PostMapping("/profile/get/data")
    @ResponseStatus(HttpStatus.OK)
    public PublicProfileDTO getUserData(@Valid @RequestBody String publicId) {
        return profileService.getPublicDataByProfileLink(publicId);
    }

    private void setUserIdIfUserWithThatLoginExists(ManagedUserVM userDTO) {
        String userLogin = SecurityUtils
            .getCurrentUserLogin()
            .orElseThrow(() -> new AccountResourceException("Current user login not found"));
        Optional<User> userOptional = userRepository.findOneByLogin(userLogin);
        if (userOptional.isEmpty()) {
            throw new AccountResourceException("User could not be found");
        }
        Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(userDTO.getEmail());
        existingUser.ifPresent(user -> userDTO.setUserId(user.getId()));
        Optional<Profile> profile = profileService.getProfileByUserID(userDTO.getUserId());
        profile.ifPresent(value -> userDTO.setLevelOfForm(value.getLevelOfForm()));
    }

    /**
     * {@code GET  /activate} : activate the registered user.
     *
     * @param key the activation key.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if the user couldn't be activated.
     */
    @GetMapping("/activate")
    public void activateAccount(@RequestParam(value = "key") String key) {
        Optional<User> user = userService.activateRegistration(key);
        if (user.isEmpty()) {
            throw new AccountResourceException("No user was found for this activation key");
        }
    }

    /**
     * {@code GET  /authenticate} : check if the user is authenticated, and return its login.
     *
     * @param request the HTTP request.
     * @return the login if the user is authenticated.
     */
    @GetMapping("/authenticate")
    public String isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }

    /**
     * {@code GET  /account} : get the current user.
     *
     * @return the current user.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if the user couldn't be returned.
     */
    @GetMapping("/account")
    public ManagedUserVM getAccount() {
        Optional<User> userOptional = userService.getUserWithAuthorities();
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return userService.getManagedUserVMFromUser(user);
        } else {
            throw new AccountResourceException("User could not be found");
        }
    }

    /**
     * {@code POST  /account} : update the current user information.
     *
     * @param userDTO the current user information.
     * @throws EmailAlreadyUsedException {@code 400 (Bad Request)} if the email is already used.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if the user login wasn't found.
     */
    @PostMapping("/account")
    public HttpStatus saveAccount(@Valid @RequestBody ManagedUserVM userDTO) {
        String userLogin = SecurityUtils
            .getCurrentUserLogin()
            .orElseThrow(() -> new AccountResourceException("Current user login not found"));
        Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(userDTO.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getLogin().equalsIgnoreCase(userLogin))) {
            throw new EmailAlreadyUsedException();
        }
        Optional<User> user = userRepository.findOneByLogin(userLogin);
        if (!user.isPresent()) {
            throw new AccountResourceException("User could not be found");
        }
        return userService.updateUser(userDTO);
    }

    /**
     * {@code POST  /account/change-password} : changes the current user's password.
     *
     * @param passwordChangeDto current and new password.
     * @throws InvalidPasswordException {@code 400 (Bad Request)} if the new password is incorrect.
     */
    @PostMapping(path = "/account/change-password")
    public void changePassword(@RequestBody PasswordChangeDTO passwordChangeDto) {
        if (isPasswordLengthInvalid(passwordChangeDto.getNewPassword())) {
            throw new InvalidPasswordException();
        }
        userService.changePassword(passwordChangeDto.getCurrentPassword(), passwordChangeDto.getNewPassword());
    }

    /**
     * {@code POST   /account/reset-password/init} : Send an email to reset the password of the user.
     *
     * @param mail the mail of the user.
     */
    @PostMapping(path = "/account/reset-password/init")
    public void requestPasswordReset(@RequestBody String mail) {
        Optional<User> user = userService.requestPasswordReset(mail);
        if (user.isPresent()) {
            mailService.sendPasswordResetMail(user.get());
        } else {
            // Pretend the request has been successful to prevent checking which emails really exist
            // but log that an invalid attempt has been made
            log.warn("Password reset requested for non existing mail");
        }
    }

    /**
     * {@code POST   /account/reset-password/finish} : Finish to reset the password of the user.
     *
     * @param keyAndPassword the generated key and the new password.
     * @throws InvalidPasswordException {@code 400 (Bad Request)} if the password is incorrect.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if the password could not be reset.
     */
    @PostMapping(path = "/account/reset-password/finish")
    public void finishPasswordReset(@RequestBody KeyAndPasswordVM keyAndPassword) {
        if (isPasswordLengthInvalid(keyAndPassword.getNewPassword())) {
            throw new InvalidPasswordException();
        }
        Optional<User> user = userService.completePasswordReset(keyAndPassword.getNewPassword(), keyAndPassword.getKey());

        if (!user.isPresent()) {
            throw new AccountResourceException("No user was found for this reset key");
        }
    }

    private static boolean isPasswordLengthInvalid(String password) {
        return (
            StringUtils.isEmpty(password) ||
            password.length() < ManagedUserVM.PASSWORD_MIN_LENGTH ||
            password.length() > ManagedUserVM.PASSWORD_MAX_LENGTH
        );
    }
}
