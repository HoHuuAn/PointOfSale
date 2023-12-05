package com.JavaTech.PointOfSales.repository;

import com.JavaTech.PointOfSales.model.Customer;
import com.JavaTech.PointOfSales.model.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
    List<OrderProduct> findAllByCustomer(Customer customer);

    List<OrderProduct> findByCreatedAtBetween(Date startDate, Date endDate);
}
