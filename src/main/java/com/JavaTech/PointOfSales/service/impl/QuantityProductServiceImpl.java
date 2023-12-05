package com.JavaTech.PointOfSales.service.impl;

import com.JavaTech.PointOfSales.model.Branch;
import com.JavaTech.PointOfSales.model.Product;
import com.JavaTech.PointOfSales.model.QuantityProduct;
import com.JavaTech.PointOfSales.repository.QuantityProductRepository;
import com.JavaTech.PointOfSales.service.QuantityProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuantityProductServiceImpl implements QuantityProductService {

    @Autowired
    private QuantityProductRepository quantityProductRepository;

    @Override
    public QuantityProduct saveOrUpdate(QuantityProduct quantityProduct) {
        return quantityProductRepository.save(quantityProduct);
    }

    @Override
    public QuantityProduct findByBranchAndProduct(Branch branch, Product product) {
        return quantityProductRepository.findQuantityProductByBranchAndProduct(branch, product);
    }
}
