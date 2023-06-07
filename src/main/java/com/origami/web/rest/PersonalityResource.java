package com.origami.web.rest;

import com.origami.domain.Personality;
import com.origami.repository.PersonalityRepository;
import com.origami.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.origami.domain.Personality}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PersonalityResource {

    private final Logger log = LoggerFactory.getLogger(PersonalityResource.class);

    private static final String ENTITY_NAME = "personality";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PersonalityRepository personalityRepository;

    public PersonalityResource(PersonalityRepository personalityRepository) {
        this.personalityRepository = personalityRepository;
    }

    /**
     * {@code POST  /personalities} : Create a new personality.
     *
     * @param personality the personality to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new personality, or with status {@code 400 (Bad Request)} if the personality has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/personalities")
    public ResponseEntity<Personality> createPersonality(@RequestBody Personality personality) throws URISyntaxException {
        log.debug("REST request to save Personality : {}", personality);
        if (personality.getId() != null) {
            throw new BadRequestAlertException("A new personality cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Personality result = personalityRepository.save(personality);
        return ResponseEntity
            .created(new URI("/api/personalities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /personalities/:id} : Updates an existing personality.
     *
     * @param id the id of the personality to save.
     * @param personality the personality to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personality,
     * or with status {@code 400 (Bad Request)} if the personality is not valid,
     * or with status {@code 500 (Internal Server Error)} if the personality couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/personalities/{id}")
    public ResponseEntity<Personality> updatePersonality(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Personality personality
    ) throws URISyntaxException {
        log.debug("REST request to update Personality : {}, {}", id, personality);
        if (personality.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, personality.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!personalityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Personality result = personalityRepository.save(personality);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, personality.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /personalities/:id} : Partial updates given fields of an existing personality, field will ignore if it is null
     *
     * @param id the id of the personality to save.
     * @param personality the personality to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personality,
     * or with status {@code 400 (Bad Request)} if the personality is not valid,
     * or with status {@code 404 (Not Found)} if the personality is not found,
     * or with status {@code 500 (Internal Server Error)} if the personality couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/personalities/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Personality> partialUpdatePersonality(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Personality personality
    ) throws URISyntaxException {
        log.debug("REST request to partial update Personality partially : {}, {}", id, personality);
        if (personality.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, personality.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!personalityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Personality> result = personalityRepository
            .findById(personality.getId())
            .map(existingPersonality -> {
                if (personality.getRelativeEmail() != null) {
                    existingPersonality.setRelativeEmail(personality.getRelativeEmail());
                }

                return existingPersonality;
            })
            .map(personalityRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, personality.getId().toString())
        );
    }

    /**
     * {@code GET  /personalities} : get all the personalities.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of personalities in body.
     */
    @GetMapping("/personalities")
    public List<Personality> getAllPersonalities() {
        log.debug("REST request to get all Personalities");
        return personalityRepository.findAll();
    }

    /**
     * {@code GET  /personalities/:id} : get the "id" personality.
     *
     * @param id the id of the personality to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the personality, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/personalities/{id}")
    public ResponseEntity<Personality> getPersonality(@PathVariable Long id) {
        log.debug("REST request to get Personality : {}", id);
        Optional<Personality> personality = personalityRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(personality);
    }

    /**
     * {@code DELETE  /personalities/:id} : delete the "id" personality.
     *
     * @param id the id of the personality to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/personalities/{id}")
    public ResponseEntity<Void> deletePersonality(@PathVariable Long id) {
        log.debug("REST request to delete Personality : {}", id);
        personalityRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
