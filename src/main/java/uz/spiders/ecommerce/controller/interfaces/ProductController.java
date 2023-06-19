package uz.spiders.ecommerce.controller.interfaces;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;
import uz.spiders.ecommerce.payload.ProductDTO;
import uz.spiders.ecommerce.payload.ApiResult;
import uz.spiders.ecommerce.utils.Constants;

import java.util.List;
import java.util.UUID;

@RequestMapping(ProductController.BASE_PATH)
public interface ProductController {
    String BASE_PATH = Constants.BASE_PATH + "/product";

    @PostMapping
    @PreAuthorize("hasAuthority('PRODUCT_CREATE')")
    ApiResult<?> createNewProduct(StandardMultipartHttpServletRequest request,
                                  @RequestPart(name = "files") List<MultipartFile> files);

    @GetMapping("/{id}")
    ApiResult<?> getProductById(@PathVariable UUID id);

    @GetMapping("/all")
    ApiResult<?> getAllProduct(
            @RequestParam(required = false,defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "id,asc") String[] sort);

    @DeleteMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('PRODUCT_DELETE')")
    ApiResult<?> deleteProduct(@PathVariable UUID id);

}
