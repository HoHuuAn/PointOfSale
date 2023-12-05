package com.JavaTech.PointOfSales.service.impl;

import com.JavaTech.PointOfSales.model.Product;
import com.JavaTech.PointOfSales.repository.ProductRepository;
import com.JavaTech.PointOfSales.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Override
    public Product saveOrUpdate(Product product) {
        return productRepository.save(product);
    }
    @Override
    public List<Product> listAll() {
        return productRepository.findAll();
    }

    @Override
    public void deleteById(String id) {
        productRepository.deleteById(id);
    }

    @Override
    public void deleteByProduct(Product product) {
        productRepository.delete(product);
    }

    @Override
    public Product findById(String id) {
        return productRepository.findById(id).orElseThrow();
    }

    @Override
    public Product findProductByBarCode(String barcode) {
        Optional<Product> product = productRepository.findProductByBarCode(barcode);
        return product.orElse(null);
    }
}
