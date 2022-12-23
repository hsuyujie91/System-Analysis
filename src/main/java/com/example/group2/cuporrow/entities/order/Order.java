package com.example.group2.cuporrow.entities.order;

import java.time.Duration;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.group2.cuporrow.entities.user.User;

@Entity(name = "orders")
@Table(name = "orders")
public class Order {

    // 預設起初有20杯子
    public static int totalCup = 20;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDateTime borrowTime;
    private LocalDateTime returnTime;

    private int bottleId;

    private int bill;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User orderUser;

    public Order() {
    }

    public Order(int bottleId, User orderUser) {
        this.bottleId = bottleId;
        this.orderUser = orderUser;
        this.borrowTime = LocalDateTime.now();
    }

    public void setBorrowTime(LocalDateTime borrowTime) {
        this.borrowTime = borrowTime;
    }

    public LocalDateTime getBorrowTime() {
        return borrowTime;
    }

    public void setReturnTime(LocalDateTime returnTime) {
        this.returnTime = returnTime;
    }

    public LocalDateTime getReturnTime() {
        return returnTime;
    }

    public void setBottleId(int bottleId) {
        this.bottleId = bottleId;
    }

    public int getBottleId() {
        return bottleId;
    }

    public void setBill(int bill) {
        this.bill = bill;
    }

    public void calculateBill() {
        long duration = Duration.between(borrowTime, returnTime).getSeconds();
        // $3/6hr -> $ 3/(6*3600)/sec
        this.bill = (int) (duration * 3 / (6*3600));
    }

    public int getBill() {
        return bill;
    }

}
