package com.origami.service;

import com.origami.domain.MembershipLevel;
import com.origami.domain.Profile;
import com.origami.repository.ProfileRepository;
import com.origami.web.rest.vm.ManagedUserVM;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    private final QRService qrService;

    private final ProfileRepository profileRepository;

    public ProfileService(QRService qrService, ProfileRepository profileRepository) {
        this.qrService = qrService;
        this.profileRepository = profileRepository;
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
        newProfile.setCodeQR("https://lastbye.com/QRCode/" + qrService.getAlphaNumericString(10));
        newProfile.setPublicProfileLink("https://lastbye.com/account/profile" + qrService.getAlphaNumericString(5));
        newProfile.setUserId(userDTO.getUserId());
        newProfile.setMembershipLevel(MembershipLevel.STANDARD);
        profileRepository.save(newProfile);
    }
}
