package com.inventory.management.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.inventory.management.entity.Order;
import com.inventory.management.entity.Product;
import com.inventory.management.repository.CustomerRepository;
import com.inventory.management.repository.OrderRepository;
import com.inventory.management.repository.ProductRepository;
import com.inventory.management.repository.SupplierRepository;

@Controller
public class DashboardController {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final SupplierRepository supplierRepository;

    public DashboardController(
            ProductRepository productRepository,
            OrderRepository orderRepository,
            CustomerRepository customerRepository,
            SupplierRepository supplierRepository) {

        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.supplierRepository = supplierRepository;
    }

    @GetMapping("/")
    public String dashboard(Model model) {

        List<Product> products = productRepository.findAll();
        List<Order> orders = orderRepository.findAll();

        // Total products
        long totalProducts = products.size();

        // Total orders
        long totalOrders = orders.size();

        // Total customers
        long totalCustomers =
                customerRepository.count();

        // Total suppliers
        long totalSuppliers =
                supplierRepository.count();

        // Total sales
        BigDecimal totalSales =
                orders.stream()
                        .map(Order::getTotalAmount)
                        .filter(amount -> amount != null)
                        .reduce(
                                BigDecimal.ZERO,
                                BigDecimal::add
                        );

        // Low stock products
        long lowStockItems =
                products.stream()
                        .filter(product ->
                                product.getStockQuantity() != null
                                &&
                                product.getStockQuantity() > 0
                                &&
                                product.getStockQuantity() <= 5
                        )
                        .count();

        // Out of stock
        long outOfStockItems =
                products.stream()
                        .filter(product ->
                                product.getStockQuantity() != null
                                &&
                                product.getStockQuantity() == 0
                        )
                        .count();

        model.addAttribute(
                "totalProducts",
                totalProducts
        );

        model.addAttribute(
                "totalOrders",
                totalOrders
        );

        model.addAttribute(
                "totalCustomers",
                totalCustomers
        );

        model.addAttribute(
                "totalSuppliers",
                totalSuppliers
        );

        model.addAttribute(
                "totalSales",
                totalSales
        );

        model.addAttribute(
                "lowStockItems",
                lowStockItems
        );

        model.addAttribute(
                "outOfStockItems",
                outOfStockItems
        );

        // Recent orders
        List<Order> recentOrders =
                orders.stream()
                        .sorted((a, b) ->
                                b.getOrderDate()
                                        .compareTo(
                                                a.getOrderDate()
                                        ))
                        .limit(5)
                        .toList();

        model.addAttribute(
                "recentOrders",
                recentOrders
        );
        
        model.addAttribute("products", products);

        return "dashboard";
    }
}