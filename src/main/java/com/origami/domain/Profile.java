package com.origami.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Profile.
 */
@Entity
@Table(name = "profile")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Profile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "surname")
    private String surname;

    @Column(name = "phone")
    private Long phone;

    @Column(name = "prefix")
    private String prefix;

    @Column(name = "burial_method")
    private String burialMethod;

    @Column(name = "clothes")
    private String clothes;

    @Column(name = "place_of_ceremony")
    private String placeOfCeremony;

    @Column(name = "photo")
    private String photo;

    @Column(name = "grave_inscription")
    private String graveInscription;

    @Column(name = "spotify")
    private String spotify;

    @Column(name = "guests")
    private String guests;

    @Column(name = "not_invited")
    private String notInvited;

    @Column(name = "obituary")
    private String obituary;

    @Column(name = "purchased_place")
    private Boolean purchasedPlace;

    @Column(name = "if_purchased_other")
    private String ifPurchasedOther;

    @Column(name = "flowers")
    private Boolean flowers;

    @Column(name = "if_flowers")
    private String ifFlowers;

    @Column(name = "farewell_letter")
    private String farewellLetter;

    @Column(name = "speech")
    private String speech;

    @Column(name = "video_speech")
    private String videoSpeech;

    @Column(name = "testament")
    private String testament;

    @Column(name = "accesses_for_relatives")
    private String accessesForRelatives;

    @Column(name = "other")
    private String other;

    @Column(name = "code_qr")
    private String codeQR;

    @Column(name = "public_profile_link")
    private String publicProfileLink;

    @Column(name = "membership_level")
    private MembershipLevel membershipLevel;

    @Column(name = "finished_editing")
    private Boolean finishedEditing;

    @Column(name = "level_of_form")
    private Long levelOfForm;

    @Column(name = "edits_left")
    private Long editsLeft;

    @Column(name = "is_open_coffin")
    private Boolean isOpenCoffin;

    public Boolean isOpenCoffin() {
        return isOpenCoffin;
    }

    public void setOpenCoffin(Boolean openCoffin) {
        isOpenCoffin = openCoffin;
    }

    /*    @Column(name ="isAlive")
    private boolean isAlive;*/

    public Long getLevelOfForm() {
        return levelOfForm;
    }

    public void setLevelOfForm(Long levelOfForm) {
        this.levelOfForm = levelOfForm;
    }

    public Boolean isFinishedEditing() {
        return finishedEditing;
    }

    public void setFinishedEditing(Boolean finishedEditing) {
        this.finishedEditing = finishedEditing;
    }

    public Long getEditsLeft() {
        return editsLeft;
    }

    public void setEditsLeft(Long editsLeft) {
        this.editsLeft = editsLeft;
    }

    @OneToMany(mappedBy = "profile")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "profile" }, allowSetters = true)
    private Set<Personality> personalities = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Profile id(Long id) {
        this.setId(id);
        return this;
    }

    public String getPublicProfileLink() {
        return publicProfileLink;
    }

    public void setPublicProfileLink(String publicProfileLink) {
        this.publicProfileLink = publicProfileLink;
    }

    public MembershipLevel getMembershipLevel() {
        return membershipLevel;
    }

    public void setMembershipLevel(MembershipLevel membershipLevel) {
        this.membershipLevel = membershipLevel;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSurname() {
        return this.surname;
    }

    public Profile surname(String surname) {
        this.setSurname(surname);
        return this;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Long getPhone() {
        return this.phone;
    }

    public Profile phone(Long phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public Profile prefix(String prefix) {
        this.setPrefix(prefix);
        return this;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getBurialMethod() {
        return this.burialMethod;
    }

    public Profile burialMethod(String burialMethod) {
        this.setBurialMethod(burialMethod);
        return this;
    }

    public void setBurialMethod(String burialMethod) {
        this.burialMethod = burialMethod;
    }

    public String getClothes() {
        return this.clothes;
    }

    public Profile clothes(String clothes) {
        this.setClothes(clothes);
        return this;
    }

    public void setClothes(String clothes) {
        this.clothes = clothes;
    }

    public String getPlaceOfCeremony() {
        return this.placeOfCeremony;
    }

    public Profile placeOfCeremony(String placeOfCeremony) {
        this.setPlaceOfCeremony(placeOfCeremony);
        return this;
    }

    public void setPlaceOfCeremony(String placeOfCeremony) {
        this.placeOfCeremony = placeOfCeremony;
    }

    public String getPhoto() {
        return this.photo;
    }

    public Profile photo(String photo) {
        this.setPhoto(photo);
        return this;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getGraveInscription() {
        return this.graveInscription;
    }

    public Profile graveInscription(String graveInscription) {
        this.setGraveInscription(graveInscription);
        return this;
    }

    public void setGraveInscription(String graveInscription) {
        this.graveInscription = graveInscription;
    }

    public String getSpotify() {
        return this.spotify;
    }

    public Profile spotify(String spotify) {
        this.setSpotify(spotify);
        return this;
    }

    public void setSpotify(String spotify) {
        this.spotify = spotify;
    }

    public String getGuests() {
        return this.guests;
    }

    public Profile guests(String guests) {
        this.setGuests(guests);
        return this;
    }

    public void setGuests(String guests) {
        this.guests = guests;
    }

    public String getNotInvited() {
        return this.notInvited;
    }

    public Profile notInvited(String notInvited) {
        this.setNotInvited(notInvited);
        return this;
    }

    public void setNotInvited(String notInvited) {
        this.notInvited = notInvited;
    }

    public String getObituary() {
        return this.obituary;
    }

    public Profile obituary(String obituary) {
        this.setObituary(obituary);
        return this;
    }

    public void setObituary(String obituary) {
        this.obituary = obituary;
    }

    public Boolean getPurchasedPlace() {
        return this.purchasedPlace;
    }

    public Profile purchasedPlace(Boolean purchasedPlace) {
        this.setPurchasedPlace(purchasedPlace);
        return this;
    }

    public void setPurchasedPlace(Boolean purchasedPlace) {
        this.purchasedPlace = purchasedPlace;
    }

    public String getIfPurchasedOther() {
        return this.ifPurchasedOther;
    }

    public Profile ifPurchasedOther(String ifPurchasedOther) {
        this.setIfPurchasedOther(ifPurchasedOther);
        return this;
    }

    public void setIfPurchasedOther(String ifPurchasedOther) {
        this.ifPurchasedOther = ifPurchasedOther;
    }

    public Boolean getFlowers() {
        return this.flowers;
    }

    public Profile flowers(Boolean flowers) {
        this.setFlowers(flowers);
        return this;
    }

    public void setFlowers(Boolean flowers) {
        this.flowers = flowers;
    }

    public String getIfFlowers() {
        return this.ifFlowers;
    }

    public Profile ifFlowers(String ifFlowers) {
        this.setIfFlowers(ifFlowers);
        return this;
    }

    public void setIfFlowers(String ifFlowers) {
        this.ifFlowers = ifFlowers;
    }

    public String getFarewellLetter() {
        return this.farewellLetter;
    }

    public Profile farewellLetter(String farewellLetter) {
        this.setFarewellLetter(farewellLetter);
        return this;
    }

    public void setFarewellLetter(String farewellLetter) {
        this.farewellLetter = farewellLetter;
    }

    public String getSpeech() {
        return this.speech;
    }

    public Profile speech(String speech) {
        this.setSpeech(speech);
        return this;
    }

    public void setSpeech(String speech) {
        this.speech = speech;
    }

    public String getVideoSpeech() {
        return this.videoSpeech;
    }

    public Profile videoSpeech(String videoSpeech) {
        this.setVideoSpeech(videoSpeech);
        return this;
    }

    public void setVideoSpeech(String videoSpeech) {
        this.videoSpeech = videoSpeech;
    }

    public String getTestament() {
        return this.testament;
    }

    public Profile testament(String testament) {
        this.setTestament(testament);
        return this;
    }

    public void setTestament(String testament) {
        this.testament = testament;
    }

    public String getAccessesForRelatives() {
        return this.accessesForRelatives;
    }

    public Profile accessesForRelatives(String accessesForRelatives) {
        this.setAccessesForRelatives(accessesForRelatives);
        return this;
    }

    public void setAccessesForRelatives(String accessesForRelatives) {
        this.accessesForRelatives = accessesForRelatives;
    }

    public String getOther() {
        return this.other;
    }

    public Profile other(String other) {
        this.setOther(other);
        return this;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getCodeQR() {
        return this.codeQR;
    }

    public Profile codeQR(String codeQR) {
        this.setCodeQR(codeQR);
        return this;
    }

    public void setCodeQR(String codeQR) {
        this.codeQR = codeQR;
    }

    public Set<Personality> getPersonalities() {
        return this.personalities;
    }

    public void setPersonalities(Set<Personality> personalities) {
        if (this.personalities != null) {
            this.personalities.forEach(i -> i.setProfile(null));
        }
        if (personalities != null) {
            personalities.forEach(i -> i.setProfile(this));
        }
        this.personalities = personalities;
    }

    public Profile personalities(Set<Personality> personalities) {
        this.setPersonalities(personalities);
        return this;
    }

    public Profile addPersonality(Personality personality) {
        this.personalities.add(personality);
        personality.setProfile(this);
        return this;
    }

    public Profile removePersonality(Personality personality) {
        this.personalities.remove(personality);
        personality.setProfile(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Profile)) {
            return false;
        }
        return id != null && id.equals(((Profile) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Profile{" +
            "id=" + getId() +
            ", surname='" + getSurname() + "'" +
            ", phone=" + getPhone() +
            ", prefix='" + getPrefix() + "'" +
            ", burialMethod='" + getBurialMethod() + "'" +
            ", clothes='" + getClothes() + "'" +
            ", placeOfCeremony='" + getPlaceOfCeremony() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", graveInscription='" + getGraveInscription() + "'" +
            ", spotify='" + getSpotify() + "'" +
            ", guests='" + getGuests() + "'" +
            ", notInvited='" + getNotInvited() + "'" +
            ", obituary='" + getObituary() + "'" +
            ", purchasedPlace='" + getPurchasedPlace() + "'" +
            ", ifPurchasedOther='" + getIfPurchasedOther() + "'" +
            ", flowers='" + getFlowers() + "'" +
            ", ifFlowers='" + getIfFlowers() + "'" +
            ", farewellLetter='" + getFarewellLetter() + "'" +
            ", speech='" + getSpeech() + "'" +
            ", videoSpeech='" + getVideoSpeech() + "'" +
            ", testament='" + getTestament() + "'" +
            ", accessesForRelatives='" + getAccessesForRelatives() + "'" +
            ", other='" + getOther() + "'" +
            ", codeQR='" + getCodeQR() + "'" +
            "}";
    }
}
