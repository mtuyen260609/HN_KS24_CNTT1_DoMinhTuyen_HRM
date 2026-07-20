package com.example.human_resource_management.controller;

import com.example.human_resource_management.entity.SystemSetting;
import com.example.human_resource_management.repository.SystemSettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/settings")
@RequiredArgsConstructor
public class SettingsController {

    private final SystemSettingRepository settingRepository;

    @GetMapping
    public ResponseEntity<Map<String, String>> getAllSettings() {
        List<SystemSetting> settings = settingRepository.findAll();
        Map<String, String> settingsMap = settings.stream()
                .collect(Collectors.toMap(SystemSetting::getKey, SystemSetting::getValue));
        return ResponseEntity.ok(settingsMap);
    }

    @PostMapping
    public ResponseEntity<Void> saveSettings(@RequestBody Map<String, String> settings) {
        for (Map.Entry<String, String> entry : settings.entrySet()) {
            SystemSetting setting = settingRepository.findById(entry.getKey())
                    .orElse(new SystemSetting(entry.getKey(), ""));
            setting.setValue(entry.getValue());
            settingRepository.save(setting);
        }
        return ResponseEntity.ok().build();
    }
}
