package com.JavaTech.PointOfSales.repository;

import com.JavaTech.PointOfSales.model.OrderDetail;
import com.JavaTech.PointOfSales.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    OrderDetail findOrderDetailByProduct(Product product);
}
