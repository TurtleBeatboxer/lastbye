package com.origami.repository;

import com.origami.domain.Files;
import com.origami.domain.Profile;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Files entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FilesRepository extends JpaRepository<Files, Long> {
    Optional<Files> findOneByProfileId(Long profileId);
    ArrayList<Files> findAllByProfileId(Long profileId);
}
