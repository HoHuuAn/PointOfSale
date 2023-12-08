package com.JavaTech.PointOfSales.controller;


import com.JavaTech.PointOfSales.model.*;
import com.JavaTech.PointOfSales.service.*;
import com.JavaTech.PointOfSales.utils.BarcodeUtil;
import com.JavaTech.PointOfSales.utils.ImageUtil;
import com.JavaTech.PointOfSales.dto.ProductDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.beans.factory.support.InstanceSupplier.using;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private BranchService branchService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private QuantityProductService quantityProductService;

    @GetMapping(value = "/list")
    public String listProduct(Model model){
        model.addAttribute("listProducts", productService.listAllDTO());
        return "/products/page-list-product";
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

    @GetMapping(value = "/add")
    public String addProduct(Model model){
        model.addAttribute("brands", brandService.listAll());
        return "/products/page-add-product";
    }

    @PostMapping(value = "/add")
    public String addPostProduct(@RequestParam(name = "name") String name,
                                 @RequestParam(name = "importPrice") int importPrice,
                                 @RequestParam(name = "retailPrice") int retailPrice,
                                 @RequestParam(name = "quantity") int quantity,
                                 @RequestParam(name = "barCode") String barCode,
                                 @RequestParam(name = "image") MultipartFile image,
                                 @RequestParam(name = "brand") String brand,
                                 @RequestParam(name = "description") String description) throws IOException {
        //save product
        Brand brand_org = brandService.findByName(brand);

        Product product = Product.builder()
                .name(name)
                .importPrice(importPrice)
                .retailPrice(retailPrice)
                .image(ImageUtil.convertToBase64(image))
                .createdAt(new Date())
                .barCode(barCode)
                .imageBarCode(ImageUtil.convertToBase64(BarcodeUtil.generateCodeBarcode(barCode, name)))
                .brand(brand_org)
                .description(description)
                .build();
        brand_org.getProducts().add(product);
        brandService.addOrSave(brand_org);
        productService.saveOrUpdate(product);

        Optional<User> info = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        User user = null;
        if(info.isPresent()){
            user = info.get();
        }
        assert user != null;

        List<Branch> allBranches = branchService.listAll();

        for (Branch branch : allBranches) {
            if (branch.equals(user.getBranch())) {
                QuantityProduct quantityProduct = QuantityProduct.builder()
                        .branch(user.getBranch())
                        .product(product)
                        .quantity(quantity)
                        .build();
                quantityProductService.saveOrUpdate(quantityProduct);
            } else {
                QuantityProduct quantityProduct = QuantityProduct.builder()
                        .branch(branch)
                        .product(product)
                        .quantity(0)
                        .build();
                quantityProductService.saveOrUpdate(quantityProduct);
            }
        }
        return "redirect:/products/list";
    }

    @GetMapping(value = "/edit/{id}")
    public String showFormEdit(@PathVariable(name = "id") String id, Model model){
        Product product = productService.findById(id);
        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
        QuantityProduct quantityProduct = findByProduct(product);
        productDTO.setQuantityOfBranch(quantityProduct.getQuantity());
        model.addAttribute("product", productDTO);
        return "/products/page-edit-product";
    }

    @PostMapping(value = "/edit/{id}")
    public String edit(@PathVariable(name = "id") String id,
                       @RequestParam(name = "name") String name,
                       @RequestParam(name = "importPrice") int importPrice,
                       @RequestParam(name = "retailPrice") int retailPrice,
                       @RequestParam(name = "quantity") int quantity,
                       @RequestParam(name = "barCode") String barCode,
                       @RequestParam(name = "image") MultipartFile image,
                       @RequestParam(name = "brand") String brand,
                       @RequestParam(name = "description") String description) throws IOException {
        //save barcode
        BarcodeUtil.generateCodeBarcode(barCode, name);
        
        Brand brand_org = brandService.findByName(brand);

        Product product = productService.findById(id);
        product.setName(name);
        product.setImportPrice(importPrice);
        product.setRetailPrice(retailPrice);
        product.setDescription(description);
        product.setBarCode(barCode);
        product.setBrand(brand_org);
        product.setImage(ImageUtil.convertToBase64(image));

        //save barcode
        BarcodeUtil.generateCodeBarcode(barCode, name);

        productService.saveOrUpdate(product);


        //quantity
        Optional<User> info = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        User user = null;
        if(info.isPresent()){
            user = info.get();
        }
        assert user != null;
        QuantityProduct quantityProduct = QuantityProduct.builder()
                .branch(user.getBranch())
                .product(product)
                .quantity(0)
                .build();
        quantityProductService.saveOrUpdate(quantityProduct);
        return "redirect:/products/list";
    }

    @GetMapping(value = "/delete/{id}")
    public String delete(@PathVariable(name = "id") String id){
        productService.deleteById(id);
        return "redirect:/products/list";
    }
}
