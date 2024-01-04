package com.sw.facades;

import com.sw.classes.Payment;
import com.sw.dao.factories.FactoryDAO;
import com.sw.dao.DAOPayment;

import java.sql.Date;

public class FacadePayment extends Facade{

    protected FactoryDAO f = FactoryDAO.getInstanceofFactoryDAO();

    protected DAOPayment daoPayment = f .getInstanceofDAOPayment();

    private static FacadePayment instance = null;

    public static FacadePayment getInstance() {
        if (instance == null) {
            instance = new FacadePayment();
        }
        return instance;
    }

    /**
     * Cette méthode s'abonne à un service avec les informations de paiement fournies.
     * @param userId Identifiant de l'utilisateur qui s'abonne.
     * @param name Nom de l'utilisateur sur la carte.
     * @param cardNumber Numéro de la carte bancaire.
     * @param expirationDate Date d'expiration de la carte.
     * @param securityCode Code de sécurité CVV de la carte.
     */
    public void subscribe(int userId, String name, String cardNumber, String expirationDate, String securityCode) {
        try {
            daoPayment.subscribe(userId, name, cardNumber, expirationDate, securityCode);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Cette méthode annule l'abonnement d'un utilisateur.
     * @param paymentId Identifiant du paiement.
     */
    public void cancelSubscription(int paymentId) {
        try {
            daoPayment.cancelSubscription(paymentId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Cette méthode récupère un paiement par son ID.
     * @param paymentId Identifiant du paiement.
     * @return Paiement.
     * @throws Exception si il y a un problème lors de la récupération.
     */
    public Payment getPaymentById(int paymentId) throws Exception {
        return daoPayment.getPaymentById(paymentId);
    }

    /**
     * Cette méthode récupère un paiement par l'ID de l'utilisateur.
     * @param userId Identifiant de l'utilisateur.
     * @return Paiement.
     * @throws Exception si il y a un problème lors de la récupération.
     */
    public Payment getPaymentByUserId(int userId) throws Exception {
        return daoPayment.getLatestPaymentByUserId(userId);
    }

    /**
     * Cette méthode vérifie si l'utilisateur est dans sa période de grâce.
     * @param userId Identifiant de l'utilisateur.
     * @return true si l'utilisateur est dans sa période de grâce, false sinon.
     * @throws Exception si il y a un problème lors de la récupération.
     */
    public boolean canArtistProposeMusic(int userId) throws Exception {
        return daoPayment.canArtistProposeMusic(userId);
    }

    /**
     * Cette méthode récupère le nombre de jours restants dans la période de grâce.
     * @param userId Identifiant de l'utilisateur.
     * @return Nombre de jours restants dans la période de grâce.
     * @throws Exception si il y a un problème lors de la récupération.
     */
    public int getDaysRemainingInDelayPeriod(int userId) throws Exception {
        return daoPayment.getDaysRemainingInDelayPeriod(userId);
    }
}
