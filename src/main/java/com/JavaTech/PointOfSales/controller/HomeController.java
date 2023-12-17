package com.JavaTech.PointOfSales.controller;

import com.JavaTech.PointOfSales.model.QuantityProduct;
import com.JavaTech.PointOfSales.model.User;
import com.JavaTech.PointOfSales.repository.UserRepository;
import com.JavaTech.PointOfSales.service.OrderProductService;
import com.JavaTech.PointOfSales.service.ProductService;
import com.JavaTech.PointOfSales.service.QuantityProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "")
public class HomeController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private QuantityProductService quantityProductService;

    @Autowired
    private OrderProductService orderProductService;

    @GetMapping(value = "/")
    public String index(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.getUserByUsername(username).orElseThrow();
        model.addAttribute("user", user);

        if(user.isFirstLogin()){
            return "redirect:/change-password-first-time";
        }
        Map<String, Long> sumByMonth = orderProductService.getSumTotalAmountByMonth(user.getBranch());
        List<String> months = new ArrayList<>();
        List<Long> amounts = new ArrayList<>();

        for (Map.Entry<String, Long> entry : sumByMonth.entrySet()) {
            String month = entry.getKey();
            Long sum = entry.getValue();
            months.add(month);
            amounts.add(sum);
        }

        //chart
        model.addAttribute("months", months);
        model.addAttribute("amounts", amounts);

        //progress circle
        model.addAttribute("sumQuantityInInventory", quantityProductService.sumQuantityByBranch(user.getBranch()));
        model.addAttribute("sumQuantitySold", orderProductService.sumQuantityByBranch(user.getBranch()));

        model.addAttribute("top3Products", productService.getTopThreeProductsByTotalSales());
        return "index";
    }
}
