package com.JavaTech.PointOfSales.service;

import com.JavaTech.PointOfSales.model.Brand;

import java.util.List;

public interface BrandService {

    List<Brand> listAll();
    Brand addOrSave(Brand brand);
    Brand findByName(String name);
    void deleteById(Long id);
    Brand findById(Long id);
}
