package com.JavaTech.PointOfSales.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ImageBrandUtil {
    public static void saveFile(String fileName,
                                MultipartFile multipartFile) throws IOException {
        File file = new File(fileName);
        String fileNameWithoutExtension = file.getName().replaceFirst("[.][^.]+$", "");

        Path uploadPath = Paths.get("./src/main/resources/static/brands/" + fileNameWithoutExtension);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        } try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }


    }
}
