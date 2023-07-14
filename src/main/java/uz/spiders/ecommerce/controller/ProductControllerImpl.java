package uz.spiders.ecommerce.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import uz.spiders.ecommerce.controller.interfaces.ProductController;
import uz.spiders.ecommerce.payload.ApiResponse;
import uz.spiders.ecommerce.payload.ProductDTO;
import uz.spiders.ecommerce.service.interfaces.ProductService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductControllerImpl implements ProductController {
    private final ProductService productService;

    @Override
    public ApiResponse<?> createNewProduct(@Valid ProductDTO productDTO, @Valid List<MultipartFile> files) {
        return productService.createNewProduct(productDTO, files);
    }

    @Override
    public ApiResponse<?> getProductById(Integer id) {
        return productService.getProductById(id);
    }

    @Override
    public ApiResponse<?> changeProductStatus(Integer id) {
        return productService.changeProductStatus(id);
    }

    @Override
    public ApiResponse<?> getAllProduct(Integer page, int size, String[] sort) {
        return productService.getAllProduct(page, size, sort);
    }

    @Override
    public ApiResponse<?> deleteProduct(Integer id) {
        return productService.deleteProduct(id);
    }

    @Override
    public ResponseEntity<?> getProductPicture(String name) {
        return productService.getProductImage(name);
    }
}
