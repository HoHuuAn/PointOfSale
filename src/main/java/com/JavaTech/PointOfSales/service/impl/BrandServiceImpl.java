package com.JavaTech.PointOfSales.service.impl;

import com.JavaTech.PointOfSales.model.Brand;
import com.JavaTech.PointOfSales.repository.BrandRepository;
import com.JavaTech.PointOfSales.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandRepository brandRepository;

    @Override
    public List<Brand> listAll() {
        return brandRepository.findAll();
    }

    @Override
    public Brand addOrSave(Brand category) {
        return brandRepository.save(category);
    }

    @Override
    public Brand findByName(String name) {
        return brandRepository.findBrandByName(name).orElseThrow();
    }

    @Override
    public void deleteById(Long id) {
        brandRepository.deleteById(id);
    }

    @Override
    public Brand findById(Long id) {
        return brandRepository.findById(id).orElseThrow();
    }
}
