package uz.spiders.ecommerce.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class StorageService {
    @Value("${app.images.product.path}")
    private String productPicturePath;

    @Value("${app.images.brand.path}")
    private String brandLogoPath;

    public Path saveProductPicture(String fileName, MultipartFile multipartFile){
        return saveMultipartFile(productPicturePath, fileName, multipartFile);
    }

    public Path saveProductPicture(MultipartFile multipartFile){
        return saveMultipartFile(productPicturePath, getFileName("product-"), multipartFile);
    }

    public Path saveBrandLogo(MultipartFile multipartFile){
        return saveMultipartFile(brandLogoPath, getFileName("logo-"), multipartFile);
    }

    public byte[] getProductPicture(String path) throws IOException {
        return getFileBytes(path);
    }

    public byte[] getFile(String path) throws IOException {
        return getFileBytes(path);
    }

    private byte[] getFileBytes(String path) throws IOException {
        return Files.readAllBytes(Path.of(path));
    }

    private Path saveFile(String path, String fileName, File file) throws IOException {
        Path filePath = Path.of(path).resolve(fileName);
        return Files.copy(file.toPath(), filePath, StandardCopyOption.REPLACE_EXISTING);
    }

    private Path saveFile(String path, File file) throws IOException {
        return saveFile(path, getNewFileName(), file);
    }

    private Path saveMultipartFile(String path, String fileName, MultipartFile multipartFile) {
        if (path.isEmpty() || fileName.isEmpty() || multipartFile == null)
            throw new IllegalArgumentException("Invalid input parameters");
        //todo optimize

        Path multipartFilePath = Path.of(path,fileName);
        try {
            Files.createDirectories(multipartFilePath);
            Files.copy(multipartFile.getInputStream(), multipartFilePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return multipartFilePath;
    }

    private String getNewFileName() {
        return "file-" + UUID.randomUUID();
    }

    private String getFileName(String preFix) {
        return preFix + UUID.randomUUID();
    }

    public void replaceExcitingFile(String path, MultipartFile multipartFile) {
        try {
            Files.copy(multipartFile.getInputStream(), Path.of(path), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
