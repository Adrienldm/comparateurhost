package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Serveur;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Serveur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServeurRepository extends JpaRepository<Serveur, Long> {}
