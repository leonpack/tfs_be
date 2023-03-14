package com.tfs.demo.tfs_crud_demo.dto;

import javax.persistence.Column;
import java.time.LocalDate;

public interface OrderDateResponse {

    public LocalDate getOrderday();
    public Long getTotalPrice();
    public Integer getTotalQuantity();

}
