package com.JavaTech.PointOfSales.service.impl;

import com.JavaTech.PointOfSales.model.*;
import com.JavaTech.PointOfSales.repository.OrderProductRepository;
import com.JavaTech.PointOfSales.service.OrderProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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

    @Override
    public Long calculateTotalProfit(List<OrderProduct> orderProducts) {
        long totalProfit = 0L;
        for (OrderProduct orderProduct : orderProducts) {
            List<OrderDetail> orderDetails = orderProduct.getOrderItems();
            for (OrderDetail orderDetail : orderDetails) {
                Product product = orderDetail.getProduct();
                int profit = (product.getRetailPrice() - product.getImportPrice()) * orderDetail.getQuantity();
                totalProfit += profit;
            }
        }
        return totalProfit;
    }

    @Override
    public Map<String, Long> getSumTotalAmountByMonth(Branch branch) {
        Calendar calendar = Calendar.getInstance();
        Date endDate = calendar.getTime();

        calendar.add(Calendar.MONTH, -11);
        Date startDate = calendar.getTime();

        List<OrderProduct> orderProducts = orderProductRepository.findByCreatedAtBetweenAndBranch(startDate, endDate, branch);

        Map<String, Long> sumByMonth = new LinkedHashMap<>();
        calendar.setTime(startDate);
        while (calendar.getTime().before(endDate) || calendar.getTime().equals(endDate)) {
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1; // Add 1 to align with desired month representation
            String key = String.format("%04d-%02d", year, month);
            sumByMonth.put(key, 0L);
            calendar.add(Calendar.MONTH, 1);
        }

        // Populate the values for existing months from the orderProducts list
        for (OrderProduct orderProduct : orderProducts) {
            Calendar orderCalendar = Calendar.getInstance();
            orderCalendar.setTime(orderProduct.getCreatedAt());
            int year = orderCalendar.get(Calendar.YEAR);
            int month = orderCalendar.get(Calendar.MONTH) + 1;
            String key = String.format("%04d-%02d", year, month);
            Long currentTotal = sumByMonth.get(key);
            sumByMonth.put(key, currentTotal + orderProduct.getTotalAmount());
        }
        return sumByMonth;
    }
}