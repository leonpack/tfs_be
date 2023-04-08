package com.tfs.demo.tfs_crud_demo.service;

import com.tfs.demo.tfs_crud_demo.dao.ServicesRepository;
import com.tfs.demo.tfs_crud_demo.entity.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ServicesServiceImplementation implements ServicesService {

    private final ServicesRepository serviceRepository;

    @Autowired
    public ServicesServiceImplementation(ServicesRepository theServiceRepository){
        serviceRepository = theServiceRepository;
    }

    @Override
    public List<Services> getAllServices() {
        return serviceRepository.findAll();
    }

    @Override
    public Services getServiceById(int id) {
        Optional<Services> result = serviceRepository.findById(id);
        Services services = null;
        if(result.isPresent()){
            services = result.get();
        } else {
            throw new RuntimeException("Service with id - " +id + " not found!");
        }
        return services;
    }

    @Override
    public Services save(Services service) {
        return serviceRepository.save(service);
    }

    @Override
    public void disableService(int id) {
        Optional<Services> result = serviceRepository.findById(id);
        Services services = null;
        if(result.isPresent()){
            services = result.get();
            services.setStatus(false);
            serviceRepository.save(services);
        } else {
            throw new RuntimeException("Service with id - " +id + " not found!");
        }
    }
}
