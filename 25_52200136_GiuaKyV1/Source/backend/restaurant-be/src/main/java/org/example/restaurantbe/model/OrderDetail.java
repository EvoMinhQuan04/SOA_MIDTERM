package org.example.restaurantbe.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@javax.persistence.Table(name = "order_detail")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long foodId;

    @Column
    private Long foodPrice;

    @Column
    private String note;

    @Column
    private String foodCategory;

    @Column
    private String foodName;

    @Column
    private Long quantity;

    @Column
    private Long totalPrice;

    @Column
    private String status;

    @Column
    private Long timestamp = System.currentTimeMillis();

    @JoinColumn(name = "order_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;
}
