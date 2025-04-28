package org.example.restaurantbe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@javax.persistence.Table(name = "r_table")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer capacity;

    @Column
    private Integer status;

}
