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

    @Query("SELECT SUM(od.quantity) FROM OrderProduct op INNER JOIN op.orderItems od WHERE op.branch = :branch")
    Integer sumQuantityByBranch(@Param("branch") Branch branch);

    @Query("SELECT COUNT(DISTINCT op.customer) FROM OrderProduct op GROUP BY op.customer HAVING COUNT(op.customer) >= 2")
    int countCustomersWithMultipleOrders();
}
