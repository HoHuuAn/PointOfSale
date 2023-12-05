package com.JavaTech.PointOfSales.service;

import com.JavaTech.PointOfSales.model.Product;
import com.JavaTech.PointOfSales.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    User saveOrUpdate(User user);

    List<User> listAll();

    Optional<User> findByUsername(String username);

    void deleteById(Long id);
}
