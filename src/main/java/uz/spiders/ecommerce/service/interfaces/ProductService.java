package uz.spiders.ecommerce.service.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import uz.spiders.ecommerce.payload.ApiResult;
import uz.spiders.ecommerce.payload.ProductDTO;

import java.util.List;

public interface ProductService {
    ApiResult<?> createNewProduct(ProductDTO productDTO, List<MultipartFile> files);

    ApiResult<?> getProductById(Integer id);

    ApiResult<?> getAllProduct(Integer page, int size, String[] sort);

    ApiResult<?> deleteProduct(Integer id);

    ApiResult<?> changeProductStatus(Integer id);

    ResponseEntity<?> getProductImage(String name);
}
