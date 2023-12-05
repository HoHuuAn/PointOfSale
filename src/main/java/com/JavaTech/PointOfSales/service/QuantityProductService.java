package com.JavaTech.PointOfSales.service;

import com.JavaTech.PointOfSales.model.Branch;
import com.JavaTech.PointOfSales.model.Product;
import com.JavaTech.PointOfSales.model.QuantityProduct;

public interface QuantityProductService {
    QuantityProduct saveOrUpdate(QuantityProduct quantityProduct);
    QuantityProduct findByBranchAndProduct(Branch branch, Product product);
}
