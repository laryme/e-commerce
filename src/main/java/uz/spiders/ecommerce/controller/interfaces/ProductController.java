package uz.spiders.ecommerce.controller.interfaces;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.spiders.ecommerce.payload.ProductDTO;
import uz.spiders.ecommerce.payload.ApiResult;
import uz.spiders.ecommerce.utils.Constants;

@RequestMapping()
public interface ProductController {
    final String BASE_PATH = Constants.BASE_PATH + "/product";

    @PostMapping
    @PreAuthorize(value = "hasAuthority('PRODUCT_CREATE')")
    ApiResult<?> createNewProduct(@RequestBody ProductDTO ProductDTO);
}
