package com.sw.dao;

import com.sw.classes.Payment;

import java.sql.Date;

/**
 * Classe DAO générique pour les paiements
 */
public abstract class DAOPayment extends DAO{

    public DAOPayment() {
        super("payment");
    }

    public abstract Payment subscribe(int userId, String name, String cardNumber, String expirationDate, String securityCode) throws Exception;

    public abstract Payment getPaymentById(int paymentId) throws Exception;

    public abstract Payment getLatestPaymentByUserId(int userId) throws Exception;

    public abstract Payment cancelSubscription(int paymentId) throws Exception;

    public abstract boolean isInDelayPeriod(int userId) throws Exception;

    public abstract int getDaysRemainingInDelayPeriod(int userId) throws Exception;
}
