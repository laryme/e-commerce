package uz.spiders.ecommerce.service.interfaces;


import uz.spiders.ecommerce.payload.ApiResponse;
import uz.spiders.ecommerce.payload.AuthDTO;
import uz.spiders.ecommerce.payload.RegisterDTO;

public interface AuthService {

    ApiResponse<?> register(RegisterDTO registerDTO);
    ApiResponse<?> authenticate(AuthDTO authDTO);
    ApiResponse<?> verification(String email, String token);
}