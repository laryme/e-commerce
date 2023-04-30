package uz.spiders.ecommerce.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterDTO(
        @NotBlank(message = "Username is required")
        @Size(min = 4, message = "Username size must be greater than 4")
        String username,
        @NotBlank(message = "Email is required")
        @Pattern(regexp = "^((?!\\.)[\\w-_.]*[^.])(@\\w+)(\\.\\w+(\\.\\w+)?[^.\\W])$", message = "Please enter valid email")
        String email,
        @NotBlank(message = "Password is required")
        @Size(min = 6, message = "Password size must be greater than 6")
        String password
) {
}
