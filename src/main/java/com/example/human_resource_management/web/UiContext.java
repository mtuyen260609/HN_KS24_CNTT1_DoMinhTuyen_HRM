package com.example.human_resource_management.web;

public record UiContext(
        String role,
        String displayName,
        String email,
        boolean employee,
        boolean manager,
        boolean hr
) {
}
