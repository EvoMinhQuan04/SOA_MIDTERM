package org.example.restaurantbe.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@javax.persistence.Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String customerPhone;

    @Column
    private String customerName;

    @Column
    private Integer status;

    @Column
    private Long timestamp = System.currentTimeMillis();

    @Column
    private Long total;

    @Column
    private Long tax;

    @Column
    private Long grandTotal;

    @ManyToOne
    @JoinColumn(name = "table_id", nullable = false)
    private RTable table;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetail> orderDetails = new ArrayList<>();
}
