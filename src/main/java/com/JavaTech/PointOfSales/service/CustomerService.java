package com.JavaTech.PointOfSales.service;

import com.JavaTech.PointOfSales.model.Customer;

import java.util.Optional;

public interface CustomerService {
    Customer saveOrUpdate(Customer customer);

    Customer findByName(String name);

    Customer findByPhone(String phone);
}
