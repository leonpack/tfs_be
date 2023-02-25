package com.tfs.demo.tfs_crud_demo.service;

import com.tfs.demo.tfs_crud_demo.dao.ComboRepository;
import com.tfs.demo.tfs_crud_demo.entity.Combo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComboServiceImplementation implements ComboService{

    private ComboRepository comboRepository;

    public ComboServiceImplementation(ComboRepository theComboRepository){
        comboRepository = theComboRepository;
    }

    @Override
    public List<Combo> getAllCombos() {
        return comboRepository.findAll();
    }

    @Override
    public Combo getComboById(int id) {
        Optional<Combo> result = comboRepository.findById(id);
        Combo combo = null;
        if(result.isPresent()){
            combo = result.get();
        } else {
            throw new RuntimeException("This combo with id - " +id +  " not found!");
        }
        return combo;
    }

    @Override
    public Combo saveCombo(Combo combo) {
        return comboRepository.save(combo);
    }

    @Override
    public void disableComboById(int id) {
        Optional<Combo> result = comboRepository.findById(id);
        Combo combo = null;
        if(result.isPresent()){
            combo = result.get();
            combo.setStatus(false);
            comboRepository.save(combo);
        } else {
            throw new RuntimeException("Combo with id - " +id + " not found!");
        }
    }

}
