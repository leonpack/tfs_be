package com.tfs.demo.tfs_crud_demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "orderservices")
public class OrderServices {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "service_id")
    private Integer serviceId;

    @Column(name = "service_name")
    private String serviceName;

    @Column(name = "service_image")
    private String serviceImage;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private Order orderService;

    @Column(name = "service_price")
    private Double servicePrice;

    @Column(name = "description")
    private String serviceDescription;

    @Column(name = "status")
    private Boolean status;

    public OrderServices(){

    }

    public OrderServices(Integer serviceId, String serviceName, String serviceImage, Double servicePrice, String serviceDescription) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.serviceImage = serviceImage;
        this.servicePrice = servicePrice;
        this.serviceDescription = serviceDescription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public Order getOrderService() {
        return orderService;
    }

    public void setOrderService(Order orderService) {
        this.orderService = orderService;
    }

    public Double getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(Double servicePrice) {
        this.servicePrice = servicePrice;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceImage() {
        return serviceImage;
    }

    public void setServiceImage(String serviceImage) {
        this.serviceImage = serviceImage;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }
}
