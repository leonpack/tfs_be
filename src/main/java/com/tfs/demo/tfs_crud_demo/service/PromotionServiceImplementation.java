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

    private final PromotionRepository promotionRepository;

    @Autowired
    public PromotionServiceImplementation(PromotionRepository thePromotionRepository){
        promotionRepository = thePromotionRepository;
    }

    @Override
    public List<Promotion> getAllPromotions() {
        return promotionRepository.findAll();
    }

    @Override
    public Promotion getPromotionById(int id) {
        Optional<Promotion> result = promotionRepository.findById(id);
        Promotion promotion = null;
        if(result.isPresent()){
            promotion = result.get();
        } else {
            throw new RuntimeException("Promotion " + id + " not found!");
        }
        return promotion;
    }

    public Promotion getPromotionByCode(String code){
        return promotionRepository.findPromotionByPromotionCode(code);
    }

    @Override
    public void savePromotion(Promotion thePromotion) {
        promotionRepository.save(thePromotion);
    }

    @Override
    public void disablePromotion(int id) {
        Optional<Promotion> result = promotionRepository.findById(id);

        Promotion thePromotion = null;
        if(result.isPresent()){
            thePromotion = result.get();
        } else {
            throw new RuntimeException("Promotion code - " +id+ " not found!");
        }
        thePromotion.setStatus(false);
    }

}
