package com.labs.servicedesk.service.impl;

import com.labs.servicedesk.domain.entity.Institution;
import com.labs.servicedesk.repository.InstitutionRepository;
import com.labs.servicedesk.service.InstitutionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Institution}.
 */
@Service
@Transactional
public class InstitutionServiceImpl implements InstitutionService {

    private final Logger log = LoggerFactory.getLogger(InstitutionServiceImpl.class);

    private final InstitutionRepository institutionRepository;

    public InstitutionServiceImpl(InstitutionRepository institutionRepository) {
        this.institutionRepository = institutionRepository;
    }

    @Override
    public Institution save(Institution institution) {
        log.debug("Request to save Institution : {}", institution);
        return institutionRepository.save(institution);
    }

    @Override
    public Optional<Institution> partialUpdate(Institution institution) {
        log.debug("Request to partially update Institution : {}", institution);

        return institutionRepository
                .findById(institution.getId())
                .map(
                        existingInstitution -> {
                            if (institution.getInstanceName() != null) {
                                existingInstitution.setInstanceName(institution.getInstanceName());
                            }
                            if (institution.getAddress() != null) {
                                existingInstitution.setAddress(institution.getAddress());
                            }
                            if (institution.getContactNumber() != null) {
                                existingInstitution.setContactNumber(institution.getContactNumber());
                            }

                            return existingInstitution;
                        }
                )
                .map(institutionRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Institution> findAll(Pageable pageable) {
        log.debug("Request to get all Institutions");
        return institutionRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Institution> findOne(Long id) {
        log.debug("Request to get Institution : {}", id);
        return institutionRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Institution : {}", id);
        institutionRepository.deleteById(id);
    }
}
