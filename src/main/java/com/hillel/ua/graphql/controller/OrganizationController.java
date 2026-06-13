//package com.hillel.ua.graphql.controller;
//
//import com.hillel.ua.graphql.dto.OrganizationRequestDto;
//import com.hillel.ua.graphql.entities.Department;
//import com.hillel.ua.graphql.entities.Employee;
//import com.hillel.ua.graphql.entities.Organization;
//import com.hillel.ua.graphql.service.OrganizationService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.graphql.data.method.annotation.*;
//import org.springframework.stereotype.Controller;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@Controller
//@RequiredArgsConstructor
//public class OrganizationController {
//
//    private final OrganizationService organizationService;
//
//    @QueryMapping
//    public Iterable<Organization> organizations() {
//        return organizationService.findAll();
//    }
//
//    @QueryMapping
//    public Organization organization(@Argument Integer id) {
//        return organizationService.findById(id);
//    }
//
//    @QueryMapping
//    public Optional<Organization> organizationName(@Argument String name) {
//        return organizationService.findByName(name);
//    }
//
//    @MutationMapping
//    public Organization newOrganization(@Argument OrganizationRequestDto organization) {
//        return organizationService.create(organization);
//    }
//
//    @MutationMapping
//    public Organization updateOrganization(@Argument OrganizationRequestDto organization, @Argument Integer id) {
//        return organizationService.update(id, organization);
//    }
//
//    @MutationMapping
//    public Organization deleteOrganization(@Argument Integer id) {
//        return organizationService.delete(id);
//    }
//
//    @BatchMapping(typeName = "Organization", field = "employees")
//    public Map<Organization, Set<Employee>> employees(List<Organization> organizations) {
//        return organizations.stream()
//                .collect(Collectors.toMap(org -> org, Organization::getEmployees));
//    }
//
//    @BatchMapping(typeName = "Organization", field = "departments")
//    public Map<Organization, Set<Department>> departments(List<Organization> organizations) {
//        return organizations.stream()
//                .collect(Collectors.toMap(org -> org, Organization::getDepartments));
//    }
//}
