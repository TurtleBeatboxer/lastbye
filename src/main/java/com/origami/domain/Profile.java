package com.origami.domain;

import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Profile.
 */
@Entity
@Table(name = "profile")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
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

    @Column(name = "life_status")
    private LifeStatus lifeStatus;

    @Column(name = "life_link")
    private String lifeLink;

    @Column(name = "friends_email")
    private String friendsEmail;

    @Column(name = "question")
    private String question;

    @Column(name = "question_answer")
    private String questionAnswer;

    @Column(name = "burial_place")
    private String burialPlace;

    @Column(name = "farewell_reader")
    private String farewellReader;

    @Column(name = "music_type")
    private String musicType;

    @Column(name = "burial_type")
    private String burialType;

    @Column(name = "if_grave_inscription")
    private Boolean ifGraveInscription;

    @Column(name = "if_guests")
    private Boolean ifGuests;

    @Column(name = "if_other_4")
    private Boolean ifOther4;

    @Column(name = "if_photo_grave")
    private Boolean ifPhotoGrave;

    @Column(name = "closest_relatives")
    private String closestRelatives;

    @Column(name = "hours_left")
    private Integer hoursLeft;

    @Column(name = "friend_mail")
    private String friendMail;
}
