package uz.spiders.ecommerce.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import uz.spiders.ecommerce.controller.interfaces.BrandController;
import uz.spiders.ecommerce.entity.Brand;
import uz.spiders.ecommerce.payload.Link;
import uz.spiders.ecommerce.payload.BrandResponseDTO;
import uz.spiders.ecommerce.utils.Constants;

@Mapper
public interface BrandMapper {
    String BRAND_LOGO_PATH = Constants.BASE_DOMAIN + BrandController.BASE_PATH + "/logo/";
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "id", target = "logo", qualifiedByName = "logoToLink")
    BrandResponseDTO fromBrand(Brand brand);

    @Named(value = "logoToLink")
    default Link logoToLink(Integer brandId){
        return new Link("self", BRAND_LOGO_PATH+brandId);
    }
}
