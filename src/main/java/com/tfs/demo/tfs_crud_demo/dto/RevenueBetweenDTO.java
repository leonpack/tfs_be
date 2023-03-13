package com.tfs.demo.tfs_crud_demo.dto;

import java.time.LocalDate;

public class RevenueBetweenDTO {

    private LocalDate fromDate;

    private LocalDate toDate;

    public RevenueBetweenDTO(){

    }

    public RevenueBetweenDTO(LocalDate fromDate, LocalDate toDate) {
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }
}
