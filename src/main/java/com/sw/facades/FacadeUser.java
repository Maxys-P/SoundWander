package com.sw.facades;

import com.sw.classes.User;
import com.sw.dao.DAOUser;
import com.sw.dao.factories.FactoryDAO;
import com.sw.exceptions.ExceptionUsedEmail;
import com.sw.exceptions.ExceptionDB;
import com.sw.exceptions.ExceptionBadLogin;
import com.sw.exceptions.ExceptionBadPassword;
import com.sw.dao.boiteAOutils.AuthentificationManager;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;


/**
 * Classe de facade pour les utilisateurs visiteurs
 * @see Facade
 */
public class FacadeUser extends Facade {

    /**
     * Factory pour les DAO
     */
    protected FactoryDAO f = FactoryDAO.getInstanceofFactoryDAO();

    /**
     * DAO pour les utilisateurs
     */
    protected DAOUser daoUser = f.getInstanceofDAOUser();


    /**
     * Instance de la facade pour le singleton
     */
    private static FacadeUser instance = null;


    /**
     * Getter de l'instance de la facade pour le singleton
     * @return FacadeUser, l'instance de la facade
     */
    public static FacadeUser getInstance() {
        if (instance == null) {
            instance = new FacadeUser();
        }
        return instance;
    }


    /**
     * Méthode pour se connecter
     * @param mail String, mail de l'utilisateur
     * @param motDePasse String, mot de passe de l'utilisateur
     * @return User, l'utilisateur connecté
     * @throws Exception si les identifiants sont incorrects
     */
    public User connexion(String mail, String motDePasse) throws Exception {
        try {
            //System.out.println(daoUser.getAllUsers());
            User user = daoUser.getUserByMail(mail);
            if (AuthentificationManager.checkPassword(motDePasse, user.getMotDePasse())) {
                Facade.currentUser = user;
                return user;
            }
            else {
                throw new ExceptionBadPassword("Mauvais mot de passe");
            }
        } catch (Exception e) {
            throw new ExceptionBadLogin("Erreur lors de la validation des identifiants");
        }
    }


    /**
     * Méthode pour s'inscrire
     * @param mail String, mail de l'utilisateur
     * @param pseudo String, pseudo de l'utilisateur
     * @param motDePasse String, mot de passe de l'utilisateur
     * @param dateNaissance Date, date de naissance de l'utilisateur
     * @return User, l'utilisateur inscrit
     * @throws Exception si l'utilisateur existe déjà
     */
    public User inscription(String mail, String pseudo, String motDePasse, LocalDate dateNaissance) throws Exception {
        if (daoUser.getUserByMail(mail) != null) {
            throw new ExceptionUsedEmail(mail);
        }
        // Si le mail n'existe pas, créer le nouvel utilisateur
        try {
            User newUser = daoUser.createUser(pseudo, mail, motDePasse, dateNaissance);
            Facade.currentUser = newUser;
            return newUser;
        } catch (SQLException e) {
            throw new ExceptionDB(e.getMessage());
        }
    }

    /**
     * Méthode pour récupérer un user par son id
     * @param id int, l'id de l'utilisateur
     * @return User, l'utilisateur récupéré
     * @throws Exception si problème pendant la récupération du user
     */
    public User getUserById(int id) throws Exception {
        return daoUser.getUserById(id);
    }

    /**
     * Méthode pour récupérer un user par son mail
     * @param mail String, le mail de l'utilisateur
     * @return User, l'utilisateur récupéré
     * @throws Exception si problème pendant la récupération du user
     */
    public User getUserByMail(String mail) throws Exception {
        return daoUser.getUserByMail(mail);
    }

    /**
     * Méthode pour mettre à jour le pseudo d'un user
     * @param idUser int, l'id de l'utilisateur
     * @param newPseudo String, le nouveau pseudo de l'utilisateur
     * @throws Exception si problème pendant la mise à jour du pseudo
     */
    public void updateUserPseudo(int idUser, String newPseudo) throws Exception {
        daoUser.updateUserPseudo(idUser, newPseudo);
    }

    /**
     * Méthode pour mettre à jour le mail d'un user
     * @param idUser int, l'id de l'utilisateur
     * @param newMail String, le nouveau mail de l'utilisateur
     * @throws Exception si problème pendant la mise à jour du mail
     */
    public void updateUserMail(int idUser, String newMail) throws Exception {
        daoUser.updateUserMail(idUser, newMail);
    }

    /**
     * Méthode pour mettre à jour le mot de passe d'un user
     * @param idUser int, l'id de l'utilisateur
     * @param newMotDePasse String, le nouveau mot de passe de l'utilisateur
     * @throws Exception si problème pendant la mise à jour du mot de passe
     */
    public void updateUserMotDePasse(int idUser, String newMotDePasse) throws Exception {
        daoUser.updateUserMotDePasse(idUser, newMotDePasse);
    }

    /**
     * Méthode pour mettre à jour la date de naissance d'un user
     * @param idUser int, l'id de l'utilisateur
     * @param newDateNaissance Date, la nouvelle date de naissance de l'utilisateur
     * @throws Exception si problème pendant la mise à jour de la date de naissance
     */
    public void updateUserDateNaissance(int idUser, LocalDate newDateNaissance) throws Exception {
        daoUser.updateUserDateNaissance(idUser, newDateNaissance);
    }

    /**
     * Méthode pour mettre à jour la photo d'un user
     * @param idUser int, l'id de l'utilisateur
     * @param newPhoto String, la nouvelle photo de l'utilisateur
     * @throws Exception si problème pendant la mise à jour de la photo
     */
    public void updateUserPhoto(int idUser, String newPhoto) throws Exception {
        daoUser.updateUserPhoto(idUser, newPhoto);
    }

}

