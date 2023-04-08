package com.tfs.demo.tfs_crud_demo.service;

import com.tfs.demo.tfs_crud_demo.dao.RegionRepository;
import com.tfs.demo.tfs_crud_demo.entity.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegionServiceImplementation implements RegionService{

    private final RegionRepository regionRepository;

    @Autowired
    public RegionServiceImplementation(RegionRepository theRegionRepository){
        regionRepository = theRegionRepository;
    }

    @Override
    public List<Region> getAllRegions() {
        return regionRepository.findAll();
    }

    @Override
    public Region getRegionById(int regionId) {
        Optional<Region> result = regionRepository.findById(regionId);

        Region theRegion = null;

        if(result.isPresent()){
            theRegion = result.get();
        }
        else{
            throw new RuntimeException("Region with id - " +regionId+ " not found");
        }
        return theRegion;
    }

    @Override
    public void saveRegion(Region region) {
        regionRepository.save(region);
    }
}
