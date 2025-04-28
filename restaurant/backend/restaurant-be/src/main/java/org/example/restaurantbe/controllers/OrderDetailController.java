package org.example.restaurantbe.controllers;

import org.example.restaurantbe.dto.OrderDetailDto;
import org.example.restaurantbe.dto.OrderDto;
import org.example.restaurantbe.model.Order;
import org.example.restaurantbe.model.OrderDetail;
import org.example.restaurantbe.repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orderDetails")
@CrossOrigin("*")
public class OrderDetailController {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(value = "status", required = false) String status) {
        try {
            if (status == null)
                return ResponseEntity.ok(nomarlize(orderDetailRepository.findAllByOrderByIdDesc())
                        .stream()
                        .map(OrderDetailDto::from)
                        .collect(Collectors.toList()));
            else
                return ResponseEntity.ok(nomarlize(orderDetailRepository.findAllByStatusOrderByIdDesc(status)).stream()
                        .map(OrderDetailDto::from)
                        .collect(Collectors.toList()));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    private static List<OrderDetail> nomarlize(List<OrderDetail> orderDetails) {
        List<OrderDetail> pendings = orderDetails.stream()
                .filter(o -> o.getStatus().equalsIgnoreCase("PENDING"))
                .collect(Collectors.toList());
        pendings.sort((o1, o2) -> o1.getTimestamp() - o2.getTimestamp() < 0 ? 1 : -1);

        List<OrderDetail> dones = orderDetails.stream()
                .filter(o -> o.getStatus().equalsIgnoreCase("DONE"))
                .collect(Collectors.toList());
        pendings.sort((o1, o2) -> o1.getTimestamp() - o2.getTimestamp() < 0 ? 1 : -1);

        List<OrderDetail> result = new ArrayList<>();
        result.addAll(pendings);
        result.addAll(dones);
        return result;
    }

    @PostMapping
    public ResponseEntity<?> updateOrderDetail(@RequestBody Map<String, Object> data) {
        try {
            Integer orderDetailId = Integer.parseInt(data.get("id").toString());
            String status = data.containsKey("status")? data.get("status").toString() : null;
            Integer quantity = data.containsKey("quantity")? Integer.parseInt(data.get("quantity").toString()) : null;
            String note = data.containsKey(("note").toString()) ? data.get("note").toString() : "";
            Optional<OrderDetail> orderDetail = orderDetailRepository.findById(Long.valueOf(orderDetailId));
            if (orderDetail.isPresent()) {
                OrderDetail o = orderDetail.get();
                if(status != null)
                    o.setStatus(status);

                if(quantity != null)
                    o.setQuantity((long) quantity);

                if(note != null && !note.isEmpty())
                    o.setNote(note);

                orderDetailRepository.save(o);
                return ResponseEntity.ok("");
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderDetail(@PathVariable("id") Long id) {
        try {

            Optional<OrderDetail> orderDetail = orderDetailRepository.findById(id);
            if (orderDetail.isPresent()) {
                if(orderDetail.get().getOrder().getStatus() == 1) {
                    return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
                }
                orderDetailRepository.deleteById(id);
                return ResponseEntity.ok("");
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
