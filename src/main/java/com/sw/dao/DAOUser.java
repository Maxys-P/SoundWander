package com.sw.dao;

import com.sw.classes.User;
import com.sw.dao.boiteAOutils.AuthentificationManager;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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
     * Instance de la classe utilitaire pour l'authentification du mot de passe
     * Elle permet de hasher et vérifier le mot de passe
     */
    protected AuthentificationManager passwordAuthentification = new AuthentificationManager();


    /**
     * Méthode pour créer un utilisateur
     * @param pseudo String, le pseudo de l'utilisateur
     * @param mail String, l'email de l'utilisateur
     * @param motDePasse String, le mot de passe de l'utilisateur
     * @param dateNaissance Date, la date de naissance de l'utilisateur
     * @return User, l'utilisateur créé
     * @throws Exception si problème pendant la création du user
     */
    public abstract User createUser(String pseudo, String mail, String motDePasse, LocalDate dateNaissance) throws Exception;


    /**
     * Méthode pour récupérer tous les users
     * @return List<User>, la liste des utilisateurs
     * @throws Exception si problème pendant la récupération des users
     */
    public abstract List<User> getAllUsers() throws Exception;


    /**
     * Méthode pour récupérer un utilisateur par son mail
     * @param mail String, le mail de l'utilisateur
     * @return User, l'utilisateur récupéré
     * @throws Exception si problème pendant la récupération du user
     */
    public abstract User getUserByMail(String mail) throws Exception;


    /**
     * Méthode pour récupérer un utilisateur par son id
     * @param id int, l'id de l'utilisateur
     * @return User, l'utilisateur récupéré
     * @throws Exception si problème pendant la récupération du user
     */
    public abstract User getUserById(int id) throws Exception;

    /**
     * Met à jour les informations d'un utilisateur et retourne l'utilisateur mis à jour.
     * @param id L'identifiant de l'utilisateur à mettre à jour.
     * @param updates Map contenant les champs à mettre à jour et leurs nouvelles valeurs.
     * @return User L'utilisateur mis à jour.
     * @throws Exception En cas d'erreur lors de la mise à jour ou de récupération des données.
     */
    public abstract User updateUser(int id, Map<String, Object> updates) throws Exception;


    /**
     * Supprimer le user de la base de donnée
     * @param id int, l'id du user
     * @throws Exception si une erreur survient lors de la suppression du user
     */
    public abstract boolean deleteUser(int id) throws Exception;




}