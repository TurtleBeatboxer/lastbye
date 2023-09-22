package com.origami.service;

import com.origami.domain.LifeStatus;
import com.origami.domain.MembershipLevel;
import com.origami.domain.Profile;
import com.origami.domain.User;
import com.origami.repository.ProfileRepository;
import com.origami.repository.UserRepository;
import com.origami.service.dto.LifeStatusChangeDTO;
import com.origami.service.dto.PublicProfileDTO;
import com.origami.web.rest.vm.ManagedUserVM;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    private final MailService mailService;
    private final QRService qrService;
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ProfileService(
        MailService mailService,
        QRService qrService,
        ProfileRepository profileRepository,
        UserRepository userRepository,
        PasswordEncoder passwordEncoder
    ) {
        this.mailService = mailService;
        this.qrService = qrService;
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /*    @PostConstruct*/
    public void startQRCountdown(LifeStatusChangeDTO lifeStatusChangeDTO) {
        /*        LifeStatusChangeDTO lifeStatusChangeDTO = new LifeStatusChangeDTO();*/

        /*       Profile profile1 = profileRepository.findAll().get(10);
        lifeStatusChangeDTO.setCodeQR(profile1.getCodeQR());
        lifeStatusChangeDTO.setLifeStatus(LifeStatus.UNKNOWN);
*/
        Optional<Profile> profileOptional = profileRepository.findOneByCodeQR(lifeStatusChangeDTO.getCodeQR());
        if (profileOptional.isPresent()) {
            Profile profile = profileOptional.get();
            lifeStatusChangeDTO.setEmailAddress(prepareMailForDTO(profile.getUserId()));

            profile.setLifeStatus(LifeStatus.UNKNOWN);
            String lifeLink = qrService.getAlphaNumericString(15);
            while (!isLifeLinkValid(lifeLink)) {
                lifeLink = qrService.getAlphaNumericString(15);
            }

            profile.setLifeLink(lifeLink);
            profileRepository.save(profile);

            qRCountdown(lifeStatusChangeDTO);
        }
    }

    public void qRCountdown(LifeStatusChangeDTO lifeStatusChangeDTO) {
        // Inner method must have this local variable declared as final, but then again we want to decrement it
        final Integer[] i = { 11 };
        Timer timer = new Timer();
        int initialDelay = 0; //delay startu
        int cooldown = 1000/*7200000*/; //2h w ms - period miedzy wywolaniami "run"

        /*ManagedUserVM userVM = new ManagedUserVM();*/

        timer.scheduleAtFixedRate(
            new TimerTask() {
                public void run() {
                    Optional<Profile> profileOptional = profileRepository.findOneByCodeQR(lifeStatusChangeDTO.getCodeQR());
                    if (profileOptional.isPresent() && profileOptional.get().getLifeStatus().equals(LifeStatus.UNKNOWN)) {
                        if (i[0] == 0) {
                            System.out.println("final mail");
                            Profile profile = profileOptional.get();
                            lifeStatusChangeDTO.setTempPassword(updateUserPasswordTemp(profile.getUserId()));
                            profile.setLifeStatus(LifeStatus.DEAD);
                            profileRepository.save(profile);
                            updateUserStatusDead(lifeStatusChangeDTO);
                            timer.cancel();
                            return;
                        }
                        mailService.sendRevivalMail(lifeStatusChangeDTO);
                        System.out.println(i[0]);
                        i[0]--;
                    } else {
                        timer.cancel();
                        return;
                    }
                }
            },
            initialDelay,
            cooldown
        );
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

    private void updateUserStatusDead(LifeStatusChangeDTO lifeStatusChangeDTO) {
        mailService.sendAfterDeadTemporaryPassword(lifeStatusChangeDTO);
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
        newProfile.setEditsLeft(2L);
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
        return profile.isFinishedEditing();
    }

    public Boolean updateProfile(ManagedUserVM userDTO) {
        Optional<Profile> profileOptional = getProfileByUserID(userDTO.getUserId());
        if (profileOptional.isPresent()) {
            if (profileOptional.get().getEditsLeft() > 0 && profileOptional.get().getLifeStatus().equals(LifeStatus.ALIVE)) {
                Profile profile = profileOptional.get();
                userDTO.setEditsLeft(profile.getEditsLeft() - 1);
                profile.setEditsLeft(profile.getEditsLeft() - 1);
                profile.setSpeech(userDTO.getSpeech());
                profile.placeOfCeremony(userDTO.getPlaceOfCeremony());
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
            profile.setOpenCoffin(userDTO.isOpenCoffin());
            profile.setClothes(userDTO.getClothes());
            profile.setLevelOfForm(2L);
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
            profile.setGuests(userDTO.getGuests());
            profile.setNotInvited(userDTO.getNotInvited());
            profile.setPlaceOfCeremony(userDTO.getPlaceOfCeremony());
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

    public void registerFourthForm(ManagedUserVM userDTO) {
        Optional<Profile> profileOptional = getProfileByUserID(userDTO.getUserId());
        if (profileOptional.isPresent()) {
            Profile profile = profileOptional.get();

            //relative z 3 fieldami kurwa to string?

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
            publicProfileDTO.setFlowers(profile.getFlowers());
            publicProfileDTO.setIfFlowers(profile.getIfFlowers());
            publicProfileDTO.setPurchasedPlace(profile.getPurchasedPlace());
            publicProfileDTO.setIsPurchasedOther(profile.getIfPurchasedOther());
            publicProfileDTO.setClothes(profile.getClothes());
            publicProfileDTO.setTestament(profile.getTestament());
            publicProfileDTO.setVideoSpeech(profile.getVideoSpeech());
            publicProfileDTO.setSpotify(profile.getSpotify());
            publicProfileDTO.setPlaceOfCeremony(profile.getPlaceOfCeremony());
            publicProfileDTO.setSpeech(profile.getSpeech());
            publicProfileDTO.setPhoto(profile.getPhoto());
            publicProfileDTO.setOther(profile.getOther());
            publicProfileDTO.setObituary(profile.getObituary());
            publicProfileDTO.setGuests(profile.getGuests());
            publicProfileDTO.setNotInvited(profile.getNotInvited());
            publicProfileDTO.setGraveInscription(profile.getGraveInscription());
            publicProfileDTO.setFarewellLetter(profile.getFarewellLetter());
            publicProfileDTO.setBurialMethod(profile.getBurialMethod());
            publicProfileDTO.setOpenCoffin(profile.isOpenCoffin());
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
}
