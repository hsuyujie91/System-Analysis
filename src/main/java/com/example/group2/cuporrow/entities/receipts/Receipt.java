package com.example.group2.cuporrow.entities.receipts;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.group2.cuporrow.entities.user.User;

@Entity(name = "receipt")
@Table(name = "receipt")
public class Receipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int bill;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    public Receipt() {
    }

    public Receipt(LocalDateTime startTime, LocalDateTime endTime, User owner, int bill) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.owner = owner;
        this.bill = bill;
    }

    public Receipt(LocalDateTime startTime, LocalDateTime endTime, int bill) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.bill = bill;
    }

    public void setBill(int bill) {
        this.bill = bill;
    }

    public int getBill() {
        return bill;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getOwner() {
        return owner;
    }
}
