package com.tfs.demo.tfs_crud_demo.entity;

import javax.persistence.*;

@Entity
@Table(name = "services")
public class Services {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "service_name")
    private String serviceName;

    @Column(name = "service_price")
    private Double servicePrice;

    @Column(name = "service_description")
    private String serviceDescription;

    @Column(name = "service_image")
    private String serviceImage;

    @Column(name = "status")
    private Boolean status;

    public Services(){

    }

    public Services(String serviceName, Double servicePrice, String serviceDescription, String serviceImage, Boolean status) {
        this.serviceName = serviceName;
        this.servicePrice = servicePrice;
        this.serviceDescription = serviceDescription;
        this.serviceImage = serviceImage;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Double getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(Double servicePrice) {
        this.servicePrice = servicePrice;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public String getServiceImage() {
        return serviceImage;
    }

    public void setServiceImage(String serviceImage) {
        this.serviceImage = serviceImage;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
