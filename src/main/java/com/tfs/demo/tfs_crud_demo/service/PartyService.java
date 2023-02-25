package com.tfs.demo.tfs_crud_demo.service;

import com.tfs.demo.tfs_crud_demo.entity.Party;

import java.util.List;

public interface PartyService {

    public List<Party> getAllParties();

    public Party getById(int id);

    public Party save(Party party);

    public void disableParty(int id);

}
