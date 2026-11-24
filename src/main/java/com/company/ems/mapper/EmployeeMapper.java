package com.company.ems.mapper;

import com.company.ems.dto.EmployeeDto;
import com.company.ems.entity.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    public EmployeeDto toDto(Employee entity) {
        return EmployeeDto.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .department(entity.getDepartment())
                .salary(entity.getSalary())
                .build();
    }

    public Employee toEntity(EmployeeDto dto) {
        return Employee.builder()
                .firstName(dto.getFirstName().trim())
                .lastName(dto.getLastName().trim())
                .email(dto.getEmail().trim().toLowerCase())
                .department(dto.getDepartment().trim())
                .salary(dto.getSalary())
                .build();
    }

    public void updateEntity(Employee existing, EmployeeDto dto) {
        existing.setFirstName(dto.getFirstName().trim());
        existing.setLastName(dto.getLastName().trim());
        existing.setEmail(dto.getEmail().trim().toLowerCase());
        existing.setDepartment(dto.getDepartment().trim());
        existing.setSalary(dto.getSalary());
    }
}