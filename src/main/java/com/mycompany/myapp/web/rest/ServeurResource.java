package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Serveur;
import com.mycompany.myapp.repository.ServeurRepository;
import com.mycompany.myapp.service.ServeurQueryService;
import com.mycompany.myapp.service.ServeurService;
import com.mycompany.myapp.service.criteria.ServeurCriteria;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Serveur}.
 */
@RestController
@RequestMapping("/api")
public class ServeurResource {

    private final Logger log = LoggerFactory.getLogger(ServeurResource.class);

    private static final String ENTITY_NAME = "serveur";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServeurService serveurService;

    private final ServeurRepository serveurRepository;

    private final ServeurQueryService serveurQueryService;

    public ServeurResource(ServeurService serveurService, ServeurRepository serveurRepository, ServeurQueryService serveurQueryService) {
        this.serveurService = serveurService;
        this.serveurRepository = serveurRepository;
        this.serveurQueryService = serveurQueryService;
    }

    /**
     * {@code POST  /serveurs} : Create a new serveur.
     *
     * @param serveur the serveur to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serveur, or with status {@code 400 (Bad Request)} if the serveur has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/serveurs")
    public ResponseEntity<Serveur> createServeur(@RequestBody Serveur serveur) throws URISyntaxException {
        log.debug("REST request to save Serveur : {}", serveur);
        if (serveur.getId() != null) {
            throw new BadRequestAlertException("A new serveur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Serveur result = serveurService.save(serveur);
        return ResponseEntity
            .created(new URI("/api/serveurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /serveurs/:id} : Updates an existing serveur.
     *
     * @param id the id of the serveur to save.
     * @param serveur the serveur to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serveur,
     * or with status {@code 400 (Bad Request)} if the serveur is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serveur couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/serveurs/{id}")
    public ResponseEntity<Serveur> updateServeur(@PathVariable(value = "id", required = false) final Long id, @RequestBody Serveur serveur)
        throws URISyntaxException {
        log.debug("REST request to update Serveur : {}, {}", id, serveur);
        if (serveur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, serveur.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!serveurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Serveur result = serveurService.update(serveur);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serveur.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /serveurs/:id} : Partial updates given fields of an existing serveur, field will ignore if it is null
     *
     * @param id the id of the serveur to save.
     * @param serveur the serveur to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serveur,
     * or with status {@code 400 (Bad Request)} if the serveur is not valid,
     * or with status {@code 404 (Not Found)} if the serveur is not found,
     * or with status {@code 500 (Internal Server Error)} if the serveur couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/serveurs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Serveur> partialUpdateServeur(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Serveur serveur
    ) throws URISyntaxException {
        log.debug("REST request to partial update Serveur partially : {}, {}", id, serveur);
        if (serveur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, serveur.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!serveurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Serveur> result = serveurService.partialUpdate(serveur);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serveur.getId().toString())
        );
    }

    /**
     * {@code GET  /serveurs} : get all the serveurs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serveurs in body.
     */
    @GetMapping("/serveurs")
    public ResponseEntity<List<Serveur>> getAllServeurs(ServeurCriteria criteria) {
        log.debug("REST request to get Serveurs by criteria: {}", criteria);
        List<Serveur> entityList = serveurQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /serveurs/count} : count all the serveurs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/serveurs/count")
    public ResponseEntity<Long> countServeurs(ServeurCriteria criteria) {
        log.debug("REST request to count Serveurs by criteria: {}", criteria);
        return ResponseEntity.ok().body(serveurQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /serveurs/:id} : get the "id" serveur.
     *
     * @param id the id of the serveur to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serveur, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/serveurs/{id}")
    public ResponseEntity<Serveur> getServeur(@PathVariable Long id) {
        log.debug("REST request to get Serveur : {}", id);
        Optional<Serveur> serveur = serveurService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serveur);
    }

    /**
     * {@code DELETE  /serveurs/:id} : delete the "id" serveur.
     *
     * @param id the id of the serveur to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/serveurs/{id}")
    public ResponseEntity<Void> deleteServeur(@PathVariable Long id) {
        log.debug("REST request to delete Serveur : {}", id);
        serveurService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
