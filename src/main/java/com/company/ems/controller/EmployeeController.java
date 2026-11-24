package com.company.ems.controller;

import com.company.ems.dto.EmployeeDto;
import com.company.ems.exception.DuplicateResourceException;
import com.company.ems.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public String listEmployees(@RequestParam(required = false) String keyword, Model model) {
        model.addAttribute("employees", employeeService.getAllEmployees(keyword));
        model.addAttribute("keyword", keyword);
        return "employees/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("employee", new EmployeeDto());
        return "employees/form";
    }

    @PostMapping
    public String createEmployee(@Valid @ModelAttribute("employee") EmployeeDto employeeDto,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "employees/form";
        }
        try {
            employeeService.createEmployee(employeeDto);
            redirectAttributes.addFlashAttribute("successMessage", "Employee created successfully.");
            return "redirect:/employees";
        } catch (DuplicateResourceException ex) {
            result.rejectValue("email", "duplicate.email", ex.getMessage());
            return "employees/form";
        }
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("employee", employeeService.getEmployeeById(id));
        return "employees/form";
    }

    @PostMapping("/{id}")
    public String updateEmployee(@PathVariable Long id,
                                 @Valid @ModelAttribute("employee") EmployeeDto employeeDto,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes) {
        employeeDto.setId(id);
        if (result.hasErrors()) {
            return "employees/form";
        }
        try {
            employeeService.updateEmployee(id, employeeDto);
            redirectAttributes.addFlashAttribute("successMessage", "Employee updated successfully.");
            return "redirect:/employees";
        } catch (DuplicateResourceException ex) {
            result.rejectValue("email", "duplicate.email", ex.getMessage());
            return "employees/form";
        }
    }

    @PostMapping("/{id}/delete")
    public String deleteEmployee(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        employeeService.deleteEmployee(id);
        redirectAttributes.addFlashAttribute("successMessage", "Employee deleted successfully.");
        return "redirect:/employees";
    }
}