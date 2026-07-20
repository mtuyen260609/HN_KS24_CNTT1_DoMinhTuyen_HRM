package com.example.human_resource_management.controller;

import com.example.human_resource_management.entity.Payroll;
import com.example.human_resource_management.repository.PayrollRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/payroll")
@RequiredArgsConstructor
public class PayrollApiController {

    private final PayrollRepository payrollRepository;

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<Payroll>> getPayrollByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(payrollRepository.findByEmployeeIdOrderByCreatedAtDesc(employeeId));
    }

    @PostMapping
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<Payroll> createPayroll(@RequestBody Payroll payroll) {
        payroll.setCreatedAt(LocalDateTime.now());
        return ResponseEntity.ok(payrollRepository.save(payroll));
    }

    @GetMapping
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<List<Payroll>> getAllPayroll() {
        return ResponseEntity.ok(payrollRepository.findAll());
    }
}
