package com.origami.service;

import com.origami.domain.MembershipLevel;
import com.origami.domain.Profile;
import com.origami.repository.ProfileRepository;
import com.origami.web.rest.vm.ManagedUserVM;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    private final QRService qrService;

    private final ProfileRepository profileRepository;

    public ProfileService(QRService qrService, ProfileRepository profileRepository) {
        this.qrService = qrService;
        this.profileRepository = profileRepository;
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
        profileRepository.save(newProfile);
    }

    public Optional<Profile> getProfileByUserID(Long userId) {
        return profileRepository.findOneByUserId(userId);
    }

    public void updateProfile(ManagedUserVM userDTO) {
        Optional<Profile> profile = getProfileByUserID(userDTO.getUserId());
        if (profile.isPresent()) {
            profile.get().setSpeech(userDTO.getSpeech());
            profile.get().placeOfCeremony(userDTO.getPlaceOfCeremony());
            profile.get().setFlowers(userDTO.isFlowers());
            if (profile.get().getFlowers()) {
                profile.get().setIfFlowers(userDTO.getIfFlowers());
            }
            profile.get().setPurchasedPlace(userDTO.isPurchasedPlace());
            if (profile.get().getPurchasedPlace()) {
                profile.get().setIfPurchasedOther(userDTO.getIsPurchasedOther());
            }
            profile.get().setOther(userDTO.getOther());
            profile.get().setNotInvited(userDTO.getNotInvited());
            profile.get().setVideoSpeech(userDTO.getVideoSpeech());
            profile.get().setPhone(userDTO.getPhone());
            profile.get().setPrefix(userDTO.getPrefix());
            profile.get().setGuests(userDTO.getGuests());
            profile.get().setTestament(userDTO.getTestament());
            profile.get().setObituary(userDTO.getObituary());
            profile.get().setSpotify(userDTO.getSpotify());
            profile.get().setAccessesForRelatives(userDTO.getAccessesForRelatives());
            profile.get().setGraveInscription(userDTO.getGraveInscription());
            profile.get().setPhoto(userDTO.getPhoto());
            profile.get().setClothes(userDTO.getClothes());
            profile.get().setBurialMethod(userDTO.getBurialMethod());
            profile.get().setFarewellLetter(userDTO.getFarewellLetter());
        }
    }
}
