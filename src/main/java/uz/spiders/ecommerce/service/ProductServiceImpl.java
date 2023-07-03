package uz.spiders.ecommerce.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.spiders.ecommerce.entity.Brand;
import uz.spiders.ecommerce.entity.Category;
import uz.spiders.ecommerce.entity.Product;
import uz.spiders.ecommerce.entity.ProductPicture;
import uz.spiders.ecommerce.exception.DataNotFoundException;
import uz.spiders.ecommerce.exception.InvalidParameterException;
import uz.spiders.ecommerce.mapper.ProductMapper;
import uz.spiders.ecommerce.payload.ApiResult;
import uz.spiders.ecommerce.payload.ProductDTO;
import uz.spiders.ecommerce.payload.ProductResponse;
import uz.spiders.ecommerce.repository.BrandRepository;
import uz.spiders.ecommerce.repository.CategoryRepository;
import uz.spiders.ecommerce.repository.ProductPictureRepository;
import uz.spiders.ecommerce.repository.ProductRepository;
import uz.spiders.ecommerce.service.interfaces.ProductService;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductPictureRepository productPictureRepository;
    private final ProductDetailService productDetailService;
    private final ProductPictureService productPictureService;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final StorageService storageService;
    private final ProductMapper productMapper;

    @Override
    public ApiResult<?> createNewProduct(ProductDTO productDTO, List<MultipartFile> files) {
        Optional<Brand> brand = brandRepository.findById(productDTO.brandID());
        Optional<Category> category = categoryRepository.findById(productDTO.categoryID());
        if (brand.isEmpty())
            throw new DataNotFoundException("brand not found with given id");

        if (category.isEmpty())
            throw new DataNotFoundException("category not found with given id");

        productRepository.save(toProduct(productDTO, files, brand.get(), category.get()));
        return ApiResult.successResponse();
    }

    @Override
    public ApiResult<ProductResponse> getProductById(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("product not found with given id"));


        ProductResponse productResponse = productMapper.fromProduct(product);
        return ApiResult
                .successResponse(productResponse);
    }

    @Override
    public ApiResult<?> getAllProduct(Integer page, int size, String[] sort) {
        if (sort.length != 2)
            throw new InvalidParameterException("please enter valid parameter");
        try {
            PageRequest pageRequest = PageRequest.of(page, size,
                    Objects.equals(sort[1], "desc") ?
                            Sort.by(sort[0])
                                    .descending() :
                            Sort.by(sort[0])
                                    .ascending());

            Page<Product> products = productRepository.findAllByIsActiveTrue(pageRequest);

            return ApiResult.successResponse(products.map(productMapper::fromProduct));
        } catch (PropertyReferenceException e) {
            throw new InvalidParameterException(String.format("Invalid parameter name:{%s} specified for pagination", sort[0]));
        }
    }

    @Override
    public ApiResult<?> deleteProduct(Integer id) {
        int affectedRows = productRepository.deleteProductById(id);
        if (affectedRows < 0)
            throw new DataNotFoundException("product not found with given parameter");

        return ApiResult.successResponse("product successfully deleted");
    }

    @Override
    public ApiResult<?> changeProductStatus(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Product not found with given id"));

        product.setActive(!product.isActive());
        productRepository.save(product);
        return ApiResult
                .successResponse("Product status successfully changed");
    }

    @Override
    public ResponseEntity<?> getProductImage(String name) {
        ProductPicture productPicture = productPictureRepository.findByFileName(name)
                .orElseThrow(() -> new DataNotFoundException("Product picture not found"));

        try {
            byte[] file = storageService.getProductPicture(productPicture.getFileUrl());
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.valueOf("image/png"))
                    .body(file);
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private Product toProduct(ProductDTO productDTO, List<MultipartFile> files, Brand brand, Category category) {
        return Product.builder()
                .name(productDTO.name())
                .price(productDTO.price())
                .description(productDTO.description())
                .brand(brand)
                .category(category)
                .productDetails(productDetailService.saveProductDetail(productDTO.productDetailDTOS()))
                .productPictures(productPictureService.saveProductPicture(files))
                .build();
    }
}
