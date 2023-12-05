package com.JavaTech.PointOfSales.repository;

import com.JavaTech.PointOfSales.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findCustomerByName(String name);

    @Query("SELECT DISTINCT c FROM Customer c LEFT JOIN FETCH c.orderProduct op WHERE c.phone = :phone")
    Optional<Customer> findCustomerByPhone(@Param("phone") String phone);
}
