package com.JavaTech.PointOfSales.repository;

import com.JavaTech.PointOfSales.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {
    Optional<Branch> findBranchByName (String name);
    Optional<Branch> findBranchByNameAndAddress (String name, String address);

}
