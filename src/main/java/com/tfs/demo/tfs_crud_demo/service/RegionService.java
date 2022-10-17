package com.tfs.demo.tfs_crud_demo.service;

import com.tfs.demo.tfs_crud_demo.entity.Region;

import java.util.List;

public interface RegionService {

    public List<Region> getAllRegions();

    public Region getRegionById(String regionId);

    public void saveRegion(Region region);

}
