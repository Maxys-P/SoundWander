package com.sw.dao.mysql;

import com.sw.classes.User;
import com.sw.dao.DAOUser;
import com.sw.dao.factory.FactoryDAO;
import com.sw.dao.methodesDB.MethodesMySQL;
import com.sw.exceptions.ExceptionDB;

import java.util.*;

/**
 *  Implémentation concrète de DAOUser pour gérer les opérations spécifiques à la base de données MySQL pour les utilisateurs.
 */
public class DAOUserMySQL extends DAOUser {

    /**
     * Default constructor
     */
    public DAOUserMySQL() {
        super();
        this.methodesDB = new MethodesMySQL();
    }

    /**
     * Méthode pour créer un utilisateur
     * @param pseudo String, le pseudo de l'utilisateur
     * @param email String, l'email de l'utilisateur
     * @param password String, le mot de passe de l'utilisateur
     * @return User, l'utilisateur créé
     * @throws Exception si problème pendant la création du user
     */
    @Override
    public User createUser(String pseudo, String email, String password) throws Exception {
        //TODO
        System.out.println("TODO");
        return null;
    }


    /**
     * Méthode pour récupérer un utilisateur par son id
     * @param id int, l'id de l'utilisateur
     * @return User, l'utilisateur récupéré
     * @throws Exception si problème pendant la récupération du user
     */
    public User getUserById(int id) throws Exception {
        //TODO
        System.out.println("TODO");
        return null;
    }

    /**
     * Méthode pour récupérer un utilisateur par son pseudo
     * @param mail String, le mail de l'utilisateur
     * @return User, l'utilisateur récupéré
     * @throws Exception si problème pendant la récupération du user
     */
    public User getUserByMail(String mail) throws Exception {
        //TODO
        System.out.println("TODO");
        return null;
    }


    /**
     * Supprimer le user de la base de donnée avec son mail
     * @param mail, le mail du user
     * @throws Exception si une erreur survient lors de la suppression du user
     */
    public void deleteUserByMail(String mail) throws Exception {
        //TODO
        System.out.println("TODO");

    }

    /**
     * Supprimer le user de la base de donnée
     * @param id int, l'id du user
     * @throws Exception si une erreur survient lors de la suppression du user
     */
    public void deleteUserById(int id) throws Exception {
        //TODO
        System.out.println("TODO");

    }



    //Update :
    /**
     * Met à jour le pseudo du user dans la base de donnée
     * @param pseudo, le pseudo du user
     * @param id int, l'id du user
     * @throws Exception si une erreur survient lors de la requête SQL
     */
    public User updateUserPseudo(String pseudo, int id) throws Exception {
        //TODO
        System.out.println("TODO");
        return null;
    }


    /**
     * Modifie le mail du user
     * @param mail String, le mail du user
     * @param id int, l'id du user
     * @return User, le user modifié
     * @throws Exception en cas de problème lors de la requête SQL
     */
    public User updateUserMail(String mail, int id) throws Exception {
        //TODO
        System.out.println("TODO");
        return null;
    }


    /**
     * Met à jour le password du user dans la base de donnée
     * @param password, le password du user
     * @param id int, l'id du user
     * @throws Exception si une erreur survient lors de la requête SQL
     */
    public User updateUserPassword(String password, int id) throws Exception {
        //TODO
        System.out.println("TODO");
        return null;
    }


}