package com.JavaTech.PointOfSales.service;

import com.JavaTech.PointOfSales.model.Branch;
import com.JavaTech.PointOfSales.model.Product;
import com.JavaTech.PointOfSales.model.QuantityProduct;

import java.util.List;

public interface QuantityProductService {
    QuantityProduct saveOrUpdate(QuantityProduct quantityProduct);
    QuantityProduct findByBranchAndProduct(Branch branch, Product product);
    int sumQuantityByBranch(Branch branch);
    List<QuantityProduct> findAllByBranch(Branch branch);
}
