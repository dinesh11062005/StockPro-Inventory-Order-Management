package com.inventory.management.controller;

import com.inventory.management.entity.Order;
import com.inventory.management.entity.Product;
import com.inventory.management.repository.OrderRepository;
import com.inventory.management.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class NotificationController {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public NotificationController(ProductRepository productRepository,
                                  OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/notifications")
    public String notifications(Model model) {

        List<Product> products = productRepository.findAll();

        List<Product> stockAlerts = products.stream()
                .filter(product -> product.getStockQuantity() == null
                        || product.getStockQuantity() <= 10)
                .collect(Collectors.toList());

        List<Order> pendingOrders = orderRepository.findAll().stream()
                .filter(order -> order.getStatus() != null
                        && order.getStatus().toString().equalsIgnoreCase("PENDING"))
                .collect(Collectors.toList());

        model.addAttribute("stockAlerts", stockAlerts);
        model.addAttribute("pendingOrders", pendingOrders);

        return "notifications";
    }
}
