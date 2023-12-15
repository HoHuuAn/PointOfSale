package com.JavaTech.PointOfSales.service;

import com.JavaTech.PointOfSales.model.Branch;
import com.JavaTech.PointOfSales.model.Customer;
import com.JavaTech.PointOfSales.model.OrderProduct;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderProductService {
    List<OrderProduct> findAllByCustomer(Customer customer);

    OrderProduct saveOrUpdate(OrderProduct orderProduct);

    OrderProduct findById(Long id);

    List<OrderProduct> getOrdersBetweenDates(Date startDate, Date endDate);
    Long calculateTotalProfit(List<OrderProduct> orderProducts);

    Map<String, Long> getSumTotalAmountByMonth(Branch branch);
}
