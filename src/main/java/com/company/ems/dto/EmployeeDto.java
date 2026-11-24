package com.company.ems.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDto {

    private Long id;

    @NotBlank(message = "First name is required")
    @Size(max = 60, message = "First name cannot exceed 60 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 60, message = "Last name cannot exceed 60 characters")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Enter a valid email address")
    private String email;

    @NotBlank(message = "Department is required")
    private String department;

    @NotNull(message = "Salary is required")
    @DecimalMin(value = "0.01", message = "Salary must be greater than zero")
    private BigDecimal salary;
}