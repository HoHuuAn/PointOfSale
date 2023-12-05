package com.JavaTech.PointOfSales.controller;

import com.JavaTech.PointOfSales.Utils.ImageBrandUtil;
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

    @PostMapping(value = "/add")
    public String addPostBrand(@RequestParam("name") String name,
                               @RequestParam("image") MultipartFile image,
                               @RequestParam("website") String website,
                               @RequestParam("description") String description) throws IOException {
        //image
        String fileName = name.trim()+".jpg";
        ImageBrandUtil.saveFile(fileName, image);

        Brand brand = Brand.builder()
                .name(name)
                .website(website)
                .description(description)
                .image(fileName)
                .build();
        brandService.addOrSave(brand);
        return "redirect:/brands/list";
    }

    @GetMapping(value = "/edit/{id}")
    public String showFormEdit(@PathVariable(name = "id") Long id, Model model){
        Brand brand = brandService.findById(id);
        model.addAttribute("brand", brand);
        return "/brands/page-edit-brand";
    }

    @PostMapping(value = "/edit/{id}")
    public String edit(@PathVariable(name = "id") Long id,
                       @RequestParam("name") String name,
                       @RequestParam("image") MultipartFile image,
                       @RequestParam("website") String website,
                       @RequestParam("description") String description) throws IOException{
        Brand brand = brandService.findById(id);
        String fileName = name.trim()+".jpg";
        ImageBrandUtil.saveFile(fileName, image);

        brand.setName(name);
        brand.setImage(fileName);
        brand.setDescription(description);
        brand.setWebsite(website);
        brandService.addOrSave(brand);
        return "redirect:/brands/list";
    }

    @GetMapping(value = "/delete/{id}")
    public String delete(@PathVariable(name = "id") Long id){
        brandService.deleteById(id);
        return "redirect:/brands/list";
    }
}
