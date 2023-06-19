package uz.spiders.ecommerce.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

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
        @NotBlank(message = "product brandID is required")
        UUID brandID,

        @JsonProperty("categoryID")
        @NotBlank(message = "product categoryID is required")
        UUID categoryID,

        @JsonProperty("productDetailDTOS")
        @Size(min = 1, message = "product details is required")
        List<ProductDetailDTO> productDetailDTOS
) {}

