package com.sw.dao.daoMysql;

import com.sw.classes.Payment;
import com.sw.dao.DAOPayment;
import com.sw.dao.boiteAOutils.MapperResultSet;
import com.sw.dao.requetesDB.RequetesMySQL;

import java.sql.Date;
import java.util.*;

/**
 * Classe DAO concrète pour les paiements
 */
public class DAOPaymentMySQL extends DAOPayment {

    /**
     * Default constructor
     */
    public DAOPaymentMySQL() {
        super();
        this.requetesDB = new RequetesMySQL();
    }


    @Override
    public Payment subscribe(int userId, String name, String cardNumber, String expirationDate, String securityCode) throws Exception {
        Map<String, Object> paymentData = new LinkedHashMap<>();
        paymentData.put("userId", userId);
        // Je simule la génération d'un token de paiement
        String paymentToken = Long.toString(Math.abs(new java.util.Random().nextLong()), 36);
        paymentData.put("paymentToken", paymentToken);
        paymentData.put("subscriptionDate", new Date(System.currentTimeMillis()));

        try {
            int paymentId = ((RequetesMySQL) requetesDB).create("payment", paymentData);
            System.out.println("Payment created with ID: " + paymentId);
            return getPaymentById(paymentId);
        } catch (Exception e) {
            throw new Exception("Erreur lors de l'inscription à l'abonnement", e);
        }
    }

    /**
     * Retrieve a Payment record by its ID.
     * @param paymentId ID of the payment record.
     * @return Payment object.
     * @throws Exception if there is a problem during the retrieval process.
     */
    @Override
    public Payment getPaymentById(int paymentId) throws Exception {
        Map<String, Object> conditions = new LinkedHashMap<>();
        conditions.put("id", paymentId);

        try {
            MapperResultSet paymentData = ((RequetesMySQL) requetesDB).selectWhere("payment", conditions);

            if (!paymentData.getListData().isEmpty()) {
                Map<String, Object> paymentDataMap = paymentData.getListData().getFirst();
                return new Payment(
                        (int) paymentDataMap.get("id"),
                        (int) paymentDataMap.get("userId"),
                        (String) paymentDataMap.get("paymentToken"),
                        (Date) paymentDataMap.get("subscriptionDate"),
                        (Date) paymentDataMap.get("cancellationDate")
                );
            } else {
                throw new Exception("Payment not found with ID: " + paymentId);
            }
        } catch (Exception e) {
            throw new Exception("Erreur lors de la récupération du paiement par id", e);
        }
    }


    /**
     * Récupère un paiement par l'ID de l'utilisateur.
     * @param userId ID de l'utilisateur.
     * @return Paiement.
     * @throws Exception si il y a un problème lors de la récupération.
     */
    @Override
    public Payment getLatestPaymentByUserId(int userId) throws Exception {
        try {
            // On récupère tous les paiements
            Map<String, Object> conditions = new HashMap<>();
            conditions.put("userId", userId);
            MapperResultSet paymentData = ((RequetesMySQL) requetesDB).selectWhere("payment", conditions);

            List<Payment> payments = new ArrayList<>();
            List<Map<String, Object>> listData = paymentData.getListData();
            for (Map<String, Object> row : listData) {
                payments.add(new Payment(
                        (int) row.get("id"),
                        (int) row.get("userId"),
                        (String) row.get("paymentToken"),
                        (Date) row.get("subscriptionDate"),
                        (Date) row.get("cancellationDate")
                ));
            }

            // On trie la liste pour obtenir le paiement le plus récent
            return payments.stream()
                    .max(Comparator.comparing(Payment::getSubscriptionDate)
                            .thenComparingInt(Payment::getId))
                    .orElse(null);
        } catch (Exception e) {
            throw new Exception("Erreur lors de la récupération du dernier paiement", e);
        }
    }


    /**
     * Met à jour la date d'annulation de l'abonnement pour un paiement spécifique et renvoie le paiement mis à jour.
     * @param paymentId ID du paiement.
     * @return Payment, l'objet paiement mis à jour.
     * @throws Exception si un problème survient lors de la mise à jour.
     */
    @Override
    public Payment cancelSubscription(int paymentId) throws Exception {
        Map<String, Object> updates = new LinkedHashMap<>();
        updates.put("cancellationDate", new Date(System.currentTimeMillis())); // Conversion de LocalDate en java.sql.Date

        Map<String, Object> whereConditions = new LinkedHashMap<>();
        whereConditions.put("id", paymentId);

        try {
            ((RequetesMySQL) requetesDB).update("payment", updates, whereConditions);
            return getPaymentById(paymentId);
        } catch (Exception e) {
            throw new Exception("Erreur lors de l'annulation de l'abonnement", e);
        }
    }

    /**
     * Vérifie si l'utilisateur peut encore proposer des musiques
     * @param userId ID de l'utilisateur.
     * @return true si l'utilisateur peut proposer des musiques, false sinon.
     * @throws Exception si un problème survient lors de la récupération.
     */
    @Override
    public boolean isInDelayPeriod(int userId) throws Exception {
        Payment latestPayment = getLatestPaymentByUserId(userId);
        if (latestPayment == null || latestPayment.getExpirationDate() == null) {
            return false;
        }

        Date cancellationDate = latestPayment.getExpirationDate();
        Calendar calCancellation = Calendar.getInstance();
        calCancellation.setTime(cancellationDate);

        Date subscriptionDate = latestPayment.getSubscriptionDate();
        Calendar calSubscription = Calendar.getInstance();
        calSubscription.setTime(subscriptionDate);
        calSubscription.set(Calendar.MONTH, calCancellation.get(Calendar.MONTH));
        calSubscription.set(Calendar.YEAR, calCancellation.get(Calendar.YEAR));

        // Adjust to the last day of the subscription month
        calSubscription.set(Calendar.DAY_OF_MONTH, calSubscription.getActualMaximum(Calendar.DAY_OF_MONTH));

        Date currentDate = new Date(System.currentTimeMillis());
        // The user is in the delay period if the current date is before the last day of the subscription month
        return currentDate.before(calSubscription.getTime());
    }

    /**
     * Récupère le nombre de jours restants pour proposer
     * @param userId ID de l'utilisateur
     * @return Nombre de jours restants
     * @throws Exception si un problème survient lors de la récupération.
     */
    @Override
    public int getDaysRemainingInDelayPeriod(int userId) throws Exception {
        Payment latestPayment = getLatestPaymentByUserId(userId);
        if (latestPayment == null || latestPayment.getExpirationDate() == null) {
            return 0;
        }

        Date cancellationDate = latestPayment.getExpirationDate();
        Calendar calCancellation = Calendar.getInstance();
        calCancellation.setTime(cancellationDate);

        Date subscriptionDate = latestPayment.getSubscriptionDate();
        Calendar calSubscription = Calendar.getInstance();
        calSubscription.setTime(subscriptionDate);
        calSubscription.set(Calendar.MONTH, calCancellation.get(Calendar.MONTH));
        calSubscription.set(Calendar.YEAR, calCancellation.get(Calendar.YEAR));
        calSubscription.set(Calendar.DAY_OF_MONTH, calSubscription.getActualMaximum(Calendar.DAY_OF_MONTH));

        long millisInDay = 24 * 60 * 60 * 1000;
        long currentTimeMillis = System.currentTimeMillis();
        long endOfDelayPeriodMillis = calSubscription.getTimeInMillis();
        long diffMillis = endOfDelayPeriodMillis - currentTimeMillis;

        if (diffMillis < 0) {
            return 0; // The delay period is over
        }

        return (int) (diffMillis / millisInDay);
    }

}
