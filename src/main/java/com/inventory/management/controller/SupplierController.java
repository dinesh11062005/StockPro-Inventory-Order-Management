package com.inventory.management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.inventory.management.entity.Supplier;
import com.inventory.management.service.SupplierService;

@Controller
public class SupplierController {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping("/suppliers")
    public String showSuppliers(Model model) {

        model.addAttribute("suppliers",
                supplierService.getAllSuppliers());

        model.addAttribute("supplier", new Supplier());

        return "suppliers";
    }

    @PostMapping("/suppliers/save")
    public String saveSupplier(
            @ModelAttribute Supplier supplier) {

        supplierService.saveSupplier(supplier);

        return "redirect:/suppliers";
    }

    @GetMapping("/suppliers/edit/{id}")
    public String editSupplier(
            @PathVariable Long id,
            Model model) {

        Supplier supplier =
                supplierService.getSupplierById(id);

        model.addAttribute("supplier", supplier);

        model.addAttribute("suppliers",
                supplierService.getAllSuppliers());

        return "suppliers";
    }

    @GetMapping("/suppliers/delete/{id}")
    public String deleteSupplier(
            @PathVariable Long id) {

        supplierService.deleteSupplier(id);

        return "redirect:/suppliers";
    }
}