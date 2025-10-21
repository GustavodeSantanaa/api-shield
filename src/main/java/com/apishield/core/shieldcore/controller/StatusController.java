package com.apishield.core.shieldcore.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class StatusController {

    @GetMapping("/status")
    public ResponseEntity<String> status() {
        return ResponseEntity.ok("API Shield is running ✅");
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Request allowed!");
    }
}
