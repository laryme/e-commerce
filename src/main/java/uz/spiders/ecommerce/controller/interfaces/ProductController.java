package uz.spiders.ecommerce.controller.interfaces;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.spiders.ecommerce.payload.ApiResponse;
import uz.spiders.ecommerce.payload.ProductDTO;
import uz.spiders.ecommerce.utils.Constants;

import java.util.List;

@RequestMapping(ProductController.BASE_PATH)
public interface ProductController {
    String BASE_PATH = Constants.BASE_PATH + "/product";
    String PRODUCT_IMAGE = "/image/{name}";

    @PostMapping
    //@PreAuthorize("hasAuthority('PRODUCT_CREATE')")
    ApiResponse<?> createNewProduct(@Valid @RequestPart(name = "product") ProductDTO product,
                                    @Valid @RequestPart List<MultipartFile> files);

    @GetMapping("/{id}")
    ApiResponse<?> getProductById(@PathVariable Integer id);

    @GetMapping("/change-status/{id}")
    @PreAuthorize("hasAuthority('PROBLEM_CHANGE_STATUS')")
    ApiResponse<?> changeProductStatus(@PathVariable Integer id);

    @GetMapping("/all")
    ApiResponse<?> getAllProduct(
            @RequestParam(required = false,defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "id,asc") String[] sort);

    @DeleteMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('PRODUCT_DELETE')")
    ApiResponse<?> deleteProduct(@PathVariable Integer id);

    @GetMapping(PRODUCT_IMAGE)
    ResponseEntity<?> getProductPicture(@PathVariable String name);

}
