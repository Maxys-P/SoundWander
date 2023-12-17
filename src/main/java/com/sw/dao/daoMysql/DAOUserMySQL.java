package com.sw.dao.daoMysql;

import com.sw.classes.User;
import com.sw.dao.DAOUser;
import com.sw.dao.connexions.ConnexionMySQL;
import com.sw.dao.requetesDB.RequetesDB;
import com.sw.dao.requetesDB.RequetesMySQL;
import com.sw.exceptions.ExceptionDB;

import java.sql.*;
import java.util.*;
import java.util.Date;


/**
 *  Implémentation concrète de DAOUser pour gérer les opérations spécifiques à la base de données MySQL pour les utilisateurs.
 */
public class DAOUserMySQL extends DAOUser {

    /**
     * Default constructor
     */
    public DAOUserMySQL() {
        super();
        this.requetesDB = new RequetesMySQL();
    }

    /**
     * Méthode pour créer un utilisateur
     * @param pseudo String, le pseudo de l'utilisateur
     * @param mail String, l'email de l'utilisateur
     * @param motDePasse String, le mot de passe de l'utilisateur
     * @param dateNaissance Date, la date de naissance de l'utilisateur
     * @return User, l'utilisateur créé
     * @throws Exception si problème pendant la création du user
     */
    @Override
    public User createUser(String pseudo, String mail, String motDePasse, Date dateNaissance) throws Exception {
        //TODO
        System.out.println("TODO");
        return null;
    }

    /**
     * Méthode pour récupérer un utilisateur par son mail
     * @param mail String, le mail de l'utilisateur
     * @return User, l'utilisateur récupéré
     * @throws Exception si problème pendant la récupération du user
     */

    @Override
    public User getUserByMail(String mail) throws Exception {
        String table = "users";
        String[] columns = {"id", "mail", "pseudo", "motDePasse", "dateNaissance"};
        String[] whereColumns = {"mail"};
        Object[] whereValues = {mail};

        System.out.println("Je suis dans la méthode getUserByMail de DAOUserMySQL");

        try (ResultSet rs = ((RequetesMySQL)requetesDB).selectWhere(table, columns, whereColumns, whereValues)) {
            if (rs.next()) {
                int id = rs.getInt("id");
                String pseudo = rs.getString("pseudo");
                String email = rs.getString("mail");
                String motDePasse = rs.getString("motDePasse");
                Date dateNaissance = rs.getDate("dateNaissance");

                User user = new User(pseudo, email, motDePasse, dateNaissance);
                user.setId(id);
                return user;
            }
            return null; // Aucun utilisateur trouvé
        } catch (SQLException e) {
            throw new Exception("Erreur lors de la récupération de l'utilisateur par mail", e);
        }
    }



    public List<User> getAllUsers() throws Exception {
        System.out.println("Je suis dans la méthode getAllUsers de DAOUserMySQL");
        List<User> users = new ArrayList<>();
        try {
            //Appel de la méthode selectAll de RequetesMySQL
            ResultSet rs = ((RequetesMySQL) requetesDB).selectAll("user");
            // Parcourir le ResultSet et construire la liste des utilisateurs
            while (rs.next()) {
                int id = rs.getInt("id");
                String pseudo = rs.getString("pseudo");
                String email = rs.getString("mail");
                String motDePasse = rs.getString("motDePasse");
                Date dateNaissance = rs.getDate("dateNaissance");

                // Créer un nouvel utilisateur et l'ajouter à la liste
                User user = new User(pseudo, email, motDePasse, dateNaissance);
                user.setId(id); // Assurez-vous que la classe User a une méthode setId
                users.add(user);
                System.out.println("ID : " + id + " Pseudo : " + pseudo + " Mail : " + email + " Mot de passe : " + motDePasse + " Date de naissance : " + dateNaissance);

            }
        } catch (Exception e) {
            throw new Exception("Erreur lors de la récupération de l'utilisateur par mail", e);
        }
        if (users.isEmpty()) {
            return null;
        } else {
            for (User user : users) {
                System.out.println("user trouvé");
                System.out.println("ID : " + user.getId() + " Pseudo : " + user.getPseudo() + " Mail : " + user.getMail() + " Mot de passe : " + user.getMotDePasse() + " Date de naissance : " + user.getDateNaissance());
            }
        }
        return users;
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