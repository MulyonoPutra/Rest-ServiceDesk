package com.labs.servicedesk.service;

import java.util.Optional;

import com.labs.servicedesk.domain.entity.Institution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Institution}.
 */
public interface InstitutionService {
    /**
     * Save a institution.
     *
     * @param institution the entity to save.
     * @return the persisted entity.
     */
    Institution save(Institution institution);

    /**
     * Partially updates a institution.
     *
     * @param institution the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Institution> partialUpdate(Institution institution);

    /**
     * Get all the institutions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Institution> findAll(Pageable pageable);

    /**
     * Get the "id" institution.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Institution> findOne(Long id);

    /**
     * Delete the "id" institution.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
