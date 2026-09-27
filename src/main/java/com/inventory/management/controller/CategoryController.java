package com.inventory.management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.inventory.management.entity.Category;
import com.inventory.management.service.CategoryService;

@Controller
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public String showCategories(Model model) {

        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("category", new Category());

        return "categories";
    }

    @PostMapping("/categories/save")
    public String saveCategory(@ModelAttribute Category category) {

        categoryService.saveCategory(category);

        return "redirect:/categories";
    }

    @GetMapping("/categories/edit/{id}")
    public String editCategory(@PathVariable Long id, Model model) {

        Category category = categoryService.getCategoryById(id);

        model.addAttribute("category", category);
        model.addAttribute("categories", categoryService.getAllCategories());

        return "categories";
    }

    @GetMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {

        categoryService.deleteCategory(id);

        return "redirect:/categories";
    }
}