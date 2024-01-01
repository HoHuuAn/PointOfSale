package com.JavaTech.PointOfSales.service.impl;

import com.JavaTech.PointOfSales.dto.ProductDTO;
import com.JavaTech.PointOfSales.model.OrderDetail;
import com.JavaTech.PointOfSales.model.Product;
import com.JavaTech.PointOfSales.model.QuantityProduct;
import com.JavaTech.PointOfSales.model.User;
import com.JavaTech.PointOfSales.repository.ProductRepository;
import com.JavaTech.PointOfSales.service.OrderDetailService;
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
    private OrderDetailService orderDetailService;

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
                    if ((quantityProduct != null)) {
                        productDTO.setQuantityOfBranch(quantityProduct.getQuantity());
                    } else {
                        productDTO.setQuantityOfBranch(0);
                    }
                    return productDTO;
                })
                .collect(Collectors.toList());
    }

    public QuantityProduct findByProduct(Product product){
        return quantityProductService.findByBranchAndProduct(userService.getCurrentUser().getBranch(), product);
    }

    @Override
    public boolean deleteById(String id) {
        Product product = findById(id);
        if (orderDetailService.findByProduct(product).isEmpty()){
            productRepository.deleteById(id);
            return true;
        }
        return false;
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
        List<Product> products = productRepository.findAll();

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
//                    QuantityProduct quantityProduct = quantityProductService
//                    productDTO.setQuantityOfBranch(quantityProduct.getQuantity());
                    return productDTO;
                })
                .collect(Collectors.toList());
    }
}
