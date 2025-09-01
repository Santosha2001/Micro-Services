package com.user.service.controller;

import com.user.service.entities.User;
import com.user.service.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    // Create a new user
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User saveUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveUser);
    }

    // Get a user by ID
    @GetMapping("/{userId}")
    @CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")
    @Retry(name = "ratingHotelService", fallbackMethod = "ratingHotelFallback")
    public ResponseEntity<User> getUserById(@PathVariable String userId) {
        User userById = userService.getUser(userId);

        logger.info("Get Single User Handler: UserController");
//        logger.info("Retry count: {}", retryCount);

        // return ResponseEntity.status(HttpStatus.OK).body(userById);
        return ResponseEntity.ok(userById);
    }

    // Get all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // Update a user by ID

    // rating hotel fallback method
    public ResponseEntity<User> ratingHotelFallback(String userId, Exception ex) {
        logger.info("Fallback is executed because service is down: {}", ex.getMessage());
        User user = User.builder()
                .email("dummy@gmail.com")
                .name("Dummy User")
                .userId("12345")
                .about("This is a dummy user due to service failure.")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

}
