package com.JavaTech.PointOfSales.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderDetailDTO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("product")
    private ProductDTO product;

    @JsonProperty("quantity")
    private int quantity;
}
