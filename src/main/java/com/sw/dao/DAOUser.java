package com.sw.dao;

import com.sw.classes.User;

/**
 *  Classe abstraite définissant les opérations standard pour interagir avec les données utilisateur, indépendamment du SGBD.
 * @see DAO
 */
public abstract class DAOUser extends DAO {

    /**
     * Default constructor
     */
    public DAOUser() {
        super("user");
    }

    /**
     * Méthode pour créer un utilisateur
     * @param pseudo String, le pseudo de l'utilisateur
     * @param email String, l'email de l'utilisateur
     * @param password String, le mot de passe de l'utilisateur
     * @return User, l'utilisateur créé
     * @throws Exception si problème pendant la création du user
     */
    public abstract User createUser(String pseudo, String email, String password) throws Exception;


    /**
     * Méthode pour récupérer un utilisateur par son id
     * @param id int, l'id de l'utilisateur
     * @return User, l'utilisateur récupéré
     * @throws Exception si problème pendant la récupération du user
     */
    public abstract User getUserById(int id) throws Exception;

    /**
     * Méthode pour récupérer un utilisateur par son pseudo
     * @param mail String, le mail de l'utilisateur
     * @return User, l'utilisateur récupéré
     * @throws Exception si problème pendant la récupération du user
     */
    public abstract User getUserByMail(String mail) throws Exception;


    /**
     * Supprimer le user de la base de donnée avec son mail
     * @param mail, le mail du user
     * @throws Exception si une erreur survient lors de la suppression du user
     */
    public abstract void deleteUserByMail(String mail) throws Exception;

    /**
     * Supprimer le user de la base de donnée
     * @param id int, l'id du user
     * @throws Exception si une erreur survient lors de la suppression du user
     */
    public abstract void deleteUserById(int id) throws Exception;



    //Update :
    /**
     * Met à jour le pseudo du user dans la base de donnée
     * @param pseudo, le pseudo du user
     * @param id int, l'id du user
     * @throws Exception si une erreur survient lors de la requête SQL
     */
    public abstract User updateUserPseudo(String pseudo, int id) throws Exception;


    /**
     * Modifie le mail du user
     * @param mail String, le mail du user
     * @param id int, l'id du user
     * @return User, le user modifié
     * @throws Exception en cas de problème lors de la requête SQL
     */
    public abstract User updateUserMail(String mail, int id) throws Exception;


    /**
     * Met à jour le password du user dans la base de donnée
     * @param password, le password du user
     * @param id int, l'id du user
     * @throws Exception si une erreur survient lors de la requête SQL
     */
    public abstract User updateUserPassword(String password, int id) throws Exception;


}