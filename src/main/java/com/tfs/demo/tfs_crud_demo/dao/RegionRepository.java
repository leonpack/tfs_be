package com.tfs.demo.tfs_crud_demo.dao;

import com.tfs.demo.tfs_crud_demo.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, String> {

}
