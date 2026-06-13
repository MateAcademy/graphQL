//package com.hillel.ua.graphql.controller;
//
//import com.hillel.ua.graphql.dto.EmployeeRequestDto;
//import com.hillel.ua.graphql.entities.Employee;
//import com.hillel.ua.graphql.filter.EmployeeFilter;
//import com.hillel.ua.graphql.service.EmployeeService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.graphql.data.method.annotation.Argument;
//import org.springframework.graphql.data.method.annotation.MutationMapping;
//import org.springframework.graphql.data.method.annotation.QueryMapping;
//import org.springframework.stereotype.Controller;
//
//@Controller
//@RequiredArgsConstructor
//public class EmployeeController {
//
//    private final EmployeeService employeeService;
//
//    @QueryMapping
//    public Iterable<Employee> employees() {
//        return employeeService.findAll();
//    }
//
//    @QueryMapping
//    public Employee employee(@Argument Integer id) {
//        return employeeService.findById(id);
//    }
//
//    @QueryMapping
//    public Iterable<Employee> employeesWithFilter(@Argument EmployeeFilter filter) {
//        return employeeService.findWithFilter(filter);
//    }
//
//    @MutationMapping
//    public Employee newEmployee(@Argument EmployeeRequestDto employee) {
//        return employeeService.create(employee);
//    }
//
//    @MutationMapping
//    public Employee updateEmployee(@Argument EmployeeRequestDto employee, @Argument Integer id) {
//        return employeeService.update(id, employee);
//    }
//
//    @MutationMapping
//    public Employee deleteEmployee(@Argument Integer id) {
//        return employeeService.delete(id);
//    }
//}
