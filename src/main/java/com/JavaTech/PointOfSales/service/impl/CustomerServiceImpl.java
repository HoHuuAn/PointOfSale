package com.JavaTech.PointOfSales.service.impl;

import com.JavaTech.PointOfSales.model.Customer;
import com.JavaTech.PointOfSales.repository.CustomerRepository;
import com.JavaTech.PointOfSales.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer saveOrUpdate(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer findByName(String name) {
        return customerRepository.findCustomerByName(name).orElseThrow();
    }

    @Override
    public Customer findByPhone(String phone) {
        Optional<Customer> customer =  customerRepository.findCustomerByPhone(phone);
        return customer.orElse(null);
    }
}
