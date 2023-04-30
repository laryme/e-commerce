package uz.spiders.ecommerce.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import uz.spiders.ecommerce.controller.interfaces.AuthController;
import uz.spiders.ecommerce.payload.ApiResult;
import uz.spiders.ecommerce.payload.AuthDTO;
import uz.spiders.ecommerce.payload.RegisterDTO;
import uz.spiders.ecommerce.service.interfaces.AuthService;


@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {
    private final AuthService authService;
    @Override
    public ApiResult<?> register(@Valid RegisterDTO registerDTO) {
        return authService.register(registerDTO);
    }


    @Override
    public ApiResult<?> authenticate(@Valid AuthDTO authDTO) {
        return authService.authenticate(authDTO);
    }


    @Override
    public ApiResult<?> verification(String email, String token) {
        System.out.println(email+" "+token);
        return authService.verification(email, token);
    }
}
