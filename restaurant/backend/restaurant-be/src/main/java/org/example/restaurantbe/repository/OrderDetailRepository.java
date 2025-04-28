package org.example.restaurantbe.repository;

import org.example.restaurantbe.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findAllByStatusOrderByIdDesc(String status);

    List<OrderDetail> findAllByOrderByIdDesc();

}
