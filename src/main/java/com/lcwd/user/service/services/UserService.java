package com.lcwd.user.service.services;

import com.lcwd.user.service.entities.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    User saveUser(User user);

    List<User> getAllUser();

    User getUser(String userId);

    User deleteUser(String userId);
}
