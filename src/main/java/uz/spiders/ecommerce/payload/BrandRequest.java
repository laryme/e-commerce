package uz.spiders.ecommerce.payload;

import jakarta.validation.constraints.NotBlank;

public record BrandRequest(
        @NotBlank(message = "brand name is required")
        String name

) {}
