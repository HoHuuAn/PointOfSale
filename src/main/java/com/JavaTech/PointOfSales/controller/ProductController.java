package com.JavaTech.PointOfSales.controller;


import com.JavaTech.PointOfSales.Utils.BarcodeUtil;
import com.JavaTech.PointOfSales.Utils.ImageUtil;
import com.JavaTech.PointOfSales.dto.ProductDTO;
import com.JavaTech.PointOfSales.model.Brand;
import com.JavaTech.PointOfSales.model.Product;
import com.JavaTech.PointOfSales.model.QuantityProduct;
import com.JavaTech.PointOfSales.model.User;
import com.JavaTech.PointOfSales.service.BrandService;
import com.JavaTech.PointOfSales.service.ProductService;
import com.JavaTech.PointOfSales.service.QuantityProductService;
import com.JavaTech.PointOfSales.service.UserService;
import jakarta.annotation.PostConstruct;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
    private ModelMapper modelMapper;

    @Autowired
    private QuantityProductService quantityProductService;

    @GetMapping(value = "/list")
    public String listProduct(Model model){
        List<ProductDTO> productDTOList = productService.listAll().stream()
                .map(product -> {
                    ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
                    QuantityProduct quantityProduct = findByProduct(product);
                    productDTO.setQuantityOfBranch(quantityProduct.getQuantity());
                    return productDTO;
                })
                .collect(Collectors.toList());
        model.addAttribute("listProducts", productDTOList);
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

        
        //save barcode
        BarcodeUtil.generateCodeBarcode(barCode, name);

        //save product
        Brand brand_org = brandService.findByName(brand);

        Product product = Product.builder()
                .name(name)
                .importPrice(importPrice)
                .retailPrice(retailPrice)
                .image(ImageUtil.convertToBase64(image))
                .createdAt(new Date())
                .barCode(barCode)
                .brand(brand_org)
                .description(description)
                .build();
        brand_org.getProducts().add(product);
        brandService.addOrSave(brand_org);
        productService.saveOrUpdate(product);
        return "redirect:/products/list";
    }

    @GetMapping(value = "/edit/{id}")
    public String showFormEdit(@PathVariable(name = "id") String id, Model model){
        Product product = productService.findById(id);
        model.addAttribute("product", product);
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
        return "redirect:/products/list";
    }

    @GetMapping(value = "/delete/{id}")
    public String delete(@PathVariable(name = "id") String id){
        productService.deleteById(id);
        return "redirect:/products/list";
    }
}
