package com.tfs.demo.tfs_crud_demo.dto;

public class RefundDTO {

    private String zptransid;

    private Long amount;

    public RefundDTO(){

    }

    public RefundDTO(String zptransid, Long amount) {
        this.zptransid = zptransid;
        this.amount = amount;
    }

    public String getZptransid() {
        return zptransid;
    }

    public void setZptransid(String zptransid) {
        this.zptransid = zptransid;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
