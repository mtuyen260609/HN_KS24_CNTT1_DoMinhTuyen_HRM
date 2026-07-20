package com.example.human_resource_management.repository;

import com.example.human_resource_management.entity.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PayrollRepository extends JpaRepository<Payroll, Long> {
    List<Payroll> findByEmployeeIdOrderByCreatedAtDesc(Long employeeId);
    List<Payroll> findByMonthYear(String monthYear);
    boolean existsByEmployeeIdAndMonthYear(Long employeeId, String monthYear);
    java.util.Optional<Payroll> findByEmployeeIdAndMonthYear(Long employeeId, String monthYear);
}
