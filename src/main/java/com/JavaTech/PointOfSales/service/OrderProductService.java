package com.JavaTech.PointOfSales.service;

import com.JavaTech.PointOfSales.model.Customer;
import com.JavaTech.PointOfSales.model.OrderProduct;

import java.util.Date;
import java.util.List;

public interface OrderProductService {


    OrderProduct saveOrUpdate(OrderProduct orderProduct);

    OrderProduct findById(Long id);

    List<OrderProduct> getOrdersBetweenDates(Date startDate, Date endDate);
    List<OrderProduct> findAllByCustomer(Customer customer);


}
