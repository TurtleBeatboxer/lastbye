package com.origami.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.origami.IntegrationTest;
import com.origami.domain.Files;
import com.origami.repository.FilesRepository;
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
 * Integration tests for the {@link FilesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FilesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_FORMAT = "AAAAAAAAAA";
    private static final String UPDATED_FORMAT = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_PATH = "AAAAAAAAAA";
    private static final String UPDATED_FILE_PATH = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/files";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FilesRepository filesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFilesMockMvc;

    private Files files;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Files createEntity(EntityManager em) {
        Files files = new Files().name(DEFAULT_NAME).type(DEFAULT_TYPE).format(DEFAULT_FORMAT).filePath(DEFAULT_FILE_PATH);
        return files;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Files createUpdatedEntity(EntityManager em) {
        Files files = new Files().name(UPDATED_NAME).type(UPDATED_TYPE).format(UPDATED_FORMAT).filePath(UPDATED_FILE_PATH);
        return files;
    }

    @BeforeEach
    public void initTest() {
        files = createEntity(em);
    }

    @Test
    @Transactional
    void createFiles() throws Exception {
        int databaseSizeBeforeCreate = filesRepository.findAll().size();
        // Create the Files
        restFilesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(files)))
            .andExpect(status().isCreated());

        // Validate the Files in the database
        List<Files> filesList = filesRepository.findAll();
        assertThat(filesList).hasSize(databaseSizeBeforeCreate + 1);
        Files testFiles = filesList.get(filesList.size() - 1);
        assertThat(testFiles.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFiles.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testFiles.getFormat()).isEqualTo(DEFAULT_FORMAT);
        assertThat(testFiles.getFilePath()).isEqualTo(DEFAULT_FILE_PATH);
    }

    @Test
    @Transactional
    void createFilesWithExistingId() throws Exception {
        // Create the Files with an existing ID
        files.setId(1L);

        int databaseSizeBeforeCreate = filesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFilesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(files)))
            .andExpect(status().isBadRequest());

        // Validate the Files in the database
        List<Files> filesList = filesRepository.findAll();
        assertThat(filesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFiles() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get all the filesList
        restFilesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(files.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].format").value(hasItem(DEFAULT_FORMAT)))
            .andExpect(jsonPath("$.[*].filePath").value(hasItem(DEFAULT_FILE_PATH)));
    }

    @Test
    @Transactional
    void getFiles() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get the files
        restFilesMockMvc
            .perform(get(ENTITY_API_URL_ID, files.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(files.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.format").value(DEFAULT_FORMAT))
            .andExpect(jsonPath("$.filePath").value(DEFAULT_FILE_PATH));
    }

    @Test
    @Transactional
    void getNonExistingFiles() throws Exception {
        // Get the files
        restFilesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFiles() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        int databaseSizeBeforeUpdate = filesRepository.findAll().size();

        // Update the files
        Files updatedFiles = filesRepository.findById(files.getId()).get();
        // Disconnect from session so that the updates on updatedFiles are not directly saved in db
        em.detach(updatedFiles);
        updatedFiles.name(UPDATED_NAME).type(UPDATED_TYPE).format(UPDATED_FORMAT).filePath(UPDATED_FILE_PATH);

        restFilesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFiles.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFiles))
            )
            .andExpect(status().isOk());

        // Validate the Files in the database
        List<Files> filesList = filesRepository.findAll();
        assertThat(filesList).hasSize(databaseSizeBeforeUpdate);
        Files testFiles = filesList.get(filesList.size() - 1);
        assertThat(testFiles.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFiles.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testFiles.getFormat()).isEqualTo(UPDATED_FORMAT);
        assertThat(testFiles.getFilePath()).isEqualTo(UPDATED_FILE_PATH);
    }

    @Test
    @Transactional
    void putNonExistingFiles() throws Exception {
        int databaseSizeBeforeUpdate = filesRepository.findAll().size();
        files.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFilesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, files.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(files))
            )
            .andExpect(status().isBadRequest());

        // Validate the Files in the database
        List<Files> filesList = filesRepository.findAll();
        assertThat(filesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFiles() throws Exception {
        int databaseSizeBeforeUpdate = filesRepository.findAll().size();
        files.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFilesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(files))
            )
            .andExpect(status().isBadRequest());

        // Validate the Files in the database
        List<Files> filesList = filesRepository.findAll();
        assertThat(filesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFiles() throws Exception {
        int databaseSizeBeforeUpdate = filesRepository.findAll().size();
        files.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFilesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(files)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Files in the database
        List<Files> filesList = filesRepository.findAll();
        assertThat(filesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFilesWithPatch() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        int databaseSizeBeforeUpdate = filesRepository.findAll().size();

        // Update the files using partial update
        Files partialUpdatedFiles = new Files();
        partialUpdatedFiles.setId(files.getId());

        partialUpdatedFiles.name(UPDATED_NAME).type(UPDATED_TYPE).filePath(UPDATED_FILE_PATH);

        restFilesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFiles.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFiles))
            )
            .andExpect(status().isOk());

        // Validate the Files in the database
        List<Files> filesList = filesRepository.findAll();
        assertThat(filesList).hasSize(databaseSizeBeforeUpdate);
        Files testFiles = filesList.get(filesList.size() - 1);
        assertThat(testFiles.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFiles.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testFiles.getFormat()).isEqualTo(DEFAULT_FORMAT);
        assertThat(testFiles.getFilePath()).isEqualTo(UPDATED_FILE_PATH);
    }

    @Test
    @Transactional
    void fullUpdateFilesWithPatch() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        int databaseSizeBeforeUpdate = filesRepository.findAll().size();

        // Update the files using partial update
        Files partialUpdatedFiles = new Files();
        partialUpdatedFiles.setId(files.getId());

        partialUpdatedFiles.name(UPDATED_NAME).type(UPDATED_TYPE).format(UPDATED_FORMAT).filePath(UPDATED_FILE_PATH);

        restFilesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFiles.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFiles))
            )
            .andExpect(status().isOk());

        // Validate the Files in the database
        List<Files> filesList = filesRepository.findAll();
        assertThat(filesList).hasSize(databaseSizeBeforeUpdate);
        Files testFiles = filesList.get(filesList.size() - 1);
        assertThat(testFiles.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFiles.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testFiles.getFormat()).isEqualTo(UPDATED_FORMAT);
        assertThat(testFiles.getFilePath()).isEqualTo(UPDATED_FILE_PATH);
    }

    @Test
    @Transactional
    void patchNonExistingFiles() throws Exception {
        int databaseSizeBeforeUpdate = filesRepository.findAll().size();
        files.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFilesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, files.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(files))
            )
            .andExpect(status().isBadRequest());

        // Validate the Files in the database
        List<Files> filesList = filesRepository.findAll();
        assertThat(filesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFiles() throws Exception {
        int databaseSizeBeforeUpdate = filesRepository.findAll().size();
        files.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFilesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(files))
            )
            .andExpect(status().isBadRequest());

        // Validate the Files in the database
        List<Files> filesList = filesRepository.findAll();
        assertThat(filesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFiles() throws Exception {
        int databaseSizeBeforeUpdate = filesRepository.findAll().size();
        files.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFilesMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(files)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Files in the database
        List<Files> filesList = filesRepository.findAll();
        assertThat(filesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFiles() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        int databaseSizeBeforeDelete = filesRepository.findAll().size();

        // Delete the files
        restFilesMockMvc
            .perform(delete(ENTITY_API_URL_ID, files.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Files> filesList = filesRepository.findAll();
        assertThat(filesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
