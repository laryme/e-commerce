package uz.spiders.ecommerce.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.spiders.ecommerce.entity.ProductPicture;
import uz.spiders.ecommerce.repository.ProductPictureRepository;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductPictureService {
    private final StorageService storageService;
    private final ProductPictureRepository productPictureRepository;

    public List<ProductPicture> saveProductPicture(List<MultipartFile> multipartFileList){
        List<ProductPicture> productPictures = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFileList) {
            Path path = storageService.saveProductPicture(multipartFile);
            productPictures.add(productPictureRepository.save(toProductPicture(String.valueOf(path.getFileName()), path.toString())));
        }

        return productPictures;
    }

    private ProductPicture toProductPicture(String fileName, String fileUrl){
        return ProductPicture.builder()
                .fileName(fileName)
                .fileUrl(fileUrl)
                .build();
    }
}
