package com.inventory.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventory.management.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}