package com.jenga_marketplace.jenga_backend.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/protected")
public class ProtectedController {

    @GetMapping
    public Map<String, Object> getProtectedData() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Protected endpoint working!");
        response.put("timestamp", System.currentTimeMillis());
        return response;
    }
}
