package com.JavaTech.PointOfSales.service;

import com.JavaTech.PointOfSales.model.Branch;

import java.util.List;

public interface BranchService {
    Branch saveOrUpdate(Branch branch);
    List<Branch> listAll();
}
