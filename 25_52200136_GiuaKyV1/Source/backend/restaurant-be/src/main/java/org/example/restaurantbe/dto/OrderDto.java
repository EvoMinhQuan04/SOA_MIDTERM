package org.example.restaurantbe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.restaurantbe.model.Order;
import org.example.restaurantbe.model.OrderDetail;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderDto {
    private Long id;
    private Integer status;
    private Long tableId;
    private String customerPhone;
    private String customerName;
    private String timestamp;
    private List<OrderDetailDto> orderDetails;
    private Long totalPrice;
    private Long totalTax;
    private Long total;

    public static OrderDto fromOrder(Order order) {
        Long total = 0L;
        for (OrderDetail orderDetail : order.getOrderDetails()) {
            total += orderDetail.getTotalPrice();
        }
        if(order.getTotal() != null && order.getTotal() > 0) {
            total = order.getTotal();
        }
        Long tax = total * 10 / 100;
        if(order.getTax() != null && order.getTax() > 0) {
            tax = order.getTax();
        }

        Long grandTotal = total + tax;
        if(order.getGrandTotal() != null && order.getGrandTotal() > 0) {
            grandTotal = order.getGrandTotal();
        }
        String dateString = "";
        if(order.getTimestamp() != null && order.getTimestamp() > 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateString = sdf.format(new Date(order.getTimestamp()));
        }
        return OrderDto.builder()
                .id(order.getId())
                .status(order.getStatus())
                .timestamp(dateString)
                .tableId(order.getTable().getId())
                .customerName(order.getCustomerName())
                .customerPhone(order.getCustomerPhone())
                .orderDetails(
                        order.getOrderDetails()
                                .stream().map(OrderDetailDto::from)
                                .collect(Collectors.toList())
                )
                .totalPrice(
                        total
                )
                .totalTax(tax)
                .total(grandTotal)
                .build();
    }
}
