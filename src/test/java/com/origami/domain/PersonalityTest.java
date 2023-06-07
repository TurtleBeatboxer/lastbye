package com.origami.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.origami.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PersonalityTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Personality.class);
        Personality personality1 = new Personality();
        personality1.setId(1L);
        Personality personality2 = new Personality();
        personality2.setId(personality1.getId());
        assertThat(personality1).isEqualTo(personality2);
        personality2.setId(2L);
        assertThat(personality1).isNotEqualTo(personality2);
        personality1.setId(null);
        assertThat(personality1).isNotEqualTo(personality2);
    }
}
