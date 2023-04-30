package uz.spiders.ecommerce.service.interfaces;


import uz.spiders.ecommerce.payload.ApiResult;
import uz.spiders.ecommerce.payload.AuthDTO;
import uz.spiders.ecommerce.payload.RegisterDTO;

public interface AuthService {

    ApiResult<?> register(RegisterDTO registerDTO);
    ApiResult<?> authenticate(AuthDTO authDTO);
    ApiResult<?> verification(String email, String token);
}
