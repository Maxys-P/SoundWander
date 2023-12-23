package com.sw.facades;

import com.sw.classes.User;
import com.sw.dao.DAOUser;
import com.sw.dao.factories.FactoryDAO;
import com.sw.exceptions.ExceptionUsedEmail;
import com.sw.exceptions.ExceptionDB;
import com.sw.exceptions.ExceptionBadLogin;
import com.sw.exceptions.ExceptionBadPassword;
import com.sw.dao.boiteAOutils.PasswordAuthentification;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


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
     * Authentification du mot de passe
     */
    protected PasswordAuthentification passwordAuthentification;

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
            User user = daoUser.getUserByMail(mail);
            if (user.getMotDePasse().equals(motDePasse)){
                //TODO : vérifier le mot de passe avec la méthode de PasswordAuthentication
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
            daoUser.createUser(pseudo, mail, motDePasse, dateNaissance);
            User newUser = daoUser.getUserByMail(mail);
            Facade.currentUser = newUser;
            return newUser;
        } catch (SQLException e) {
            throw new ExceptionDB(e.getMessage());
        }
    }

}

