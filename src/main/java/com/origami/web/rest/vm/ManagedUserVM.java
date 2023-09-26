package com.origami.web.rest.vm;

import com.origami.domain.LifeStatus;
import com.origami.domain.dtos.RelativeDTO;
import com.origami.service.dto.AdminUserDTO;
import java.util.ArrayList;
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
    private Boolean purchasedPlace;
    private String isPurchasedOther;
    private Boolean flowers;
    private String ifFlowers;
    private String farewellLetter;
    private String speech;
    private String videoSpeech;
    private String testament;
    private String accessesForRelatives;
    private String other;
    private Long levelOfForm;
    private Boolean isOpenCoffin;
    // Not obtainable from frontend needed for clarity of code later on when its invoked
    private Long userId;
    private Long editsLeft;
    private LifeStatus lifeStatus;
    private String burialPlace;
    private String farewellReader;

    private String musicType;
    private String burialType;
    private Boolean ifGraveInscription;
    private Boolean ifGuests;
    private Boolean ifOther4;
    private Boolean ifPhotoGrave;

    private ArrayList<RelativeDTO> relativeDTOs;

    public ArrayList<RelativeDTO> getRelativeDTOs() {
        return relativeDTOs;
    }

    public void setRelativeDTOs(ArrayList<RelativeDTO> relativeDTOs) {
        this.relativeDTOs = relativeDTOs;
    }

    public Boolean getIfPhotoGrave() {
        return ifPhotoGrave;
    }

    public void setIfPhotoGrave(Boolean ifPhotoGrave) {
        this.ifPhotoGrave = ifPhotoGrave;
    }

    public Boolean getOpenCoffin() {
        return isOpenCoffin;
    }

    public String getBurialType() {
        return burialType;
    }

    public void setBurialType(String burialType) {
        this.burialType = burialType;
    }

    public Boolean getIfGraveInscription() {
        return ifGraveInscription;
    }

    public void setIfGraveInscription(Boolean ifGraveInscription) {
        this.ifGraveInscription = ifGraveInscription;
    }

    public Boolean getIfGuests() {
        return ifGuests;
    }

    public void setIfGuests(Boolean ifGuests) {
        this.ifGuests = ifGuests;
    }

    public Boolean getIfOther4() {
        return ifOther4;
    }

    public void setIfOther4(Boolean ifOther4) {
        this.ifOther4 = ifOther4;
    }

    public String getMusicType() {
        return musicType;
    }

    public void setMusicType(String musicType) {
        this.musicType = musicType;
    }

    public LifeStatus getLifeStatus() {
        return lifeStatus;
    }

    public void setLifeStatus(LifeStatus lifeStatus) {
        this.lifeStatus = lifeStatus;
    }

    public ManagedUserVM() {
        // Empty constructor needed for Jackson.
    }

    public Boolean getPurchasedPlace() {
        return purchasedPlace;
    }

    public String getBurialPlace() {
        return burialPlace;
    }

    public void setBurialPlace(String burialPlace) {
        this.burialPlace = burialPlace;
    }

    public String getFarewellReader() {
        return farewellReader;
    }

    public void setFarewellReader(String farewellReader) {
        this.farewellReader = farewellReader;
    }

    public Boolean isOpenCoffin() {
        return isOpenCoffin;
    }

    public Long getLevelOfForm() {
        return levelOfForm;
    }

    public Long getEditsLeft() {
        return editsLeft;
    }

    public void setEditsLeft(Long editsLeft) {
        this.editsLeft = editsLeft;
    }

    public void setLevelOfForm(Long levelOfForm) {
        this.levelOfForm = levelOfForm;
    }

    public void setOpenCoffin(Boolean openCoffin) {
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
