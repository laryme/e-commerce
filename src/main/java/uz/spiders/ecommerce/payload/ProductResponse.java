package uz.spiders.ecommerce.payload;

import java.util.List;


public record ProductResponse(
        String name,
        Double price,
        String description,
        List<DetailCategory> details,
        BrandDTO brandDTO,
        List<Link> images,
        Link links
        //List<Link> relatedProducts
) {}
