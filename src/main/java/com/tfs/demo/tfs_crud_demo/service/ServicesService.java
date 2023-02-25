package com.tfs.demo.tfs_crud_demo.service;

import com.tfs.demo.tfs_crud_demo.entity.Services;

import java.util.List;

public interface ServicesService {

    public List<Services> getAllServices();

    public Services getServiceById(int id);

    public Services save(Services service);

    public void disableService(int id);

}
