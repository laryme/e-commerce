package uz.spiders.ecommerce.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import uz.spiders.ecommerce.controller.interfaces.BrandController;
import uz.spiders.ecommerce.payload.ApiResponse;
import uz.spiders.ecommerce.payload.BrandRequest;
import uz.spiders.ecommerce.service.BrandService;

@RestController
@RequiredArgsConstructor
public class BrandControllerImpl implements BrandController {
    private final BrandService brandService;

    @Override
    public ApiResponse<?> createBrand(BrandRequest brand, MultipartFile logo) {
        return brandService.createNewBrand(brand, logo);
    }

    @Override
    public ApiResponse<?> getBrand(Integer id) {
        return brandService.getBrandById(id);
    }

    @Override
    public ApiResponse<?> getAllBrands(Integer page, int size) {
        return brandService.getAllBrands(page, size);
    }

    @Override
    public ResponseEntity<?> getBrandLogo(Integer id) {
        return brandService.getBrandLogo(id);
    }

    @Override
    public ApiResponse<?> editBrand(Integer id, BrandRequest brand, MultipartFile logo) {
        return brandService.editBrand(id, brand, logo);
    }

    @Override
    public ApiResponse<?> deleteBrand(Integer id) {
        return brandService.deleteBrandById(id);
    }
}
