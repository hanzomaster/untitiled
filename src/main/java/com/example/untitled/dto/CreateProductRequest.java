package com.example.untitled.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateProductRequest(
        @NotBlank(message = "Product name is required")
                @Size(min = 3, max = 50, message = "Product name must be between 3 and 50 characters.")
                String name,
        @NotBlank(message = "Product description is required") String description,
        @NotBlank(message = "Product price is required") String price) {}
