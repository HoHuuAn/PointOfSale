package com.JavaTech.PointOfSales.service.impl;

import com.JavaTech.PointOfSales.dto.ProductDTO;
import com.JavaTech.PointOfSales.model.Product;
import com.JavaTech.PointOfSales.model.QuantityProduct;
import com.JavaTech.PointOfSales.model.User;
import com.JavaTech.PointOfSales.repository.ProductRepository;
import com.JavaTech.PointOfSales.service.ProductService;
import com.JavaTech.PointOfSales.service.QuantityProductService;
import com.JavaTech.PointOfSales.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private QuantityProductService quantityProductService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Product saveOrUpdate(Product product) {
        return productRepository.save(product);
    }
    @Override
    public List<Product> listAll() {
        return productRepository.findAll();
    }

    @Override
    public List<ProductDTO> listAllDTO() {
        return listAll().stream()
                .map(product -> {
                    ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
                    QuantityProduct quantityProduct = findByProduct(product);
                    productDTO.setQuantityOfBranch(quantityProduct.getQuantity());
                    return productDTO;
                })
                .collect(Collectors.toList());
    }

    public QuantityProduct findByProduct(Product product){
        Optional<User> info = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        User user = null;
        if(info.isPresent()){
            user = info.get();
        }
        assert user != null;
        return quantityProductService.findByBranchAndProduct(user.getBranch(), product);
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

    @Override
    public List<ProductDTO> getTopThreeProductsByTotalSales() {
        // Retrieve all products from the repository
        List<Product> products = productRepository.findAll();

        // Sort the products by their total sales in descending order
        List<Product> list = products.stream()
                .sorted(Comparator.comparingInt(Product::getTotalSales).reversed())
                .limit(3)
                .collect(Collectors.toList());

        return listDTO(list);
    }

    @Override
    public List<ProductDTO> listDTO( List<Product> list) {
        return list.stream()
                .map(product -> {
                    ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
                    QuantityProduct quantityProduct = findByProduct(product);
                    productDTO.setQuantityOfBranch(quantityProduct.getQuantity());
                    return productDTO;
                })
                .collect(Collectors.toList());
    }
}
