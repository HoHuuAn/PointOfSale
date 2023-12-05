package com.JavaTech.PointOfSales.repository;


import com.JavaTech.PointOfSales.model.Branch;
import com.JavaTech.PointOfSales.model.Product;
import com.JavaTech.PointOfSales.model.QuantityProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuantityProductRepository extends JpaRepository<QuantityProduct, Long> {
    QuantityProduct findQuantityProductByBranchAndProduct(Branch branch, Product product);
}
