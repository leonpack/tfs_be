package com.tfs.demo.tfs_crud_demo.dto;

import java.time.LocalDate;
import java.util.List;

public class RevenueResponseDTO {

    private LocalDate date;
    private Double totalPrice;
    private Integer totalQuantity;

    public RevenueResponseDTO(){

    }

    public RevenueResponseDTO(LocalDate date, Double totalPrice, Integer totalQuantity) {
        this.date = date;
        this.totalPrice = totalPrice;
        this.totalQuantity = totalQuantity;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
}
