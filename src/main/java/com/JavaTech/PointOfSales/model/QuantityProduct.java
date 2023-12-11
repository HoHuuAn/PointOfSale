package com.JavaTech.PointOfSales.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "quantity_product")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuantityProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "quantity")
    private int quantity;
}
