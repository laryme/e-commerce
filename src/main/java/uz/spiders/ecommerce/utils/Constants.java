package uz.spiders.ecommerce.utils;


import uz.spiders.ecommerce.controller.interfaces.AuthController;
import uz.spiders.ecommerce.controller.interfaces.ProductController;

public interface Constants {
    String[] OPEN_PAGES = {
            AuthController.BASE_PATH + "/**",
            ProductController.BASE_PATH+"/image/**",
            "/*"
    };
    String AUTH_HEADER = "Authorization";
    String AUTH_TYPE_BEARER = "Bearer ";
    String BASE_PATH = "/api/v1";
    String BASE_DOMAIN = "http://localhost:8080";
}
