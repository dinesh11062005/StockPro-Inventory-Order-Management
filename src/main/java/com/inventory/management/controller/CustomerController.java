package com.inventory.management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.inventory.management.entity.Customer;
import com.inventory.management.service.CustomerService;

@Controller
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers")
    public String showCustomers(Model model) {

        model.addAttribute("customers",
                customerService.getAllCustomers());

        model.addAttribute("customer",
                new Customer());

        return "customers";
    }

    @PostMapping("/customers/save")
    public String saveCustomer(
            @ModelAttribute Customer customer) {

        customerService.saveCustomer(customer);

        return "redirect:/customers";
    }

    @GetMapping("/customers/edit/{id}")
    public String editCustomer(
            @PathVariable Long id,
            Model model) {

        Customer customer =
                customerService.getCustomerById(id);

        model.addAttribute("customer", customer);

        model.addAttribute("customers",
                customerService.getAllCustomers());

        return "customers";
    }

    @GetMapping("/customers/delete/{id}")
    public String deleteCustomer(
            @PathVariable Long id) {

        customerService.deleteCustomer(id);

        return "redirect:/customers";
    }
}