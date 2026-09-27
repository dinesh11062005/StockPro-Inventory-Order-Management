package com.inventory.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventory.management.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}