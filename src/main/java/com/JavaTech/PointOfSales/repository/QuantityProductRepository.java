package com.JavaTech.PointOfSales.repository;


import com.JavaTech.PointOfSales.model.Branch;
import com.JavaTech.PointOfSales.model.Product;
import com.JavaTech.PointOfSales.model.QuantityProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuantityProductRepository extends JpaRepository<QuantityProduct, Long> {
    Optional<QuantityProduct> findQuantityProductByBranchAndProduct(Branch branch, Product product);

    @Query("SELECT SUM(qp.quantity) FROM QuantityProduct qp WHERE qp.branch = :branch")
    Integer sumQuantityByBranch(@Param("branch") Branch branch);

    List<QuantityProduct> findAllByBranch(Branch branch);
}
