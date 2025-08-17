package com.lcwd.user.service.controllers;

import com.lcwd.user.service.entities.User;
import com.lcwd.user.service.services.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    //create user
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User user1 = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }

    //single user get
    int retryCount = 1;
//    @GetMapping(value = "/{userId}", produces = {"application/json", "application/xml"})
    @GetMapping("/{userId}")
//    @CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")
    @Retry(name = "ratingHotelService", fallbackMethod = "ratingHotelFallback")
    public ResponseEntity<User> getSingleUser(@PathVariable String userId) {
        logger.info("Get Single User Handler: UserController");
        logger.info("Retry count: {}", retryCount);
        retryCount++;
        return Optional.ofNullable(userService.getUser(userId))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    //creating fall back method for circuitbreaker
    public ResponseEntity<User> ratingHotelFallback(String userId, Exception ex) {
        logger.info("Fallback is executed because any of the service is down: {}", ex.getMessage());
        User dummyUser = User.builder()
                .email("dummy@gmail.com")
                .name("Dummy")
                .about("Hello ji, mai hu dummy")
                .userId("c5576c657v7uv8v7vb7ygh").build();
        return new ResponseEntity<>(dummyUser, HttpStatus.OK);
    }

    //all user get
    int retryCountForAll = 1;
    @GetMapping
//    @CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallbackAll")
    @Retry(name = "ratingHotelService", fallbackMethod = "ratingHotelFallbackAll")
    public ResponseEntity<List<User>> getAllUser() {
        logger.info("Get All User Handler: UserController");
        logger.info("Retry count For All: {} ", retryCountForAll);
        retryCountForAll++;
        return Optional.ofNullable(userService.getAllUser())
                .filter(list -> !list.isEmpty())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    public ResponseEntity<List<User>> ratingHotelFallbackAll(Exception ex) {
        logger.info("Fallback for getAllUser executed because any of the service is down: {}", ex.getMessage());
        User dummyUser = User.builder()
                .email("dummy@gmail.com")
                .name("Dummy")
                .about("Hello ji, mai hu dummy")
                .userId("c5576c657v7uv8v7vb7ygh").build();
        return ResponseEntity.ok(Collections.singletonList(dummyUser));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<User> deleteUserById(@PathVariable String userId) {
        return ResponseEntity.ok(userService.deleteUser(userId));
    }
}
