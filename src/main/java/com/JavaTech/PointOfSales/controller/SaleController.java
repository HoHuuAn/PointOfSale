package com.JavaTech.PointOfSales.controller;


import com.JavaTech.PointOfSales.utils.InvoiceGeneratorUtil;
import com.JavaTech.PointOfSales.dto.CustomerDTO;
import com.JavaTech.PointOfSales.dto.OrderProductDTO;
import com.JavaTech.PointOfSales.dto.ProductDTO;
import com.JavaTech.PointOfSales.model.*;
import com.JavaTech.PointOfSales.payload.CompleteOrderPayload;
import com.JavaTech.PointOfSales.payload.OrderPayload;
import com.JavaTech.PointOfSales.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/sales")
public class SaleController {

    @Autowired
    private OrderProductService orderProductService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private QuantityProductService quantityProductService;

    @GetMapping(value = "/sales-page")
    public String viewOrder(Model model){
        model.addAttribute("listProducts", productService.listAllDTO());
        return "/sales/page-sale";
    }

    @PostMapping(value = "/search-and-add")
    @ResponseBody
    public ResponseEntity<?> searchAndAdd(@RequestParam("barcode") String barcode){
        Product product = productService.findProductByBarCode(barcode);

        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
        productDTO.setQuantityOfBranch(findByProduct(product).getQuantity());
        if(product != null){
            Map<String, Object> response = new HashMap<>();
            response.put("product", productDTO);
            return ResponseEntity.ok(response);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }



    public QuantityProduct findByProduct(Product product){
        return quantityProductService.findByBranchAndProduct(userService.getCurrentUser().getBranch(), product);
    }

    @GetMapping(value = "/page-checkout")
    public String checkout(@RequestParam(name = "id") Long orderId, Model model){
        model.addAttribute("orderProduct", modelMapper.map(orderProductService.findById(orderId), OrderProductDTO.class));
        return "/sales/page-checkout";
    }

    @PostMapping(value = "/complete-order")
    @ResponseBody
    public ResponseEntity<byte[]> completeOrder(@RequestBody CompleteOrderPayload payload) throws IOException {
        OrderProduct orderProduct = orderProductService.findById(payload.getIdValue());
        CustomerDTO customerDTO = payload.getCustomer();

        orderProduct.setCash(payload.getCash());
        orderProduct.setCustomer(modelMapper.map(customerDTO, Customer.class));
        orderProductService.saveOrUpdate(orderProduct);

        ByteArrayOutputStream byteArrayOutputStream = InvoiceGeneratorUtil.Generate(customerDTO, orderProduct);

        Path uploadPath = Paths.get("./src/main/resources/static/invoice/");
        String path = uploadPath.resolve(orderProduct.getId()+".pdf").toString();

        //save invoice
        FileOutputStream fileOutputStream = new FileOutputStream(path);
        byteArrayOutputStream.writeTo(fileOutputStream);

        byte[] pdfContents = byteArrayOutputStream.toByteArray();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename="+orderProduct.getId()+".pdf");
        headers.add("Content-Type", "application/pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfContents);
    }
}
