package com.origami.web.rest.vm;

import com.origami.domain.MembershipLevel;
import com.origami.service.dto.AdminUserDTO;
import javax.validation.constraints.Size;

/**
 * View Model extending the AdminUserDTO, which is meant to be used in the user management UI.
 */
public class ManagedUserVM extends AdminUserDTO {

    public static final int PASSWORD_MIN_LENGTH = 4;

    public static final int PASSWORD_MAX_LENGTH = 100;

    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;

    private String surname;
    private Long phone;
    private String prefix;
    private String burialMethod;
    private String clothes;
    private String placeOfCeremony;
    private String photo;
    private String graveInscription;
    private String spotify;
    private String guests;
    private String notInvited;
    private String obituary;
    private boolean purchasedPlace;
    private String isPurchasedOther;
    private boolean flowers;
    private String ifFlowers;
    private String farewellLetter;
    private String speech;
    private String videoSpeech;
    private String testament;
    private String accessesForRelatives;
    private String other;
    private boolean isOpenCoffin;
    // Not obtainable from frontend needed for clarity of code later on when its invoked
    private Long userId;

    public ManagedUserVM() {
        // Empty constructor needed for Jackson.
    }

    public boolean isOpenCoffin() {
        return isOpenCoffin;
    }

    public void setOpenCoffin(boolean openCoffin) {
        isOpenCoffin = openCoffin;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ManagedUserVM{" + super.toString() + "} ";
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
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

    public boolean isPurchasedPlace() {
        return purchasedPlace;
    }

    public void setPurchasedPlace(boolean purchasedPlace) {
        this.purchasedPlace = purchasedPlace;
    }

    public String getIsPurchasedOther() {
        return isPurchasedOther;
    }

    public void setIsPurchasedOther(String isPurchasedOther) {
        this.isPurchasedOther = isPurchasedOther;
    }

    public boolean isFlowers() {
        return flowers;
    }

    public void setFlowers(boolean flowers) {
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

    public String getAccessesForRelatives() {
        return accessesForRelatives;
    }

    public void setAccessesForRelatives(String accessesForRelatives) {
        this.accessesForRelatives = accessesForRelatives;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}
