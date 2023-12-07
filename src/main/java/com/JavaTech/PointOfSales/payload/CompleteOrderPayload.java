
















package com.JavaTech.PointOfSales.payload;

import com.JavaTech.PointOfSales.dto.CustomerDTO;
import com.JavaTech.PointOfSales.model.Customer;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class CompleteOrderPayload {
    @JsonProperty("idValue")
    private Long idValue;

    @JsonProperty("cash")
    private Long cash;

    @JsonProperty("customer")
    private CustomerDTO customer;
}
