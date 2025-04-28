package org.example.restaurantbe.repository;

import org.example.restaurantbe.model.RTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TableRepository extends JpaRepository<RTable, Long> {
}
