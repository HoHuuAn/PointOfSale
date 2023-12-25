package com.JavaTech.PointOfSales.repository;

import com.JavaTech.PointOfSales.model.Branch;
import com.JavaTech.PointOfSales.model.Customer;
import com.JavaTech.PointOfSales.model.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

    List<OrderProduct> findAllByCustomer(Customer customer);

    List<OrderProduct> findByCreatedAtBetween(Date startDate, Date endDate);

    List<OrderProduct> findByCreatedAtBetweenAndBranch(Date startDate, Date endDate, Branch branch);

    @Query("SELECT COALESCE(SUM(od.quantity), 0) FROM OrderProduct op LEFT JOIN op.orderItems od WHERE op.branch = :branch")
    Integer sumQuantityByBranch(@Param("branch") Branch branch);

    @Query(value = "SELECT COALESCE((SELECT SUM(customer_count) FROM (SELECT COUNT(DISTINCT op.customer_id) AS customer_count FROM orders_product op GROUP BY op.customer_id HAVING COUNT(op.customer_id) >= 2) AS subquery), 0) AS result", nativeQuery = true)
    int countCustomersWithMultipleOrders();
}
