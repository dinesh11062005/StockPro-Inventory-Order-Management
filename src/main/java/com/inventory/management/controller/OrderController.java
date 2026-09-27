package com.inventory.management.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.inventory.management.entity.Order;
import com.inventory.management.entity.OrderItem;
import com.inventory.management.service.CustomerService;
import com.inventory.management.service.OrderService;
import com.inventory.management.service.ProductService;

@Controller
public class OrderController {

    private final OrderService orderService;
    private final CustomerService customerService;
    private final ProductService productService;

    public OrderController(OrderService orderService,
                           CustomerService customerService,
                           ProductService productService) {

        this.orderService = orderService;
        this.customerService = customerService;
        this.productService = productService;
    }

    @GetMapping("/orders")
    public String showOrders(Model model) {

        model.addAttribute("orders",
                orderService.getAllOrders());

        Order order = new Order();

        OrderItem item = new OrderItem();

        order.setItems(new ArrayList<>());
        order.getItems().add(item);

        model.addAttribute("order", order);

        model.addAttribute("customers",
                customerService.getAllCustomers());

        model.addAttribute("products",
                productService.getAllProducts());

        return "orders";
    }

    @PostMapping("/orders/save")
    public String saveOrder(
            @ModelAttribute Order order) {

        orderService.saveOrder(order);

        return "redirect:/orders";
    }

    @GetMapping("/orders/view/{id}")
    public String viewOrder(
            @PathVariable Long id,
            Model model) {

        Order order = orderService.getOrderById(id);

        model.addAttribute("order", order);

        return "order-details";
    }

    @GetMapping("/orders/delete/{id}")
    public String deleteOrder(
            @PathVariable Long id) {

        orderService.deleteOrder(id);

        return "redirect:/orders";
    }
    @GetMapping("/orders/invoice/{id}")
    public String invoice(
            @PathVariable Long id,
            Model model) {

        Order order = orderService.getOrderById(id);

        model.addAttribute(
                "order",
                order
        );

        return "invoice";
    }
}