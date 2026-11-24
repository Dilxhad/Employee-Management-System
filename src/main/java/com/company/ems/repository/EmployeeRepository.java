package com.company.ems.repository;

import com.company.ems.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    boolean existsByEmail(String email);

    Optional<Employee> findByEmail(String email);

    List<Employee> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrDepartmentContainingIgnoreCase(
            String firstName, String lastName, String department);
}