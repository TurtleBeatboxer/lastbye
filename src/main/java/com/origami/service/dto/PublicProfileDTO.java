package com.origami.service.dto;

import com.origami.domain.Profile;

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBurialMethod() {
        return burialMethod;
    }

    public void setBurialMethod(String burialMethod) {
        this.burialMethod = burialMethod;
    }

    public String getClothes() {
        return clothes;
    }

    public void setClothes(String clothes) {
        this.clothes = clothes;
    }

    public String getPlaceOfCeremony() {
        return placeOfCeremony;
    }

    public void setPlaceOfCeremony(String placeOfCeremony) {
        this.placeOfCeremony = placeOfCeremony;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getGraveInscription() {
        return graveInscription;
    }

    public void setGraveInscription(String graveInscription) {
        this.graveInscription = graveInscription;
    }

    public String getSpotify() {
        return spotify;
    }

    public void setSpotify(String spotify) {
        this.spotify = spotify;
    }

    public String getGuests() {
        return guests;
    }

    public void setGuests(String guests) {
        this.guests = guests;
    }

    public String getNotInvited() {
        return notInvited;
    }

    public void setNotInvited(String notInvited) {
        this.notInvited = notInvited;
    }

    public String getObituary() {
        return obituary;
    }

    public void setObituary(String obituary) {
        this.obituary = obituary;
    }

    public Boolean isPurchasedPlace() {
        return purchasedPlace;
    }

    public void setPurchasedPlace(Boolean purchasedPlace) {
        this.purchasedPlace = purchasedPlace;
    }

    public String getIsPurchasedOther() {
        return isPurchasedOther;
    }

    public void setIsPurchasedOther(String isPurchasedOther) {
        this.isPurchasedOther = isPurchasedOther;
    }

    public Boolean isFlowers() {
        return flowers;
    }

    public void setFlowers(Boolean flowers) {
        this.flowers = flowers;
    }

    public String getIfFlowers() {
        return ifFlowers;
    }

    public void setIfFlowers(String ifFlowers) {
        this.ifFlowers = ifFlowers;
    }

    public String getFarewellLetter() {
        return farewellLetter;
    }

    public void setFarewellLetter(String farewellLetter) {
        this.farewellLetter = farewellLetter;
    }

    public String getSpeech() {
        return speech;
    }

    public void setSpeech(String speech) {
        this.speech = speech;
    }

    public String getVideoSpeech() {
        return videoSpeech;
    }

    public void setVideoSpeech(String videoSpeech) {
        this.videoSpeech = videoSpeech;
    }

    public String getTestament() {
        return testament;
    }

    public void setTestament(String testament) {
        this.testament = testament;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public Boolean isOpenCoffin() {
        return isOpenCoffin;
    }

    public void setOpenCoffin(Boolean openCoffin) {
        isOpenCoffin = openCoffin;
    }

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
        this.isOpenCoffin = profile.isOpenCoffin();
    }

    public PublicProfileDTO() {}
}
