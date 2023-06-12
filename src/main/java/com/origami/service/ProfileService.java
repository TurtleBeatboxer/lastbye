package com.origami.service;

import com.origami.domain.MembershipLevel;
import com.origami.domain.Profile;
import com.origami.domain.User;
import com.origami.repository.ProfileRepository;
import com.origami.repository.UserRepository;
import com.origami.web.rest.vm.ManagedUserVM;
import org.apache.commons.logging.Log;
import org.h2.expression.function.StringFunction1;
import org.hibernate.loader.plan.exec.process.spi.ReturnReader;
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
}
