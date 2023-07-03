package uz.spiders.ecommerce.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import uz.spiders.ecommerce.controller.interfaces.ProductController;
import uz.spiders.ecommerce.entity.Brand;
import uz.spiders.ecommerce.entity.Product;
import uz.spiders.ecommerce.entity.ProductDetail;
import uz.spiders.ecommerce.entity.ProductPicture;
import uz.spiders.ecommerce.payload.*;
import uz.spiders.ecommerce.utils.Constants;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface ProductMapper {
    String PRODUCT_IMAGE_PATH = Constants.BASE_DOMAIN + ProductController.BASE_PATH + "/image/";
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
    @Mapping(source = "name", target = "name")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "brand", target = "brandDTO", qualifiedByName = "brandToBrandDTO")
    @Mapping(source = "productPictures", target = "images", qualifiedByName = "productPicturesToLinks")
    @Mapping(source = "id", target = "links", qualifiedByName = "productToLinks")
    @Mapping(source = "productDetails", target = "details", qualifiedByName = "productDetailsToDetails")
    ProductResponse fromProduct(Product product);

    @Named("productPicturesToLinks")
    default List<Link> productPicturesToLinks(List<ProductPicture> productPictures) {
        return productPictures.stream().map(r -> new Link("self", PRODUCT_IMAGE_PATH + r.getFileName())).collect(Collectors.toList());
    }

    @Named("productToLinks")
    default Link productToLinks(Integer id) {
        return new Link("self", "/link/"+id);
    }

    @Named(value = "brandToBrandDTO")
    default BrandDTO brandToBrandDTO(Brand brand) {
        return new BrandDTO(
                brand.getName(),
                new Link(
                        "self",
                        "/link/to/brand"
                )
        );
    }

    @Named("productDetailsToDetails")
    default List<DetailCategory> productToLinks(List<ProductDetail> productDetails) {
        return productDetails.stream()
                .collect(Collectors.groupingBy(
                        productDetail -> productDetail.getDetailKey().getDetailCategory()
                ))
                .entrySet()
                .stream()
                .map(entry -> new DetailCategory(
                        entry.getKey().getName(),
                        entry.getValue().stream()
                                .map(productDetail -> new Detail(
                                        productDetail.getDetailKey().getName(),
                                        productDetail.getValue()
                                ))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }

    static ProductMapper getInstance(){
        return INSTANCE;
    }
}