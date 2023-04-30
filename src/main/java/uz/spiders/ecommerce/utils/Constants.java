package uz.spiders.ecommerce.utils;


import uz.spiders.ecommerce.controller.interfaces.AuthController;

public interface Constants {
    String[] OPEN_PAGES = {
            AuthController.BASE_PATH + "/**",
            "/*"
    };
    String AUTH_HEADER = "Authorization";
    String AUTH_TYPE_BEARER = "Bearer ";
    String BASE_PATH = "/api/v1";
}
