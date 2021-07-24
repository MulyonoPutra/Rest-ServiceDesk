package com.labs.servicedesk.repository;

import com.labs.servicedesk.domain.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
//    @Query("select report from Report report where report.user.login = ?#{principal.username}")
//    List<Report> findByUserIsCurrentUser();
}
