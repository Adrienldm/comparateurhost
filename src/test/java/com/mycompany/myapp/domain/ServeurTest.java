package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ServeurTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Serveur.class);
        Serveur serveur1 = new Serveur();
        serveur1.setId(1L);
        Serveur serveur2 = new Serveur();
        serveur2.setId(serveur1.getId());
        assertThat(serveur1).isEqualTo(serveur2);
        serveur2.setId(2L);
        assertThat(serveur1).isNotEqualTo(serveur2);
        serveur1.setId(null);
        assertThat(serveur1).isNotEqualTo(serveur2);
    }
}
