package org.example.restaurantbe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.restaurantbe.model.OrderDetail;
import org.modelmapper.ModelMapper;

import java.text.SimpleDateFormat;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDetailDto {
    private Long id;
    private Long foodId;
    private Long foodPrice;
    private String note;
    private String foodCategory;
    private String foodName;
    private Long quantity;
    private Long totalPrice;
    private String status;
    private Long tableId;
    private String orderTime;

    public static OrderDetailDto from(OrderDetail orderDetail) {
        OrderDetailDto dto =  new ModelMapper().map(orderDetail, OrderDetailDto.class);
        dto.setTableId(orderDetail.getOrder().getTable().getId());
        String dateString = "";
        if(orderDetail.getTimestamp() != null && orderDetail.getTimestamp() > 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateString = sdf.format(new Date(orderDetail.getTimestamp()));
        }
        dto.setOrderTime(dateString);
        return dto;
    }
}
