package uz.spiders.ecommerce.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Service
@RequiredArgsConstructor
public class S3StorageService {

    private final AmazonS3 amazonS3;

    @Value("${aws.s3.bucket.name}")
    private String s3BucketName;

    @Value("${aws.s3.endpointUrl}")
    private String endpointUrl;

    @Value("${aws.s3.fileNamePrefix}")
    private String fileNamePathPrefix;

    @Async
    public CompletableFuture<S3ObjectInputStream> findByName(String fileName) {
        return CompletableFuture
                .completedFuture(amazonS3.getObject(s3BucketName, fileName).getObjectContent());
    }

    @Async
    public Future<String> save(String keyName, final MultipartFile file) {
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());
            InputStream inputStream = file.getInputStream();
            amazonS3.putObject(new PutObjectRequest(s3BucketName,fileNamePathPrefix+keyName, inputStream, metadata));
            inputStream.close();

            return CompletableFuture.supplyAsync(() -> amazonS3.getUrl(s3BucketName, fileNamePathPrefix+keyName).toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*public static String generatePresignedUrl(String bucketName, String key) {
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, key);
        request.setExpiration(); // 1 hour
        return ;
    }*/
}