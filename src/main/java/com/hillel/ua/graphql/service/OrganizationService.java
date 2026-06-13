package com.hillel.ua.graphql.service;

import com.hillel.ua.graphql.dto.OrganizationRequestDto;
import com.hillel.ua.graphql.entities.Organization;
import com.hillel.ua.graphql.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    public Iterable<Organization> findAll() {
        return organizationRepository.findAll();
    }

    public Organization findById(Integer id) {
        return organizationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Organization not found: " + id));
    }

    public Optional<Organization> findByName(String name) {
        return organizationRepository.findByName(name);
    }

    public Organization create(OrganizationRequestDto dto) {
        return organizationRepository.save(new Organization(null, dto.getName(), null, null));
    }

    public Organization update(Integer id, OrganizationRequestDto dto) {
        Organization organization = findById(id);
        organization.setName(dto.getName());
        return organizationRepository.save(organization);
    }

    public Organization delete(Integer id) {
        Organization organization = findById(id);
        organizationRepository.delete(organization);
        return organization;
    }
}
