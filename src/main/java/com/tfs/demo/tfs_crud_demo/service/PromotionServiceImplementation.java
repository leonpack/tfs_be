package com.tfs.demo.tfs_crud_demo.service;

import com.tfs.demo.tfs_crud_demo.dao.PromotionRepository;
import com.tfs.demo.tfs_crud_demo.entity.Promotion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class PromotionServiceImplementation implements PromotionService{

    private PromotionRepository promotionRepository;

    @Autowired
    public PromotionServiceImplementation(PromotionRepository thePromotionRepository){
        promotionRepository = thePromotionRepository;
    }

    @Override
    public List<Promotion> getAllPromotions() {
        return promotionRepository.findAll();
    }

    @Override
    public Promotion getPromotionByCode(String promotionCode) {
        Optional<Promotion> result = promotionRepository.findById(promotionCode);

        Promotion thePromotion = null;
        if(result.isPresent()){
            thePromotion = result.get();
        }
        else {
            throw new RuntimeException("Promotion code - " +promotionCode+ " not found!");
        }
        return thePromotion;
    }

    @Override
    public void savePromotion(Promotion thePromotion) {
        promotionRepository.save(thePromotion);
    }

    @Override
    public void disablePromotion(String promotionCode) {
        Optional<Promotion> result = promotionRepository.findById(promotionCode);

        Promotion thePromotion = null;
        if(result.isPresent()){
            thePromotion = result.get();
        } else {
            throw new RuntimeException("Promotion code - " +promotionCode+ " not found!");
        }
        thePromotion.setStatus(false);
    }

    @Override
    public boolean checkDuplicatePromotionCode(String promotionCode) {
        Optional<Promotion> result = promotionRepository.findById(promotionCode);

        Promotion thePromotion = null;
        if(result.isPresent()){
            throw new RuntimeException("Promotion code - " +promotionCode +" already in the list, please try again!");
        }
        return true;
    }

}
