package com.example.human_resource_management.controller;

import com.example.human_resource_management.entity.Employee;
import com.example.human_resource_management.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        return ResponseEntity.ok(employeeRepository.save(employee));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        return employeeRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee updatedEmployee) {
        return employeeRepository.findById(id).map(emp -> {
            emp.setFullName(updatedEmployee.getFullName());
            emp.setGender(updatedEmployee.getGender());
            emp.setPhone(updatedEmployee.getPhone());
            emp.setAddress(updatedEmployee.getAddress());
            emp.setPosition(updatedEmployee.getPosition());
            emp.setDepartment(updatedEmployee.getDepartment());
            emp.setDateOfBirth(updatedEmployee.getDateOfBirth());
            emp.setAvatarUrl(updatedEmployee.getAvatarUrl());
            return ResponseEntity.ok(employeeRepository.save(emp));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/face")
    public ResponseEntity<?> updateFaceDescriptor(@PathVariable Long id, @RequestBody java.util.List<Float> faceDescriptor) {
        return employeeRepository.findById(id).map(emp -> {
            emp.setFaceDescriptor(faceDescriptor.toString());
            employeeRepository.save(emp);
            return ResponseEntity.ok().body("Cập nhật dữ liệu khuôn mặt thành công!");
        }).orElse(ResponseEntity.notFound().build());
    }
}
