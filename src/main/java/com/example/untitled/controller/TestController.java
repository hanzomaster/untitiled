package com.example.untitled.controller;

import com.example.untitled.dto.CreateProductRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestController {
    @GetMapping
    public String test() {
        log.info("Test endpoint hit");
        return "Hello, World!";
    }

    @PostMapping("/products")
    public ResponseEntity<String> addProduct(@Valid @RequestBody CreateProductRequest productRequest) {
        // If validation passes, this logic is executed.
        // For demo purposes, we're just returning a success message.
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Product '" + productRequest.name() + "' created successfully.");
    }
}
