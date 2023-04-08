package com.tfs.demo.tfs_crud_demo.rest;

import com.tfs.demo.tfs_crud_demo.entity.Party;
import com.tfs.demo.tfs_crud_demo.service.CartService;
import com.tfs.demo.tfs_crud_demo.service.PartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class PartyRestController {

    private PartyService partyService;
    private CartService cartService;

    @Autowired
    public PartyRestController(PartyService thePartyService, CartService theCartService){
        partyService = thePartyService;
        cartService = theCartService;
    }


    @GetMapping("/parties")
    public List<Party> getAllParties(){
        return partyService.getAllParties();
    }

    @GetMapping("/parties/{partyId}")
    public Party getById(@PathVariable int partyId){
        return partyService.getById(partyId);
    }

    @PostMapping("/parties")
    public Party addNewParty(@RequestBody Party party){
        partyService.save(party);
        return party;
    }

    @PutMapping("/parties")
    public Party updateParty(@RequestBody Party party){
        Party existParty = partyService.getById(party.getId());

        if(party.getCart()==null){
            existParty.setCart(existParty.getCart());
        }

        if(party.getOrder()==null){
            existParty.setOrder(existParty.getOrder());
        }

        if(party.getNote()==null || party.getNote().isEmpty()){
            party.setNote(existParty.getNote());
        }

        if(party.getQuantity()==null || party.getQuantity().toString().isEmpty()){
            party.setQuantity(existParty.getQuantity());
        }

        if(party.getItemList()==null || party.getItemList().isEmpty()){
            party.setItemList(existParty.getItemList());
        }

        if(party.getPartyTemplate()==null || party.getPartyTemplate().isEmpty()){
            party.setPartyTemplate(existParty.getPartyTemplate());
        }

        if(party.getTotalPrice()==null){
            party.setTotalPrice(existParty.getTotalPrice());
        }

        if(party.getPartyName()==null){
            party.setPartyName(existParty.getPartyName());
        }

        if(party.getSubTotal()==null){
            party.setSubTotal(existParty.getSubTotal());
        }

        party.setItemList(party.getItemList());

        partyService.save(party);

        return party;
    }

    @DeleteMapping("/parties/{partiesId}")
    public ResponseEntity<String> deleteParty(@PathVariable int partiesId){
        partyService.removeById(partiesId);
        return ResponseEntity.ok("Xoá bàn tiệc thành công");
    }

}
