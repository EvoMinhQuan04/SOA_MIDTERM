package org.example.restaurantbe.controllers;

import org.example.restaurantbe.model.RTable;
import org.example.restaurantbe.repository.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tables")
@CrossOrigin("*")
public class TableControllers {

    @Autowired
    private TableRepository tableRepository;

    @GetMapping
    public ResponseEntity<?> getTables() {
        try{
            return ResponseEntity.ok(tableRepository.findAll());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
