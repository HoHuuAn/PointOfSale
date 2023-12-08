package com.JavaTech.PointOfSales.controller;


import com.JavaTech.PointOfSales.dto.OrderProductDTO;
import com.JavaTech.PointOfSales.model.OrderDetail;
import com.JavaTech.PointOfSales.model.OrderProduct;
import com.JavaTech.PointOfSales.service.OrderProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/report")
public class ReportController {

    @Autowired
    private OrderProductService orderProductService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public String getReport(){
        return "/report/page-report";
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<?> getOrders(@RequestParam("startDate") String startDateString,
                                       @RequestParam("endDate") String endDateString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
        Date startDate = dateFormat.parse(startDateString);
        Date endDate = dateFormat.parse(endDateString);

        List<OrderProduct> orderList = orderProductService.getOrdersBetweenDates(startDate, endDate);
        Long totalAmount = calculateTotalSum(orderList);
        Long totalProduct = calculateTotalProductQuantity(orderList);

        List<OrderProductDTO> orderDTOList = orderList.stream()
                .map(orderProduct -> {
                    OrderProductDTO orderProductDTO = modelMapper.map(orderProduct, OrderProductDTO.class);
                    orderProductDTO.setNameOfCustomer(orderProduct.getCustomer().getName());
                    return orderProductDTO;
                })
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("orderList", orderDTOList);
        response.put("totalAmount", totalAmount);
        response.put("totalProduct", totalProduct);

        return ResponseEntity.ok(response);

    }

    private Long calculateTotalSum(List<OrderProduct> orderList) {
        return orderList.stream()
                .mapToLong(OrderProduct::getTotalAmount)
                .sum();
    }

    private Long calculateTotalProductQuantity(List<OrderProduct> orderList) {
        return orderList.stream()
                .flatMap(order -> order.getOrderItems().stream())
                .mapToLong(OrderDetail::getQuantity)
                .sum();
    }
}
