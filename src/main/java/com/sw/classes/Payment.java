package com.sw.classes;

import java.sql.Date;
import java.time.LocalDate;

public class Payment {
    private int id;
    private int userId;
    private String name;
    private String paymentToken;
    private Date subscriptionDate;
    private Date expirationDate;

    public Payment(int id, int userId, String paymentToken, Date subscriptionDate) {
        this.id = id;
        this.userId = userId;
        this.paymentToken = paymentToken;
        this.subscriptionDate = subscriptionDate;
    }

    public Payment(int id, int userId, String paymentToken, Date subscriptionDate, Date expirationDate) {
        this.id = id;
        this.userId = userId;
        this.paymentToken = paymentToken;
        this.subscriptionDate = subscriptionDate;
        this.expirationDate = expirationDate;
    }

    // Getters et Setters pour chaque champ

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPaymentToken() {
        return paymentToken;
    }

    public void setPaymentToken(String paymentToken) {
        this.paymentToken = paymentToken;
    }

    public Date getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(Date subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

}
