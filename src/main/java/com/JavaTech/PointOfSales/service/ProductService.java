package com.JavaTech.PointOfSales.service;

import com.JavaTech.PointOfSales.dto.ProductDTO;
import com.JavaTech.PointOfSales.model.Product;

import java.util.List;

public interface ProductService {

    Product saveOrUpdate(Product product);

    List<Product> listAll();

    List<ProductDTO> listAllDTO();

    void deleteById(String id);

    void deleteByProduct(Product product);

    Product findById(String id);

    Product findProductByBarCode(String Barcode);
}
