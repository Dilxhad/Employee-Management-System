package com.company.ems.service.impl;

import com.company.ems.dto.EmployeeDto;
import com.company.ems.entity.Employee;
import com.company.ems.exception.DuplicateResourceException;
import com.company.ems.exception.ResourceNotFoundException;
import com.company.ems.mapper.EmployeeMapper;
import com.company.ems.repository.EmployeeRepository;
import com.company.ems.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;
    private final EmployeeMapper mapper;

    @Override
    public List<EmployeeDto> getAllEmployees(String keyword) {
        List<Employee> employees = (keyword == null || keyword.isBlank())
                ? repository.findAll()
                : repository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrDepartmentContainingIgnoreCase(
                        keyword.trim(), keyword.trim(), keyword.trim());

        return employees.stream().map(mapper::toDto).toList();
    }

    @Override
    public EmployeeDto getEmployeeById(Long id) {
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + id));
        return mapper.toDto(employee);
    }

    @Override
    @Transactional
    public EmployeeDto createEmployee(EmployeeDto dto) {
        String normalizedEmail = dto.getEmail().trim().toLowerCase();
        if (repository.existsByEmail(normalizedEmail)) {
            throw new DuplicateResourceException("An employee with this email already exists.");
        }
        Employee saved = repository.save(mapper.toEntity(dto));
        return mapper.toDto(saved);
    }

    @Override
    @Transactional
    public EmployeeDto updateEmployee(Long id, EmployeeDto dto) {
        Employee existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + id));

        String normalizedEmail = dto.getEmail().trim().toLowerCase();
        repository.findByEmail(normalizedEmail).ifPresent(other -> {
            if (!other.getId().equals(id)) {
                throw new DuplicateResourceException("Another employee already uses this email.");
            }
        });

        mapper.updateEntity(existing, dto);
        return mapper.toDto(repository.save(existing));
    }

    @Override
    @Transactional
    public void deleteEmployee(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Employee not found with ID: " + id);
        }
        repository.deleteById(id);
    }
}