package com.tfs.demo.tfs_crud_demo.rest;

import com.tfs.demo.tfs_crud_demo.entity.Party;
import com.tfs.demo.tfs_crud_demo.service.PartyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class PartyRestController {

    private PartyService partyService;

    public PartyRestController(PartyService thePartyService){
        partyService = thePartyService;
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

        if(party.getPartyName()==null){
            party.setPartyName(existParty.getPartyName());
        }
        if(party.getPartyDescription()==null){
            party.setPartyDescription(existParty.getPartyDescription());
        }
        if(party.getPartyImage()==null){
            party.setPartyImage(existParty.getPartyImage());
        }
        if(party.getPartyPrice()==null){
            party.setPartyPrice(existParty.getPartyPrice());
        }
        party.setPartyItems(party.getPartyItems());
        if(party.getPartyItems()==null || party.getPartyItems().isEmpty()){
            party.setPartyItems(existParty.getPartyItems());
        }
        partyService.save(party);
        return party;
    }

    @PutMapping("/parties/clear/{partyId}")
    public Party clearPartyItem(@PathVariable int partyId){
        Party party = partyService.getById(partyId);
        party.setPartyItems(null);
        return party;
    }

    @DeleteMapping("/parties/{partyId}")
    public String disableParty(@PathVariable int partyId){
        partyService.disableParty(partyId);
        return "Disabled!";
    }

}
