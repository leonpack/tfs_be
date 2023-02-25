package com.tfs.demo.tfs_crud_demo.service;

import com.tfs.demo.tfs_crud_demo.dao.PartyRepository;
import com.tfs.demo.tfs_crud_demo.entity.Party;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PartyServiceImplementation implements PartyService{

    private PartyRepository partyRepository;

    @Autowired
    public PartyServiceImplementation(PartyRepository thePartyRepository){
        partyRepository = thePartyRepository;
    }


    @Override
    public List<Party> getAllParties() {
        return partyRepository.findAll();
    }

    @Override
    public Party getById(int id) {
        Optional<Party> result = partyRepository.findById(id);
        Party party = null;
        if(result.isPresent()){
            party = result.get();
        }
        else {
            throw new RuntimeException("Party with id - " +id + " not found!");
        }
        return party;
    }

    @Override
    public Party save(Party party) {
        return partyRepository.save(party);
    }

    @Override
    public void disableParty(int id) {
        Optional<Party> result = partyRepository.findById(id);
        Party party = null;
        if(result.isPresent()){
            party = result.get();
            party.setStatus(false);
            partyRepository.save(party);
        }
        else {
            throw new RuntimeException("Party with id - " +id + " not found!");
        }
    }
}
