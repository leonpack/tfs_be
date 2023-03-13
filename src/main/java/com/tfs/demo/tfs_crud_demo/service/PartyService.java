package com.tfs.demo.tfs_crud_demo.service;

import com.tfs.demo.tfs_crud_demo.entity.Party;

import java.util.List;

public interface PartyService {

    List<Party> getAllParties();

    Party getById(int id);

    Party save(Party party);

    void removeById(int id);

}
