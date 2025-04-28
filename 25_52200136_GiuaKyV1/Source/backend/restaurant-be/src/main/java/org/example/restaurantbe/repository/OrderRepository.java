package org.example.restaurantbe.repository;

import org.example.restaurantbe.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByOrderByIdDesc();

    List<Order> findAllByStatusOrderByIdDesc(Integer status);

    Order findByTableIdAndStatusIn(Long table_id, List<Integer> status);
}
