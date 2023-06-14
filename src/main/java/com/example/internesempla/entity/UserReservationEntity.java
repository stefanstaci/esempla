package com.example.internesempla.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "user_reservation")
public class UserReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer totalSize;
    private Integer usedSize;
    @ManyToOne()
    @JoinColumn(name = "user_id")
    private UserEntity userId;
    private Boolean activated;
    private String createdBy;
    private LocalDate createdDate;

    public UserReservationEntity() {
    }

    public UserReservationEntity(Integer totalSize, Integer usedSize, UserEntity userId, Boolean activated, String createdBy, LocalDate createdDate) {
        this.totalSize = totalSize;
        this.usedSize = usedSize;
        this.userId = userId;
        this.activated = activated;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
    }

    public UserReservationEntity(Integer id, Integer totalSize, Integer usedSize, UserEntity userId, Boolean activated, String createdBy, LocalDate createdDate) {
        this.id = id;
        this.totalSize = totalSize;
        this.usedSize = usedSize;
        this.userId = userId;
        this.activated = activated;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Integer totalSize) {
        this.totalSize = totalSize;
    }

    public Integer getUsedSize() {
        return usedSize;
    }

    public void setUsedSize(Integer usedSize) {
        this.usedSize = usedSize;
    }

    public UserEntity getUserId() {
        return userId;
    }

    public void setUserId(UserEntity userId) {
        this.userId = userId;
    }

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }
}
