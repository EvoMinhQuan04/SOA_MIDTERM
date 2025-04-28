package org.example.restaurantbe.controllers;

import lombok.*;
import org.example.restaurantbe.model.Account;
import org.example.restaurantbe.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/accounts")
@CrossOrigin("*")
public class AccountControllers {
    @Autowired
    private AccountRepository accountRepository;

    @PostMapping("/login")
    public ResponseEntity<?> checkLogin(@RequestBody Map<String, Object> data) {
        try {

            String username = data.containsKey("username") ? data.get("username").toString() : null;
            String password = data.containsKey("password") ? data.get("password").toString() : null;

            if (username == null || password == null) {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
            Account account = accountRepository.findByUsername(username);
            if (account == null) {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }

            if (!account.getPassword().equals(password)) {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }

            return ResponseEntity.ok(
                    Resposne.builder()
                            .username(username)
                            .role(account.getRole())
                            .build()
            );
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class Resposne {
        private String username;
        private String role;
    }
}
