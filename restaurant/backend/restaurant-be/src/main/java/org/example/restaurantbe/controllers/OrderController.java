package org.example.restaurantbe.controllers;

import org.example.restaurantbe.dto.OrderDetailDto;
import org.example.restaurantbe.dto.OrderDto;
import org.example.restaurantbe.model.Food;
import org.example.restaurantbe.model.Order;
import org.example.restaurantbe.model.OrderDetail;
import org.example.restaurantbe.model.RTable;
import org.example.restaurantbe.repository.FoodRepository;
import org.example.restaurantbe.repository.OrderDetailRepository;
import org.example.restaurantbe.repository.OrderRepository;
import org.example.restaurantbe.repository.TableRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
@CrossOrigin("*")
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private TableRepository tableRepository;

    // 2025-04-14
    @GetMapping
    public ResponseEntity<?> getAllOrders(@RequestParam(value = "status", required = false) Integer status,
                                          @RequestParam(value = "startDate", required = false) String startDate,
                                          @RequestParam(value = "endDate", required = false) String endDate) {
        try {
            List<Order> orders = new ArrayList<>();
            if(status == null) {
                orders = orderRepository.findAllByOrderByIdDesc();
            } else {
                orders = orderRepository.findAllByStatusOrderByIdDesc(status);
            }

            if(startDate != null && endDate != null) {
                orders = filterDateTime(orders, startDate, endDate);
            }

            return ResponseEntity.ok(orders
                    .stream().map(OrderDto::fromOrder)
                    .collect(Collectors.toList()));

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private static List<Order> filterDateTime(List<Order> orders, String startDate, String endDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            startDate += " 00:00:00";
            endDate += " 23:59:59";
            Date dateStart = sdf.parse(startDate);


            String specifiedDateString = sdf.format(dateStart);
            long startDateTimestamp = sdf.parse(specifiedDateString).getTime();

            Date dateEnd = sdf.parse(endDate);

            long endDateTimestamp = sdf.parse(sdf.format(dateEnd)).getTime();

            return orders.stream().filter(o ->
                    o.getTimestamp() >= startDateTimestamp && o.getTimestamp() <= endDateTimestamp)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return orders;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable("id") Long id) {
        try {
            Optional<Order> order = orderRepository.findById(id);
            if (order.isPresent()) {
                return ResponseEntity.ok(OrderDto.fromOrder(order.get()));
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/table/{tableId}")
    public ResponseEntity<?> getOrderByTableId(@PathVariable("tableId") Long tableId) {
        try {
            Order order = orderRepository.findByTableIdAndStatusIn(tableId, Arrays.asList(0, 2, 3));
            if (order != null) {
                return ResponseEntity.ok(OrderDto.fromOrder(order));
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{id}/add")
    public ResponseEntity<?> addItemToOrder(@PathVariable("id") Long id, @RequestBody Map<String, Object> data) {
        try {
            Long foodId = Long.parseLong(data.get("foodId").toString());
            Long orderQuantity = Long.parseLong(data.get("quantity").toString());
            Long orderId = Long.parseLong(data.get("orderId").toString());
//            String note = data.get("note").toString();
            String note = "";

            Food food = foodRepository.findById(foodId).orElse(null);
            Order order = orderRepository.findById(id).orElse(null);
            if (food == null || order == null) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }

            if(order.getStatus() == 1) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }

            for (OrderDetail o : order.getOrderDetails()) {
                if (o.getFoodId().equals(foodId)) {
                    o.setQuantity(o.getQuantity() + orderQuantity);
                    o.setTotalPrice(o.getTotalPrice() + food.getPrice() * orderQuantity);
                    o.setTimestamp(System.currentTimeMillis());
                    orderRepository.save(order);
                    return ResponseEntity.ok("");
                }
            }

            order.setStatus(0);
            OrderDetail orderDetail = OrderDetail.builder()
                    .foodId(foodId)
                    .foodCategory(food.getCategory())
                    .foodName(food.getName())
                    .foodPrice(food.getPrice())
                    .quantity(orderQuantity)
                    .note(note)
                    .totalPrice(food.getPrice() * orderQuantity)
                    .order(order)
                    .status("PENDING")
                    .timestamp(System.currentTimeMillis())
                    .build();

            orderDetailRepository.save(orderDetail);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{id}/payment")
    public ResponseEntity<?> paymentOrder(@PathVariable("id") Long id) {
        try {

            Order order = orderRepository.findById(id).orElse(null);
            if (order == null) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }

            OrderDto orderDto = OrderDto.fromOrder(order);
            order.setStatus(1);
            order.setTotal(orderDto.getTotalPrice());
            order.setTax(orderDto.getTotalTax());
            order.setGrandTotal(orderDto.getTotal());
            order.setTimestamp(System.currentTimeMillis());
            order.getTable().setStatus(1);
            orderRepository.save(order);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/new")
    public ResponseEntity<?> newOrder(@RequestBody Map<String, Object> data) {
        try {

            String customerName = data.get("customerName").toString();
            String customerPhone = data.get("customerPhone").toString();
            Long tableId = Long.parseLong(data.get("tableId").toString());

            RTable rTable = tableRepository.findById(tableId).orElse(null);

            if (rTable == null) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            Order o = Order.builder()
                    .tax(0L)
                    .customerName(customerName)
                    .customerPhone(customerPhone)
                    .grandTotal(0L)
                    .total(0L)
                    .table(rTable)
                    .status(3)
                    .build();

            orderRepository.save(o);

            rTable.setStatus(0);
            tableRepository.save(rTable);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable("id") Long id) {
        try {

            Optional<Order> order = orderRepository.findById(id);
            if (order.isPresent()) {
                if(order.get().getStatus() == 1) {
                    return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
                }
                orderRepository.deleteById(id);
                RTable table = order.get().getTable();
                table.setStatus(1);
                tableRepository.save(table);
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
