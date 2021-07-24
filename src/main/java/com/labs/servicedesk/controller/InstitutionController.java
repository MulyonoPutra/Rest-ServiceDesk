package com.labs.servicedesk.controller;

import com.labs.servicedesk.domain.entity.Institution;
import com.labs.servicedesk.exception.BadRequestAlertException;
import com.labs.servicedesk.repository.InstitutionRepository;
import com.labs.servicedesk.service.InstitutionService;
import com.labs.servicedesk.utils.HeaderUtils;
import com.labs.servicedesk.utils.PaginationUtil;
import com.labs.servicedesk.utils.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * REST controller for managing {@link com.labs.servicedesk.domain.entity.Institution}.
 */
@RestController
@RequestMapping("/api")
public class InstitutionController {

  private final Logger log = LoggerFactory.getLogger(
    InstitutionController.class
  );

  private static final String ENTITY_NAME = "institution";

  private final InstitutionService institutionService;

  private final InstitutionRepository institutionRepository;

  public InstitutionController(
    InstitutionService institutionService,
    InstitutionRepository institutionRepository
  ) {
    this.institutionService = institutionService;
    this.institutionRepository = institutionRepository;
  }

  /**
   * {@code POST  /institutions} : Create a new institution.
   *
   * @param institution the institution to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new institution, or with status {@code 400 (Bad Request)} if the institution has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("/institutions")
  public ResponseEntity<Institution> createInstitution(
    @Valid @RequestBody Institution institution
  )
    throws URISyntaxException, BadRequestAlertException {
    log.debug("REST request to save Institution : {}", institution);
    if (institution.getId() != null) {
      throw new BadRequestAlertException(
        "A new institution cannot already have an ID"
      );
    }
    Institution result = institutionService.save(institution);
    return ResponseEntity
      .created(new URI("/api/institutions/" + result.getId()))
      .headers(
        HeaderUtils.createEntityCreationAlert(
          false,
          ENTITY_NAME,
          result.getId().toString()
        )
      )
      .body(result);
  }

  /**
   * {@code PUT  /institutions/:id} : Updates an existing institution.
   *
   * @param id the id of the institution to save.
   * @param institution the institution to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated institution,
   * or with status {@code 400 (Bad Request)} if the institution is not valid,
   * or with status {@code 500 (Internal Server Error)} if the institution couldn't be updated.
   * @throws BadRequestAlertException if the Location URI syntax is incorrect.
   */
  @PutMapping("/institutions/{id}")
  public ResponseEntity<Institution> updateInstitution(
    @PathVariable(value = "id", required = false) final Long id,
    @Valid @RequestBody Institution institution
  )
    throws BadRequestAlertException {
    log.debug("REST request to update Institution : {}, {}", id, institution);
    if (institution.getId() == null) {
      throw new BadRequestAlertException("Invalid id");
    }
    if (!Objects.equals(id, institution.getId())) {
      throw new BadRequestAlertException("Invalid ID");
    }

    if (!institutionRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found");
    }

    Institution result = institutionService.save(institution);
    return ResponseEntity
      .ok()
      .headers(
        HeaderUtils.createEntityUpdateAlert(
          false,
          ENTITY_NAME,
          institution.getId().toString()
        )
      )
      .body(result);
  }

  /**
   * {@code PATCH  /institutions/:id} : Partial updates given fields of an existing institution, field will ignore if it is null
   *
   * @param id the id of the institution to save.
   * @param institution the institution to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated institution,
   * or with status {@code 400 (Bad Request)} if the institution is not valid,
   * or with status {@code 404 (Not Found)} if the institution is not found,
   * or with status {@code 500 (Internal Server Error)} if the institution couldn't be updated.
   * @throws BadRequestAlertException if the Location URI syntax is incorrect.
   */
  @PatchMapping(
    value = "/institutions/{id}",
    consumes = "application/merge-patch+json"
  )
  public ResponseEntity<Institution> partialUpdateInstitution(
    @PathVariable(value = "id", required = false) final Long id,
    @NotNull @RequestBody Institution institution
  )
    throws BadRequestAlertException {
    log.debug(
      "REST request to partial update Institution partially : {}, {}",
      id,
      institution
    );
    if (institution.getId() == null) {
      throw new BadRequestAlertException("Invalid id");
    }
    if (!Objects.equals(id, institution.getId())) {
      throw new BadRequestAlertException("Invalid ID");
    }

    if (!institutionRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found");
    }

    Optional<Institution> result = institutionService.partialUpdate(
      institution
    );

    return ResponseUtil.wrapOrNotFound(
      result,
      HeaderUtils.createEntityUpdateAlert(
        false,
        ENTITY_NAME,
        institution.getId().toString()
      )
    );
  }

  /**
   * {@code GET  /institutions} : get all the institutions.
   *
   * @param pageable the pagination information.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of institutions in body.
   */
  @GetMapping("/institutions")
  public ResponseEntity<List<Institution>> getAllInstitutions(
    Pageable pageable
  ) {
    log.debug("REST request to get a page of Institutions");
    Page<Institution> page = institutionService.findAll(pageable);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
      ServletUriComponentsBuilder.fromCurrentRequest(),
      page
    );
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  /**
   * {@code GET  /institutions/:id} : get the "id" institution.
   *
   * @param id the id of the institution to retrieve.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the institution, or with status {@code 404 (Not Found)}.
   */
  @GetMapping("/institutions/{id}")
  public ResponseEntity<Institution> getInstitution(@PathVariable Long id) {
    log.debug("REST request to get Institution : {}", id);
    Optional<Institution> institution = institutionService.findOne(id);
    return ResponseUtil.wrapOrNotFound(institution);
  }

  /**
   * {@code DELETE  /institutions/:id} : delete the "id" institution.
   *
   * @param id the id of the institution to delete.
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/institutions/{id}")
  public ResponseEntity<Void> deleteInstitution(@PathVariable Long id) {
    log.debug("REST request to delete Institution : {}", id);
    institutionService.delete(id);
    return ResponseEntity
      .noContent()
      .headers(
        HeaderUtils.createEntityDeletionAlert(false, ENTITY_NAME, id.toString())
      )
      .build();
  }
}
