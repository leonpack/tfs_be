package com.tfs.demo.tfs_crud_demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "region")
public class Region {

    @Id
    @Column(name = "region_id")
    private String id;

    @Column(name = "region_name")
    private String region_name;

    public Region(){

    }

    public Region(String id, String region_name) {
        this.id = id;
        this.region_name = region_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegion_name() {
        return region_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }

    @Override
    public String toString() {
        return "Region{" +
                "id='" + id + '\'' +
                ", region_name='" + region_name + '\'' +
                '}';
    }
}
