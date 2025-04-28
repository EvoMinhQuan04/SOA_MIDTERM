package org.example.restaurantbe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@javax.persistence.Table(name = "food")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String category;

    @Column
    private String image;

    @Column
    private Long price;

    @Column
    private Integer status;
}
