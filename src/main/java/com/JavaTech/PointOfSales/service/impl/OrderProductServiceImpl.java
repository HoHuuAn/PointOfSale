package com.JavaTech.PointOfSales.service.impl;

import com.JavaTech.PointOfSales.model.Customer;
import com.JavaTech.PointOfSales.model.OrderProduct;
import com.JavaTech.PointOfSales.repository.OrderProductRepository;
import com.JavaTech.PointOfSales.service.OrderProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderProductServiceImpl implements OrderProductService {
    @Autowired
    private OrderProductRepository orderProductRepository;

    @Override
    public List<OrderProduct> findAllByCustomer(Customer customer) {
        return orderProductRepository.findAllByCustomer(customer);
    }

    @Override
    public OrderProduct saveOrUpdate(OrderProduct orderProduct) {
        return orderProductRepository.save(orderProduct);
    }

    @Override
    public OrderProduct findById(Long id) {
        return orderProductRepository.findById(id).orElse(null);
    }

    @Override
    public List<OrderProduct> getOrdersBetweenDates(Date startDate, Date endDate) {
        return orderProductRepository.findByCreatedAtBetween(startDate, endDate);
    }
}