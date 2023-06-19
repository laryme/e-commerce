package uz.spiders.ecommerce.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductDetailDTO(
        @JsonProperty("detailKeyDTO")
        @NotNull(message = "productDetails detailKeyDTO is required")
        DetailKeyDTO detailKeyDTO,
        @NotBlank(message = "productDetails value is required")
        String value
) {}
