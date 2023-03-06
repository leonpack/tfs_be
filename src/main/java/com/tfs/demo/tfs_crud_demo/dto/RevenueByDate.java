package com.tfs.demo.tfs_crud_demo.dto;

import java.util.Date;

public class RevenueByDate {

    private Date revenueDate;

    public RevenueByDate(){

    }

    public RevenueByDate(Date revenueDate) {
        this.revenueDate = revenueDate;
    }

    public Date getRevenueDate() {
        return revenueDate;
    }

    public void setRevenueDate(Date revenueDate) {
        this.revenueDate = revenueDate;
    }
}
