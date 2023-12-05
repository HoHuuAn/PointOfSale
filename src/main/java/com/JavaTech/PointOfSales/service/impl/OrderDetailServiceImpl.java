package com.JavaTech.PointOfSales.service.impl;

import com.JavaTech.PointOfSales.model.OrderDetail;
import com.JavaTech.PointOfSales.repository.OrderDetailRepository;
import com.JavaTech.PointOfSales.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public OrderDetail saveOrUpdate(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }
}
