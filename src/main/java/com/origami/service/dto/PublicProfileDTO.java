package com.origami.service.dto;

import com.origami.domain.Profile;
import lombok.Data;

@Data
public class PublicProfileDTO {

    private String firstName;
    private String lastName;
    private String burialMethod;
    private String clothes;
    private String placeOfCeremony;
    private String photo;
    private String graveInscription;
    private String spotify;
    private String guests;
    private String notInvited;
    private String obituary;
    private Boolean purchasedPlace;
    private String isPurchasedOther;
    private Boolean flowers;
    private String ifFlowers;
    private String farewellLetter;
    private String speech;
    private String videoSpeech;
    private String testament;
    private String other;
    private Boolean isOpenCoffin;

    public PublicProfileDTO(Profile profile) {
        this.flowers = profile.getFlowers();
        this.ifFlowers = profile.getIfFlowers();
        this.purchasedPlace = profile.getPurchasedPlace();
        this.isPurchasedOther = profile.getIfPurchasedOther();
        this.clothes = profile.getClothes();
        this.testament = profile.getTestament();
        this.videoSpeech = profile.getVideoSpeech();
        this.spotify = profile.getSpotify();
        this.placeOfCeremony = profile.getPlaceOfCeremony();
        this.speech = profile.getSpeech();
        this.photo = profile.getPhoto();
        this.other = profile.getOther();
        this.obituary = profile.getObituary();
        this.guests = profile.getGuests();
        this.notInvited = profile.getNotInvited();
        this.graveInscription = profile.getGraveInscription();
        this.farewellLetter = profile.getFarewellLetter();
        this.burialMethod = profile.getBurialMethod();
        this.isOpenCoffin = profile.getIsOpenCoffin();
    }

    public PublicProfileDTO() {}
}
