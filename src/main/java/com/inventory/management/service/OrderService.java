package com.inventory.management.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inventory.management.entity.Order;
import com.inventory.management.entity.OrderItem;
import com.inventory.management.entity.Product;
import com.inventory.management.repository.OrderRepository;
import com.inventory.management.repository.ProductRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository,
                        ProductRepository productRepository) {

        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Transactional
    public Order saveOrder(Order order) {

        if (order.getOrderDate() == null) {
            order.setOrderDate(LocalDateTime.now());
        }

        if (order.getStatus() == null || order.getStatus().isBlank()) {
            order.setStatus("Pending");
        }

        if (order.getPaymentStatus() == null
                || order.getPaymentStatus().isBlank()) {
            order.setPaymentStatus("Pending");
        }

        if (order.getItems() == null || order.getItems().isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one product");
        }

        BigDecimal orderTotal = BigDecimal.ZERO;

        for (OrderItem item : order.getItems()) {

            if (item.getProduct() == null
                    || item.getProduct().getId() == null) {

                throw new IllegalArgumentException("Please select a product");
            }

            if (item.getQuantity() == null
                    || item.getQuantity() <= 0) {

                throw new IllegalArgumentException(
                        "Quantity must be greater than zero");
            }

            Product product = productRepository
                    .findById(item.getProduct().getId())
                    .orElseThrow(() ->
                            new IllegalArgumentException(
                                    "Product not found"));

            // Check available stock
            if (product.getStockQuantity() < item.getQuantity()) {

                throw new IllegalArgumentException(
                        "Not enough stock for product: "
                                + product.getName());
            }

            // Set actual product
            item.setProduct(product);

            // Set order
            item.setOrder(order);

            // Use current product price
            item.setPrice(product.getPrice());

            // Calculate item total
            BigDecimal itemTotal =
                    product.getPrice()
                            .multiply(
                                    BigDecimal.valueOf(
                                            item.getQuantity()));

            item.setTotal(itemTotal);

            // Add to order total
            orderTotal = orderTotal.add(itemTotal);

            // Reduce inventory
            product.setStockQuantity(
                    product.getStockQuantity()
                            - item.getQuantity());

            productRepository.save(product);
        }

        order.setTotalAmount(orderTotal);

        return orderRepository.save(order);
    }

    @Transactional
    public void deleteOrder(Long id) {

        Order order = orderRepository
                .findById(id)
                .orElse(null);

        if (order == null) {
            return;
        }

        // Restore stock when order is deleted
        for (OrderItem item : order.getItems()) {

            Product product = item.getProduct();

            product.setStockQuantity(
                    product.getStockQuantity()
                            + item.getQuantity());

            productRepository.save(product);
        }

        orderRepository.delete(order);
    }
}