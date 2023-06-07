package com.origami.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.origami.IntegrationTest;
import com.origami.domain.Personality;
import com.origami.repository.PersonalityRepository;
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
 * Integration tests for the {@link PersonalityResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PersonalityResourceIT {

    private static final String DEFAULT_RELATIVE_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_RELATIVE_EMAIL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/personalities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PersonalityRepository personalityRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPersonalityMockMvc;

    private Personality personality;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Personality createEntity(EntityManager em) {
        Personality personality = new Personality().relativeEmail(DEFAULT_RELATIVE_EMAIL);
        return personality;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Personality createUpdatedEntity(EntityManager em) {
        Personality personality = new Personality().relativeEmail(UPDATED_RELATIVE_EMAIL);
        return personality;
    }

    @BeforeEach
    public void initTest() {
        personality = createEntity(em);
    }

    @Test
    @Transactional
    void createPersonality() throws Exception {
        int databaseSizeBeforeCreate = personalityRepository.findAll().size();
        // Create the Personality
        restPersonalityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personality)))
            .andExpect(status().isCreated());

        // Validate the Personality in the database
        List<Personality> personalityList = personalityRepository.findAll();
        assertThat(personalityList).hasSize(databaseSizeBeforeCreate + 1);
        Personality testPersonality = personalityList.get(personalityList.size() - 1);
        assertThat(testPersonality.getRelativeEmail()).isEqualTo(DEFAULT_RELATIVE_EMAIL);
    }

    @Test
    @Transactional
    void createPersonalityWithExistingId() throws Exception {
        // Create the Personality with an existing ID
        personality.setId(1L);

        int databaseSizeBeforeCreate = personalityRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonalityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personality)))
            .andExpect(status().isBadRequest());

        // Validate the Personality in the database
        List<Personality> personalityList = personalityRepository.findAll();
        assertThat(personalityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPersonalities() throws Exception {
        // Initialize the database
        personalityRepository.saveAndFlush(personality);

        // Get all the personalityList
        restPersonalityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personality.getId().intValue())))
            .andExpect(jsonPath("$.[*].relativeEmail").value(hasItem(DEFAULT_RELATIVE_EMAIL)));
    }

    @Test
    @Transactional
    void getPersonality() throws Exception {
        // Initialize the database
        personalityRepository.saveAndFlush(personality);

        // Get the personality
        restPersonalityMockMvc
            .perform(get(ENTITY_API_URL_ID, personality.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(personality.getId().intValue()))
            .andExpect(jsonPath("$.relativeEmail").value(DEFAULT_RELATIVE_EMAIL));
    }

    @Test
    @Transactional
    void getNonExistingPersonality() throws Exception {
        // Get the personality
        restPersonalityMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPersonality() throws Exception {
        // Initialize the database
        personalityRepository.saveAndFlush(personality);

        int databaseSizeBeforeUpdate = personalityRepository.findAll().size();

        // Update the personality
        Personality updatedPersonality = personalityRepository.findById(personality.getId()).get();
        // Disconnect from session so that the updates on updatedPersonality are not directly saved in db
        em.detach(updatedPersonality);
        updatedPersonality.relativeEmail(UPDATED_RELATIVE_EMAIL);

        restPersonalityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPersonality.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPersonality))
            )
            .andExpect(status().isOk());

        // Validate the Personality in the database
        List<Personality> personalityList = personalityRepository.findAll();
        assertThat(personalityList).hasSize(databaseSizeBeforeUpdate);
        Personality testPersonality = personalityList.get(personalityList.size() - 1);
        assertThat(testPersonality.getRelativeEmail()).isEqualTo(UPDATED_RELATIVE_EMAIL);
    }

    @Test
    @Transactional
    void putNonExistingPersonality() throws Exception {
        int databaseSizeBeforeUpdate = personalityRepository.findAll().size();
        personality.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonalityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, personality.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personality))
            )
            .andExpect(status().isBadRequest());

        // Validate the Personality in the database
        List<Personality> personalityList = personalityRepository.findAll();
        assertThat(personalityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPersonality() throws Exception {
        int databaseSizeBeforeUpdate = personalityRepository.findAll().size();
        personality.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonalityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personality))
            )
            .andExpect(status().isBadRequest());

        // Validate the Personality in the database
        List<Personality> personalityList = personalityRepository.findAll();
        assertThat(personalityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPersonality() throws Exception {
        int databaseSizeBeforeUpdate = personalityRepository.findAll().size();
        personality.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonalityMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personality)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Personality in the database
        List<Personality> personalityList = personalityRepository.findAll();
        assertThat(personalityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePersonalityWithPatch() throws Exception {
        // Initialize the database
        personalityRepository.saveAndFlush(personality);

        int databaseSizeBeforeUpdate = personalityRepository.findAll().size();

        // Update the personality using partial update
        Personality partialUpdatedPersonality = new Personality();
        partialUpdatedPersonality.setId(personality.getId());

        partialUpdatedPersonality.relativeEmail(UPDATED_RELATIVE_EMAIL);

        restPersonalityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPersonality.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPersonality))
            )
            .andExpect(status().isOk());

        // Validate the Personality in the database
        List<Personality> personalityList = personalityRepository.findAll();
        assertThat(personalityList).hasSize(databaseSizeBeforeUpdate);
        Personality testPersonality = personalityList.get(personalityList.size() - 1);
        assertThat(testPersonality.getRelativeEmail()).isEqualTo(UPDATED_RELATIVE_EMAIL);
    }

    @Test
    @Transactional
    void fullUpdatePersonalityWithPatch() throws Exception {
        // Initialize the database
        personalityRepository.saveAndFlush(personality);

        int databaseSizeBeforeUpdate = personalityRepository.findAll().size();

        // Update the personality using partial update
        Personality partialUpdatedPersonality = new Personality();
        partialUpdatedPersonality.setId(personality.getId());

        partialUpdatedPersonality.relativeEmail(UPDATED_RELATIVE_EMAIL);

        restPersonalityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPersonality.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPersonality))
            )
            .andExpect(status().isOk());

        // Validate the Personality in the database
        List<Personality> personalityList = personalityRepository.findAll();
        assertThat(personalityList).hasSize(databaseSizeBeforeUpdate);
        Personality testPersonality = personalityList.get(personalityList.size() - 1);
        assertThat(testPersonality.getRelativeEmail()).isEqualTo(UPDATED_RELATIVE_EMAIL);
    }

    @Test
    @Transactional
    void patchNonExistingPersonality() throws Exception {
        int databaseSizeBeforeUpdate = personalityRepository.findAll().size();
        personality.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonalityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, personality.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(personality))
            )
            .andExpect(status().isBadRequest());

        // Validate the Personality in the database
        List<Personality> personalityList = personalityRepository.findAll();
        assertThat(personalityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPersonality() throws Exception {
        int databaseSizeBeforeUpdate = personalityRepository.findAll().size();
        personality.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonalityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(personality))
            )
            .andExpect(status().isBadRequest());

        // Validate the Personality in the database
        List<Personality> personalityList = personalityRepository.findAll();
        assertThat(personalityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPersonality() throws Exception {
        int databaseSizeBeforeUpdate = personalityRepository.findAll().size();
        personality.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonalityMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(personality))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Personality in the database
        List<Personality> personalityList = personalityRepository.findAll();
        assertThat(personalityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePersonality() throws Exception {
        // Initialize the database
        personalityRepository.saveAndFlush(personality);

        int databaseSizeBeforeDelete = personalityRepository.findAll().size();

        // Delete the personality
        restPersonalityMockMvc
            .perform(delete(ENTITY_API_URL_ID, personality.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Personality> personalityList = personalityRepository.findAll();
        assertThat(personalityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
