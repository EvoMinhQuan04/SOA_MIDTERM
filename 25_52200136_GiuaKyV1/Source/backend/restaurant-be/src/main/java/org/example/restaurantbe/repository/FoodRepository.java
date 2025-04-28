package org.example.restaurantbe.repository;

import org.example.restaurantbe.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findAllByStatus(Integer status);
}
