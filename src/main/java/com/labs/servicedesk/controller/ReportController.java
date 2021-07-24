package com.labs.servicedesk.controller;

import com.labs.servicedesk.domain.entity.Report;
import com.labs.servicedesk.exception.BadRequestAlertException;
import com.labs.servicedesk.repository.ReportRepository;
import com.labs.servicedesk.service.ReportService;
import com.labs.servicedesk.utils.HeaderUtils;
import com.labs.servicedesk.utils.PaginationUtil;
import com.labs.servicedesk.utils.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * REST controller for managing {@link com.labs.servicedesk.domain.entity.Report}.
 */
@RestController
@RequestMapping("/api")
public class ReportController {

  private final Logger log = LoggerFactory.getLogger(ReportController.class);

  private static final String ENTITY_NAME = "report";

  private final ReportService reportService;

  private final ReportRepository reportRepository;

  public ReportController(
    ReportService reportService,
    ReportRepository reportRepository
  ) {
    this.reportService = reportService;
    this.reportRepository = reportRepository;
  }

  /**
   * {@code POST  /reports} : Create a new report.
   *
   * @param report the report to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new report, or with status {@code 400 (Bad Request)} if the report has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("/reports")
  public ResponseEntity<Report> createReport(@Valid @RequestBody Report report)
    throws URISyntaxException, BadRequestAlertException {
    log.debug("REST request to save Report : {}", report);
    if (report.getId() != null) {
      throw new BadRequestAlertException(
        "A new report cannot already have an ID"
      );
    }
    Report result = reportService.save(report);
    return ResponseEntity
      .created(new URI("/api/reports/" + result.getId()))
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
   * {@code GET  /reports} : get all the reports.
   *
   * @param pageable the pagination information.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reports in body.
   */
  @GetMapping("/reports")
  public ResponseEntity<List<Report>> getAllReports(Pageable pageable) {
    log.debug("REST request to get a page of Reports");
    Page<Report> page = reportService.findAll(pageable);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
      ServletUriComponentsBuilder.fromCurrentRequest(),
      page
    );
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  /**
   * {@code GET  /reports/:id} : get the "id" report.
   *
   * @param id the id of the report to retrieve.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the report, or with status {@code 404 (Not Found)}.
   */
  @GetMapping("/reports/{id}")
  public ResponseEntity<Report> getReport(@PathVariable Long id) {
    log.debug("REST request to get Report : {}", id);
    Optional<Report> report = reportService.findOne(id);
    return ResponseUtil.wrapOrNotFound(report);
  }

  /**
   * {@code DELETE  /reports/:id} : delete the "id" report.
   *
   * @param id the id of the report to delete.
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/reports/{id}")
  public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
    log.debug("REST request to delete Report : {}", id);
    reportService.delete(id);
    return ResponseEntity
      .noContent()
      .headers(
        HeaderUtils.createEntityDeletionAlert(false, ENTITY_NAME, id.toString())
      )
      .build();
  }
}
