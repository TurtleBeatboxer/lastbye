package com.origami.web.rest;

import com.origami.domain.Profile;
import com.origami.repository.ProfileRepository;
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
 * REST controller for managing {@link com.origami.domain.Profile}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ProfileResource {

    private final Logger log = LoggerFactory.getLogger(ProfileResource.class);

    private static final String ENTITY_NAME = "profile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProfileRepository profileRepository;

    public ProfileResource(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    /**
     * {@code POST  /profiles} : Create a new profile.
     *
     * @param profile the profile to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new profile, or with status {@code 400 (Bad Request)} if the profile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/profiles")
    public ResponseEntity<Profile> createProfile(@RequestBody Profile profile) throws URISyntaxException {
        log.debug("REST request to save Profile : {}", profile);
        if (profile.getId() != null) {
            throw new BadRequestAlertException("A new profile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Profile result = profileRepository.save(profile);
        return ResponseEntity
            .created(new URI("/api/profiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /profiles/:id} : Updates an existing profile.
     *
     * @param id the id of the profile to save.
     * @param profile the profile to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated profile,
     * or with status {@code 400 (Bad Request)} if the profile is not valid,
     * or with status {@code 500 (Internal Server Error)} if the profile couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/profiles/{id}")
    public ResponseEntity<Profile> updateProfile(@PathVariable(value = "id", required = false) final Long id, @RequestBody Profile profile)
        throws URISyntaxException {
        log.debug("REST request to update Profile : {}, {}", id, profile);
        if (profile.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, profile.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!profileRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Profile result = profileRepository.save(profile);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, profile.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /profiles/:id} : Partial updates given fields of an existing profile, field will ignore if it is null
     *
     * @param id the id of the profile to save.
     * @param profile the profile to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated profile,
     * or with status {@code 400 (Bad Request)} if the profile is not valid,
     * or with status {@code 404 (Not Found)} if the profile is not found,
     * or with status {@code 500 (Internal Server Error)} if the profile couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/profiles/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Profile partialUpdateProfile(@PathVariable(value = "id", required = false) final Long id, @RequestBody Profile profile)
        throws URISyntaxException {
        log.debug("REST request to partial update Profile partially : {}, {}", id, profile);
        if (profile.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, profile.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!profileRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Profile> result = profileRepository
            .findById(profile.getId())
            .map(existingProfile -> {
                if (profile.getSurname() != null) {
                    existingProfile.setSurname(profile.getSurname());
                }
                if (profile.getPhone() != null) {
                    existingProfile.setPhone(profile.getPhone());
                }
                if (profile.getPrefix() != null) {
                    existingProfile.setPrefix(profile.getPrefix());
                }
                if (profile.getBurialMethod() != null) {
                    existingProfile.setBurialMethod(profile.getBurialMethod());
                }
                if (profile.getBurialPlace() != null) {
                    existingProfile.setBurialPlace(profile.getBurialPlace());
                }
                if (profile.getClothes() != null) {
                    existingProfile.setClothes(profile.getClothes());
                }
                if (profile.getPlaceOfCeremony() != null) {
                    existingProfile.setPlaceOfCeremony(profile.getPlaceOfCeremony());
                }
                if (profile.getPhoto() != null) {
                    existingProfile.setPhoto(profile.getPhoto());
                }
                if (profile.getGraveInscription() != null) {
                    existingProfile.setGraveInscription(profile.getGraveInscription());
                }
                if (profile.getSpotify() != null) {
                    existingProfile.setSpotify(profile.getSpotify());
                }
                if (profile.getIfGuests() != null) {
                    existingProfile.setIfGuests(profile.getIfGuests());
                }
                if (profile.getIfGraveInscription() != null) {
                    existingProfile.setIfGraveInscription(profile.getIfGraveInscription());
                }
                if (profile.getOpenCoffin() != null) {
                    existingProfile.setOpenCoffin(profile.getOpenCoffin());
                }
                if (profile.getGuests() != null) {
                    existingProfile.setGuests(profile.getGuests());
                }
                if (profile.getNotInvited() != null) {
                    existingProfile.setNotInvited(profile.getNotInvited());
                }
                if (profile.getObituary() != null) {
                    existingProfile.setObituary(profile.getObituary());
                }
                if (profile.getPurchasedPlace() != null) {
                    existingProfile.setPurchasedPlace(profile.getPurchasedPlace());
                }
                if (profile.getIfPurchasedOther() != null) {
                    existingProfile.setIfPurchasedOther(profile.getIfPurchasedOther());
                }
                if (profile.getFlowers() != null) {
                    existingProfile.setFlowers(profile.getFlowers());
                }
                if (profile.getIfFlowers() != null) {
                    existingProfile.setIfFlowers(profile.getIfFlowers());
                }
                if (profile.getFarewellLetter() != null) {
                    existingProfile.setFarewellLetter(profile.getFarewellLetter());
                }
                if (profile.getFarewellReader() != null) {
                    existingProfile.setFarewellReader(profile.getFarewellReader());
                }
                if (profile.getSpeech() != null) {
                    existingProfile.setSpeech(profile.getSpeech());
                }
                if (profile.getVideoSpeech() != null) {
                    existingProfile.setVideoSpeech(profile.getVideoSpeech());
                }
                if (profile.getTestament() != null) {
                    existingProfile.setTestament(profile.getTestament());
                }
                if (profile.getAccessesForRelatives() != null) {
                    existingProfile.setAccessesForRelatives(profile.getAccessesForRelatives());
                }
                if (profile.getOther() != null) {
                    existingProfile.setOther(profile.getOther());
                }
                if (profile.getCodeQR() != null) {
                    existingProfile.setCodeQR(profile.getCodeQR());
                }
                if (profile.getIfOther4() != null) {
                    existingProfile.setIfOther4(profile.getIfOther4());
                }
                if (profile.getIfTestament() != null) {
                    existingProfile.setIfTestament(profile.getIfTestament());
                }
                if (profile.getIfVideoSpeech() != null) {
                    existingProfile.setIfVideoSpeech(profile.getIfVideoSpeech());
                }
                if (profile.getIfFarewellLetter() != null) {
                    existingProfile.setIfFarewellLetter(profile.getIfFarewellLetter());
                }
                if (profile.getMusicType() != null) {
                    existingProfile.setMusicType(profile.getMusicType());
                }
                if (profile.getBurialType() != null) {
                    existingProfile.setBurialType(profile.getBurialType());
                }
                if (profile.getFarewellReader() != null) {
                    existingProfile.setFarewellReader(profile.getFarewellReader());
                }

                return existingProfile;
            })
            .map(profileRepository::save);

        //        return ResponseUtil.wrapOrNotFound(
        //
        //            result.isPresent() ? result.get() : result;
        //            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, profile.getId().toString())
        //        );
        return result.get();
    }

    /**
     * {@code GET  /profiles} : get all the profiles.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of profiles in body.
     */
    @GetMapping("/profiles")
    public List<Profile> getAllProfiles() {
        log.debug("REST request to get all Profiles");
        return profileRepository.findAll();
    }

    /**
     * {@code GET  /profiles/:id} : get the "id" profile.
     *
     * @param id the id of the profile to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the profile, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/profiles/{id}")
    public ResponseEntity<Profile> getProfile(@PathVariable Long id) {
        log.debug("REST request to get Profile : {}", id);
        Optional<Profile> profile = profileRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(profile);
    }

    /**
     * {@code DELETE  /profiles/:id} : delete the "id" profile.
     *
     * @param id the id of the profile to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/profiles/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long id) {
        log.debug("REST request to delete Profile : {}", id);
        profileRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
