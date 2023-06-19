package uz.spiders.ecommerce.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;
import uz.spiders.ecommerce.controller.interfaces.ProductController;
import uz.spiders.ecommerce.payload.ApiResult;
import uz.spiders.ecommerce.payload.ProductDTO;
import uz.spiders.ecommerce.service.interfaces.ProductService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ProductControllerImpl implements ProductController {
    private final ProductService productService;
    private final ObjectMapper objectMapper;

    @Override
    public ApiResult<?> createNewProduct(StandardMultipartHttpServletRequest request, List<MultipartFile> files) {
        ProductDTO productDTO;
        try {
            productDTO = objectMapper.readValue(request.getParameter("product"), ProductDTO.class);
            System.out.println(productDTO);
        } catch (JsonProcessingException e) {
            //todo optimize
            throw new RuntimeException(e);
        }
        return productService.createNewProduct(productDTO, files);
    }

    @Override
    public ApiResult<?> getProductById(UUID id) {
        return productService.getProductById(id);
    }

    @Override
    public ApiResult<?> getAllProduct(Integer page, int size, String[] sort) {
        return productService.getAllProduct(page, size, sort);
    }

    @Override
    public ApiResult<?> deleteProduct(UUID id) {
        return productService.deleteProduct(id);
    }
}
