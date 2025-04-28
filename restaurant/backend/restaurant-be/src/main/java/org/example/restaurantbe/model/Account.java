package org.example.restaurantbe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@javax.persistence.Table(name = "accounts")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column
    private String password;

    @Column
    private String role; // KITCHEN, EMPLOYEE, MANAGER
}
