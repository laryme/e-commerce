package uz.spiders.ecommerce.payload;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record DetailKeyDTO(
        @NotBlank(message = "productDetailKey name is required")
        String name,
        @NotBlank(message = "productDetailKey isHeader is required")
        boolean isHeader,
        @NotBlank(message = "productDetailKey detailCategoryId is required")
        UUID detailCategoryId
) {}