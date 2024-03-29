package com.tfs.demo.tfs_crud_demo.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "message")
    private String message;

    @Column(name = "account_id")
    private String accountId;

    @Column(name = "created")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "is_checked")
    private Boolean isChecked = false;

    public Notification(){

    }

    public Notification(String message, String accountId) {
        this.message = message;
        this.accountId = accountId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, message, accountId, createdAt, isChecked);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }
        if(obj == null || getClass() != obj.getClass()){
            return false;
        }
        Notification notification = (Notification) obj;
        return Objects.equals(id, notification.id) &&
                Objects.equals(message, notification.message) &&
                Objects.equals(accountId, notification.accountId) &&
                Objects.equals(createdAt, notification.createdAt) &&
                Objects.equals(isChecked, notification.isChecked);
    }
}
