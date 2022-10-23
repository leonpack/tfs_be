package com.tfs.demo.tfs_crud_demo.service;

import com.tfs.demo.tfs_crud_demo.dao.RestaurantRepository;
import com.tfs.demo.tfs_crud_demo.entity.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImplementation implements RestaurantService{

    private RestaurantRepository restaurantRepository;
    private EntityManager entityManager;

    @Autowired
    public RestaurantServiceImplementation(RestaurantRepository theRestaurantRepository, EntityManager theEntityManager){
        restaurantRepository = theRestaurantRepository;
        entityManager = theEntityManager;
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    @Override
    public Restaurant getRestaurantById(String restaurantId) {
        Optional<Restaurant> result = restaurantRepository.findById(restaurantId);
        Restaurant theRestaurant = null;
        if(result.isPresent()){
            theRestaurant = result.get();
        } else {
            throw new RuntimeException("Restaurant with Id - "+restaurantId+ " not found!");
        }
        return theRestaurant;
    }

    @Override
    public void saveRestaurant(Restaurant theRestaurant) {
        restaurantRepository.save(theRestaurant);
    }

    @Override
    public void disableRestaurant(String restaurantId) {
        Optional<Restaurant> result = restaurantRepository.findById(restaurantId);
        Restaurant theRestaurant = null;
        if(result.isPresent()){
            theRestaurant = result.get();
        } else {
            throw new RuntimeException("Restaurant with id - " +restaurantId+" not found!");
        }
        theRestaurant.setStatus(false);
        restaurantRepository.save(theRestaurant);
    }

    @Override
    public boolean checkDuplicateId(String checkRestaurantId) {
        Optional<Restaurant> result = restaurantRepository.findById(checkRestaurantId);
        if(result.isPresent()){
            throw new RuntimeException("Duplicate with restaurant id "+checkRestaurantId+" has been found, please try again!");
        }
        return true;
    }

    @Override
    public boolean checkDuplicatePhoneNumber(String checkPhoneNumber) {
        Query theQuery = entityManager.createQuery("select restaurantNumber from Restaurant ");
        for(int i = 0;i<theQuery.getResultList().size();i++){
            if(checkPhoneNumber.equals(theQuery.getResultList().get(i))){
                throw new RuntimeException("This phone number -" +checkPhoneNumber + " has been linked with another restaurant, please try again");
            }
        }
        return true;
    }

    @Override
    public boolean checkDuplicateName(String checkRestaurantName) {
        Query theQuery = entityManager.createQuery("select restaurantName from Restaurant");
        for(int i=0;i<theQuery.getResultList().size();i++){
            if (checkRestaurantName.equals(theQuery.getResultList().get(i))){
                throw new RuntimeException("This restaurant name - " +checkRestaurantName+" has been taken by another restaurant, please try again!");
            }
        }
        return true;
    }

}
