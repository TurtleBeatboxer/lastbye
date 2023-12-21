package com.origami.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.origami.domain.LifeStatus;
import com.origami.domain.MembershipLevel;
import com.origami.domain.Profile;
import com.origami.domain.User;
import com.origami.repository.ProfileRepository;
import com.origami.repository.UserRepository;
import com.origami.service.dto.*;
import com.origami.web.rest.vm.ManagedUserVM;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    private final MailService mailService;
    private final QRService qrService;
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final FileService fileService;

    public ProfileService(
        MailService mailService,
        QRService qrService,
        ProfileRepository profileRepository,
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        FileService fileService
    ) {
        this.mailService = mailService;
        this.qrService = qrService;
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.fileService = fileService;
    }

    public HttpStatus prepareDataForQrCountdown(QRStartProcessDTO qrStartProcessDTO) throws JsonProcessingException {
        Optional<Profile> profileOptional = profileRepository.findOneByCodeQR(qrStartProcessDTO.getCodeQR());
        if (profileOptional.isEmpty()) return HttpStatus.BAD_REQUEST;
        Profile profile = profileOptional.get();
        if (profile.getLifeStatus().equals(LifeStatus.ALIVE)) {
            List<String> relativesEmails = getRelativesEmailsFromJsonString(profile.getClosestRelatives());
            if (
                profile.getQuestionAnswer().equals(qrStartProcessDTO.getAnswer()) &&
                relativesEmails.contains(qrStartProcessDTO.getEmailAddress())
            ) {
                LifeStatusChangeDTO lifeStatusChangeDTO = new LifeStatusChangeDTO();
                lifeStatusChangeDTO.setCodeQR(qrStartProcessDTO.getCodeQR());
                lifeStatusChangeDTO.setFriendAddress(qrStartProcessDTO.getEmailAddress());
                startQRCountdown(lifeStatusChangeDTO);
                return HttpStatus.ACCEPTED;
            }
        }
        return HttpStatus.BAD_REQUEST;
    }

    public void startQRCountdown(LifeStatusChangeDTO lifeStatusChangeDTO) {
        Optional<Profile> profileOptional = profileRepository.findOneByCodeQR(lifeStatusChangeDTO.getCodeQR());
        if (profileOptional.isPresent()) {
            Profile profile = profileOptional.get();
            profile.setLifeStatus(LifeStatus.UNKNOWN);
            String lifeLink = qrService.getAlphaNumericString(15);
            while (!isLifeLinkValid(lifeLink)) {
                lifeLink = qrService.getAlphaNumericString(15);
            }
            profile.setFriendMail(lifeStatusChangeDTO.getFriendAddress());
            profile.setHoursLeft(24);
            profile.setLifeLink(lifeLink);
            //Can add first mail here for instant feedback
            profileRepository.save(profile);
        }
    }

    @Scheduled(cron = "0 0 */2 * * *")
    public void qRCountdown() {
        List<Profile> profiles = profileRepository.findAllByLifeStatus(LifeStatus.UNKNOWN);
        for (Profile profile : profiles) {
            outer:if (profile.getLifeStatus().equals(LifeStatus.UNKNOWN)) {
                inner:if (profile.getHoursLeft() == 0) {
                    DeathMailDTO deathMailDTO = new DeathMailDTO();
                    deathMailDTO.setUserEmail(prepareMailForDTO(profile.getUserId()));
                    deathMailDTO.setTempPassword(updateUserPasswordTemp(profile.getUserId()));
                    deathMailDTO.setFriendEmail(profile.getFriendMail());
                    profile.setLifeStatus(LifeStatus.DEAD);
                    profileRepository.save(profile);
                    System.out.println("final mail");
                    sendDeathMail(deathMailDTO);
                    break outer;
                }
                RevivalMailDTO revivalMailDTO = new RevivalMailDTO();
                revivalMailDTO.setLifeLink(profile.getLifeLink());
                revivalMailDTO.setUserEmail(prepareMailForDTO(profile.getUserId()));
                profile.setHoursLeft(profile.getHoursLeft() - 2);
                profileRepository.save(profile);
                mailService.sendRevivalMail(revivalMailDTO);
            }
        }
    }

    private String updateUserPasswordTemp(Long userId) {
        Optional<User> userOptional = userRepository.findOneById(userId);
        String tempPassword = qrService.getAlphaNumericString(8);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setPassword(passwordEncoder.encode(tempPassword));
        }
        return tempPassword;
    }

    private void sendDeathMail(DeathMailDTO deathMailDTO) {
        mailService.sendAfterDeadTemporaryPassword(deathMailDTO);
    }

    public HttpStatus updateUserStatusAlive(LifeStatusChangeDTO lifeStatusChangeDTO) {
        String lifeLink = lifeStatusChangeDTO.getLifeLink();
        if (!lifeLink.isEmpty() && !lifeLink.isBlank() && !lifeLink.equals("")) {
            Optional<Profile> profileOptional = profileRepository.findOneByLifeLink(lifeLink);
            if (profileOptional.isPresent()) {
                Profile profile = profileOptional.get();
                if (profile.getLifeStatus().equals(LifeStatus.UNKNOWN)) {
                    profile.setLifeStatus(LifeStatus.ALIVE);
                    profile.setLifeLink(null);
                    profile.setHoursLeft(null);
                    profileRepository.save(profile);
                    mailService.sendWereGladYoureBack(prepareMailForDTO(profile.getUserId()));
                    return HttpStatus.OK;
                }
            }
        }
        return HttpStatus.BAD_REQUEST;
    }

    public void createNewProfile(ManagedUserVM userDTO) {
        Profile newProfile = new Profile();
        newProfile.setLifeStatus(LifeStatus.ALIVE);
        newProfile.setUserId(userDTO.getUserId());
        newProfile.setMembershipLevel(MembershipLevel.STANDARD);
        newProfile.setEditsLeft(4L);
        newProfile.setLevelOfForm(0L);
        newProfile.setFinishedEditing(false);
        profileRepository.save(newProfile);
    }

    public Optional<Profile> getProfileByUserID(Long userId) {
        return profileRepository.findOneByUserId(userId);
    }

    public Boolean isEditingFinished(ManagedUserVM userDTO) {
        Optional<Profile> profileOptional = getProfileByUserID(userDTO.getUserId());
        Profile profile = profileOptional.get();
        return profile.getFinishedEditing();
    }

    public Boolean updateProfile(ManagedUserVM userDTO) {
        Optional<Profile> profileOptional = getProfileByUserID(userDTO.getUserId());
        if (profileOptional.isPresent()) {
            if (profileOptional.get().getEditsLeft() > 0 && profileOptional.get().getLifeStatus().equals(LifeStatus.ALIVE)) {
                Profile profile = profileOptional.get();
                profile.setSpeech(userDTO.getSpeech());
                profile.setPlaceOfCeremony(userDTO.getPlaceOfCeremony());
                profile.setFlowers(userDTO.isFlowers());
                profile.setIfFlowers(userDTO.getIfFlowers());
                profile.setPurchasedPlace(userDTO.isPurchasedPlace());
                profile.setIfPurchasedOther(userDTO.getIsPurchasedOther());
                profile.setOther(userDTO.getOther());
                profile.setNotInvited(userDTO.getNotInvited());
                profile.setVideoSpeech(userDTO.getVideoSpeech());
                profile.setPhone(userDTO.getPhone());
                profile.setPrefix(userDTO.getPrefix());
                profile.setGuests(userDTO.getGuests());
                profile.setTestament(userDTO.getTestament());
                profile.setObituary(userDTO.getObituary());
                profile.setSpotify(userDTO.getSpotify());
                profile.setAccessesForRelatives(userDTO.getAccessesForRelatives());
                profile.setGraveInscription(userDTO.getGraveInscription());
                profile.setPhoto(userDTO.getPhoto());
                profile.setClothes(userDTO.getClothes());
                profile.setBurialMethod(userDTO.getBurialMethod());
                profile.setFarewellLetter(userDTO.getFarewellLetter());
                profile.setEditsLeft(profile.getEditsLeft() - 1);
                profileRepository.save(profile);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public void registerZeroForm(ManagedUserVM userDTO) {
        Optional<Profile> profileOptional = getProfileByUserID(userDTO.getUserId());
        if (profileOptional.isPresent()) {
            Profile profile = profileOptional.get();
            profile.setPrefix(userDTO.getPrefix());
            profile.setPhone(userDTO.getPhone());

            profile.setLevelOfForm(1L);
            profileRepository.save(profile);
        }
    }

    public void registerFirstForm(ManagedUserVM userDTO) {
        Optional<Profile> profileOptional = getProfileByUserID(userDTO.getUserId());
        if (profileOptional.isPresent()) {
            Profile profile = profileOptional.get();
            profile.setBurialMethod(userDTO.getBurialMethod());
            System.out.println(userDTO.getIsPurchasedOther());
            profile.setIfPurchasedOther(userDTO.getIsPurchasedOther());
            profile.setGraveInscription(userDTO.getGraveInscription());
            profile.setIsOpenCoffin((userDTO.isOpenCoffin()));
            profile.setClothes(userDTO.getClothes());
            profile.setBurialPlace(userDTO.getBurialPlace());
            profile.setBurialType(userDTO.getBurialType());
            profile.setLevelOfForm(2L);
            profile.setIfGraveInscription(userDTO.getIfGraveInscription());
            profile.setIfPhotoGrave(userDTO.getIfPhotoGrave());
            profileRepository.save(profile);
        }
    }

    public void registerSecondForm(ManagedUserVM userDTO) {
        Optional<Profile> profileOptional = getProfileByUserID(userDTO.getUserId());
        if (profileOptional.isPresent()) {
            Profile profile = profileOptional.get();
            profile.setFlowers(userDTO.isFlowers());
            profile.setIfFlowers(userDTO.getIfFlowers());
            profile.setObituary(userDTO.getObituary());
            profile.setSpotify(userDTO.getSpotify());
            profile.setMusicType(userDTO.getMusicType());
            profile.setGuests(userDTO.getGuests());
            profile.setNotInvited(userDTO.getNotInvited());
            profile.setBurialType(userDTO.getBurialType());
            profile.setIfGraveInscription(userDTO.getIfGraveInscription());
            profile.setPlaceOfCeremony(userDTO.getPlaceOfCeremony());
            profile.setIfGuests(userDTO.getIfGuests());
            profile.setLevelOfForm(3L);
            profileRepository.save(profile);
        }
    }

    public void registerThirdForm(ManagedUserVM userDTO) {
        Optional<Profile> profileOptional = getProfileByUserID(userDTO.getUserId());
        if (profileOptional.isPresent()) {
            Profile profile = profileOptional.get();
            profile.setFarewellLetter(userDTO.getFarewellLetter());
            profile.setVideoSpeech(userDTO.getVideoSpeech());
            profile.setTestament(userDTO.getTestament());
            profile.setOther(userDTO.getOther());

            profile.setLevelOfForm(4L);
            profileRepository.save(profile);
        }
    }

    public void registerFourthForm(ManagedUserVM userDTO) throws JsonProcessingException {
        Optional<Profile> profileOptional = getProfileByUserID(userDTO.getUserId());
        if (profileOptional.isPresent()) {
            Profile profile = profileOptional.get();

            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(userDTO.getRelativeDTOs());
            profile.setClosestRelatives(jsonString);

            String qrCode = qrService.getAlphaNumericString(10);
            while (!isQRValid(qrCode)) {
                qrCode = qrService.getAlphaNumericString(10);
            }
            profile.setCodeQR(qrCode);
            String publicLink = qrService.getAlphaNumericString(5);
            while (!isPublicLinkValid(publicLink)) {
                publicLink = qrService.getAlphaNumericString(5);
            }
            profile.setPublicProfileLink(publicLink);
            profileRepository.save(profile);
            profile.setFinishedEditing(true);
        }
    }

    public PublicProfileDTO getPublicDataByProfileLink(String publicProfileLink) {
        System.out.println(publicProfileLink);
        Optional<Profile> optionalProfile = profileRepository.findProfileByPublicProfileLink(publicProfileLink);
        PublicProfileDTO publicProfileDTO = new PublicProfileDTO();
        if (optionalProfile.isPresent()) {
            Profile profile = optionalProfile.get();
            publicProfileDTO = new PublicProfileDTO(profile);
            Optional<User> userOptional = userRepository.findOneById(profile.getUserId());
            if (userOptional.isPresent()) {
                publicProfileDTO.setFirstName(userOptional.get().getFirstName());
                publicProfileDTO.setLastName(userOptional.get().getLastName());
            }
        }
        return publicProfileDTO;
    }

    private String prepareMailForDTO(Long userID) {
        Optional<User> userOptional = userRepository.findOneById(userID);
        if (userOptional.isPresent()) return userOptional.get().getEmail();
        return null;
    }

    private boolean isQRValid(String qrCode) {
        int size = profileRepository.findAll().size() - 1;
        while (size >= 0) {
            String string = profileRepository.findAll().get(size).getCodeQR();
            if (string != null) {
                if (string.equals(qrCode)) {
                    return false;
                }
            }
            size -= 1;
        }
        return true;
    }

    private boolean isPublicLinkValid(String publicLink) {
        int size = profileRepository.findAll().size() - 1;
        while (size >= 0) {
            String string = profileRepository.findAll().get(size).getPublicProfileLink();
            if (string != null) {
                if (string.equals(publicLink)) {
                    return false;
                }
            }
            size -= 1;
        }
        return true;
    }

    private boolean isLifeLinkValid(String lifeLink) {
        int size = profileRepository.findAll().size() - 1;
        while (size >= 0) {
            String string = profileRepository.findAll().get(size).getLifeLink();
            if (string != null) {
                if (string.equals(lifeLink)) {
                    return false;
                }
            }
            size -= 1;
        }
        return true;
    }

    private List<String> getRelativesEmailsFromJsonString(String jsonString) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<RelativeDTO> listOfRelatives = mapper.readValue(jsonString, new TypeReference<List<RelativeDTO>>() {});
        List<String> emails = new ArrayList<>();
        for (RelativeDTO relativeDTO : listOfRelatives) {
            emails.add(relativeDTO.getEmail());
        }
        return emails;
    }
}
