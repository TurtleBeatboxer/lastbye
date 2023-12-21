package com.origami.web.rest;

import com.origami.domain.Files;
import com.origami.domain.User;
import com.origami.repository.FilesRepository;
import com.origami.service.FileService;
import com.origami.service.UserService;
import com.origami.service.dto.FileDTO;
import com.origami.web.rest.errors.BadRequestAlertException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
    private final UserService userService;

    public FilesResource(FilesRepository filesRepository, FileService fileService, UserService userService) {
        this.filesRepository = filesRepository;
        this.fileService = fileService;
        this.userService = userService;
    }

    /**
     * {@code POST  /files} : Create a new files.
     *
     * @param files the files to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new files, or with status {@code 400 (Bad Request)} if the files has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/files")
    public ResponseEntity<Files> createFiles(@NotNull @Valid @RequestBody Files files) throws URISyntaxException {
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

    //Can't dodge using 2 params
    @PostMapping("/profile/pictures")
    public ResponseEntity<?> uploadFilesFirstTime(
        @RequestParam("file") @NotNull MultipartFile file,
        @RequestParam("type") @NotNull String type
    ) throws Exception {
        Optional<User> userOptional = userService.getUserWithAuthorities();
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return ResponseEntity.status(HttpStatus.OK).body(fileService.uploadImagePDF(new FileDTO(file, type, user.getId())));
        }
        return null;
    }

    @GetMapping("/user/pictures")
    public ResponseEntity<?> downloadAllUserImages() throws Exception {
        Optional<User> userOptional = userService.getUserWithAuthorities();
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return fileService.getAllFilesFromUser(user.getId());
        }
        return null;
    }

    @PostMapping("/profile/publicImage")
    public @ResponseBody ResponseEntity<byte[]> getPublicImage(@NotNull @Valid @RequestBody String publicProfileId)
        throws NullPointerException, IOException {
        return ResponseEntity.status(HttpStatus.OK).body(fileService.downloadPublicProfileImage(publicProfileId));
    }
}
