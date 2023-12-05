package com.JavaTech.PointOfSales.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders_detail")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private OrderProduct order;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private Product product;

    @Column(name = "quantity")
    private int quantity;

    @Override
    public String toString() {
        return "OrderDetail{" +
                "id=" + id +
                ", order=" + order.getId() +
                ", product=" + product.getName() +
                ", quantity=" + quantity +
                '}';
    }
}
