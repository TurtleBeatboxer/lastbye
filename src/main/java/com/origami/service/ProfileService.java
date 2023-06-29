package com.origami.service;

import com.origami.domain.MembershipLevel;
import com.origami.domain.Profile;
import com.origami.domain.User;
import com.origami.repository.ProfileRepository;
import com.origami.repository.UserRepository;
import com.origami.service.dto.PublicProfileDTO;
import com.origami.web.rest.vm.ManagedUserVM;
import java.util.Optional;
import javax.transaction.Transactional;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    private final QRService qrService;

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    public ProfileService(QRService qrService, ProfileRepository profileRepository, UserRepository userRepository) {
        this.qrService = qrService;
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
    }

    public boolean isQRValid(String qrCode) {
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

    public boolean isPublicLinkValid(String publicLink) {
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

    public void createNewProfile(ManagedUserVM userDTO) {
        Profile newProfile = new Profile();

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
            if (profileOptional.get().getEditsLeft() > 0) {
                Profile profile = profileOptional.get();
                userDTO.setEditsLeft(profile.getEditsLeft() - 1);
                profile.setEditsLeft(profile.getEditsLeft() - 1);
                profile.setSpeech(userDTO.getSpeech());
                profile.placeOfCeremony(userDTO.getPlaceOfCeremony());
                /* profile.setFlowers(userDTO.isFlowers());*/
                profile.setIfFlowers(userDTO.getIfFlowers());
                /*   profile.setPurchasedPlace(userDTO.isPurchasedPlace());*/
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
        }
    }

    public void registerFirstForm(ManagedUserVM userDTO) {
        Optional<Profile> profileOptional = getProfileByUserID(userDTO.getUserId());
        if (profileOptional.isPresent()) {
            Profile profile = profileOptional.get();
            profile.setBurialMethod(userDTO.getBurialMethod());
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
            profile.setFinishedEditing(true);
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
        }
    }

    public PublicProfileDTO getPublicDataByProfileLink(String publicProfileLink) {
        System.out.println("test");
        System.out.println(publicProfileLink);
        Optional<Profile> optionalProfile = profileRepository.findProfileByPublicProfileLink(publicProfileLink);
        PublicProfileDTO publicProfileDTO = new PublicProfileDTO();
        if (optionalProfile.isPresent()) {
            Profile profile = optionalProfile.get();

            publicProfileDTO.setFlowers(profile.getFlowers());
            if (profile.getFlowers()) {
                publicProfileDTO.setIfFlowers(profile.getIfFlowers());
            }
            publicProfileDTO.setPurchasedPlace(profile.getPurchasedPlace());
            if (profile.getPurchasedPlace()) {
                publicProfileDTO.setIsPurchasedOther(profile.getIfPurchasedOther());
            }
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
}
