package com.tfs.demo.tfs_crud_demo.dto;

import java.time.LocalDate;

public interface OrderDateForOwnerResponse {

    public LocalDate getOrderday();
    public Long getTotalPrice();
    public Integer getTotalQuantity();

}
