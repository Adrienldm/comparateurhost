package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Serveur;
import com.mycompany.myapp.repository.ServeurRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ServeurResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ServeurResourceIT {

    private static final String DEFAULT_NOM_HEBERGEUR = "AAAAAAAAAA";
    private static final String UPDATED_NOM_HEBERGEUR = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_SERVEUR = "AAAAAAAAAA";
    private static final String UPDATED_NOM_SERVEUR = "BBBBBBBBBB";

    private static final String DEFAULT_ARCH = "AAAAAAAAAA";
    private static final String UPDATED_ARCH = "BBBBBBBBBB";

    private static final Long DEFAULT_CPU_NOMBRE = 1L;
    private static final Long UPDATED_CPU_NOMBRE = 2L;

    private static final Long DEFAULT_RAM = 1L;
    private static final Long UPDATED_RAM = 2L;

    private static final Long DEFAULT_MAX_SIZE = 1L;
    private static final Long UPDATED_MAX_SIZE = 2L;

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Double DEFAULT_PRICE_MONTHLY = 1D;
    private static final Double UPDATED_PRICE_MONTHLY = 2D;

    private static final Double DEFAULT_HOURLY_PRICE = 1D;
    private static final Double UPDATED_HOURLY_PRICE = 2D;

    private static final Boolean DEFAULT_IPV_6 = false;
    private static final Boolean UPDATED_IPV_6 = true;

    private static final Long DEFAULT_BAND_WIDTH_INTERNAL = 1L;
    private static final Long UPDATED_BAND_WIDTH_INTERNAL = 2L;

    private static final Long DEFAULT_BAND_WIDTH_EXTERNAL = 1L;
    private static final Long UPDATED_BAND_WIDTH_EXTERNAL = 2L;

    private static final String ENTITY_API_URL = "/api/serveurs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ServeurRepository serveurRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServeurMockMvc;

    private Serveur serveur;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Serveur createEntity(EntityManager em) {
        Serveur serveur = new Serveur()
            .nomHebergeur(DEFAULT_NOM_HEBERGEUR)
            .nomServeur(DEFAULT_NOM_SERVEUR)
            .arch(DEFAULT_ARCH)
            .cpuNombre(DEFAULT_CPU_NOMBRE)
            .ram(DEFAULT_RAM)
            .maxSize(DEFAULT_MAX_SIZE)
            .type(DEFAULT_TYPE)
            .priceMonthly(DEFAULT_PRICE_MONTHLY)
            .hourlyPrice(DEFAULT_HOURLY_PRICE)
            .ipv6(DEFAULT_IPV_6)
            .bandWidthInternal(DEFAULT_BAND_WIDTH_INTERNAL)
            .bandWidthExternal(DEFAULT_BAND_WIDTH_EXTERNAL);
        return serveur;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Serveur createUpdatedEntity(EntityManager em) {
        Serveur serveur = new Serveur()
            .nomHebergeur(UPDATED_NOM_HEBERGEUR)
            .nomServeur(UPDATED_NOM_SERVEUR)
            .arch(UPDATED_ARCH)
            .cpuNombre(UPDATED_CPU_NOMBRE)
            .ram(UPDATED_RAM)
            .maxSize(UPDATED_MAX_SIZE)
            .type(UPDATED_TYPE)
            .priceMonthly(UPDATED_PRICE_MONTHLY)
            .hourlyPrice(UPDATED_HOURLY_PRICE)
            .ipv6(UPDATED_IPV_6)
            .bandWidthInternal(UPDATED_BAND_WIDTH_INTERNAL)
            .bandWidthExternal(UPDATED_BAND_WIDTH_EXTERNAL);
        return serveur;
    }

    @BeforeEach
    public void initTest() {
        serveur = createEntity(em);
    }

    @Test
    @Transactional
    void createServeur() throws Exception {
        int databaseSizeBeforeCreate = serveurRepository.findAll().size();
        // Create the Serveur
        restServeurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(serveur)))
            .andExpect(status().isCreated());

        // Validate the Serveur in the database
        List<Serveur> serveurList = serveurRepository.findAll();
        assertThat(serveurList).hasSize(databaseSizeBeforeCreate + 1);
        Serveur testServeur = serveurList.get(serveurList.size() - 1);
        assertThat(testServeur.getNomHebergeur()).isEqualTo(DEFAULT_NOM_HEBERGEUR);
        assertThat(testServeur.getNomServeur()).isEqualTo(DEFAULT_NOM_SERVEUR);
        assertThat(testServeur.getArch()).isEqualTo(DEFAULT_ARCH);
        assertThat(testServeur.getCpuNombre()).isEqualTo(DEFAULT_CPU_NOMBRE);
        assertThat(testServeur.getRam()).isEqualTo(DEFAULT_RAM);
        assertThat(testServeur.getMaxSize()).isEqualTo(DEFAULT_MAX_SIZE);
        assertThat(testServeur.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testServeur.getPriceMonthly()).isEqualTo(DEFAULT_PRICE_MONTHLY);
        assertThat(testServeur.getHourlyPrice()).isEqualTo(DEFAULT_HOURLY_PRICE);
        assertThat(testServeur.getIpv6()).isEqualTo(DEFAULT_IPV_6);
        assertThat(testServeur.getBandWidthInternal()).isEqualTo(DEFAULT_BAND_WIDTH_INTERNAL);
        assertThat(testServeur.getBandWidthExternal()).isEqualTo(DEFAULT_BAND_WIDTH_EXTERNAL);
    }

    @Test
    @Transactional
    void createServeurWithExistingId() throws Exception {
        // Create the Serveur with an existing ID
        serveur.setId(1L);

        int databaseSizeBeforeCreate = serveurRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restServeurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(serveur)))
            .andExpect(status().isBadRequest());

        // Validate the Serveur in the database
        List<Serveur> serveurList = serveurRepository.findAll();
        assertThat(serveurList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllServeurs() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList
        restServeurMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serveur.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomHebergeur").value(hasItem(DEFAULT_NOM_HEBERGEUR)))
            .andExpect(jsonPath("$.[*].nomServeur").value(hasItem(DEFAULT_NOM_SERVEUR)))
            .andExpect(jsonPath("$.[*].arch").value(hasItem(DEFAULT_ARCH)))
            .andExpect(jsonPath("$.[*].cpuNombre").value(hasItem(DEFAULT_CPU_NOMBRE.intValue())))
            .andExpect(jsonPath("$.[*].ram").value(hasItem(DEFAULT_RAM.intValue())))
            .andExpect(jsonPath("$.[*].maxSize").value(hasItem(DEFAULT_MAX_SIZE.intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].priceMonthly").value(hasItem(DEFAULT_PRICE_MONTHLY.doubleValue())))
            .andExpect(jsonPath("$.[*].hourlyPrice").value(hasItem(DEFAULT_HOURLY_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].ipv6").value(hasItem(DEFAULT_IPV_6.booleanValue())))
            .andExpect(jsonPath("$.[*].bandWidthInternal").value(hasItem(DEFAULT_BAND_WIDTH_INTERNAL.intValue())))
            .andExpect(jsonPath("$.[*].bandWidthExternal").value(hasItem(DEFAULT_BAND_WIDTH_EXTERNAL.intValue())));
    }

    @Test
    @Transactional
    void getServeur() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get the serveur
        restServeurMockMvc
            .perform(get(ENTITY_API_URL_ID, serveur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(serveur.getId().intValue()))
            .andExpect(jsonPath("$.nomHebergeur").value(DEFAULT_NOM_HEBERGEUR))
            .andExpect(jsonPath("$.nomServeur").value(DEFAULT_NOM_SERVEUR))
            .andExpect(jsonPath("$.arch").value(DEFAULT_ARCH))
            .andExpect(jsonPath("$.cpuNombre").value(DEFAULT_CPU_NOMBRE.intValue()))
            .andExpect(jsonPath("$.ram").value(DEFAULT_RAM.intValue()))
            .andExpect(jsonPath("$.maxSize").value(DEFAULT_MAX_SIZE.intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.priceMonthly").value(DEFAULT_PRICE_MONTHLY.doubleValue()))
            .andExpect(jsonPath("$.hourlyPrice").value(DEFAULT_HOURLY_PRICE.doubleValue()))
            .andExpect(jsonPath("$.ipv6").value(DEFAULT_IPV_6.booleanValue()))
            .andExpect(jsonPath("$.bandWidthInternal").value(DEFAULT_BAND_WIDTH_INTERNAL.intValue()))
            .andExpect(jsonPath("$.bandWidthExternal").value(DEFAULT_BAND_WIDTH_EXTERNAL.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingServeur() throws Exception {
        // Get the serveur
        restServeurMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingServeur() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        int databaseSizeBeforeUpdate = serveurRepository.findAll().size();

        // Update the serveur
        Serveur updatedServeur = serveurRepository.findById(serveur.getId()).get();
        // Disconnect from session so that the updates on updatedServeur are not directly saved in db
        em.detach(updatedServeur);
        updatedServeur
            .nomHebergeur(UPDATED_NOM_HEBERGEUR)
            .nomServeur(UPDATED_NOM_SERVEUR)
            .arch(UPDATED_ARCH)
            .cpuNombre(UPDATED_CPU_NOMBRE)
            .ram(UPDATED_RAM)
            .maxSize(UPDATED_MAX_SIZE)
            .type(UPDATED_TYPE)
            .priceMonthly(UPDATED_PRICE_MONTHLY)
            .hourlyPrice(UPDATED_HOURLY_PRICE)
            .ipv6(UPDATED_IPV_6)
            .bandWidthInternal(UPDATED_BAND_WIDTH_INTERNAL)
            .bandWidthExternal(UPDATED_BAND_WIDTH_EXTERNAL);

        restServeurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedServeur.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedServeur))
            )
            .andExpect(status().isOk());

        // Validate the Serveur in the database
        List<Serveur> serveurList = serveurRepository.findAll();
        assertThat(serveurList).hasSize(databaseSizeBeforeUpdate);
        Serveur testServeur = serveurList.get(serveurList.size() - 1);
        assertThat(testServeur.getNomHebergeur()).isEqualTo(UPDATED_NOM_HEBERGEUR);
        assertThat(testServeur.getNomServeur()).isEqualTo(UPDATED_NOM_SERVEUR);
        assertThat(testServeur.getArch()).isEqualTo(UPDATED_ARCH);
        assertThat(testServeur.getCpuNombre()).isEqualTo(UPDATED_CPU_NOMBRE);
        assertThat(testServeur.getRam()).isEqualTo(UPDATED_RAM);
        assertThat(testServeur.getMaxSize()).isEqualTo(UPDATED_MAX_SIZE);
        assertThat(testServeur.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testServeur.getPriceMonthly()).isEqualTo(UPDATED_PRICE_MONTHLY);
        assertThat(testServeur.getHourlyPrice()).isEqualTo(UPDATED_HOURLY_PRICE);
        assertThat(testServeur.getIpv6()).isEqualTo(UPDATED_IPV_6);
        assertThat(testServeur.getBandWidthInternal()).isEqualTo(UPDATED_BAND_WIDTH_INTERNAL);
        assertThat(testServeur.getBandWidthExternal()).isEqualTo(UPDATED_BAND_WIDTH_EXTERNAL);
    }

    @Test
    @Transactional
    void putNonExistingServeur() throws Exception {
        int databaseSizeBeforeUpdate = serveurRepository.findAll().size();
        serveur.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServeurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, serveur.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(serveur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Serveur in the database
        List<Serveur> serveurList = serveurRepository.findAll();
        assertThat(serveurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchServeur() throws Exception {
        int databaseSizeBeforeUpdate = serveurRepository.findAll().size();
        serveur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServeurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(serveur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Serveur in the database
        List<Serveur> serveurList = serveurRepository.findAll();
        assertThat(serveurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamServeur() throws Exception {
        int databaseSizeBeforeUpdate = serveurRepository.findAll().size();
        serveur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServeurMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(serveur)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Serveur in the database
        List<Serveur> serveurList = serveurRepository.findAll();
        assertThat(serveurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateServeurWithPatch() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        int databaseSizeBeforeUpdate = serveurRepository.findAll().size();

        // Update the serveur using partial update
        Serveur partialUpdatedServeur = new Serveur();
        partialUpdatedServeur.setId(serveur.getId());

        partialUpdatedServeur
            .nomHebergeur(UPDATED_NOM_HEBERGEUR)
            .nomServeur(UPDATED_NOM_SERVEUR)
            .ram(UPDATED_RAM)
            .maxSize(UPDATED_MAX_SIZE)
            .bandWidthInternal(UPDATED_BAND_WIDTH_INTERNAL)
            .bandWidthExternal(UPDATED_BAND_WIDTH_EXTERNAL);

        restServeurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServeur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedServeur))
            )
            .andExpect(status().isOk());

        // Validate the Serveur in the database
        List<Serveur> serveurList = serveurRepository.findAll();
        assertThat(serveurList).hasSize(databaseSizeBeforeUpdate);
        Serveur testServeur = serveurList.get(serveurList.size() - 1);
        assertThat(testServeur.getNomHebergeur()).isEqualTo(UPDATED_NOM_HEBERGEUR);
        assertThat(testServeur.getNomServeur()).isEqualTo(UPDATED_NOM_SERVEUR);
        assertThat(testServeur.getArch()).isEqualTo(DEFAULT_ARCH);
        assertThat(testServeur.getCpuNombre()).isEqualTo(DEFAULT_CPU_NOMBRE);
        assertThat(testServeur.getRam()).isEqualTo(UPDATED_RAM);
        assertThat(testServeur.getMaxSize()).isEqualTo(UPDATED_MAX_SIZE);
        assertThat(testServeur.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testServeur.getPriceMonthly()).isEqualTo(DEFAULT_PRICE_MONTHLY);
        assertThat(testServeur.getHourlyPrice()).isEqualTo(DEFAULT_HOURLY_PRICE);
        assertThat(testServeur.getIpv6()).isEqualTo(DEFAULT_IPV_6);
        assertThat(testServeur.getBandWidthInternal()).isEqualTo(UPDATED_BAND_WIDTH_INTERNAL);
        assertThat(testServeur.getBandWidthExternal()).isEqualTo(UPDATED_BAND_WIDTH_EXTERNAL);
    }

    @Test
    @Transactional
    void fullUpdateServeurWithPatch() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        int databaseSizeBeforeUpdate = serveurRepository.findAll().size();

        // Update the serveur using partial update
        Serveur partialUpdatedServeur = new Serveur();
        partialUpdatedServeur.setId(serveur.getId());

        partialUpdatedServeur
            .nomHebergeur(UPDATED_NOM_HEBERGEUR)
            .nomServeur(UPDATED_NOM_SERVEUR)
            .arch(UPDATED_ARCH)
            .cpuNombre(UPDATED_CPU_NOMBRE)
            .ram(UPDATED_RAM)
            .maxSize(UPDATED_MAX_SIZE)
            .type(UPDATED_TYPE)
            .priceMonthly(UPDATED_PRICE_MONTHLY)
            .hourlyPrice(UPDATED_HOURLY_PRICE)
            .ipv6(UPDATED_IPV_6)
            .bandWidthInternal(UPDATED_BAND_WIDTH_INTERNAL)
            .bandWidthExternal(UPDATED_BAND_WIDTH_EXTERNAL);

        restServeurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServeur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedServeur))
            )
            .andExpect(status().isOk());

        // Validate the Serveur in the database
        List<Serveur> serveurList = serveurRepository.findAll();
        assertThat(serveurList).hasSize(databaseSizeBeforeUpdate);
        Serveur testServeur = serveurList.get(serveurList.size() - 1);
        assertThat(testServeur.getNomHebergeur()).isEqualTo(UPDATED_NOM_HEBERGEUR);
        assertThat(testServeur.getNomServeur()).isEqualTo(UPDATED_NOM_SERVEUR);
        assertThat(testServeur.getArch()).isEqualTo(UPDATED_ARCH);
        assertThat(testServeur.getCpuNombre()).isEqualTo(UPDATED_CPU_NOMBRE);
        assertThat(testServeur.getRam()).isEqualTo(UPDATED_RAM);
        assertThat(testServeur.getMaxSize()).isEqualTo(UPDATED_MAX_SIZE);
        assertThat(testServeur.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testServeur.getPriceMonthly()).isEqualTo(UPDATED_PRICE_MONTHLY);
        assertThat(testServeur.getHourlyPrice()).isEqualTo(UPDATED_HOURLY_PRICE);
        assertThat(testServeur.getIpv6()).isEqualTo(UPDATED_IPV_6);
        assertThat(testServeur.getBandWidthInternal()).isEqualTo(UPDATED_BAND_WIDTH_INTERNAL);
        assertThat(testServeur.getBandWidthExternal()).isEqualTo(UPDATED_BAND_WIDTH_EXTERNAL);
    }

    @Test
    @Transactional
    void patchNonExistingServeur() throws Exception {
        int databaseSizeBeforeUpdate = serveurRepository.findAll().size();
        serveur.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServeurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, serveur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(serveur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Serveur in the database
        List<Serveur> serveurList = serveurRepository.findAll();
        assertThat(serveurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchServeur() throws Exception {
        int databaseSizeBeforeUpdate = serveurRepository.findAll().size();
        serveur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServeurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(serveur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Serveur in the database
        List<Serveur> serveurList = serveurRepository.findAll();
        assertThat(serveurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamServeur() throws Exception {
        int databaseSizeBeforeUpdate = serveurRepository.findAll().size();
        serveur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServeurMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(serveur)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Serveur in the database
        List<Serveur> serveurList = serveurRepository.findAll();
        assertThat(serveurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteServeur() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        int databaseSizeBeforeDelete = serveurRepository.findAll().size();

        // Delete the serveur
        restServeurMockMvc
            .perform(delete(ENTITY_API_URL_ID, serveur.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Serveur> serveurList = serveurRepository.findAll();
        assertThat(serveurList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
