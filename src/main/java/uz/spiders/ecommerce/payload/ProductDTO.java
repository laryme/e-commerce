package uz.spiders.ecommerce.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

import java.util.List;

public record ProductDTO(
        @JsonProperty("name")
        @NotBlank(message = "product name is required.")
        String name,

        @JsonProperty("price")
        @DecimalMin(value = "0.0", inclusive = false, message = "price must be greater than 0")
        Double price,

        @JsonProperty("description")
        @NotBlank(message = "product description is required")
        String description,

        @JsonProperty("brandID")
        @NotNull(message = "product brandID is required")
        @Positive
        Integer brandID,

        @JsonProperty("categoryID")
        @NotNull(message = "product categoryID is required")
        @Positive
        Integer categoryID,

        @JsonProperty("productDetailDTOS")
        @Size(min = 1, message = "product details is required")
        List<ProductDetailDTO> productDetailDTOS
) {}

