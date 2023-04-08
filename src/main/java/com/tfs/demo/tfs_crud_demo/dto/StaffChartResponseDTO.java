package com.tfs.demo.tfs_crud_demo.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface StaffChartResponseDTO {

    public LocalDate getOrderday();
    public Long getTotalPrice();
    public int getTotalQuantity();

}
