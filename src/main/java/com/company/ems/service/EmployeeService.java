package com.company.ems.service;

import com.company.ems.dto.EmployeeDto;
import java.util.List;

public interface EmployeeService {
    List<EmployeeDto> getAllEmployees(String keyword);
    EmployeeDto getEmployeeById(Long id);
    EmployeeDto createEmployee(EmployeeDto employeeDto);
    EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto);
    void deleteEmployee(Long id);
}