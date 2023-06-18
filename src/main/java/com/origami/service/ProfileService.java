package com.origami.service;

import com.origami.domain.MembershipLevel;
import com.origami.domain.Profile;
import com.origami.repository.ProfileRepository;
import com.origami.repository.UserRepository;
import com.origami.web.rest.vm.ManagedUserVM;
import java.util.Optional;
import net.bytebuddy.implementation.bytecode.Throw;
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
        newProfile.setPhone(userDTO.getPhone());
        newProfile.setPrefix(userDTO.getPrefix());
        newProfile.setBurialMethod(userDTO.getBurialMethod());
        newProfile.setClothes(userDTO.getClothes());
        newProfile.setPlaceOfCeremony(userDTO.getPlaceOfCeremony());
        newProfile.setPhoto(userDTO.getPhoto());
        newProfile.setGraveInscription(userDTO.getGraveInscription());
        newProfile.setSpotify(userDTO.getSpotify());
        newProfile.setGuests(userDTO.getGuests());
        newProfile.setNotInvited(userDTO.getNotInvited());
        newProfile.setObituary(userDTO.getObituary());
        newProfile.setPurchasedPlace(userDTO.isPurchasedPlace());
        if (newProfile.getPurchasedPlace()) {
            newProfile.setIfPurchasedOther(userDTO.getIsPurchasedOther());
        }
        newProfile.setFlowers(userDTO.isFlowers());
        if (newProfile.getFlowers()) {
            newProfile.setIfFlowers(userDTO.getIfFlowers());
        }
        newProfile.setFarewellLetter(userDTO.getFarewellLetter());
        newProfile.setSpeech(userDTO.getSpeech());
        newProfile.setVideoSpeech(userDTO.getVideoSpeech());
        newProfile.setTestament(userDTO.getTestament());
        newProfile.setAccessesForRelatives(userDTO.getAccessesForRelatives());
        newProfile.setOther(userDTO.getOther());

        String qrCode = qrService.getAlphaNumericString(10);
        while (!isQRValid(qrCode)) {
            qrCode = qrService.getAlphaNumericString(10);
        }
        newProfile.setCodeQR(qrCode);

        String publicLink = qrService.getAlphaNumericString(5);
        while (!isPublicLinkValid(publicLink)) {
            publicLink = qrService.getAlphaNumericString(5);
        }
        newProfile.setPublicProfileLink(publicLink);

        newProfile.setUserId(userDTO.getUserId());
        newProfile.setMembershipLevel(MembershipLevel.STANDARD);
        newProfile.setEditsLeft(2L);
        newProfile.setOpenCoffin(false);
        newProfile.setFinishedEditing(true);
        profileRepository.save(newProfile);
    }

    public Optional<Profile> getProfileByUserID(Long userId) {
        return profileRepository.findOneByUserId(userId);
    }

    public void updateProfile(ManagedUserVM userDTO) {
        Optional<Profile> profileOptional = getProfileByUserID(userDTO.getUserId());
        if (profileOptional.isPresent()) {
            if (profileOptional.get().getEditsLeft() > 0) {
                Profile profile = profileOptional.get();
                profile.setEditsLeft(profile.getEditsLeft() - 1);
                profile.setSpeech(userDTO.getSpeech());
                profile.placeOfCeremony(userDTO.getPlaceOfCeremony());
                profile.setFlowers(userDTO.isFlowers());
                if (profile.getFlowers()) {
                    profile.setIfFlowers(userDTO.getIfFlowers());
                }
                profile.setPurchasedPlace(userDTO.isPurchasedPlace());
                if (profile.getPurchasedPlace()) {
                    profile.setIfPurchasedOther(userDTO.getIsPurchasedOther());
                }
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
            } else {
                //Tutaj coś w przyszlosci wyrzucimy
            }
        } else {
            //tutaj raczej nic nie powinno sie wydarzyc, ale chuj wie
        }
    }

    public void registerFirstForm(ManagedUserVM userDTO) {
        Optional<Profile> profileOptional = getProfileByUserID(userDTO.getUserId());
        if (profileOptional.isPresent()) {
            Profile profile = profileOptional.get();
            profile.setBurialMethod(userDTO.getBurialMethod());
            profile.setGraveInscription(userDTO.getGraveInscription());
            profile.setOpenCoffin(userDTO.isOpenCoffin());
            if (userDTO.isOpenCoffin()) {
                profile.setClothes(userDTO.getClothes());
            }
            profileRepository.save(profile);
        }
    }

    public void registerSecondForm(ManagedUserVM userDTO) {
        Optional<Profile> profileOptional = getProfileByUserID(userDTO.getUserId());
        if (profileOptional.isPresent()) {
            Profile profile = profileOptional.get();
            profile.setFlowers(userDTO.isFlowers());
            if (userDTO.isFlowers()) {
                profile.setIfFlowers(userDTO.getIfFlowers());
            }
            profile.setObituary(userDTO.getObituary());
            profile.setSpotify(userDTO.getSpotify());
            profile.setGuests(userDTO.getGuests());
            profile.setNotInvited(userDTO.getNotInvited());
            profile.setPlaceOfCeremony(userDTO.getPlaceOfCeremony());
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
            profileRepository.save(profile);
        }
    }
}
