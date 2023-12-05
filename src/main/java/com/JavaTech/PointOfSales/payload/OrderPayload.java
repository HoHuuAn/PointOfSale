package com.JavaTech.PointOfSales.payload;

import com.JavaTech.PointOfSales.dto.ProductDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderPayload {
    @JsonProperty("product")
    private ProductDTO product;

    @JsonProperty("quantity")
    private int quantity;
}
