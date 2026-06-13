//package com.hillel.ua.graphql.controller;
//
//import com.hillel.ua.graphql.dto.DepartmentRequestDto;
//import com.hillel.ua.graphql.entities.Department;
//import com.hillel.ua.graphql.entities.Employee;
//import com.hillel.ua.graphql.entities.Organization;
//import com.hillel.ua.graphql.service.DepartmentService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.graphql.data.method.annotation.*;
//import org.springframework.stereotype.Controller;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@Controller
//@RequiredArgsConstructor
//public class DepartmentController {
//
//    private final DepartmentService departmentService;
//
//    @QueryMapping
//    public Iterable<Department> departments() {
//        return departmentService.findAll();
//    }
//
//    @QueryMapping
//    public Department department(@Argument Integer id) {
//        return departmentService.findById(id);
//    }
//
//    @MutationMapping
//    public Department newDepartment(@Argument DepartmentRequestDto department) {
//        return departmentService.create(department);
//    }
//
//    @MutationMapping
//    public Department updateDepartment(@Argument DepartmentRequestDto department, @Argument Integer id) {
//        return departmentService.update(id, department);
//    }
//
//    @MutationMapping
//    public Department deleteDepartment(@Argument Integer id) {
//        return departmentService.delete(id);
//    }
//
//    @SchemaMapping(typeName = "Department", field = "organization")
//    public Organization organization(Department department) {
//        return department.getOrganization();
//    }
//
//    @BatchMapping(typeName = "Department", field = "employees")
//    public Map<Department, Set<Employee>> employees(List<Department> departments) {
//        return departments.stream()
//                .collect(Collectors.toMap(dep -> dep, Department::getEmployees));
//    }
//}
