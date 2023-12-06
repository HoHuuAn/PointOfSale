package com.JavaTech.PointOfSales.service.impl;

import com.JavaTech.PointOfSales.model.Branch;
import com.JavaTech.PointOfSales.repository.BranchRepository;
import com.JavaTech.PointOfSales.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchServiceImpl implements BranchService {

    @Autowired
    private BranchRepository branchRepository;

    @Override
    public Branch saveOrUpdate(Branch branch) {
        return branchRepository.save(branch);
    }

    @Override
    public List<Branch> listAll() {
        return branchRepository.findAll();
    }
}
