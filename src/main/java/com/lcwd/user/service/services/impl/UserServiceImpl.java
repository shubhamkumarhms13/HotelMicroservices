package com.lcwd.user.service.services.impl;

import com.lcwd.user.service.entities.Hotel;
import com.lcwd.user.service.entities.Rating;
import com.lcwd.user.service.entities.User;
import com.lcwd.user.service.exceptions.ResourceNotFoundException;
import com.lcwd.user.service.external.services.HotelService;
import com.lcwd.user.service.repositories.UserRepository;
import com.lcwd.user.service.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelService hotelService;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User saveUser(User user) {
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

//    @Override
//    public User getUser(String userId) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new ResourceNotFoundException("User with given ID not found on server " + userId));
//        Rating[] ratingsOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/" + user.getUserId(), Rating[].class);
//        logger.info("{} ", (Object) ratingsOfUser);
//        if (ratingsOfUser == null || ratingsOfUser.length == 0) {
//            user.setRatings(Collections.emptyList());
//            return user;
//        }
//        List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();
//        List<Rating> ratingList = ratings.stream().map(rating -> {
//            Hotel hotel = hotelService.getHotel(rating.getHotelId());
//            rating.setHotel(hotel);
//            return rating;
//        }).collect(Collectors.toList());
//        user.setRatings(ratingList);
//        return user;
//    }

    @Override
    public User getUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with given ID not found on server " + userId));
        Rating[] ratingsOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/" + user.getUserId(), Rating[].class);
        if (ratingsOfUser == null || ratingsOfUser.length == 0) {
            user.setRatings(Collections.emptyList());
            return user;
        }
        List<Rating> ratingList = Arrays.stream(ratingsOfUser).map(rating -> {
                    if (rating.getHotelId() == null || rating.getHotelId().isEmpty()) {
                        rating.setHotel(null);
                        return rating;
                    }
                    try {
                        Hotel hotel = hotelService.getHotel(rating.getHotelId());
                        rating.setHotel(hotel);
                    } catch (Exception e) {
                        rating.setHotel(null);
                    }
                    return rating;
                }).collect(Collectors.toList());
        user.setRatings(ratingList);
        return user;
    }

    @Override
    public User deleteUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found!! "));
        userRepository.deleteById(userId);
        return user;
    }
}
