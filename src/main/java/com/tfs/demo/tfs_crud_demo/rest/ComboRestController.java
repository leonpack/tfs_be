package com.tfs.demo.tfs_crud_demo.rest;

import com.tfs.demo.tfs_crud_demo.entity.Combo;
import com.tfs.demo.tfs_crud_demo.service.ComboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ComboRestController {

    private final ComboService comboService;

    @Autowired
    public ComboRestController(ComboService theComboService){
        comboService = theComboService;
    }

    @GetMapping("/combos")
    public List<Combo> getAllCombos(){
        return comboService.getAllCombos();
    }

    @GetMapping("/combos/{comboId}")
    public Combo findById(@PathVariable int comboId){
        return comboService.getComboById(comboId);
    }

    @PostMapping("/combos")
    public Combo addNewCombo(@RequestBody Combo combo){
        comboService.saveCombo(combo);
        return combo;
    }

    @PutMapping("/combos")
    public Combo updateCombo(@RequestBody Combo combo){
        Combo existCombo = comboService.getComboById(combo.getId());

        if(combo.getComboName()==null){
            combo.setComboName(existCombo.getComboName());
        }
        if(combo.getComboPrice()==null){
            combo.setComboPrice(existCombo.getComboPrice());
        }
        if(combo.getImage()==null){
            combo.setImage(existCombo.getImage());
        }
        if(combo.getDescription()==null){
            combo.setDescription(existCombo.getDescription());
        }
        combo.setComboItems(combo.getComboItems());
        if(combo.getComboItems()==null || combo.getComboItems().isEmpty()){
            combo.setComboItems(existCombo.getComboItems());
        }
        if(combo.getStatus()==null){
            combo.setStatus(existCombo.getStatus());
        }
        comboService.saveCombo(combo);
        return combo;
    }

    @PutMapping("/combos/clear/{comboId}")
    public Combo clearComboItem(@PathVariable int comboId){
        Combo combo = comboService.getComboById(comboId);
        combo.setComboItems(null);
        return combo;
    }

    @DeleteMapping("/combos/{comboId}")
    public String disableCombo(@PathVariable int comboId){
        comboService.disableComboById(comboId);
        return "Disabled combo " +comboId + " done!";
    }

}
