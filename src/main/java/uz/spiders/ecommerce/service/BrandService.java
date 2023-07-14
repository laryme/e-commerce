package uz.spiders.ecommerce.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.spiders.ecommerce.entity.Brand;
import uz.spiders.ecommerce.exception.DataNotFoundException;
import uz.spiders.ecommerce.mapper.BrandMapper;
import uz.spiders.ecommerce.payload.ApiResponse;
import uz.spiders.ecommerce.payload.BrandRequest;
import uz.spiders.ecommerce.payload.BrandResponseDTO;
import uz.spiders.ecommerce.repository.BrandRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;
    private final StorageService storageService;
    private final BrandMapper brandMapper;

    public ApiResponse<?> createNewBrand(BrandRequest brandRequest, MultipartFile multipartFile) {
        Brand brand = Brand.builder()
                .name(brandRequest.name())
                .logoUrl(storageService.saveBrandLogo(multipartFile).toString())
                .build();

        brandRepository.save(brand);
        return ApiResponse.successResponse("Brand successfully created");
    }

    public ApiResponse<?> getBrandById(Integer id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("brand not found with given id"));

        return ApiResponse
                .successResponse(brandMapper.fromBrand(brand));
    }

    public ApiResponse<?> getAllBrands(Integer page, int size) {
        Page<BrandResponseDTO> brandList = brandRepository
                .findAll(PageRequest.of(page, size)).map(brandMapper::fromBrand);

        return ApiResponse
                .successResponse(brandList);
    }

    public ApiResponse<?> deleteBrandById(Integer id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Brand not found"));

        String logoUrl = brand.getLogoUrl();
        File file = new File(Path.of(logoUrl).toUri());
        file.delete();

        brandRepository.delete(brand);

        return ApiResponse
                .successResponse("Brand successfully deleted");
    }

    public ApiResponse<?> editBrand(Integer id, BrandRequest brandRequest, MultipartFile logo) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Brand not found"));

        brand.setName(brandRequest.name());
        storageService.replaceExcitingFile(brand.getLogoUrl(), logo);

        brandRepository.save(brand);

        return ApiResponse
                .successResponse("Brand updated successfully");
    }

    public ResponseEntity<?> getBrandLogo(Integer id) {
        String logoUrl = brandRepository.getBrandLogoById(id)
                .orElseThrow(() -> new DataNotFoundException("Brand not found with given id"));


        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.valueOf("image/png"))
                    .body(storageService.getFile(logoUrl));
        } catch (IOException e) {
            return ResponseEntity
                    .notFound().build();
        }
    }
}
