package com.inventory.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventory.management.entity.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {

}