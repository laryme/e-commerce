package uz.spiders.ecommerce.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import uz.spiders.ecommerce.entity.Product;
import uz.spiders.ecommerce.payload.ApiResult;
import uz.spiders.ecommerce.payload.ProductDTO;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    ApiResult<?> createNewProduct(ProductDTO productDTO, List<MultipartFile> files);

    ApiResult<Product> getProductById(UUID id);

    ApiResult<Page<Product>> getAllProduct(Integer page, int size, String[] sort);

    ApiResult<?> deleteProduct(UUID id);
}
