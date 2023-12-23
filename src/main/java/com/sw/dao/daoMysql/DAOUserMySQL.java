package com.sw.dao.daoMysql;

import com.sw.classes.User;
import com.sw.dao.DAOUser;
import com.sw.dao.boiteAOutils.MapperResultSet;
import com.sw.dao.requetesDB.RequetesMySQL;

import java.time.LocalDate;
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
    public User createUser(String pseudo, String mail, String motDePasse, LocalDate dateNaissance) throws Exception {
        Map<String, Object> userData = new LinkedHashMap<>();
        userData.put("pseudo", pseudo);
        userData.put("mail", mail);
        userData.put("motDePasse", motDePasse);

        // Convertir LocalDate en java.sql.Date
        if (dateNaissance != null) {
            userData.put("dateNaissance", java.sql.Date.valueOf(dateNaissance));
        } else {
            userData.put("dateNaissance", null); // ou gérer différemment si la date de naissance est obligatoire
        }

        try {
            int id = ((RequetesMySQL) requetesDB).create(table, userData);
            return getUserById(id);
        } catch (Exception e) {
            throw new Exception("Erreur lors de la création du user", e);
        }
    }

    /**
     * Méthode pour récupérer tous les users
     * @return List<User>, la liste des utilisateurs
     * @throws Exception si problème pendant la récupération des users
     */
    @Override
    public List<User> getAllUsers() throws Exception {
        List<User> users = new ArrayList<>();
        try {
            //Appel de la méthode selectAll de RequetesMySQL
            MapperResultSet userdata = ((RequetesMySQL) requetesDB).selectAll(table);
            // Parcourir le MapperResultSet et construire la liste des users
            List<Map<String, Object>> listData = userdata.getListData();
            for (Map<String, Object> row : listData) {
                try {
                    Integer id = (Integer) row.get("id");
                    String pseudo = (String) row.get("pseudo");
                    String mail = (String) row.get("mail");
                    String motDePasse = (String) row.get("motDePasse");
                    LocalDate dateNaissance = null;

                    if (row.get("dateNaissance") != null) {
                        dateNaissance = ((java.sql.Date) row.get("dateNaissance")).toLocalDate();
                    }

                    User user = new User(id, pseudo, mail, motDePasse, dateNaissance);
                    users.add(user);
                } catch (Exception e) {
                    System.out.println("Erreur lors de la récupération d'un utilisateur : " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            throw new Exception("Erreur lors de la récupération de l'utilisateur par mail", e);
        }
        return users;
    }


    /**
     * Méthode pour récupérer un utilisateur par son mail
     * @param mail String, le mail de l'utilisateur
     * @return User, l'utilisateur récupéré
     * @throws Exception si problème pendant la récupération du user
     */
    @Override
    public User getUserByMail(String mail) throws Exception {
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("mail", mail);
        //on ajoute ici d'autres conditions si besoin pour vos usecases
        try {
            // Appel de selectWhere
            MapperResultSet userData = ((RequetesMySQL) requetesDB).selectWhere(table, conditions);
            if (!userData.getListData().isEmpty()) {
                Map<String, Object> userDetails = userData.getListData().getFirst();

                Integer id = (Integer) userDetails.get("id");
                String pseudo = (String) userDetails.get("pseudo");
                String motDePasse = (String) userDetails.get("motDePasse");
                LocalDate dateNaissance = null;
                if (userDetails.get("dateNaissance") != null) {
                    dateNaissance = ((java.sql.Date) userDetails.get("dateNaissance")).toLocalDate();
                }

                return new User(id, pseudo, mail, motDePasse, dateNaissance);

            }
            else {
                System.out.println("Pb quand on appelle selectWhere");
            }
            return null;
        } catch (Exception e) {
            throw new Exception("Erreur lors de la récupération de l'utilisateur par mail", e);
        }
    }


    /**
     * Méthode pour récupérer un utilisateur par son id
     * @param id int, l'id de l'utilisateur
     * @return User, l'utilisateur récupéré
     * @throws Exception si problème pendant la récupération du user
     */
    @Override
    public User getUserById(int id) throws Exception {
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("id", id);
        // Ajoutez d'autres conditions si nécessaire

        try {
            MapperResultSet userData = ((RequetesMySQL) requetesDB).selectWhere(table, conditions);
            if (!userData.getListData().isEmpty()) {
                Map<String, Object> userDetails = userData.getListData().getFirst();

                String pseudo = (String) userDetails.get("pseudo");
                String mail = (String) userDetails.get("mail");
                String motDePasse = (String) userDetails.get("motDePasse");
                LocalDate dateNaissance = null;
                if (userDetails.get("dateNaissance") != null) {
                    dateNaissance = ((java.sql.Date) userDetails.get("dateNaissance")).toLocalDate();
                }

                return new User(id, pseudo, mail, motDePasse, dateNaissance);
            }
            return null;
        } catch (Exception e) {
            throw new Exception("Erreur lors de la récupération de l'utilisateur par id", e);
        }
    }


    /**
     * Méthode qui met à jour les informations d'un user et retourne le user mis à jour.
     * @param id L'identifiant du user à mettre à jour.
     * @param updates Map contenant les champs à mettre à jour et leurs nouvelles valeurs.
     * @return User L'utilisateur mis à jour.
     * @throws Exception En cas d'erreur lors de la mise à jour ou de récupération des données.
     */
    public User updateUser(int id, Map<String, Object> updates) throws Exception {
        if (updates == null || updates.isEmpty()) {
            throw new IllegalArgumentException("Aucune mise à jour fournie.");
        }

        Map<String, Object> whereConditions = new HashMap<>();
        whereConditions.put("id", id);

        try {
            ((RequetesMySQL) requetesDB).update(table, updates, whereConditions);
            return getUserById(id);
        } catch (Exception e) {
            throw new Exception("Erreur lors de la mise à jour de l'utilisateur", e);
        }
    }


    /**
     * Méthode qui supprime un user de la base de donnée
     * @param id int, l'id du user
     * @throws Exception si une erreur survient lors de la suppression du user
     */
    public boolean deleteUser(int id) throws Exception {
        try {
            // Création de la condition WHERE avec l'ID
            Map<String, Object> whereConditions = new HashMap<>();
            whereConditions.put("id", id);

            // Appel de la méthode générique delete de RequetesMySQL
            int affectedRows = ((RequetesMySQL) requetesDB).delete(table, whereConditions);

            // Vérifier si la suppression a affecté au moins une ligne
            return affectedRows > 0;
        } catch (Exception e) {
            // Gérer ou propager d'autres types d'exceptions
            throw new Exception("Erreur inattendue lors de la suppression de l'utilisateur", e);
        }
    }


}