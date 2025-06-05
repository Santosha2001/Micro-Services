package com.user.service.service.impl;

import com.user.service.entities.Hotel;
import com.user.service.entities.Rating;
import com.user.service.entities.User;
import com.user.service.exception.ResourceNotFoundException;
import com.user.service.repository.UserRepository;
import com.user.service.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public User saveUser(User user) {
        String randomId = UUID.randomUUID().toString();
        user.setUserId(randomId);
        return userRepository.save(user);
    }

    @Override
    public User getUser(String userId) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("User with given id not found in database: " + userId)
                );


        // Fetch USER rating from the RATING SERVICE
        String userUrl = "http://localhost:8083/api/ratings/users/" + user.getUserId();
        Rating[] ratingsOfUser = restTemplate.getForObject(userUrl, Rating[].class);

        // Convert the array of ratings to a List
        List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();

        /*List<Rating> ratingList = ratings.stream().map(rating -> {
            // api call to HOTEL SERVICE to get hotel details for each rating
            ResponseEntity<Hotel> hotelResponseEntity = restTemplate.getForEntity("http://localhost:8082/api/hotels/" + rating.getHotelId(), Hotel.class);
            Hotel hotel = hotelResponseEntity.getBody();
            rating.setHotel(hotel);

            return rating;
        }).collect(Collectors.toList());*/
        List<Rating> ratingList = ratings.stream().map(rating -> {
            try {
                // API call to HOTEL SERVICE to get hotel details for each rating
                ResponseEntity<Hotel> hotelResponseEntity = restTemplate.getForEntity("http://localhost:8082/api/hotels/" + rating.getHotelId(), Hotel.class);
                Hotel hotel = hotelResponseEntity.getBody();
                rating.setHotel(hotel);
            } catch (org.springframework.web.client.HttpClientErrorException.NotFound e) {
                logger.error("Hotel not found for hotelId: {}", rating.getHotelId());
                rating.setHotel(null); // Handle gracefully by setting hotel to null
            } catch (Exception e) {
                logger.error("Error while fetching hotel details for hotelId {}: {}", rating.getHotelId(), e.getMessage());
                rating.setHotel(null);
            }
            return rating;
        }).collect(Collectors.toList());

        logger.info("Ratings {}", ratingList);
        user.setRatings(ratingList);

        return user;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> allUsers = userRepository.findAll();

        try {
            // Fetch all USERS and their RATING from RATING SERVICE
            for (User user : allUsers) {
                String url = "http://localhost:8083/api/ratings/users/" + user.getUserId();
                Rating[] ratingsOfUser = restTemplate.getForObject(url, Rating[].class);

                List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();
                // api call to HOTEL SERVICE to get hotel details for each rating
                List<Rating> ratingList = ratings.stream().map((rating) -> {
                    String hotelId = rating.getHotelId();
                    String hotelUrl = "http://localhost:8082/api/hotels/" + hotelId;
                    ResponseEntity<Hotel> hotelResponseEntity = restTemplate.getForEntity(hotelUrl, Hotel.class);
                    Hotel hotel = hotelResponseEntity.getBody();
                    rating.setHotel(hotel);

                    return rating;
                }).collect(Collectors.toList());
                user.setRatings(ratingList);
                logger.info("Ratings fetched successfully for user with id {}: {}", user.getUserId(), ratingsOfUser);
            }
        } catch (Exception e) {
            // throw new RuntimeException(e);
            logger.error("Error while fetching ratings for users: {}", e.getMessage());
        }
        return allUsers;
    }

    @Override
    public void deleteUser(String userId) {
        // TODO document why this method is empty
    }

    @Override
    public User updateUser(String userId, User userDetails) {
        return null;
    }
}
