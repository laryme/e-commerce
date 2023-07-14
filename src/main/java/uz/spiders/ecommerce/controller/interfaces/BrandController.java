package uz.spiders.ecommerce.controller.interfaces;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.spiders.ecommerce.payload.ApiResponse;
import uz.spiders.ecommerce.payload.BrandRequest;
import uz.spiders.ecommerce.utils.Constants;

@RequestMapping(BrandController.BASE_PATH)
public interface BrandController {
    String BASE_PATH = Constants.BASE_PATH + "/brand";

    @PostMapping
    ApiResponse<?> createBrand(@Valid @RequestPart BrandRequest brand, @RequestPart MultipartFile logo);

    @GetMapping("/{id}")
    ApiResponse<?> getBrand(@PathVariable Integer id);

    @GetMapping("/all")
    ApiResponse<?> getAllBrands(
            @RequestParam(required = false,defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") int size
    );

    @GetMapping("/image/{id}")
    ResponseEntity<?> getBrandLogo(@PathVariable Integer id);

    ////@RequestParam(required = false, defaultValue = "id,asc") String[] sort

    @PutMapping("/{id}")
    ApiResponse<?> editBrand(@PathVariable Integer id, @Valid @RequestPart BrandRequest brand, @RequestPart MultipartFile logo);

    @DeleteMapping("/{id}")
    ApiResponse<?> deleteBrand(@PathVariable Integer id);

}
