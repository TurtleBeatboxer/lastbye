package com.origami.web.rest;

import com.origami.domain.Files;
import com.origami.domain.dtos.FileDTO;
import com.origami.repository.FilesRepository;
import com.origami.service.FileService;
import com.origami.web.rest.errors.BadRequestAlertException;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.origami.domain.Files}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class FilesResource {

    private final Logger log = LoggerFactory.getLogger(FilesResource.class);

    private static final String ENTITY_NAME = "files";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FilesRepository filesRepository;
    private final FileService fileService;

    public FilesResource(FilesRepository filesRepository, FileService fileService) {
        this.filesRepository = filesRepository;
        this.fileService = fileService;
    }

    /**
     * {@code POST  /files} : Create a new files.
     *
     * @param files the files to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new files, or with status {@code 400 (Bad Request)} if the files has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/files")
    public ResponseEntity<Files> createFiles(@RequestBody Files files) throws URISyntaxException {
        log.debug("REST request to save Files : {}", files);
        if (files.getId() != null) {
            throw new BadRequestAlertException("A new files cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Files result = filesRepository.save(files);
        return ResponseEntity
            .created(new URI("/api/files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /files/:id} : Updates an existing files.
     *
     * @param id the id of the files to save.
     * @param files the files to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated files,
     * or with status {@code 400 (Bad Request)} if the files is not valid,
     * or with status {@code 500 (Internal Server Error)} if the files couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/files/{id}")
    public ResponseEntity<Files> updateFiles(@PathVariable(value = "id", required = false) final Long id, @RequestBody Files files)
        throws URISyntaxException {
        log.debug("REST request to update Files : {}, {}", id, files);
        if (files.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, files.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!filesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Files result = filesRepository.save(files);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, files.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /files/:id} : Partial updates given fields of an existing files, field will ignore if it is null
     *
     * @param id the id of the files to save.
     * @param files the files to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated files,
     * or with status {@code 400 (Bad Request)} if the files is not valid,
     * or with status {@code 404 (Not Found)} if the files is not found,
     * or with status {@code 500 (Internal Server Error)} if the files couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/files/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Files> partialUpdateFiles(@PathVariable(value = "id", required = false) final Long id, @RequestBody Files files)
        throws URISyntaxException {
        log.debug("REST request to partial update Files partially : {}, {}", id, files);
        if (files.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, files.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!filesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Files> result = filesRepository
            .findById(files.getId())
            .map(existingFiles -> {
                if (files.getName() != null) {
                    existingFiles.setName(files.getName());
                }
                if (files.getFileType() != null) {
                    existingFiles.setFileType(files.getFileType());
                }
                if (files.getFormat() != null) {
                    existingFiles.setFormat(files.getFormat());
                }
                if (files.getFilePath() != null) {
                    existingFiles.setFilePath(files.getFilePath());
                }

                return existingFiles;
            })
            .map(filesRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, files.getId().toString())
        );
    }

    /**
     * {@code GET  /files} : get all the files.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of files in body.
     */
    @GetMapping("/files")
    public List<Files> getAllFiles() {
        log.debug("REST request to get all Files");
        return filesRepository.findAll();
    }

    /**
     * {@code GET  /files/:id} : get the "id" files.
     *
     * @param id the id of the files to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the files, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/files/{id}")
    public ResponseEntity<Files> getFiles(@PathVariable Long id) {
        log.debug("REST request to get Files : {}", id);
        Optional<Files> files = filesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(files);
    }

    /**
     * {@code DELETE  /files/:id} : delete the "id" files.
     *
     * @param id the id of the files to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/files/{id}")
    public ResponseEntity<Void> deleteFiles(@PathVariable Long id) {
        log.debug("REST request to delete Files : {}", id);
        filesRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    //Can't dodge using 3 params
    @PostMapping("/profile/pictures")
    public ResponseEntity<?> uploadImageToFIleSystem(
        @RequestParam("file") MultipartFile file,
        @RequestParam("type") String type,
        @RequestParam("user") String userId
    ) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(fileService.uploadImage(new FileDTO(file, type, userId)));
    }

    @PostMapping("/profile/publicImage")
    public ResponseEntity<File> getPublicImage(@Valid @RequestBody String publicProfileId) throws NullPointerException {
        return ResponseEntity.status(HttpStatus.OK).body(fileService.downloadPublicProfileImage(publicProfileId));
    }
}
