package com.JavaTech.PointOfSales.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Lob;
import lombok.*;

import java.util.Base64;

@Getter
@Setter
@ToString
public class ProductDTO {
    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @Lob
    @JsonProperty("image")
    private byte[] image;

    @JsonProperty("barCode")
    private String barCode;

    @JsonProperty("ImageBarCode")
    private String ImageBarCode;

    @JsonProperty("TotalSales")
    private String totalSales;

    @JsonProperty("importPrice")
    private int importPrice;

    @JsonProperty("retailPrice")
    private int retailPrice;

    private int quantityOfBranch;

    @JsonProperty("brand")
    private BrandDTO brand;

    @JsonProperty("description")
    private String description;

    public String getEncodedImage() {
        return Base64.getEncoder().encodeToString(image);
    }
}