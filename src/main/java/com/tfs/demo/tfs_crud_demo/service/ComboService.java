package com.tfs.demo.tfs_crud_demo.service;

import com.tfs.demo.tfs_crud_demo.entity.Combo;

import java.util.List;

public interface ComboService {

    public List<Combo> getAllCombos();

    public Combo getComboById(int id);

    public Combo saveCombo(Combo combo);

    public void disableComboById(int id);

}
