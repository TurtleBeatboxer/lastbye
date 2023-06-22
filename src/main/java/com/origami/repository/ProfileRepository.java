package com.origami.repository;

import com.origami.domain.Profile;
import com.origami.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Profile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findOneByUserId(Long userId);
    Optional<Profile> findProfileByPublicProfileLink(String publicProfileLink);
}
