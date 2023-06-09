package uz.spiders.ecommerce.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.spiders.ecommerce.entity.DetailCategory;
import uz.spiders.ecommerce.entity.DetailKey;
import uz.spiders.ecommerce.entity.ProductDetail;
import uz.spiders.ecommerce.exception.DataNotFoundException;
import uz.spiders.ecommerce.payload.DetailKeyDTO;
import uz.spiders.ecommerce.payload.ProductDetailDTO;
import uz.spiders.ecommerce.repository.DetailCategoryRepository;
import uz.spiders.ecommerce.repository.DetailKeyRepository;
import uz.spiders.ecommerce.repository.ProductDetailRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductDetailService {

    private final ProductDetailRepository productDetailRepository;
    private final DetailKeyRepository detailKeyRepository;
    private final DetailCategoryRepository detailCategoryRepository;

    public List<ProductDetail> saveProductDetail(List<ProductDetailDTO> productDetailDTOS){
        return productDetailDTOS.stream()
                .map(this::toProductDetail)
                .map(productDetailRepository::save)
                .collect(Collectors.toList());
    }

    private ProductDetail toProductDetail(ProductDetailDTO productDetailDTO){
        ProductDetail productDetail = ProductDetail.builder()
                .detailKey(toDetailKey(productDetailDTO.detailKeyDTO()))
                .value(productDetailDTO.value())
                .build();

        return productDetailRepository.save(productDetail);
    }

    private DetailKey toDetailKey(DetailKeyDTO detailKeyDTO){
        DetailCategory detailCategory = detailCategoryRepository.findById(detailKeyDTO.detailCategoryId()).orElseThrow(() -> new DataNotFoundException("detailCategory not found"));
        return detailKeyRepository.findById(detailKeyDTO.detailKeyId())
                .orElseThrow(() -> new DataNotFoundException("detailKey not found"));
    }
}
