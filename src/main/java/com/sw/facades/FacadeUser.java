package com.sw.facades;

import com.sw.classes.User;
import com.sw.dao.DAOUser;
import com.sw.dao.factory.FactoryDAO;
import com.sw.exceptions.ExceptionUsedEmail;
import com.sw.exceptions.ExceptionUsedPseudo;
import com.sw.exceptions.ExceptionDB;
import com.sw.exceptions.ExceptionBadLogin;
import com.sw.exceptions.ExceptionBadPassword;
import com.sw.dao.boiteAOutils.PasswordAuthentification;

import java.sql.SQLException;

/**
 * Classe de facade pour les utilisateurs visiteurs
 * @see Facade
 */
public class FacadeUser extends Facade {

    /**
     * Modèle de user
     */
    protected DAOUser daoUser;

    /**
     * Authentification du mot de passe
     */
    protected PasswordAuthentification passwordAuthentification;

    /**
     * Instance de la facade pour le singleton
     */
    private static FacadeUser instance = null;


    /**
     * Constructeur de la facade pour le singleton
     */
    protected FacadeUser() {
        this.daoUser = FactoryDAO.getInstance().getDAOUser();
    }


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
            User user = this.daoUser.getUserByMail(mail);
            if (user != null /*&& user.getPassword().equals(motDePasse)*/){
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
     * @return User, l'utilisateur inscrit
     * @throws Exception si l'utilisateur existe déjà
     */
    public User inscription(String mail, String pseudo, String motDePasse) throws Exception {
        if (daoUser.dataExist(mail, "mail")) {
            throw new ExceptionUsedEmail(mail);
        } else if (daoUser.dataExist(pseudo, "pseudo")) {
            throw new ExceptionUsedPseudo(pseudo);
        } else {
            try {

                daoUser.createUser(mail, pseudo, motDePasse);
                User newuser = daoUser.getUserByMail(mail);
                Facade.currentUser = newuser;
                return newuser;
            } catch (SQLException e) {
                throw new ExceptionDB(e.getMessage());
            }
        }
    }
}

