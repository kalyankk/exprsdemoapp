package com.example.exprsdemo.controller;


import com.example.exprsdemo.model.IProduct;
import com.example.exprsdemo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public Collection<IProduct> getAllProducts(@RequestParam (required = false) String subcategory, @RequestParam(required = false) String sort) {
        if(subcategory != null)
            return productService.getBySubCategory(subcategory);
        if(sort == null)
            return productService.getAll();
        if(sort.equals("price"))
            return productService.getAllSortByPrice();
        if(sort.equals("popular"))
            return productService.getAllSortByPopularity();
        return productService.getAll();
    }

    @GetMapping("/product/{productId}")
    public IProduct getProductById(@PathVariable Integer productId) {
        return productService.getById(productId);
    }
}
