package com.JavaTech.PointOfSales.service.impl;

import com.JavaTech.PointOfSales.model.OrderDetail;
import com.JavaTech.PointOfSales.model.Product;
import com.JavaTech.PointOfSales.repository.OrderDetailRepository;
import com.JavaTech.PointOfSales.service.OrderDetailService;
import com.JavaTech.PointOfSales.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public OrderDetail saveOrUpdate(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public Optional<OrderDetail> findByProduct(Product product) {
        return Optional.ofNullable(orderDetailRepository.findOrderDetailByProduct(product));
    }
}
