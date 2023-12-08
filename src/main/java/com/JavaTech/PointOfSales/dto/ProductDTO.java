package com.JavaTech.PointOfSales.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@ToString
public class ProductDTO {
    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("image")
    private String image;

    @JsonProperty("barCode")
    private String barCode;

    @JsonProperty("ImageBarCode")
    private String ImageBarCode;

    @JsonProperty("importPrice")
    private int importPrice;

    @JsonProperty("retailPrice")
    private int retailPrice;

    private int quantityOfBranch;

    @JsonProperty("brand")
    private BrandDTO brand;

    @JsonProperty("description")
    private String description;
}