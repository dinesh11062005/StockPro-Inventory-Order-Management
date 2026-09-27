package com.inventory.management.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
public class SalesReportController {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final SupplierRepository supplierRepository;

    public SalesReportController(
            OrderRepository orderRepository,
            ProductRepository productRepository,
            CustomerRepository customerRepository,
            SupplierRepository supplierRepository) {

        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.supplierRepository = supplierRepository;
    }

    @GetMapping("/sales")
    public String salesReport(Model model) {

        List<Order> orders = orderRepository.findAll();

        BigDecimal totalSales = orders.stream()
                .map(Order::getTotalAmount)
                .filter(amount -> amount != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        long totalOrders = orders.size();

        BigDecimal averageOrderValue =
                totalOrders == 0
                        ? BigDecimal.ZERO
                        : totalSales.divide(
                                BigDecimal.valueOf(totalOrders),
                                2,
                                RoundingMode.HALF_UP
                        );

        long totalCustomers = customerRepository.count();

        long pendingOrders = orders.stream()
                .filter(order ->
                        order.getStatus() != null &&
                        order.getStatus().equalsIgnoreCase("Pending"))
                .count();

        long completedOrders = orders.stream()
                .filter(order ->
                        order.getStatus() != null &&
                        order.getStatus().equalsIgnoreCase("Completed"))
                .count();

        long cancelledOrders = orders.stream()
                .filter(order ->
                        order.getStatus() != null &&
                        order.getStatus().equalsIgnoreCase("Cancelled"))
                .count();

        List<Order> recentOrders = orders.stream()
                .sorted((a, b) -> {

                    if (a.getOrderDate() == null &&
                        b.getOrderDate() == null) {
                        return 0;
                    }

                    if (a.getOrderDate() == null) {
                        return 1;
                    }

                    if (b.getOrderDate() == null) {
                        return -1;
                    }

                    return b.getOrderDate()
                            .compareTo(a.getOrderDate());
                })
                .limit(5)
                .toList();

        model.addAttribute("orders", orders);
        model.addAttribute("recentOrders", recentOrders);

        model.addAttribute("totalSales", totalSales);
        model.addAttribute("totalOrders", totalOrders);
        model.addAttribute("totalCustomers", totalCustomers);
        model.addAttribute("averageOrderValue", averageOrderValue);

        model.addAttribute("pendingOrders", pendingOrders);
        model.addAttribute("completedOrders", completedOrders);
        model.addAttribute("cancelledOrders", cancelledOrders);

        return "sales";
    }

    @GetMapping("/reports")
    public String reports(Model model) {

        List<Order> orders = orderRepository.findAll();
        List<Product> products = productRepository.findAll();

        BigDecimal totalSales = orders.stream()
                .map(Order::getTotalAmount)
                .filter(amount -> amount != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        long totalOrders = orders.size();

        long pendingOrders = orders.stream()
                .filter(order ->
                        order.getStatus() != null &&
                        order.getStatus().equalsIgnoreCase("Pending"))
                .count();

        long completedOrders = orders.stream()
                .filter(order ->
                        order.getStatus() != null &&
                        order.getStatus().equalsIgnoreCase("Completed"))
                .count();

        long cancelledOrders = orders.stream()
                .filter(order ->
                        order.getStatus() != null &&
                        order.getStatus().equalsIgnoreCase("Cancelled"))
                .count();

        long totalProducts = products.size();

        long lowStockItems = products.stream()
                .filter(product ->
                        product.getStockQuantity() != null &&
                        product.getStockQuantity() > 0 &&
                        product.getStockQuantity() <= 5)
                .count();

        long outOfStockItems = products.stream()
                .filter(product ->
                        product.getStockQuantity() != null &&
                        product.getStockQuantity() == 0)
                .count();

        long totalCustomers = customerRepository.count();
        long totalSuppliers = supplierRepository.count();

        List<Order> recentOrders = orders.stream()
                .sorted((a, b) -> {

                    if (a.getOrderDate() == null &&
                        b.getOrderDate() == null) {
                        return 0;
                    }

                    if (a.getOrderDate() == null) {
                        return 1;
                    }

                    if (b.getOrderDate() == null) {
                        return -1;
                    }

                    return b.getOrderDate()
                            .compareTo(a.getOrderDate());
                })
                .limit(5)
                .toList();

        model.addAttribute("totalSales", totalSales);
        model.addAttribute("totalOrders", totalOrders);
        model.addAttribute("totalCustomers", totalCustomers);
        model.addAttribute("totalProducts", totalProducts);
        model.addAttribute("totalSuppliers", totalSuppliers);

        model.addAttribute("pendingOrders", pendingOrders);
        model.addAttribute("completedOrders", completedOrders);
        model.addAttribute("cancelledOrders", cancelledOrders);

        model.addAttribute("lowStockItems", lowStockItems);
        model.addAttribute("outOfStockItems", outOfStockItems);

        model.addAttribute("recentOrders", recentOrders);

        return "reports";
    }
}