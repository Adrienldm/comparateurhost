package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Serveur;
import com.mycompany.myapp.repository.ServeurRepository;
import com.mycompany.myapp.service.criteria.ServeurCriteria;
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
    private static final Long SMALLER_CPU_NOMBRE = 1L - 1L;

    private static final Long DEFAULT_RAM = 1L;
    private static final Long UPDATED_RAM = 2L;
    private static final Long SMALLER_RAM = 1L - 1L;

    private static final Long DEFAULT_MAX_SIZE = 1L;
    private static final Long UPDATED_MAX_SIZE = 2L;
    private static final Long SMALLER_MAX_SIZE = 1L - 1L;

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Double DEFAULT_PRICE_MONTHLY = 1D;
    private static final Double UPDATED_PRICE_MONTHLY = 2D;
    private static final Double SMALLER_PRICE_MONTHLY = 1D - 1D;

    private static final Double DEFAULT_HOURLY_PRICE = 1D;
    private static final Double UPDATED_HOURLY_PRICE = 2D;
    private static final Double SMALLER_HOURLY_PRICE = 1D - 1D;

    private static final Boolean DEFAULT_IPV_6 = false;
    private static final Boolean UPDATED_IPV_6 = true;

    private static final Long DEFAULT_BAND_WIDTH_INTERNAL = 1L;
    private static final Long UPDATED_BAND_WIDTH_INTERNAL = 2L;
    private static final Long SMALLER_BAND_WIDTH_INTERNAL = 1L - 1L;

    private static final Long DEFAULT_BAND_WIDTH_EXTERNAL = 1L;
    private static final Long UPDATED_BAND_WIDTH_EXTERNAL = 2L;
    private static final Long SMALLER_BAND_WIDTH_EXTERNAL = 1L - 1L;

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
    void getServeursByIdFiltering() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        Long id = serveur.getId();

        defaultServeurShouldBeFound("id.equals=" + id);
        defaultServeurShouldNotBeFound("id.notEquals=" + id);

        defaultServeurShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultServeurShouldNotBeFound("id.greaterThan=" + id);

        defaultServeurShouldBeFound("id.lessThanOrEqual=" + id);
        defaultServeurShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllServeursByNomHebergeurIsEqualToSomething() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where nomHebergeur equals to DEFAULT_NOM_HEBERGEUR
        defaultServeurShouldBeFound("nomHebergeur.equals=" + DEFAULT_NOM_HEBERGEUR);

        // Get all the serveurList where nomHebergeur equals to UPDATED_NOM_HEBERGEUR
        defaultServeurShouldNotBeFound("nomHebergeur.equals=" + UPDATED_NOM_HEBERGEUR);
    }

    @Test
    @Transactional
    void getAllServeursByNomHebergeurIsInShouldWork() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where nomHebergeur in DEFAULT_NOM_HEBERGEUR or UPDATED_NOM_HEBERGEUR
        defaultServeurShouldBeFound("nomHebergeur.in=" + DEFAULT_NOM_HEBERGEUR + "," + UPDATED_NOM_HEBERGEUR);

        // Get all the serveurList where nomHebergeur equals to UPDATED_NOM_HEBERGEUR
        defaultServeurShouldNotBeFound("nomHebergeur.in=" + UPDATED_NOM_HEBERGEUR);
    }

    @Test
    @Transactional
    void getAllServeursByNomHebergeurIsNullOrNotNull() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where nomHebergeur is not null
        defaultServeurShouldBeFound("nomHebergeur.specified=true");

        // Get all the serveurList where nomHebergeur is null
        defaultServeurShouldNotBeFound("nomHebergeur.specified=false");
    }

    @Test
    @Transactional
    void getAllServeursByNomHebergeurContainsSomething() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where nomHebergeur contains DEFAULT_NOM_HEBERGEUR
        defaultServeurShouldBeFound("nomHebergeur.contains=" + DEFAULT_NOM_HEBERGEUR);

        // Get all the serveurList where nomHebergeur contains UPDATED_NOM_HEBERGEUR
        defaultServeurShouldNotBeFound("nomHebergeur.contains=" + UPDATED_NOM_HEBERGEUR);
    }

    @Test
    @Transactional
    void getAllServeursByNomHebergeurNotContainsSomething() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where nomHebergeur does not contain DEFAULT_NOM_HEBERGEUR
        defaultServeurShouldNotBeFound("nomHebergeur.doesNotContain=" + DEFAULT_NOM_HEBERGEUR);

        // Get all the serveurList where nomHebergeur does not contain UPDATED_NOM_HEBERGEUR
        defaultServeurShouldBeFound("nomHebergeur.doesNotContain=" + UPDATED_NOM_HEBERGEUR);
    }

    @Test
    @Transactional
    void getAllServeursByNomServeurIsEqualToSomething() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where nomServeur equals to DEFAULT_NOM_SERVEUR
        defaultServeurShouldBeFound("nomServeur.equals=" + DEFAULT_NOM_SERVEUR);

        // Get all the serveurList where nomServeur equals to UPDATED_NOM_SERVEUR
        defaultServeurShouldNotBeFound("nomServeur.equals=" + UPDATED_NOM_SERVEUR);
    }

    @Test
    @Transactional
    void getAllServeursByNomServeurIsInShouldWork() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where nomServeur in DEFAULT_NOM_SERVEUR or UPDATED_NOM_SERVEUR
        defaultServeurShouldBeFound("nomServeur.in=" + DEFAULT_NOM_SERVEUR + "," + UPDATED_NOM_SERVEUR);

        // Get all the serveurList where nomServeur equals to UPDATED_NOM_SERVEUR
        defaultServeurShouldNotBeFound("nomServeur.in=" + UPDATED_NOM_SERVEUR);
    }

    @Test
    @Transactional
    void getAllServeursByNomServeurIsNullOrNotNull() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where nomServeur is not null
        defaultServeurShouldBeFound("nomServeur.specified=true");

        // Get all the serveurList where nomServeur is null
        defaultServeurShouldNotBeFound("nomServeur.specified=false");
    }

    @Test
    @Transactional
    void getAllServeursByNomServeurContainsSomething() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where nomServeur contains DEFAULT_NOM_SERVEUR
        defaultServeurShouldBeFound("nomServeur.contains=" + DEFAULT_NOM_SERVEUR);

        // Get all the serveurList where nomServeur contains UPDATED_NOM_SERVEUR
        defaultServeurShouldNotBeFound("nomServeur.contains=" + UPDATED_NOM_SERVEUR);
    }

    @Test
    @Transactional
    void getAllServeursByNomServeurNotContainsSomething() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where nomServeur does not contain DEFAULT_NOM_SERVEUR
        defaultServeurShouldNotBeFound("nomServeur.doesNotContain=" + DEFAULT_NOM_SERVEUR);

        // Get all the serveurList where nomServeur does not contain UPDATED_NOM_SERVEUR
        defaultServeurShouldBeFound("nomServeur.doesNotContain=" + UPDATED_NOM_SERVEUR);
    }

    @Test
    @Transactional
    void getAllServeursByArchIsEqualToSomething() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where arch equals to DEFAULT_ARCH
        defaultServeurShouldBeFound("arch.equals=" + DEFAULT_ARCH);

        // Get all the serveurList where arch equals to UPDATED_ARCH
        defaultServeurShouldNotBeFound("arch.equals=" + UPDATED_ARCH);
    }

    @Test
    @Transactional
    void getAllServeursByArchIsInShouldWork() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where arch in DEFAULT_ARCH or UPDATED_ARCH
        defaultServeurShouldBeFound("arch.in=" + DEFAULT_ARCH + "," + UPDATED_ARCH);

        // Get all the serveurList where arch equals to UPDATED_ARCH
        defaultServeurShouldNotBeFound("arch.in=" + UPDATED_ARCH);
    }

    @Test
    @Transactional
    void getAllServeursByArchIsNullOrNotNull() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where arch is not null
        defaultServeurShouldBeFound("arch.specified=true");

        // Get all the serveurList where arch is null
        defaultServeurShouldNotBeFound("arch.specified=false");
    }

    @Test
    @Transactional
    void getAllServeursByArchContainsSomething() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where arch contains DEFAULT_ARCH
        defaultServeurShouldBeFound("arch.contains=" + DEFAULT_ARCH);

        // Get all the serveurList where arch contains UPDATED_ARCH
        defaultServeurShouldNotBeFound("arch.contains=" + UPDATED_ARCH);
    }

    @Test
    @Transactional
    void getAllServeursByArchNotContainsSomething() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where arch does not contain DEFAULT_ARCH
        defaultServeurShouldNotBeFound("arch.doesNotContain=" + DEFAULT_ARCH);

        // Get all the serveurList where arch does not contain UPDATED_ARCH
        defaultServeurShouldBeFound("arch.doesNotContain=" + UPDATED_ARCH);
    }

    @Test
    @Transactional
    void getAllServeursByCpuNombreIsEqualToSomething() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where cpuNombre equals to DEFAULT_CPU_NOMBRE
        defaultServeurShouldBeFound("cpuNombre.equals=" + DEFAULT_CPU_NOMBRE);

        // Get all the serveurList where cpuNombre equals to UPDATED_CPU_NOMBRE
        defaultServeurShouldNotBeFound("cpuNombre.equals=" + UPDATED_CPU_NOMBRE);
    }

    @Test
    @Transactional
    void getAllServeursByCpuNombreIsInShouldWork() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where cpuNombre in DEFAULT_CPU_NOMBRE or UPDATED_CPU_NOMBRE
        defaultServeurShouldBeFound("cpuNombre.in=" + DEFAULT_CPU_NOMBRE + "," + UPDATED_CPU_NOMBRE);

        // Get all the serveurList where cpuNombre equals to UPDATED_CPU_NOMBRE
        defaultServeurShouldNotBeFound("cpuNombre.in=" + UPDATED_CPU_NOMBRE);
    }

    @Test
    @Transactional
    void getAllServeursByCpuNombreIsNullOrNotNull() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where cpuNombre is not null
        defaultServeurShouldBeFound("cpuNombre.specified=true");

        // Get all the serveurList where cpuNombre is null
        defaultServeurShouldNotBeFound("cpuNombre.specified=false");
    }

    @Test
    @Transactional
    void getAllServeursByCpuNombreIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where cpuNombre is greater than or equal to DEFAULT_CPU_NOMBRE
        defaultServeurShouldBeFound("cpuNombre.greaterThanOrEqual=" + DEFAULT_CPU_NOMBRE);

        // Get all the serveurList where cpuNombre is greater than or equal to UPDATED_CPU_NOMBRE
        defaultServeurShouldNotBeFound("cpuNombre.greaterThanOrEqual=" + UPDATED_CPU_NOMBRE);
    }

    @Test
    @Transactional
    void getAllServeursByCpuNombreIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where cpuNombre is less than or equal to DEFAULT_CPU_NOMBRE
        defaultServeurShouldBeFound("cpuNombre.lessThanOrEqual=" + DEFAULT_CPU_NOMBRE);

        // Get all the serveurList where cpuNombre is less than or equal to SMALLER_CPU_NOMBRE
        defaultServeurShouldNotBeFound("cpuNombre.lessThanOrEqual=" + SMALLER_CPU_NOMBRE);
    }

    @Test
    @Transactional
    void getAllServeursByCpuNombreIsLessThanSomething() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where cpuNombre is less than DEFAULT_CPU_NOMBRE
        defaultServeurShouldNotBeFound("cpuNombre.lessThan=" + DEFAULT_CPU_NOMBRE);

        // Get all the serveurList where cpuNombre is less than UPDATED_CPU_NOMBRE
        defaultServeurShouldBeFound("cpuNombre.lessThan=" + UPDATED_CPU_NOMBRE);
    }

    @Test
    @Transactional
    void getAllServeursByCpuNombreIsGreaterThanSomething() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where cpuNombre is greater than DEFAULT_CPU_NOMBRE
        defaultServeurShouldNotBeFound("cpuNombre.greaterThan=" + DEFAULT_CPU_NOMBRE);

        // Get all the serveurList where cpuNombre is greater than SMALLER_CPU_NOMBRE
        defaultServeurShouldBeFound("cpuNombre.greaterThan=" + SMALLER_CPU_NOMBRE);
    }

    @Test
    @Transactional
    void getAllServeursByRamIsEqualToSomething() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where ram equals to DEFAULT_RAM
        defaultServeurShouldBeFound("ram.equals=" + DEFAULT_RAM);

        // Get all the serveurList where ram equals to UPDATED_RAM
        defaultServeurShouldNotBeFound("ram.equals=" + UPDATED_RAM);
    }

    @Test
    @Transactional
    void getAllServeursByRamIsInShouldWork() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where ram in DEFAULT_RAM or UPDATED_RAM
        defaultServeurShouldBeFound("ram.in=" + DEFAULT_RAM + "," + UPDATED_RAM);

        // Get all the serveurList where ram equals to UPDATED_RAM
        defaultServeurShouldNotBeFound("ram.in=" + UPDATED_RAM);
    }

    @Test
    @Transactional
    void getAllServeursByRamIsNullOrNotNull() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where ram is not null
        defaultServeurShouldBeFound("ram.specified=true");

        // Get all the serveurList where ram is null
        defaultServeurShouldNotBeFound("ram.specified=false");
    }

    @Test
    @Transactional
    void getAllServeursByRamIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where ram is greater than or equal to DEFAULT_RAM
        defaultServeurShouldBeFound("ram.greaterThanOrEqual=" + DEFAULT_RAM);

        // Get all the serveurList where ram is greater than or equal to UPDATED_RAM
        defaultServeurShouldNotBeFound("ram.greaterThanOrEqual=" + UPDATED_RAM);
    }

    @Test
    @Transactional
    void getAllServeursByRamIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where ram is less than or equal to DEFAULT_RAM
        defaultServeurShouldBeFound("ram.lessThanOrEqual=" + DEFAULT_RAM);

        // Get all the serveurList where ram is less than or equal to SMALLER_RAM
        defaultServeurShouldNotBeFound("ram.lessThanOrEqual=" + SMALLER_RAM);
    }

    @Test
    @Transactional
    void getAllServeursByRamIsLessThanSomething() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where ram is less than DEFAULT_RAM
        defaultServeurShouldNotBeFound("ram.lessThan=" + DEFAULT_RAM);

        // Get all the serveurList where ram is less than UPDATED_RAM
        defaultServeurShouldBeFound("ram.lessThan=" + UPDATED_RAM);
    }

    @Test
    @Transactional
    void getAllServeursByRamIsGreaterThanSomething() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where ram is greater than DEFAULT_RAM
        defaultServeurShouldNotBeFound("ram.greaterThan=" + DEFAULT_RAM);

        // Get all the serveurList where ram is greater than SMALLER_RAM
        defaultServeurShouldBeFound("ram.greaterThan=" + SMALLER_RAM);
    }

    @Test
    @Transactional
    void getAllServeursByMaxSizeIsEqualToSomething() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where maxSize equals to DEFAULT_MAX_SIZE
        defaultServeurShouldBeFound("maxSize.equals=" + DEFAULT_MAX_SIZE);

        // Get all the serveurList where maxSize equals to UPDATED_MAX_SIZE
        defaultServeurShouldNotBeFound("maxSize.equals=" + UPDATED_MAX_SIZE);
    }

    @Test
    @Transactional
    void getAllServeursByMaxSizeIsInShouldWork() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where maxSize in DEFAULT_MAX_SIZE or UPDATED_MAX_SIZE
        defaultServeurShouldBeFound("maxSize.in=" + DEFAULT_MAX_SIZE + "," + UPDATED_MAX_SIZE);

        // Get all the serveurList where maxSize equals to UPDATED_MAX_SIZE
        defaultServeurShouldNotBeFound("maxSize.in=" + UPDATED_MAX_SIZE);
    }

    @Test
    @Transactional
    void getAllServeursByMaxSizeIsNullOrNotNull() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where maxSize is not null
        defaultServeurShouldBeFound("maxSize.specified=true");

        // Get all the serveurList where maxSize is null
        defaultServeurShouldNotBeFound("maxSize.specified=false");
    }

    @Test
    @Transactional
    void getAllServeursByMaxSizeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where maxSize is greater than or equal to DEFAULT_MAX_SIZE
        defaultServeurShouldBeFound("maxSize.greaterThanOrEqual=" + DEFAULT_MAX_SIZE);

        // Get all the serveurList where maxSize is greater than or equal to UPDATED_MAX_SIZE
        defaultServeurShouldNotBeFound("maxSize.greaterThanOrEqual=" + UPDATED_MAX_SIZE);
    }

    @Test
    @Transactional
    void getAllServeursByMaxSizeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where maxSize is less than or equal to DEFAULT_MAX_SIZE
        defaultServeurShouldBeFound("maxSize.lessThanOrEqual=" + DEFAULT_MAX_SIZE);

        // Get all the serveurList where maxSize is less than or equal to SMALLER_MAX_SIZE
        defaultServeurShouldNotBeFound("maxSize.lessThanOrEqual=" + SMALLER_MAX_SIZE);
    }

    @Test
    @Transactional
    void getAllServeursByMaxSizeIsLessThanSomething() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where maxSize is less than DEFAULT_MAX_SIZE
        defaultServeurShouldNotBeFound("maxSize.lessThan=" + DEFAULT_MAX_SIZE);

        // Get all the serveurList where maxSize is less than UPDATED_MAX_SIZE
        defaultServeurShouldBeFound("maxSize.lessThan=" + UPDATED_MAX_SIZE);
    }

    @Test
    @Transactional
    void getAllServeursByMaxSizeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where maxSize is greater than DEFAULT_MAX_SIZE
        defaultServeurShouldNotBeFound("maxSize.greaterThan=" + DEFAULT_MAX_SIZE);

        // Get all the serveurList where maxSize is greater than SMALLER_MAX_SIZE
        defaultServeurShouldBeFound("maxSize.greaterThan=" + SMALLER_MAX_SIZE);
    }

    @Test
    @Transactional
    void getAllServeursByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where type equals to DEFAULT_TYPE
        defaultServeurShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the serveurList where type equals to UPDATED_TYPE
        defaultServeurShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllServeursByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultServeurShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the serveurList where type equals to UPDATED_TYPE
        defaultServeurShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllServeursByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where type is not null
        defaultServeurShouldBeFound("type.specified=true");

        // Get all the serveurList where type is null
        defaultServeurShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllServeursByTypeContainsSomething() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where type contains DEFAULT_TYPE
        defaultServeurShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the serveurList where type contains UPDATED_TYPE
        defaultServeurShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllServeursByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where type does not contain DEFAULT_TYPE
        defaultServeurShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the serveurList where type does not contain UPDATED_TYPE
        defaultServeurShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllServeursByPriceMonthlyIsEqualToSomething() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where priceMonthly equals to DEFAULT_PRICE_MONTHLY
        defaultServeurShouldBeFound("priceMonthly.equals=" + DEFAULT_PRICE_MONTHLY);

        // Get all the serveurList where priceMonthly equals to UPDATED_PRICE_MONTHLY
        defaultServeurShouldNotBeFound("priceMonthly.equals=" + UPDATED_PRICE_MONTHLY);
    }

    @Test
    @Transactional
    void getAllServeursByPriceMonthlyIsInShouldWork() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where priceMonthly in DEFAULT_PRICE_MONTHLY or UPDATED_PRICE_MONTHLY
        defaultServeurShouldBeFound("priceMonthly.in=" + DEFAULT_PRICE_MONTHLY + "," + UPDATED_PRICE_MONTHLY);

        // Get all the serveurList where priceMonthly equals to UPDATED_PRICE_MONTHLY
        defaultServeurShouldNotBeFound("priceMonthly.in=" + UPDATED_PRICE_MONTHLY);
    }

    @Test
    @Transactional
    void getAllServeursByPriceMonthlyIsNullOrNotNull() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where priceMonthly is not null
        defaultServeurShouldBeFound("priceMonthly.specified=true");

        // Get all the serveurList where priceMonthly is null
        defaultServeurShouldNotBeFound("priceMonthly.specified=false");
    }

    @Test
    @Transactional
    void getAllServeursByPriceMonthlyIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where priceMonthly is greater than or equal to DEFAULT_PRICE_MONTHLY
        defaultServeurShouldBeFound("priceMonthly.greaterThanOrEqual=" + DEFAULT_PRICE_MONTHLY);

        // Get all the serveurList where priceMonthly is greater than or equal to UPDATED_PRICE_MONTHLY
        defaultServeurShouldNotBeFound("priceMonthly.greaterThanOrEqual=" + UPDATED_PRICE_MONTHLY);
    }

    @Test
    @Transactional
    void getAllServeursByPriceMonthlyIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where priceMonthly is less than or equal to DEFAULT_PRICE_MONTHLY
        defaultServeurShouldBeFound("priceMonthly.lessThanOrEqual=" + DEFAULT_PRICE_MONTHLY);

        // Get all the serveurList where priceMonthly is less than or equal to SMALLER_PRICE_MONTHLY
        defaultServeurShouldNotBeFound("priceMonthly.lessThanOrEqual=" + SMALLER_PRICE_MONTHLY);
    }

    @Test
    @Transactional
    void getAllServeursByPriceMonthlyIsLessThanSomething() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where priceMonthly is less than DEFAULT_PRICE_MONTHLY
        defaultServeurShouldNotBeFound("priceMonthly.lessThan=" + DEFAULT_PRICE_MONTHLY);

        // Get all the serveurList where priceMonthly is less than UPDATED_PRICE_MONTHLY
        defaultServeurShouldBeFound("priceMonthly.lessThan=" + UPDATED_PRICE_MONTHLY);
    }

    @Test
    @Transactional
    void getAllServeursByPriceMonthlyIsGreaterThanSomething() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where priceMonthly is greater than DEFAULT_PRICE_MONTHLY
        defaultServeurShouldNotBeFound("priceMonthly.greaterThan=" + DEFAULT_PRICE_MONTHLY);

        // Get all the serveurList where priceMonthly is greater than SMALLER_PRICE_MONTHLY
        defaultServeurShouldBeFound("priceMonthly.greaterThan=" + SMALLER_PRICE_MONTHLY);
    }

    @Test
    @Transactional
    void getAllServeursByHourlyPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where hourlyPrice equals to DEFAULT_HOURLY_PRICE
        defaultServeurShouldBeFound("hourlyPrice.equals=" + DEFAULT_HOURLY_PRICE);

        // Get all the serveurList where hourlyPrice equals to UPDATED_HOURLY_PRICE
        defaultServeurShouldNotBeFound("hourlyPrice.equals=" + UPDATED_HOURLY_PRICE);
    }

    @Test
    @Transactional
    void getAllServeursByHourlyPriceIsInShouldWork() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where hourlyPrice in DEFAULT_HOURLY_PRICE or UPDATED_HOURLY_PRICE
        defaultServeurShouldBeFound("hourlyPrice.in=" + DEFAULT_HOURLY_PRICE + "," + UPDATED_HOURLY_PRICE);

        // Get all the serveurList where hourlyPrice equals to UPDATED_HOURLY_PRICE
        defaultServeurShouldNotBeFound("hourlyPrice.in=" + UPDATED_HOURLY_PRICE);
    }

    @Test
    @Transactional
    void getAllServeursByHourlyPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where hourlyPrice is not null
        defaultServeurShouldBeFound("hourlyPrice.specified=true");

        // Get all the serveurList where hourlyPrice is null
        defaultServeurShouldNotBeFound("hourlyPrice.specified=false");
    }

    @Test
    @Transactional
    void getAllServeursByHourlyPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where hourlyPrice is greater than or equal to DEFAULT_HOURLY_PRICE
        defaultServeurShouldBeFound("hourlyPrice.greaterThanOrEqual=" + DEFAULT_HOURLY_PRICE);

        // Get all the serveurList where hourlyPrice is greater than or equal to UPDATED_HOURLY_PRICE
        defaultServeurShouldNotBeFound("hourlyPrice.greaterThanOrEqual=" + UPDATED_HOURLY_PRICE);
    }

    @Test
    @Transactional
    void getAllServeursByHourlyPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where hourlyPrice is less than or equal to DEFAULT_HOURLY_PRICE
        defaultServeurShouldBeFound("hourlyPrice.lessThanOrEqual=" + DEFAULT_HOURLY_PRICE);

        // Get all the serveurList where hourlyPrice is less than or equal to SMALLER_HOURLY_PRICE
        defaultServeurShouldNotBeFound("hourlyPrice.lessThanOrEqual=" + SMALLER_HOURLY_PRICE);
    }

    @Test
    @Transactional
    void getAllServeursByHourlyPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where hourlyPrice is less than DEFAULT_HOURLY_PRICE
        defaultServeurShouldNotBeFound("hourlyPrice.lessThan=" + DEFAULT_HOURLY_PRICE);

        // Get all the serveurList where hourlyPrice is less than UPDATED_HOURLY_PRICE
        defaultServeurShouldBeFound("hourlyPrice.lessThan=" + UPDATED_HOURLY_PRICE);
    }

    @Test
    @Transactional
    void getAllServeursByHourlyPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where hourlyPrice is greater than DEFAULT_HOURLY_PRICE
        defaultServeurShouldNotBeFound("hourlyPrice.greaterThan=" + DEFAULT_HOURLY_PRICE);

        // Get all the serveurList where hourlyPrice is greater than SMALLER_HOURLY_PRICE
        defaultServeurShouldBeFound("hourlyPrice.greaterThan=" + SMALLER_HOURLY_PRICE);
    }

    @Test
    @Transactional
    void getAllServeursByIpv6IsEqualToSomething() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where ipv6 equals to DEFAULT_IPV_6
        defaultServeurShouldBeFound("ipv6.equals=" + DEFAULT_IPV_6);

        // Get all the serveurList where ipv6 equals to UPDATED_IPV_6
        defaultServeurShouldNotBeFound("ipv6.equals=" + UPDATED_IPV_6);
    }

    @Test
    @Transactional
    void getAllServeursByIpv6IsInShouldWork() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where ipv6 in DEFAULT_IPV_6 or UPDATED_IPV_6
        defaultServeurShouldBeFound("ipv6.in=" + DEFAULT_IPV_6 + "," + UPDATED_IPV_6);

        // Get all the serveurList where ipv6 equals to UPDATED_IPV_6
        defaultServeurShouldNotBeFound("ipv6.in=" + UPDATED_IPV_6);
    }

    @Test
    @Transactional
    void getAllServeursByIpv6IsNullOrNotNull() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where ipv6 is not null
        defaultServeurShouldBeFound("ipv6.specified=true");

        // Get all the serveurList where ipv6 is null
        defaultServeurShouldNotBeFound("ipv6.specified=false");
    }

    @Test
    @Transactional
    void getAllServeursByBandWidthInternalIsEqualToSomething() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where bandWidthInternal equals to DEFAULT_BAND_WIDTH_INTERNAL
        defaultServeurShouldBeFound("bandWidthInternal.equals=" + DEFAULT_BAND_WIDTH_INTERNAL);

        // Get all the serveurList where bandWidthInternal equals to UPDATED_BAND_WIDTH_INTERNAL
        defaultServeurShouldNotBeFound("bandWidthInternal.equals=" + UPDATED_BAND_WIDTH_INTERNAL);
    }

    @Test
    @Transactional
    void getAllServeursByBandWidthInternalIsInShouldWork() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where bandWidthInternal in DEFAULT_BAND_WIDTH_INTERNAL or UPDATED_BAND_WIDTH_INTERNAL
        defaultServeurShouldBeFound("bandWidthInternal.in=" + DEFAULT_BAND_WIDTH_INTERNAL + "," + UPDATED_BAND_WIDTH_INTERNAL);

        // Get all the serveurList where bandWidthInternal equals to UPDATED_BAND_WIDTH_INTERNAL
        defaultServeurShouldNotBeFound("bandWidthInternal.in=" + UPDATED_BAND_WIDTH_INTERNAL);
    }

    @Test
    @Transactional
    void getAllServeursByBandWidthInternalIsNullOrNotNull() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where bandWidthInternal is not null
        defaultServeurShouldBeFound("bandWidthInternal.specified=true");

        // Get all the serveurList where bandWidthInternal is null
        defaultServeurShouldNotBeFound("bandWidthInternal.specified=false");
    }

    @Test
    @Transactional
    void getAllServeursByBandWidthInternalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where bandWidthInternal is greater than or equal to DEFAULT_BAND_WIDTH_INTERNAL
        defaultServeurShouldBeFound("bandWidthInternal.greaterThanOrEqual=" + DEFAULT_BAND_WIDTH_INTERNAL);

        // Get all the serveurList where bandWidthInternal is greater than or equal to UPDATED_BAND_WIDTH_INTERNAL
        defaultServeurShouldNotBeFound("bandWidthInternal.greaterThanOrEqual=" + UPDATED_BAND_WIDTH_INTERNAL);
    }

    @Test
    @Transactional
    void getAllServeursByBandWidthInternalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where bandWidthInternal is less than or equal to DEFAULT_BAND_WIDTH_INTERNAL
        defaultServeurShouldBeFound("bandWidthInternal.lessThanOrEqual=" + DEFAULT_BAND_WIDTH_INTERNAL);

        // Get all the serveurList where bandWidthInternal is less than or equal to SMALLER_BAND_WIDTH_INTERNAL
        defaultServeurShouldNotBeFound("bandWidthInternal.lessThanOrEqual=" + SMALLER_BAND_WIDTH_INTERNAL);
    }

    @Test
    @Transactional
    void getAllServeursByBandWidthInternalIsLessThanSomething() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where bandWidthInternal is less than DEFAULT_BAND_WIDTH_INTERNAL
        defaultServeurShouldNotBeFound("bandWidthInternal.lessThan=" + DEFAULT_BAND_WIDTH_INTERNAL);

        // Get all the serveurList where bandWidthInternal is less than UPDATED_BAND_WIDTH_INTERNAL
        defaultServeurShouldBeFound("bandWidthInternal.lessThan=" + UPDATED_BAND_WIDTH_INTERNAL);
    }

    @Test
    @Transactional
    void getAllServeursByBandWidthInternalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where bandWidthInternal is greater than DEFAULT_BAND_WIDTH_INTERNAL
        defaultServeurShouldNotBeFound("bandWidthInternal.greaterThan=" + DEFAULT_BAND_WIDTH_INTERNAL);

        // Get all the serveurList where bandWidthInternal is greater than SMALLER_BAND_WIDTH_INTERNAL
        defaultServeurShouldBeFound("bandWidthInternal.greaterThan=" + SMALLER_BAND_WIDTH_INTERNAL);
    }

    @Test
    @Transactional
    void getAllServeursByBandWidthExternalIsEqualToSomething() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where bandWidthExternal equals to DEFAULT_BAND_WIDTH_EXTERNAL
        defaultServeurShouldBeFound("bandWidthExternal.equals=" + DEFAULT_BAND_WIDTH_EXTERNAL);

        // Get all the serveurList where bandWidthExternal equals to UPDATED_BAND_WIDTH_EXTERNAL
        defaultServeurShouldNotBeFound("bandWidthExternal.equals=" + UPDATED_BAND_WIDTH_EXTERNAL);
    }

    @Test
    @Transactional
    void getAllServeursByBandWidthExternalIsInShouldWork() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where bandWidthExternal in DEFAULT_BAND_WIDTH_EXTERNAL or UPDATED_BAND_WIDTH_EXTERNAL
        defaultServeurShouldBeFound("bandWidthExternal.in=" + DEFAULT_BAND_WIDTH_EXTERNAL + "," + UPDATED_BAND_WIDTH_EXTERNAL);

        // Get all the serveurList where bandWidthExternal equals to UPDATED_BAND_WIDTH_EXTERNAL
        defaultServeurShouldNotBeFound("bandWidthExternal.in=" + UPDATED_BAND_WIDTH_EXTERNAL);
    }

    @Test
    @Transactional
    void getAllServeursByBandWidthExternalIsNullOrNotNull() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where bandWidthExternal is not null
        defaultServeurShouldBeFound("bandWidthExternal.specified=true");

        // Get all the serveurList where bandWidthExternal is null
        defaultServeurShouldNotBeFound("bandWidthExternal.specified=false");
    }

    @Test
    @Transactional
    void getAllServeursByBandWidthExternalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where bandWidthExternal is greater than or equal to DEFAULT_BAND_WIDTH_EXTERNAL
        defaultServeurShouldBeFound("bandWidthExternal.greaterThanOrEqual=" + DEFAULT_BAND_WIDTH_EXTERNAL);

        // Get all the serveurList where bandWidthExternal is greater than or equal to UPDATED_BAND_WIDTH_EXTERNAL
        defaultServeurShouldNotBeFound("bandWidthExternal.greaterThanOrEqual=" + UPDATED_BAND_WIDTH_EXTERNAL);
    }

    @Test
    @Transactional
    void getAllServeursByBandWidthExternalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where bandWidthExternal is less than or equal to DEFAULT_BAND_WIDTH_EXTERNAL
        defaultServeurShouldBeFound("bandWidthExternal.lessThanOrEqual=" + DEFAULT_BAND_WIDTH_EXTERNAL);

        // Get all the serveurList where bandWidthExternal is less than or equal to SMALLER_BAND_WIDTH_EXTERNAL
        defaultServeurShouldNotBeFound("bandWidthExternal.lessThanOrEqual=" + SMALLER_BAND_WIDTH_EXTERNAL);
    }

    @Test
    @Transactional
    void getAllServeursByBandWidthExternalIsLessThanSomething() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where bandWidthExternal is less than DEFAULT_BAND_WIDTH_EXTERNAL
        defaultServeurShouldNotBeFound("bandWidthExternal.lessThan=" + DEFAULT_BAND_WIDTH_EXTERNAL);

        // Get all the serveurList where bandWidthExternal is less than UPDATED_BAND_WIDTH_EXTERNAL
        defaultServeurShouldBeFound("bandWidthExternal.lessThan=" + UPDATED_BAND_WIDTH_EXTERNAL);
    }

    @Test
    @Transactional
    void getAllServeursByBandWidthExternalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where bandWidthExternal is greater than DEFAULT_BAND_WIDTH_EXTERNAL
        defaultServeurShouldNotBeFound("bandWidthExternal.greaterThan=" + DEFAULT_BAND_WIDTH_EXTERNAL);

        // Get all the serveurList where bandWidthExternal is greater than SMALLER_BAND_WIDTH_EXTERNAL
        defaultServeurShouldBeFound("bandWidthExternal.greaterThan=" + SMALLER_BAND_WIDTH_EXTERNAL);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultServeurShouldBeFound(String filter) throws Exception {
        restServeurMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
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

        // Check, that the count call also returns 1
        restServeurMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultServeurShouldNotBeFound(String filter) throws Exception {
        restServeurMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restServeurMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
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
