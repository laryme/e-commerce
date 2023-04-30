package uz.spiders.ecommerce.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserDTO(
        @JsonProperty("fullname")
        String fullName,
        @NotBlank(message = "Username is required")
        String username,
        @NotBlank(message = "Username is required")
        String password,
        @Email
        String email
) {
}
