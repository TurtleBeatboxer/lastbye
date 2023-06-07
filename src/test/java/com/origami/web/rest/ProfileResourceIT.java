package com.origami.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.origami.IntegrationTest;
import com.origami.domain.Profile;
import com.origami.repository.ProfileRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ProfileResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProfileResourceIT {

    private static final String DEFAULT_SURNAME = "AAAAAAAAAA";
    private static final String UPDATED_SURNAME = "BBBBBBBBBB";

    private static final Long DEFAULT_PHONE = 1L;
    private static final Long UPDATED_PHONE = 2L;

    private static final String DEFAULT_PREFIX = "AAAAAAAAAA";
    private static final String UPDATED_PREFIX = "BBBBBBBBBB";

    private static final String DEFAULT_BURIAL_METHOD = "AAAAAAAAAA";
    private static final String UPDATED_BURIAL_METHOD = "BBBBBBBBBB";

    private static final String DEFAULT_CLOTHES = "AAAAAAAAAA";
    private static final String UPDATED_CLOTHES = "BBBBBBBBBB";

    private static final String DEFAULT_PLACE_OF_CEREMONY = "AAAAAAAAAA";
    private static final String UPDATED_PLACE_OF_CEREMONY = "BBBBBBBBBB";

    private static final String DEFAULT_PHOTO = "AAAAAAAAAA";
    private static final String UPDATED_PHOTO = "BBBBBBBBBB";

    private static final String DEFAULT_GRAVE_INSCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_GRAVE_INSCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_SPOTIFY = "AAAAAAAAAA";
    private static final String UPDATED_SPOTIFY = "BBBBBBBBBB";

    private static final String DEFAULT_GUESTS = "AAAAAAAAAA";
    private static final String UPDATED_GUESTS = "BBBBBBBBBB";

    private static final String DEFAULT_NOT_INVITED = "AAAAAAAAAA";
    private static final String UPDATED_NOT_INVITED = "BBBBBBBBBB";

    private static final String DEFAULT_OBITUARY = "AAAAAAAAAA";
    private static final String UPDATED_OBITUARY = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PURCHASED_PLACE = false;
    private static final Boolean UPDATED_PURCHASED_PLACE = true;

    private static final String DEFAULT_IF_PURCHASED_OTHER = "AAAAAAAAAA";
    private static final String UPDATED_IF_PURCHASED_OTHER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_FLOWERS = false;
    private static final Boolean UPDATED_FLOWERS = true;

    private static final String DEFAULT_IF_FLOWERS = "AAAAAAAAAA";
    private static final String UPDATED_IF_FLOWERS = "BBBBBBBBBB";

    private static final String DEFAULT_FAREWELL_LETTER = "AAAAAAAAAA";
    private static final String UPDATED_FAREWELL_LETTER = "BBBBBBBBBB";

    private static final String DEFAULT_SPEECH = "AAAAAAAAAA";
    private static final String UPDATED_SPEECH = "BBBBBBBBBB";

    private static final String DEFAULT_VIDEO_SPEECH = "AAAAAAAAAA";
    private static final String UPDATED_VIDEO_SPEECH = "BBBBBBBBBB";

    private static final String DEFAULT_TESTAMENT = "AAAAAAAAAA";
    private static final String UPDATED_TESTAMENT = "BBBBBBBBBB";

    private static final String DEFAULT_ACCESSES_FOR_RELATIVES = "AAAAAAAAAA";
    private static final String UPDATED_ACCESSES_FOR_RELATIVES = "BBBBBBBBBB";

    private static final String DEFAULT_OTHER = "AAAAAAAAAA";
    private static final String UPDATED_OTHER = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_QR = "AAAAAAAAAA";
    private static final String UPDATED_CODE_QR = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/profiles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProfileMockMvc;

    private Profile profile;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Profile createEntity(EntityManager em) {
        Profile profile = new Profile()
            .surname(DEFAULT_SURNAME)
            .phone(DEFAULT_PHONE)
            .prefix(DEFAULT_PREFIX)
            .burialMethod(DEFAULT_BURIAL_METHOD)
            .clothes(DEFAULT_CLOTHES)
            .placeOfCeremony(DEFAULT_PLACE_OF_CEREMONY)
            .photo(DEFAULT_PHOTO)
            .graveInscription(DEFAULT_GRAVE_INSCRIPTION)
            .spotify(DEFAULT_SPOTIFY)
            .guests(DEFAULT_GUESTS)
            .notInvited(DEFAULT_NOT_INVITED)
            .obituary(DEFAULT_OBITUARY)
            .purchasedPlace(DEFAULT_PURCHASED_PLACE)
            .ifPurchasedOther(DEFAULT_IF_PURCHASED_OTHER)
            .flowers(DEFAULT_FLOWERS)
            .ifFlowers(DEFAULT_IF_FLOWERS)
            .farewellLetter(DEFAULT_FAREWELL_LETTER)
            .speech(DEFAULT_SPEECH)
            .videoSpeech(DEFAULT_VIDEO_SPEECH)
            .testament(DEFAULT_TESTAMENT)
            .accessesForRelatives(DEFAULT_ACCESSES_FOR_RELATIVES)
            .other(DEFAULT_OTHER)
            .codeQR(DEFAULT_CODE_QR);
        return profile;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Profile createUpdatedEntity(EntityManager em) {
        Profile profile = new Profile()
            .surname(UPDATED_SURNAME)
            .phone(UPDATED_PHONE)
            .prefix(UPDATED_PREFIX)
            .burialMethod(UPDATED_BURIAL_METHOD)
            .clothes(UPDATED_CLOTHES)
            .placeOfCeremony(UPDATED_PLACE_OF_CEREMONY)
            .photo(UPDATED_PHOTO)
            .graveInscription(UPDATED_GRAVE_INSCRIPTION)
            .spotify(UPDATED_SPOTIFY)
            .guests(UPDATED_GUESTS)
            .notInvited(UPDATED_NOT_INVITED)
            .obituary(UPDATED_OBITUARY)
            .purchasedPlace(UPDATED_PURCHASED_PLACE)
            .ifPurchasedOther(UPDATED_IF_PURCHASED_OTHER)
            .flowers(UPDATED_FLOWERS)
            .ifFlowers(UPDATED_IF_FLOWERS)
            .farewellLetter(UPDATED_FAREWELL_LETTER)
            .speech(UPDATED_SPEECH)
            .videoSpeech(UPDATED_VIDEO_SPEECH)
            .testament(UPDATED_TESTAMENT)
            .accessesForRelatives(UPDATED_ACCESSES_FOR_RELATIVES)
            .other(UPDATED_OTHER)
            .codeQR(UPDATED_CODE_QR);
        return profile;
    }

    @BeforeEach
    public void initTest() {
        profile = createEntity(em);
    }

    @Test
    @Transactional
    void createProfile() throws Exception {
        int databaseSizeBeforeCreate = profileRepository.findAll().size();
        // Create the Profile
        restProfileMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(profile)))
            .andExpect(status().isCreated());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeCreate + 1);
        Profile testProfile = profileList.get(profileList.size() - 1);
        assertThat(testProfile.getSurname()).isEqualTo(DEFAULT_SURNAME);
        assertThat(testProfile.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testProfile.getPrefix()).isEqualTo(DEFAULT_PREFIX);
        assertThat(testProfile.getBurialMethod()).isEqualTo(DEFAULT_BURIAL_METHOD);
        assertThat(testProfile.getClothes()).isEqualTo(DEFAULT_CLOTHES);
        assertThat(testProfile.getPlaceOfCeremony()).isEqualTo(DEFAULT_PLACE_OF_CEREMONY);
        assertThat(testProfile.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testProfile.getGraveInscription()).isEqualTo(DEFAULT_GRAVE_INSCRIPTION);
        assertThat(testProfile.getSpotify()).isEqualTo(DEFAULT_SPOTIFY);
        assertThat(testProfile.getGuests()).isEqualTo(DEFAULT_GUESTS);
        assertThat(testProfile.getNotInvited()).isEqualTo(DEFAULT_NOT_INVITED);
        assertThat(testProfile.getObituary()).isEqualTo(DEFAULT_OBITUARY);
        assertThat(testProfile.getPurchasedPlace()).isEqualTo(DEFAULT_PURCHASED_PLACE);
        assertThat(testProfile.getIfPurchasedOther()).isEqualTo(DEFAULT_IF_PURCHASED_OTHER);
        assertThat(testProfile.getFlowers()).isEqualTo(DEFAULT_FLOWERS);
        assertThat(testProfile.getIfFlowers()).isEqualTo(DEFAULT_IF_FLOWERS);
        assertThat(testProfile.getFarewellLetter()).isEqualTo(DEFAULT_FAREWELL_LETTER);
        assertThat(testProfile.getSpeech()).isEqualTo(DEFAULT_SPEECH);
        assertThat(testProfile.getVideoSpeech()).isEqualTo(DEFAULT_VIDEO_SPEECH);
        assertThat(testProfile.getTestament()).isEqualTo(DEFAULT_TESTAMENT);
        assertThat(testProfile.getAccessesForRelatives()).isEqualTo(DEFAULT_ACCESSES_FOR_RELATIVES);
        assertThat(testProfile.getOther()).isEqualTo(DEFAULT_OTHER);
        assertThat(testProfile.getCodeQR()).isEqualTo(DEFAULT_CODE_QR);
    }

    @Test
    @Transactional
    void createProfileWithExistingId() throws Exception {
        // Create the Profile with an existing ID
        profile.setId(1L);

        int databaseSizeBeforeCreate = profileRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfileMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(profile)))
            .andExpect(status().isBadRequest());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProfiles() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList
        restProfileMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profile.getId().intValue())))
            .andExpect(jsonPath("$.[*].surname").value(hasItem(DEFAULT_SURNAME)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.intValue())))
            .andExpect(jsonPath("$.[*].prefix").value(hasItem(DEFAULT_PREFIX)))
            .andExpect(jsonPath("$.[*].burialMethod").value(hasItem(DEFAULT_BURIAL_METHOD)))
            .andExpect(jsonPath("$.[*].clothes").value(hasItem(DEFAULT_CLOTHES)))
            .andExpect(jsonPath("$.[*].placeOfCeremony").value(hasItem(DEFAULT_PLACE_OF_CEREMONY)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(DEFAULT_PHOTO)))
            .andExpect(jsonPath("$.[*].graveInscription").value(hasItem(DEFAULT_GRAVE_INSCRIPTION)))
            .andExpect(jsonPath("$.[*].spotify").value(hasItem(DEFAULT_SPOTIFY)))
            .andExpect(jsonPath("$.[*].guests").value(hasItem(DEFAULT_GUESTS)))
            .andExpect(jsonPath("$.[*].notInvited").value(hasItem(DEFAULT_NOT_INVITED)))
            .andExpect(jsonPath("$.[*].obituary").value(hasItem(DEFAULT_OBITUARY)))
            .andExpect(jsonPath("$.[*].purchasedPlace").value(hasItem(DEFAULT_PURCHASED_PLACE.booleanValue())))
            .andExpect(jsonPath("$.[*].ifPurchasedOther").value(hasItem(DEFAULT_IF_PURCHASED_OTHER)))
            .andExpect(jsonPath("$.[*].flowers").value(hasItem(DEFAULT_FLOWERS.booleanValue())))
            .andExpect(jsonPath("$.[*].ifFlowers").value(hasItem(DEFAULT_IF_FLOWERS)))
            .andExpect(jsonPath("$.[*].farewellLetter").value(hasItem(DEFAULT_FAREWELL_LETTER)))
            .andExpect(jsonPath("$.[*].speech").value(hasItem(DEFAULT_SPEECH)))
            .andExpect(jsonPath("$.[*].videoSpeech").value(hasItem(DEFAULT_VIDEO_SPEECH)))
            .andExpect(jsonPath("$.[*].testament").value(hasItem(DEFAULT_TESTAMENT)))
            .andExpect(jsonPath("$.[*].accessesForRelatives").value(hasItem(DEFAULT_ACCESSES_FOR_RELATIVES)))
            .andExpect(jsonPath("$.[*].other").value(hasItem(DEFAULT_OTHER)))
            .andExpect(jsonPath("$.[*].codeQR").value(hasItem(DEFAULT_CODE_QR)));
    }

    @Test
    @Transactional
    void getProfile() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get the profile
        restProfileMockMvc
            .perform(get(ENTITY_API_URL_ID, profile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(profile.getId().intValue()))
            .andExpect(jsonPath("$.surname").value(DEFAULT_SURNAME))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.intValue()))
            .andExpect(jsonPath("$.prefix").value(DEFAULT_PREFIX))
            .andExpect(jsonPath("$.burialMethod").value(DEFAULT_BURIAL_METHOD))
            .andExpect(jsonPath("$.clothes").value(DEFAULT_CLOTHES))
            .andExpect(jsonPath("$.placeOfCeremony").value(DEFAULT_PLACE_OF_CEREMONY))
            .andExpect(jsonPath("$.photo").value(DEFAULT_PHOTO))
            .andExpect(jsonPath("$.graveInscription").value(DEFAULT_GRAVE_INSCRIPTION))
            .andExpect(jsonPath("$.spotify").value(DEFAULT_SPOTIFY))
            .andExpect(jsonPath("$.guests").value(DEFAULT_GUESTS))
            .andExpect(jsonPath("$.notInvited").value(DEFAULT_NOT_INVITED))
            .andExpect(jsonPath("$.obituary").value(DEFAULT_OBITUARY))
            .andExpect(jsonPath("$.purchasedPlace").value(DEFAULT_PURCHASED_PLACE.booleanValue()))
            .andExpect(jsonPath("$.ifPurchasedOther").value(DEFAULT_IF_PURCHASED_OTHER))
            .andExpect(jsonPath("$.flowers").value(DEFAULT_FLOWERS.booleanValue()))
            .andExpect(jsonPath("$.ifFlowers").value(DEFAULT_IF_FLOWERS))
            .andExpect(jsonPath("$.farewellLetter").value(DEFAULT_FAREWELL_LETTER))
            .andExpect(jsonPath("$.speech").value(DEFAULT_SPEECH))
            .andExpect(jsonPath("$.videoSpeech").value(DEFAULT_VIDEO_SPEECH))
            .andExpect(jsonPath("$.testament").value(DEFAULT_TESTAMENT))
            .andExpect(jsonPath("$.accessesForRelatives").value(DEFAULT_ACCESSES_FOR_RELATIVES))
            .andExpect(jsonPath("$.other").value(DEFAULT_OTHER))
            .andExpect(jsonPath("$.codeQR").value(DEFAULT_CODE_QR));
    }

    @Test
    @Transactional
    void getNonExistingProfile() throws Exception {
        // Get the profile
        restProfileMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProfile() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        int databaseSizeBeforeUpdate = profileRepository.findAll().size();

        // Update the profile
        Profile updatedProfile = profileRepository.findById(profile.getId()).get();
        // Disconnect from session so that the updates on updatedProfile are not directly saved in db
        em.detach(updatedProfile);
        updatedProfile
            .surname(UPDATED_SURNAME)
            .phone(UPDATED_PHONE)
            .prefix(UPDATED_PREFIX)
            .burialMethod(UPDATED_BURIAL_METHOD)
            .clothes(UPDATED_CLOTHES)
            .placeOfCeremony(UPDATED_PLACE_OF_CEREMONY)
            .photo(UPDATED_PHOTO)
            .graveInscription(UPDATED_GRAVE_INSCRIPTION)
            .spotify(UPDATED_SPOTIFY)
            .guests(UPDATED_GUESTS)
            .notInvited(UPDATED_NOT_INVITED)
            .obituary(UPDATED_OBITUARY)
            .purchasedPlace(UPDATED_PURCHASED_PLACE)
            .ifPurchasedOther(UPDATED_IF_PURCHASED_OTHER)
            .flowers(UPDATED_FLOWERS)
            .ifFlowers(UPDATED_IF_FLOWERS)
            .farewellLetter(UPDATED_FAREWELL_LETTER)
            .speech(UPDATED_SPEECH)
            .videoSpeech(UPDATED_VIDEO_SPEECH)
            .testament(UPDATED_TESTAMENT)
            .accessesForRelatives(UPDATED_ACCESSES_FOR_RELATIVES)
            .other(UPDATED_OTHER)
            .codeQR(UPDATED_CODE_QR);

        restProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProfile.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedProfile))
            )
            .andExpect(status().isOk());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate);
        Profile testProfile = profileList.get(profileList.size() - 1);
        assertThat(testProfile.getSurname()).isEqualTo(UPDATED_SURNAME);
        assertThat(testProfile.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testProfile.getPrefix()).isEqualTo(UPDATED_PREFIX);
        assertThat(testProfile.getBurialMethod()).isEqualTo(UPDATED_BURIAL_METHOD);
        assertThat(testProfile.getClothes()).isEqualTo(UPDATED_CLOTHES);
        assertThat(testProfile.getPlaceOfCeremony()).isEqualTo(UPDATED_PLACE_OF_CEREMONY);
        assertThat(testProfile.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testProfile.getGraveInscription()).isEqualTo(UPDATED_GRAVE_INSCRIPTION);
        assertThat(testProfile.getSpotify()).isEqualTo(UPDATED_SPOTIFY);
        assertThat(testProfile.getGuests()).isEqualTo(UPDATED_GUESTS);
        assertThat(testProfile.getNotInvited()).isEqualTo(UPDATED_NOT_INVITED);
        assertThat(testProfile.getObituary()).isEqualTo(UPDATED_OBITUARY);
        assertThat(testProfile.getPurchasedPlace()).isEqualTo(UPDATED_PURCHASED_PLACE);
        assertThat(testProfile.getIfPurchasedOther()).isEqualTo(UPDATED_IF_PURCHASED_OTHER);
        assertThat(testProfile.getFlowers()).isEqualTo(UPDATED_FLOWERS);
        assertThat(testProfile.getIfFlowers()).isEqualTo(UPDATED_IF_FLOWERS);
        assertThat(testProfile.getFarewellLetter()).isEqualTo(UPDATED_FAREWELL_LETTER);
        assertThat(testProfile.getSpeech()).isEqualTo(UPDATED_SPEECH);
        assertThat(testProfile.getVideoSpeech()).isEqualTo(UPDATED_VIDEO_SPEECH);
        assertThat(testProfile.getTestament()).isEqualTo(UPDATED_TESTAMENT);
        assertThat(testProfile.getAccessesForRelatives()).isEqualTo(UPDATED_ACCESSES_FOR_RELATIVES);
        assertThat(testProfile.getOther()).isEqualTo(UPDATED_OTHER);
        assertThat(testProfile.getCodeQR()).isEqualTo(UPDATED_CODE_QR);
    }

    @Test
    @Transactional
    void putNonExistingProfile() throws Exception {
        int databaseSizeBeforeUpdate = profileRepository.findAll().size();
        profile.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, profile.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(profile))
            )
            .andExpect(status().isBadRequest());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProfile() throws Exception {
        int databaseSizeBeforeUpdate = profileRepository.findAll().size();
        profile.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(profile))
            )
            .andExpect(status().isBadRequest());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProfile() throws Exception {
        int databaseSizeBeforeUpdate = profileRepository.findAll().size();
        profile.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfileMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(profile)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProfileWithPatch() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        int databaseSizeBeforeUpdate = profileRepository.findAll().size();

        // Update the profile using partial update
        Profile partialUpdatedProfile = new Profile();
        partialUpdatedProfile.setId(profile.getId());

        partialUpdatedProfile
            .surname(UPDATED_SURNAME)
            .phone(UPDATED_PHONE)
            .prefix(UPDATED_PREFIX)
            .placeOfCeremony(UPDATED_PLACE_OF_CEREMONY)
            .spotify(UPDATED_SPOTIFY)
            .guests(UPDATED_GUESTS)
            .obituary(UPDATED_OBITUARY)
            .purchasedPlace(UPDATED_PURCHASED_PLACE)
            .flowers(UPDATED_FLOWERS)
            .ifFlowers(UPDATED_IF_FLOWERS)
            .speech(UPDATED_SPEECH)
            .videoSpeech(UPDATED_VIDEO_SPEECH)
            .accessesForRelatives(UPDATED_ACCESSES_FOR_RELATIVES);

        restProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProfile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProfile))
            )
            .andExpect(status().isOk());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate);
        Profile testProfile = profileList.get(profileList.size() - 1);
        assertThat(testProfile.getSurname()).isEqualTo(UPDATED_SURNAME);
        assertThat(testProfile.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testProfile.getPrefix()).isEqualTo(UPDATED_PREFIX);
        assertThat(testProfile.getBurialMethod()).isEqualTo(DEFAULT_BURIAL_METHOD);
        assertThat(testProfile.getClothes()).isEqualTo(DEFAULT_CLOTHES);
        assertThat(testProfile.getPlaceOfCeremony()).isEqualTo(UPDATED_PLACE_OF_CEREMONY);
        assertThat(testProfile.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testProfile.getGraveInscription()).isEqualTo(DEFAULT_GRAVE_INSCRIPTION);
        assertThat(testProfile.getSpotify()).isEqualTo(UPDATED_SPOTIFY);
        assertThat(testProfile.getGuests()).isEqualTo(UPDATED_GUESTS);
        assertThat(testProfile.getNotInvited()).isEqualTo(DEFAULT_NOT_INVITED);
        assertThat(testProfile.getObituary()).isEqualTo(UPDATED_OBITUARY);
        assertThat(testProfile.getPurchasedPlace()).isEqualTo(UPDATED_PURCHASED_PLACE);
        assertThat(testProfile.getIfPurchasedOther()).isEqualTo(DEFAULT_IF_PURCHASED_OTHER);
        assertThat(testProfile.getFlowers()).isEqualTo(UPDATED_FLOWERS);
        assertThat(testProfile.getIfFlowers()).isEqualTo(UPDATED_IF_FLOWERS);
        assertThat(testProfile.getFarewellLetter()).isEqualTo(DEFAULT_FAREWELL_LETTER);
        assertThat(testProfile.getSpeech()).isEqualTo(UPDATED_SPEECH);
        assertThat(testProfile.getVideoSpeech()).isEqualTo(UPDATED_VIDEO_SPEECH);
        assertThat(testProfile.getTestament()).isEqualTo(DEFAULT_TESTAMENT);
        assertThat(testProfile.getAccessesForRelatives()).isEqualTo(UPDATED_ACCESSES_FOR_RELATIVES);
        assertThat(testProfile.getOther()).isEqualTo(DEFAULT_OTHER);
        assertThat(testProfile.getCodeQR()).isEqualTo(DEFAULT_CODE_QR);
    }

    @Test
    @Transactional
    void fullUpdateProfileWithPatch() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        int databaseSizeBeforeUpdate = profileRepository.findAll().size();

        // Update the profile using partial update
        Profile partialUpdatedProfile = new Profile();
        partialUpdatedProfile.setId(profile.getId());

        partialUpdatedProfile
            .surname(UPDATED_SURNAME)
            .phone(UPDATED_PHONE)
            .prefix(UPDATED_PREFIX)
            .burialMethod(UPDATED_BURIAL_METHOD)
            .clothes(UPDATED_CLOTHES)
            .placeOfCeremony(UPDATED_PLACE_OF_CEREMONY)
            .photo(UPDATED_PHOTO)
            .graveInscription(UPDATED_GRAVE_INSCRIPTION)
            .spotify(UPDATED_SPOTIFY)
            .guests(UPDATED_GUESTS)
            .notInvited(UPDATED_NOT_INVITED)
            .obituary(UPDATED_OBITUARY)
            .purchasedPlace(UPDATED_PURCHASED_PLACE)
            .ifPurchasedOther(UPDATED_IF_PURCHASED_OTHER)
            .flowers(UPDATED_FLOWERS)
            .ifFlowers(UPDATED_IF_FLOWERS)
            .farewellLetter(UPDATED_FAREWELL_LETTER)
            .speech(UPDATED_SPEECH)
            .videoSpeech(UPDATED_VIDEO_SPEECH)
            .testament(UPDATED_TESTAMENT)
            .accessesForRelatives(UPDATED_ACCESSES_FOR_RELATIVES)
            .other(UPDATED_OTHER)
            .codeQR(UPDATED_CODE_QR);

        restProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProfile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProfile))
            )
            .andExpect(status().isOk());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate);
        Profile testProfile = profileList.get(profileList.size() - 1);
        assertThat(testProfile.getSurname()).isEqualTo(UPDATED_SURNAME);
        assertThat(testProfile.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testProfile.getPrefix()).isEqualTo(UPDATED_PREFIX);
        assertThat(testProfile.getBurialMethod()).isEqualTo(UPDATED_BURIAL_METHOD);
        assertThat(testProfile.getClothes()).isEqualTo(UPDATED_CLOTHES);
        assertThat(testProfile.getPlaceOfCeremony()).isEqualTo(UPDATED_PLACE_OF_CEREMONY);
        assertThat(testProfile.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testProfile.getGraveInscription()).isEqualTo(UPDATED_GRAVE_INSCRIPTION);
        assertThat(testProfile.getSpotify()).isEqualTo(UPDATED_SPOTIFY);
        assertThat(testProfile.getGuests()).isEqualTo(UPDATED_GUESTS);
        assertThat(testProfile.getNotInvited()).isEqualTo(UPDATED_NOT_INVITED);
        assertThat(testProfile.getObituary()).isEqualTo(UPDATED_OBITUARY);
        assertThat(testProfile.getPurchasedPlace()).isEqualTo(UPDATED_PURCHASED_PLACE);
        assertThat(testProfile.getIfPurchasedOther()).isEqualTo(UPDATED_IF_PURCHASED_OTHER);
        assertThat(testProfile.getFlowers()).isEqualTo(UPDATED_FLOWERS);
        assertThat(testProfile.getIfFlowers()).isEqualTo(UPDATED_IF_FLOWERS);
        assertThat(testProfile.getFarewellLetter()).isEqualTo(UPDATED_FAREWELL_LETTER);
        assertThat(testProfile.getSpeech()).isEqualTo(UPDATED_SPEECH);
        assertThat(testProfile.getVideoSpeech()).isEqualTo(UPDATED_VIDEO_SPEECH);
        assertThat(testProfile.getTestament()).isEqualTo(UPDATED_TESTAMENT);
        assertThat(testProfile.getAccessesForRelatives()).isEqualTo(UPDATED_ACCESSES_FOR_RELATIVES);
        assertThat(testProfile.getOther()).isEqualTo(UPDATED_OTHER);
        assertThat(testProfile.getCodeQR()).isEqualTo(UPDATED_CODE_QR);
    }

    @Test
    @Transactional
    void patchNonExistingProfile() throws Exception {
        int databaseSizeBeforeUpdate = profileRepository.findAll().size();
        profile.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, profile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(profile))
            )
            .andExpect(status().isBadRequest());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProfile() throws Exception {
        int databaseSizeBeforeUpdate = profileRepository.findAll().size();
        profile.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(profile))
            )
            .andExpect(status().isBadRequest());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProfile() throws Exception {
        int databaseSizeBeforeUpdate = profileRepository.findAll().size();
        profile.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfileMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(profile)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProfile() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        int databaseSizeBeforeDelete = profileRepository.findAll().size();

        // Delete the profile
        restProfileMockMvc
            .perform(delete(ENTITY_API_URL_ID, profile.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
