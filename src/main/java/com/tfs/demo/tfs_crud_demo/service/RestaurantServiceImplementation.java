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

    private final RestaurantRepository restaurantRepository;
    private final EntityManager entityManager;

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
    public List<Restaurant> getAllByAvailableStatus(Boolean status) {
        return restaurantRepository.findAllByAvailableStatus(status);
    }

    @Override
    public Restaurant getRestaurantById(int restaurantId) {
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
    public void disableRestaurant(int restaurantId) {
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
    public boolean checkDuplicatePhoneNumber(String checkPhoneNumber) {
        Query theQuery = entityManager.createQuery("select restaurantNumber from Restaurant ");
        for(int i = 0;i<theQuery.getResultList().size();i++){
            if(checkPhoneNumber.equals(theQuery.getResultList().get(i))){
                throw new RuntimeException("This phone number -" +checkPhoneNumber + " has been linked with another restaurant, please try again");
            }
        }
        return true;
    }


}
