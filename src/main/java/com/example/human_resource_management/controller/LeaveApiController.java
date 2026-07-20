package com.example.human_resource_management.controller;

import com.example.human_resource_management.entity.LeaveRequest;
import com.example.human_resource_management.repository.LeaveRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/leaves")
@RequiredArgsConstructor
public class LeaveApiController {

    private final LeaveRequestRepository leaveRequestRepository;

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<LeaveRequest>> getLeavesByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(leaveRequestRepository.findByEmployeeIdOrderByCreatedAtDesc(employeeId));
    }

    @PostMapping
    public ResponseEntity<LeaveRequest> createLeaveRequest(@RequestBody LeaveRequest leaveRequest) {
        leaveRequest.setCreatedAt(LocalDateTime.now());
        leaveRequest.setStatus("PENDING");
        return ResponseEntity.ok(leaveRequestRepository.save(leaveRequest));
    }

    @PutMapping("/{id}/approve")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<LeaveRequest> approveLeave(@PathVariable Long id, @RequestParam String status) {
        LeaveRequest leave = leaveRequestRepository.findById(id).orElseThrow(() -> new RuntimeException("Leave not found"));
        leave.setStatus(status);
        leave.setUpdatedAt(LocalDateTime.now());
        return ResponseEntity.ok(leaveRequestRepository.save(leave));
    }

    @GetMapping
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<List<LeaveRequest>> getAllLeaves() {
        return ResponseEntity.ok(leaveRequestRepository.findAll());
    }
}
