package com.tfs.demo.tfs_crud_demo.dao;

import com.tfs.demo.tfs_crud_demo.entity.Food;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class FoodDAOJpaImplementation implements FoodDAO{

    private EntityManager entityManager;

    @Autowired
    public FoodDAOJpaImplementation(EntityManager theEntityManager){
        entityManager = theEntityManager;
    }

    @Override
    public List<Food> getAllFoods() {
        //create getAllFood query
        Query theQuery = entityManager.createQuery("from Food");
        //execute the query to get list foods
        List<Food> theFoodList = theQuery.getResultList();
        //return list
        return theFoodList;
    }

    @Override
    public Food getFoodById(int id) {
        //get food from id
        Food theFood = entityManager.find(Food.class,id);

        return theFood;
    }

    @Override
    public void saveFood(Food theFood) {
        //to be dated
    }

    @Override
    public void deleteFood(int id) {
        //to be dated
    }
}
