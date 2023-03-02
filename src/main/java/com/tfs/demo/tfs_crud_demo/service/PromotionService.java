package com.tfs.demo.tfs_crud_demo.service;

import com.tfs.demo.tfs_crud_demo.entity.Promotion;

import java.util.List;

public interface PromotionService {

    public List<Promotion> getAllPromotions();

    public Promotion getPromotionByCode(String promotionCode);

    public void savePromotion(Promotion thePromotion);

    public void disablePromotion(int id);

}
