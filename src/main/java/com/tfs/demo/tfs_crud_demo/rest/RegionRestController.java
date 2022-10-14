package com.tfs.demo.tfs_crud_demo.rest;

import com.tfs.demo.tfs_crud_demo.entity.Region;
import com.tfs.demo.tfs_crud_demo.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RegionRestController {

    private RegionService regionService;

    @Autowired
    public RegionRestController(RegionService theRegionService){
        regionService = theRegionService;
    }

    @GetMapping("/regions")
    public List<Region> getAllRegion(){
        return regionService.getAllRegions();
    }

    @GetMapping("/regions/{regionId}")
    public Region getRegionById(@PathVariable String regionId){
        Region theRegion = regionService.getRegionById(regionId);
        if(theRegion == null){
            throw new RuntimeException("Region with id - " +regionId + " not found!");
        }
        return theRegion;
    }
}
