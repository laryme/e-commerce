package uz.spiders.ecommerce.service.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import uz.spiders.ecommerce.payload.ApiResponse;
import uz.spiders.ecommerce.payload.ProductDTO;

import java.util.List;

public interface ProductService {
    ApiResponse<?> createNewProduct(ProductDTO productDTO, List<MultipartFile> files);

    ApiResponse<?> getProductById(Integer id);

    ApiResponse<?> getAllProduct(Integer page, int size, String[] sort);

    ApiResponse<?> deleteProduct(Integer id);

    ApiResponse<?> changeProductStatus(Integer id);

    ResponseEntity<?> getProductImage(String name);
}
