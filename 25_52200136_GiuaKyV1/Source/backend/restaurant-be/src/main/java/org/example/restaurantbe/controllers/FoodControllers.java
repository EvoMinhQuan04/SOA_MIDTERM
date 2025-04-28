package org.example.restaurantbe.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.util.Strings;
import org.example.restaurantbe.dto.FoodDto;
import org.example.restaurantbe.model.Food;
import org.example.restaurantbe.repository.FoodRepository;
import org.example.restaurantbe.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/foods")
@CrossOrigin("*")
@Tag(name = "Books", description = "Operations related to books")
public class FoodControllers {
    @Autowired
    private FoodRepository foodRepository;

    @GetMapping
    @Operation(summary = "Get book by ID", description = "Retrieve a book's details by its ID")
    public ResponseEntity<?> getAllFoods(@RequestParam(value = "status", required = false) Integer status) {
        try{
            if(status == null)
                return ResponseEntity.ok(foodRepository.findAll());
            else
                return ResponseEntity.ok(foodRepository.findAllByStatus(status));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchFoods(@RequestParam(value = "status", required = false) Integer status,
                                         @RequestParam(value = "name", required = false) String name,
                                         @RequestParam(value = "category", required = false) String category) {
        try{
            List<Food> foodList = status == null?
                    foodRepository.findAll() : foodRepository.findAllByStatus(status);

            if(name != null && !name.isEmpty()) {
                foodList = foodList.stream()
                        .filter(x -> x.getName().toLowerCase().contains(name.toLowerCase()))
                        .collect(Collectors.toList());
            }

            if(category != null && !category.isEmpty() && !category.equalsIgnoreCase("all")) {
                foodList = foodList.stream()
                        .filter(x -> x.getCategory().toLowerCase().contains(category.toLowerCase()))
                        .collect(Collectors.toList());
            }
            return ResponseEntity.ok(foodList);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/categories")
    public ResponseEntity<?> getAllCategories() {
        try{
            Set<String> s = new HashSet<>(foodRepository.findAll().stream().map(Food::getCategory).collect(Collectors.toList()));
            return ResponseEntity.ok(s);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> saveOrUpdateFood(@RequestBody FoodDto foodDto) {
        try{
            if(foodDto.getId() != null && foodDto.getId() > 0) {
                Food f = foodRepository.findById(foodDto.getId()).orElse(null);
                if(f == null) {
                    return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
                }
                if(foodDto.getCategory() != null && !Strings.isEmpty(foodDto.getCategory()))
                    f.setCategory(foodDto.getCategory());

                if(foodDto.getPrice() != null && foodDto.getPrice() > 0)
                    f.setPrice(foodDto.getPrice());

                if(foodDto.getName() != null && !Strings.isEmpty(foodDto.getName()))
                    f.setName(foodDto.getName());

                if(foodDto.getStatus() != null)
                    f.setStatus(foodDto.getStatus());

                if(foodDto.getImage() != null && !Strings.isEmpty(foodDto.getImage()))
                    f.setImage(foodDto.getImage());

                foodRepository.save(f);
                return ResponseEntity.ok("");
            } else {
                foodRepository.save(foodDto.toDomain());
                return ResponseEntity.ok("");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
