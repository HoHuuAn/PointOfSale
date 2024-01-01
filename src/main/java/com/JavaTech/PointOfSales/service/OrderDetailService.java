package com.JavaTech.PointOfSales.service;

import com.JavaTech.PointOfSales.model.OrderDetail;
import com.JavaTech.PointOfSales.model.Product;

import java.util.List;
import java.util.Optional;

public interface OrderDetailService {
    OrderDetail saveOrUpdate(OrderDetail orderDetail);
    Optional<OrderDetail> findByProduct(Product product);
}
