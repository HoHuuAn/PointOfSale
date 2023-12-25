package com.JavaTech.PointOfSales.repository;

import com.JavaTech.PointOfSales.model.Brand;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    Optional<Brand> findBrandByName(String name);
}
