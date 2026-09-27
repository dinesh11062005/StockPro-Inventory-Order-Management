package com.inventory.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventory.management.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}