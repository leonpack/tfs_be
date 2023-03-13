package com.tfs.demo.tfs_crud_demo.dto;

import java.time.LocalDate;

public class RevenueByDate {

    private LocalDate revenueDate;

    public RevenueByDate(){

    }

    public RevenueByDate(LocalDate revenueDate) {
        this.revenueDate = revenueDate;
    }

    public LocalDate getRevenueDate() {
        return revenueDate;
    }

    public void setRevenueDate(LocalDate revenueDate) {
        this.revenueDate = revenueDate;
    }
}
