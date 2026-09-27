package com.inventory.management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.inventory.management.service.ProductService;

@Controller
public class InventoryController {

    private final ProductService productService;

    public InventoryController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/inventory")
    public String showInventory(Model model) {

        model.addAttribute(
                "products",
                productService.getAllProducts()
        );

        return "inventory";
    }
}