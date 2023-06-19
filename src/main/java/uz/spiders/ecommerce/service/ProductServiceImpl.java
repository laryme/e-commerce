package uz.spiders.ecommerce.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.spiders.ecommerce.entity.Brand;
import uz.spiders.ecommerce.entity.Category;
import uz.spiders.ecommerce.entity.Product;
import uz.spiders.ecommerce.exception.DataNotFoundException;
import uz.spiders.ecommerce.exception.InvalidParameterException;
import uz.spiders.ecommerce.payload.ApiResult;
import uz.spiders.ecommerce.payload.ProductDTO;
import uz.spiders.ecommerce.repository.BrandRepository;
import uz.spiders.ecommerce.repository.CategoryRepository;
import uz.spiders.ecommerce.repository.ProductRepository;
import uz.spiders.ecommerce.service.interfaces.ProductService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductDetailService productDetailService;
    private final ProductPictureService productPictureService;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    @Override
    public ApiResult<?> createNewProduct(ProductDTO productDTO, List<MultipartFile> files) {
        Optional<Brand> brand = brandRepository.findById(productDTO.brandID());
        Optional<Category> category = categoryRepository.findById(productDTO.categoryID());
        if(brand.isEmpty())
            throw new DataNotFoundException("brand not found with given id");

        if(category.isEmpty())
            throw new DataNotFoundException("category not found with given id");

        productRepository.save(toProduct(productDTO, files, brand.get(), category.get()));
        return ApiResult.successResponse();
    }

    @Override
    public ApiResult<Product> getProductById(UUID id) {
        return ApiResult
                .successResponse(productRepository.findByIdAndDeletedFalse(id)
                        .orElseThrow(() -> new DataNotFoundException("product not found with given id")));
    }

    @Override
    public ApiResult<Page<Product>> getAllProduct(Integer page, int size, String[] sort) {
        if(sort.length != 2)
            throw new InvalidParameterException("please enter valid parameter");
        try {
            PageRequest pageRequest = PageRequest.of(page, size,
                    Objects.equals(sort[1], "desc") ?
                            Sort.by(sort[0])
                                    .descending() :
                            Sort.by(sort[0])
                                    .ascending());

            Page<Product> products = productRepository.findAllByDeletedFalse(pageRequest);
            return ApiResult.successResponse(products);
        } catch (PropertyReferenceException e) {
            throw new InvalidParameterException(String.format("Invalid parameter name:{%s} specified for pagination", sort[0]));
        }
    }

    @Override
    public ApiResult<?> deleteProduct(UUID id) {
        int affectedRows = productRepository.softDeleteProductById(id);
        if(affectedRows < 0)
            throw new DataNotFoundException("product not found with given parameter");

        return ApiResult.successResponse("product successfully deleted");
    }

    private Product toProduct(ProductDTO productDTO, List<MultipartFile> files, Brand brand, Category category){
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
