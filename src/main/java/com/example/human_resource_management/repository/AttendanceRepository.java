package com.example.human_resource_management.repository;

import com.example.human_resource_management.entity.Attendance;
import com.example.human_resource_management.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Optional<Attendance> findByEmployeeAndWorkDate(Employee employee, LocalDate workDate);
    java.util.List<Attendance> findByEmployeeIdOrderByWorkDateDesc(Long employeeId);
    java.util.List<Attendance> findByEmployeeId(Long employeeId);
}
