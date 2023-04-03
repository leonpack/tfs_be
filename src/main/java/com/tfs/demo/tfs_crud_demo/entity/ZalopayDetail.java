package com.tfs.demo.tfs_crud_demo.entity;

import javax.persistence.*;

@Entity
@Table(name = "zalopay_detail")
public class ZalopayDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "apptransid")
    private String apptransid;

    @Column(name = "zptransid")
    private String zptransid;

    public ZalopayDetail(){

    }

    public ZalopayDetail(Integer orderId, String apptransid, String zptransid) {
        this.orderId = orderId;
        this.apptransid = apptransid;
        this.zptransid = zptransid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getApptransid() {
        return apptransid;
    }

    public void setApptransid(String apptransid) {
        this.apptransid = apptransid;
    }

    public String getZptransid() {
        return zptransid;
    }

    public void setZptransid(String zptransid) {
        this.zptransid = zptransid;
    }
}
