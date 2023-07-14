package uz.spiders.ecommerce.controller.interfaces;

import org.springframework.web.bind.annotation.*;
import uz.spiders.ecommerce.payload.ApiResponse;
import uz.spiders.ecommerce.payload.AuthDTO;
import uz.spiders.ecommerce.payload.RegisterDTO;
import uz.spiders.ecommerce.utils.Constants;


@RequestMapping(AuthController.BASE_PATH)
public interface AuthController {
    String BASE_PATH = Constants.BASE_PATH+"/auth";
    @PostMapping("/register")
    ApiResponse<?> register(@RequestBody RegisterDTO authDTO);
    @PostMapping("/authenticate")
    ApiResponse<?> authenticate(@RequestBody AuthDTO authDTO);

    @GetMapping("/verify")
    ApiResponse<?> verification(@RequestParam(name = "s") String email, @RequestParam String token);
}
