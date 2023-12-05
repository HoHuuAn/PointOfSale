package com.JavaTech.PointOfSales.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class BarcodeUtil {

    public static void main(String[] args) {
        String barcodeData = "12-345-65213412341234123";
        try {
            generateCodeBarcode(barcodeData, "iphone");
            System.out.println("Code128 barcode with text created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void generateCodeBarcode(String data, String name) throws IOException {
        try {
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

            BitMatrix bitMatrix = new MultiFormatWriter().encode(data, BarcodeFormat.CODE_128, 280, 100, hints);

            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            BufferedImage image = new BufferedImage(width, height + 30, BufferedImage.TYPE_INT_RGB);

            Graphics2D graphics = image.createGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, width, height + 30);
            graphics.setColor(Color.BLACK);

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    if (bitMatrix.get(x, y)) {
                        image.setRGB(x, y, 0xFF000000);
                    }
                }
            }
            // Draw data (barcode text) below the barcode
            graphics.setFont(new Font("Arial", Font.PLAIN, 12));
            FontMetrics fontMetrics = graphics.getFontMetrics();
            int textWidth = fontMetrics.stringWidth(data);
            graphics.drawString(data, (width - textWidth) / 2, height + 20);


            //write
            File outputFile = new File("./src/main/resources/static/products/" + name + "/code.jpg");
            if (!Files.exists(outputFile.toPath())) {
                Files.createDirectories(outputFile.toPath());
            }
            ImageIO.write(image, "png", outputFile);
        } catch (Exception e) {
            throw new IOException("Error generating Code128 barcode with text.", e);
        }
    }
}