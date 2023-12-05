package com.JavaTech.PointOfSales.utils;

import java.io.*;
import java.nio.file.*;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {

    public static void saveFile(String fileName,
                                MultipartFile multipartFile) throws IOException {
        File file = new File(fileName);
        String fileNameWithoutExtension = file.getName().replaceFirst("[.][^.]+$", "");

        Path uploadPath = Paths.get("./src/main/resources/static/user-photos/" + fileNameWithoutExtension);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
    }
}