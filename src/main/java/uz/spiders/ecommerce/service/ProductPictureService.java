package uz.spiders.ecommerce.service;

import jakarta.mail.Multipart;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.spiders.ecommerce.entity.ProductPicture;
import uz.spiders.ecommerce.repository.ProductPictureRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
@RequiredArgsConstructor
public class ProductPictureService {
    private final S3StorageService s3StorageService;
    private final ProductPictureRepository productPictureRepository;

    public List<ProductPicture> saveProductPicture(List<MultipartFile> multipartFileList){
        List<ProductPicture> productPictures = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFileList) {
            String pictureKeyName = "product-" + UUID.randomUUID();
            Future<String> objectUrl = s3StorageService.save("product-" + pictureKeyName, multipartFile);

            try {
                productPictures.add(toProductPicture(pictureKeyName, objectUrl.get()));
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
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
