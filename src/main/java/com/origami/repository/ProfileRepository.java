package com.origami.repository;

import com.origami.domain.Profile;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Profile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {}
