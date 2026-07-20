package com.example.human_resource_management.dto;

import lombok.Data;
import java.util.List;

@Data
public class CheckInRequest {
    private List<Float> faceDescriptor;
    private String location;
}
