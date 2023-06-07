package com.origami.repository;

import com.origami.domain.Personality;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Personality entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonalityRepository extends JpaRepository<Personality, Long> {}
