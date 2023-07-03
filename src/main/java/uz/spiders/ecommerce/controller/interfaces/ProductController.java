package uz.spiders.ecommerce.controller.interfaces;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.spiders.ecommerce.payload.ApiResult;
import uz.spiders.ecommerce.payload.ProductDTO;
import uz.spiders.ecommerce.utils.Constants;

import java.util.List;

@RequestMapping(ProductController.BASE_PATH)
public interface ProductController {
    String BASE_PATH = Constants.BASE_PATH + "/product";
    String PRODUCT_IMAGE = "/image/{name}";

    @PostMapping
    //@PreAuthorize("hasAuthority('PRODUCT_CREATE')")
    ApiResult<?> createNewProduct(@Valid @RequestPart(name = "product") ProductDTO product,
                                  @Valid @RequestPart List<MultipartFile> files);

    @GetMapping("/{id}")
    ApiResult<?> getProductById(@PathVariable Integer id);

    @GetMapping("/change-status/{id}")
    @PreAuthorize("hasAuthority('PROBLEM_CHANGE_STATUS')")
    ApiResult<?> changeProductStatus(@PathVariable Integer id);

    @GetMapping("/all")
    ApiResult<?> getAllProduct(
            @RequestParam(required = false,defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "id,asc") String[] sort);

    @DeleteMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('PRODUCT_DELETE')")
    ApiResult<?> deleteProduct(@PathVariable Integer id);

    @GetMapping(PRODUCT_IMAGE)
    ResponseEntity<?> getProductPicture(@PathVariable String name);

}
