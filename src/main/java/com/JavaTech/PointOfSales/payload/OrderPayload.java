

















package com.JavaTech.PointOfSales.payload;

import com.JavaTech.PointOfSales.dto.ProductDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;




@ToString
@Getter
@Setter
public class OrderPayload {


    @JsonProperty("quantity")
    private int quantity;

    @JsonProperty("product")
    private ProductDTO product;
}
