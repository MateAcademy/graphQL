package com.hillel.ua.graphql.service;

import com.hillel.ua.graphql.dto.DepartmentRequestDto;
import com.hillel.ua.graphql.entities.Department;
import com.hillel.ua.graphql.entities.Organization;
import com.hillel.ua.graphql.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final OrganizationService organizationService;

    public Iterable<Department> findAll() {
        return departmentRepository.findAll();
    }

    public Department findById(Integer id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found: " + id));
    }

    public Department create(DepartmentRequestDto dto) {
        Organization organization = organizationService.findById(dto.getOrganizationId());
        return departmentRepository.save(new Department(null, dto.getName(), null, organization));
    }

    public Department update(Integer id, DepartmentRequestDto dto) {
        Department department = findById(id);
        department.setName(dto.getName());
        return departmentRepository.save(department);
    }

    public Department delete(Integer id) {
        Department department = findById(id);
        departmentRepository.delete(department);
        return department;
    }
}
