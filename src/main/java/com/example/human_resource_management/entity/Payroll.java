package com.example.human_resource_management.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "payrolls")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payroll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "month_year")
    private String monthYear; // Format: MM/yyyy

    @Column(name = "base_salary")
    private Long baseSalary;

    @Column(name = "working_days")
    private Integer workingDays;

    private Long bonus;

    @Column(name = "total_salary")
    private Long totalSalary;

    private String status; // e.g., "Đã thanh toán", "Chờ xử lý"

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public String getTone() {
        if ("Đã thanh toán".equals(status)) return "success";
        if ("Chờ xử lý".equals(status)) return "warning";
        return "info";
    }
}
