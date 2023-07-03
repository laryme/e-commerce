package uz.spiders.ecommerce.payload;

import jakarta.validation.constraints.NotNull;

public record DetailKeyDTO(
        @NotNull(message = "productDetailKey id is required")
        Integer detailKeyId,
        @NotNull(message = "productDetailKey detailCategoryId is required")
        Integer detailCategoryId
) {}