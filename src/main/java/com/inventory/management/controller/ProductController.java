package com.inventory.management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.inventory.management.entity.Product;
import com.inventory.management.service.CategoryService;
import com.inventory.management.service.ProductService;
import com.inventory.management.service.SupplierService;

@Controller
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final SupplierService supplierService;

    public ProductController(ProductService productService,
                             CategoryService categoryService,
                             SupplierService supplierService) {

        this.productService = productService;
        this.categoryService = categoryService;
        this.supplierService = supplierService;
    }

    @GetMapping("/products")
    public String showProducts(Model model) {

        model.addAttribute("products",
                productService.getAllProducts());

        model.addAttribute("product",
                new Product());

        model.addAttribute("categories",
                categoryService.getAllCategories());

        model.addAttribute("suppliers",
                supplierService.getAllSuppliers());

        return "products";
    }

    @PostMapping("/products/save")
    public String saveProduct(
            @ModelAttribute Product product) {

        productService.saveProduct(product);

        return "redirect:/products";
    }

    @GetMapping("/products/edit/{id}")
    public String editProduct(
            @PathVariable Long id,
            Model model) {

        Product product =
                productService.getProductById(id);

        model.addAttribute("product", product);

        model.addAttribute("products",
                productService.getAllProducts());

        model.addAttribute("categories",
                categoryService.getAllCategories());

        model.addAttribute("suppliers",
                supplierService.getAllSuppliers());

        return "products";
    }

    @GetMapping("/products/delete/{id}")
    public String deleteProduct(
            @PathVariable Long id) {

        productService.deleteProduct(id);

        return "redirect:/products";
    }
}