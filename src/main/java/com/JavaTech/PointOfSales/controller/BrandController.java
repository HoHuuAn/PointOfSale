package com.JavaTech.PointOfSales.controller;

import com.JavaTech.PointOfSales.utils.ImageBrandUtil;
import com.JavaTech.PointOfSales.model.Brand;
import com.JavaTech.PointOfSales.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping(value = "/brands")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping(value = "/list")
    public String listBrand(Model model){
        model.addAttribute("listBrands", brandService.listAll());
        return "/brands/page-list-brand";
    }

    @GetMapping(value = "/add")
    public String addBrand(){
        return "/brands/page-add-brand";
    }

   

    @GetMapping(value = "/edit/{id}")
    public String showFormEdit(@PathVariable(name = "id") Long id, Model model){
        Brand brand = brandService.findById(id);
        model.addAttribute("brand", brand);
        return "/brands/page-edit-brand";
    }

   

}
