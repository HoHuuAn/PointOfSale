package com.JavaTech.PointOfSales.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

public class ImageUtil {
    public static String convertToBase64(MultipartFile file) throws IOException {
        byte[] fileContent = file.getBytes();
        return Base64.getEncoder().encodeToString(fileContent);






    }}
