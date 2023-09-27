package com.origami.repository;

import com.origami.domain.LifeStatus;
import com.origami.domain.Profile;
import com.origami.domain.User;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Profile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findOneByUserId(Long userId);
    Optional<Profile> findOneByCodeQR(String codeQR);
    Optional<Profile> findProfileByPublicProfileLink(String publicProfileLink);
    Optional<Profile> findOneByLifeLink(String lifeLink);
    List<Profile> findAllByLifeStatus(LifeStatus lifeStatus);
}
