package com.JavaTech.PointOfSales.controller;

import com.JavaTech.PointOfSales.dto.CustomerDTO;
import com.JavaTech.PointOfSales.model.Customer;
import com.JavaTech.PointOfSales.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping(value = "/customers")
public class CustomerController {




    @Autowired
    private CustomerService customerService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = "/list")
    public String listCustomer(){
        return "/customers/page-list-customers";
    }

    @PostMapping(value = "/find-by-phone")
    @ResponseBody
    public ResponseEntity<?> findByPhone(@RequestParam("phone") String phone) {
        Customer customer = customerService.findByPhone(phone);
        System.out.println(customer);
        CustomerDTO customerDTO = modelMapper.map(customer, CustomerDTO.class);
        if (customer != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("customer", customerDTO);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/update-by-phone")
    @ResponseBody
    public ResponseEntity<?> updateByPhone(@RequestParam("phone") String phone,
                                           @RequestParam("name") String name,
                                           @RequestParam("address") String address) {
        Customer customer = customerService.findByPhone(phone);
        if(customer == null){
            customerService.saveOrUpdate(Customer.builder()
                    .phone(phone)
                    .name(name)
                    .address(address).build());
        }
        CustomerDTO customerDTO = modelMapper.map(customerService.findByPhone(phone), CustomerDTO.class);
        Map<String, Object> response = new HashMap<>();
        response.put("customer", customerDTO);
        return ResponseEntity.ok(response);
    }
}
