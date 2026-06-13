package com.hillel.ua.graphql.service;

import com.hillel.ua.graphql.dto.EmployeeRequestDto;
import com.hillel.ua.graphql.entities.Department;
import com.hillel.ua.graphql.entities.Employee;
import com.hillel.ua.graphql.entities.Organization;
import com.hillel.ua.graphql.filter.EmployeeFilter;
import com.hillel.ua.graphql.filter.FilterField;
import com.hillel.ua.graphql.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentService departmentService;
    private final OrganizationService organizationService;

    public Iterable<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Employee findById(Integer id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found: " + id));
    }

    public Iterable<Employee> findWithFilter(EmployeeFilter filter) {
        Specification<Employee> spec = null;
        if (filter.getSalary() != null)   spec = bySalary(filter.getSalary());
        if (filter.getAge() != null)      spec = combine(spec, byAge(filter.getAge()));
        if (filter.getPosition() != null) spec = combine(spec, byPosition(filter.getPosition()));
        return spec != null ? employeeRepository.findAll(spec) : employeeRepository.findAll();
    }

    public Employee create(EmployeeRequestDto dto) {
        Department department = departmentService.findById(dto.getDepartmentId());
        Organization organization = organizationService.findById(dto.getOrganizationId());
        return employeeRepository.save(new Employee(null, dto.getFirstName(), dto.getLastName(),
                dto.getPosition(), dto.getAge(), dto.getSalary(), department, organization));
    }

    public Employee update(Integer id, EmployeeRequestDto dto) {
        Employee employee = findById(id);
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setAge(dto.getAge());
        employee.setPosition(dto.getPosition());
        employee.setSalary(dto.getSalary());
        return employeeRepository.save(employee);
    }

    public Employee delete(Integer id) {
        Employee employee = findById(id);
        employeeRepository.delete(employee);
        return employee;
    }

    private Specification<Employee> combine(Specification<Employee> existing, Specification<Employee> next) {
        return existing == null ? next : existing.and(next);
    }

    private Specification<Employee> bySalary(FilterField f) {
        return (root, query, builder) -> f.generateCriteria(builder, root.get("salary"));
    }

    private Specification<Employee> byAge(FilterField f) {
        return (root, query, builder) -> f.generateCriteria(builder, root.get("age"));
    }

    private Specification<Employee> byPosition(FilterField f) {
        return (root, query, builder) -> f.generateCriteria(builder, root.get("position"));
    }
}
