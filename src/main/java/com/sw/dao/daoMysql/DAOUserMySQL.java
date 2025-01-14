package com.sw.dao.daoMysql;

import com.sw.classes.*;
import com.sw.commons.SearchCriteria;
import com.sw.dao.DAOUser;
import com.sw.dao.boiteAOutils.MapperResultSet;
import com.sw.dao.requetesDB.RequetesMySQL;
import com.sw.dao.boiteAOutils.AuthentificationManager;
import com.sw.exceptions.ExceptionDB;
import com.sw.facades.FacadeMusic;

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


    private FacadeMusic facadeMusic = FacadeMusic.getInstance();



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

        // Hacher le mot de passe avant de l'insérer
        String hashedPassword = AuthentificationManager.hashPassword(motDePasse);
        userData.put("motDePasse", hashedPassword);

        // Convertir LocalDate en java.sql.Date
        if (dateNaissance != null) {
            userData.put("dateNaissance", java.sql.Date.valueOf(dateNaissance));
        } else {
            userData.put("dateNaissance", null); // ou gérer différemment si la date de naissance est obligatoire
        }

        userData.put("role", "user");

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

                    String photo = (String) row.get("photo");

                    String role = (String) row.get("role");

                    User user = new User(id, pseudo, mail, motDePasse, dateNaissance, photo, role);
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
     * Méthode qui retourne les users qui correspondent aux critères de recherche
     * @param conditionsWhere Map, les critères de recherche
     * @return List<User>, la liste des users
     * @throws Exception si une erreur survient lors de la récupération des users
     */
    /*
    //jsp si ça fonctionne j'ai pas testé mais un truc du genre peut etre pratique
    public List<User> getAllUsersWhere(Map<String, Object> conditionsWhere) throws Exception {
        List<User> users = new ArrayList<>();
        try {
            //Appel de la méthode selectAll de RequetesMySQL
            MapperResultSet userdata = ((RequetesMySQL) requetesDB).selectWhere(table, conditionsWhere);
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

                    String photo = null;

                    String role = (String) row.get("role");

                    User user = new User(id, pseudo, mail, motDePasse, dateNaissance, photo, role);
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
    */
    
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

        try {
            MapperResultSet userData = ((RequetesMySQL) requetesDB).selectWhere(table, conditions);
            if (!userData.getListData().isEmpty()) {
                Map<String, Object> userDetails = userData.getListData().getFirst();
                User user = setUserRole(userDetails);
                user.setPrivatePlaylist(getPrivatePlaylist(user.getId()));
                return user;
            }
            else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
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

        try {
            MapperResultSet userData = ((RequetesMySQL) requetesDB).selectWhere(table, conditions);
            if (!userData.getListData().isEmpty()) {
                Map<String, Object> userDetails = userData.getListData().getFirst();
                User user = setUserRole(userDetails);
                user.setPrivatePlaylist(getPrivatePlaylist(id));
                return user;
            } else {
                throw new ExceptionDB("Aucun utilisateur trouvé avec l'id " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
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
    @Override
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
     * Met à jour le pseudo de l'utilisateur.
     * @param idUser int, l'id de l'utilisateur
     * @param newPseudo String, le nouveau pseudo de l'utilisateur
     * @throws ExceptionDB en cas d'échec de la mise à jour
     */
    @Override
    public void updateUserPseudo(int idUser, String newPseudo) throws ExceptionDB {
        try {
            Map<String, Object> updates = new HashMap<>();
            updates.put("pseudo", newPseudo);
            updateUser(idUser, updates);
        } catch (Exception e) {
            throw new ExceptionDB("Erreur lors de la mise à jour du pseudo", e);
        }
    }

    /**
     * Met à jour le mail de l'utilisateur.
     * @param idUser int, l'id de l'utilisateur
     * @param newMail String, le nouveau mail de l'utilisateur
     * @throws ExceptionDB en cas d'échec de la mise à jour
     */
    @Override
    public void updateUserMail(int idUser, String newMail) throws ExceptionDB {
        try {
            Map<String, Object> updates = new HashMap<>();
            updates.put("mail", newMail);
            updateUser(idUser, updates);
        } catch (Exception e) {
            throw new ExceptionDB("Erreur lors de la mise à jour du mail", e);
        }
    }

    /**
     * Met à jour le mot de passe de l'utilisateur.
     * @param idUser int, l'id de l'utilisateur
     * @param newMotDePasse String, le nouveau mot de passe de l'utilisateur
     * @throws ExceptionDB en cas d'échec de la mise à jour
     */
    @Override
    public void updateUserMotDePasse(int idUser, String newMotDePasse) throws ExceptionDB {
        try {
            Map<String, Object> updates = new HashMap<>();
            updates.put("motDePasse", newMotDePasse);
            updateUser(idUser, updates);
        } catch (Exception e) {
            throw new ExceptionDB("Erreur lors de la mise à jour du mot de passe", e);
        }
    }

    /**
     * Met à jour la date de naissance de l'utilisateur.
     * @param idUser int, l'id de l'utilisateur
     * @param newDateNaissance LocalDate, la nouvelle date de naissance de l'utilisateur
     * @throws ExceptionDB en cas d'échec de la mise à jour
     */
    @Override
    public void updateUserDateNaissance(int idUser, LocalDate newDateNaissance) throws ExceptionDB {
        try {
            Map<String, Object> updates = new HashMap<>();
            updates.put("dateNaissance", newDateNaissance);
            updateUser(idUser, updates);
        } catch (Exception e) {
            throw new ExceptionDB("Erreur lors de la mise à jour de la date de naissance", e);
        }
    }

    /**
     * Met à jour la photo de l'utilisateur.
     * @param idUser int, l'id de l'utilisateur
     * @param newPhoto String, la nouvelle photo de l'utilisateur
     * @throws ExceptionDB en cas d'échec de la mise à jour
     */
    @Override
    public void updateUserPhoto(int idUser, String newPhoto) throws ExceptionDB {
        try {
            Map<String, Object> updates = new HashMap<>();
            updates.put("photo", newPhoto);
            updateUser(idUser, updates);
        } catch (Exception e) {
            throw new ExceptionDB("Erreur lors de la mise à jour de la photo", e);
        }
    }

    /**
     * Méthode qui supprime un user de la base de donnée
     * @param id int, l'id du user
     * @throws Exception si une erreur survient lors de la suppression du user
     */
    @Override
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

    /**
     * Méthode qui permet de choisir le rôle d'un user (user, artist, musicalExpert, admin)
     * @param userData ResultSet, les données du user issues de la requête
     * @return User, l'utilisateur avec son rôle
     */
    @Override
    protected User setUserRole(Map<String, Object> userData) throws Exception {
        Integer id = (Integer) userData.get("id");
        String pseudo = (String) userData.get("pseudo");
        String mail = (String) userData.get("mail");
        String motDePasse = (String) userData.get("motDePasse");
        LocalDate dateNaissance = userData.get("dateNaissance") != null ? ((java.sql.Date) userData.get("dateNaissance")).toLocalDate() : null;
        String photo = (String) userData.get("photo");
        String role = (String) userData.get("role");

        switch (role) {
            case "admin":
                return new Admin(id, pseudo, mail, motDePasse, dateNaissance, photo, role);
            case "musicalExpert":
                return new MusicalExpert(id, pseudo, mail, motDePasse, dateNaissance, photo, role);
            case "artist":
                return new Artist(id, pseudo, mail, motDePasse, dateNaissance, photo, role);
            default:
                return new User(id, pseudo, mail, motDePasse, dateNaissance, photo, role);
        }
    }


    /**
     * Méthode qui permet à un user de devenir artiste
     * @param idUser int, l'id du user
     * @return Artist, l'artiste créé
     * @throws Exception si une erreur survient lors de la suppression du user
     */
    @Override
    public Artist userBecomeArtist(int idUser) throws Exception {
        try {
            Map<String, Object> updates = new HashMap<>();
            updates.put("role", "artist");
            Map<String, Object> whereConditions = new HashMap<>();
            whereConditions.put("id", idUser);

            ((RequetesMySQL) requetesDB).update(table, updates, whereConditions);

            return (Artist) getUserById(idUser);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionDB("Erreur lors du passage de l'utilisateur en artiste");
        }
    }

    /**
     * Méthode qui permet à un user de devenir expert musical
     * @param idUser int, l'id de l'utilisateur
     * @return MusicalExpert, l'expert musical
     * @throws Exception si problème pendant la création de l'expert musical
     */
    @Override
    public MusicalExpert userBecomeMusicalExpert(int idUser) throws Exception {
        try {
            Map<String, Object> updates = new HashMap<>();
            updates.put("role", "musicalExpert");
            Map<String, Object> whereConditions = new HashMap<>();
            whereConditions.put("id", idUser);

            ((RequetesMySQL) requetesDB).update(table, updates, whereConditions);

            return (MusicalExpert) getUserById(idUser);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionDB("Erreur lors du passage de l'utilisateur en expert musical");
        }
    }

    /**
     * Méthode qui permet à un user de devenir admin
     * @param idUser int, l'id de l'utilisateur
     * @return Admin, l'admin
     * @throws Exception si problème pendant la création de l'admin
     */
    @Override
    public Admin userBecomeAdmin(int idUser) throws Exception {
        try {
            Map<String, Object> updates = new HashMap<>();
            updates.put("role", "admin");
            Map<String, Object> whereConditions = new HashMap<>();
            whereConditions.put("id", idUser);

            ((RequetesMySQL) requetesDB).update(table, updates, whereConditions);

            return (Admin) getUserById(idUser);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionDB("Erreur lors du passage de l'utilisateur en admin");
        }
    }

  /**
     * Méthode pour récupérer l'artiste qui correspond à la recherche.
     * @param criteria SearchCriteria, le critère entré dans la barre de recherche
     * @return l'Artist qui correspond à la recherche
     * @throws ExceptionDB en cas d'échec
     */
  @Override
  public List<Object> search(SearchCriteria criteria) {
      List<Object> matchingArtists = new ArrayList<>();
      Map<String, Object> whereConditions = new HashMap<>();
      whereConditions.put("pseudo", criteria.getSearchTerm());

      try {
          // Recherchez d'abord les artistes par leur pseudo
          MapperResultSet mapperResultSet = ((RequetesMySQL) requetesDB).selectWhere("user", whereConditions);
          for (Map<String, Object> rowData : mapperResultSet.getListData()) {
              int id = (int) rowData.get("id");
              String pseudo = (String) rowData.get("pseudo");
              String mail = (String) rowData.get("mail");
              String motDePasse = (String) rowData.get("motDePasse");
              LocalDate dateNaissance = ((java.sql.Date) rowData.get("dateNaissance")).toLocalDate();
              String photo = (String) rowData.get("photo");
              String role = (String) rowData.get("role");

              // Récupérez également la liste de musiques de l'artiste ici

               // Remplacez cela par votre logique pour récupérer les musiques

              // Créez l'objet Artist avec les données récupérées, y compris les musiques
              Artist artist = new Artist(id, pseudo, mail, motDePasse, dateNaissance, photo, role);
              List<Music> musics = artist.getMusics();
              artist.setMusics(musics);
              System.out.println("artist du dao" + artist);

              // Ajoutez l'artiste à la liste
              matchingArtists.add(artist);
          }
      } catch (ExceptionDB e) {
          e.printStackTrace();
      }
      return matchingArtists;
  }





    public List<Music> addMusicToPrivatePlaylist(User user, Music music) throws Exception {
        // vérification
        System.out.println("[DAOMySQL] Vérification si la musique est déjà dans la playlist privée");
        try {
            Map<String, Object> whereConditions = new HashMap<>();
            whereConditions.put("userId", user.getId());
            whereConditions.put("musicId", music.getId());

            MapperResultSet resultSet = ((RequetesMySQL) requetesDB).selectWhere("user_music", whereConditions);
            if (!resultSet.getListData().isEmpty()) { // Si la musique est déjà dans la playlist
                System.out.println("[DAOMySQL] La musique est déjà dans la playlist, ajout non effectué.");
                return null;
            }
            System.out.println("[DAOMySQL] La musique n'est pas dans la playlist, ajout en cours ...");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionDB("[DAOMySQL] Erreur lors de la vérification : " + e.getMessage());
        }

        // ajout
        try {
            Map<String, Object> data = new HashMap<>();
            data.put("userId", user.getId());
            data.put("musicId", music.getId());
            ((RequetesMySQL) requetesDB).createNoReturn("user_music", data);

            System.out.println("[DAOMySQL] Musique ajoutée à la playlist privée");
            return getPrivatePlaylist(user.getId());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionDB("[DAOMySQL] Erreur lors de l'ajout de la musique à la playlist privée : " + e.getMessage());
        }
    }

    @Override
    public List<Music> deleteMusicInPrivatePlaylist(User user, Music music) throws Exception {
        try {
            System.out.println("[DAOMySQL] Suppression de la musique de la playlist privée");
            Map<String, Object> whereConditions = new HashMap<>();
            whereConditions.put("userId", user.getId());
            whereConditions.put("musicId", music.getId());

            int rowsAffected = ((RequetesMySQL) requetesDB).delete("user_music", whereConditions);
            if (rowsAffected > 0) {
                System.out.println("[DAOMySQL] Musique supprimée de la playlist privée");
                return getPrivatePlaylist(user.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionDB("[DAOMySQL] Erreur lors de la suppression de la musique de la playlist privée : " + e.getMessage());
        }
        return null;
    }

    public List<Music> getPrivatePlaylist(int userId) throws Exception {
        List<Music> playlist = new ArrayList<>();
        Map<String, Object> whereConditions = new HashMap<>();
        whereConditions.put("userId", userId);

        MapperResultSet userData = ((RequetesMySQL) requetesDB).selectWithJoin("user_music", List.of("music"), List.of("user_music.musicId = music.id"), whereConditions);

        List<Map<String, Object>> listData = userData.getListData();
        for (Map<String, Object> row : listData) {
            Integer musicId = (Integer) row.get("musicId");
            Music music = facadeMusic.getMusicById(musicId);
            if (music != null) {
                playlist.add(music);
            }
        }
        return playlist;
    }


    /**
     * Méthode qui permet de savoir si un artiste est abonné
     * @param id int, l'id de l'artiste
     * @return boolean, true si l'artiste est abonné, false sinon
     */
    @Override
    public boolean isArtistSubscribed(int id) throws Exception {
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("userId", id);

        try {
            MapperResultSet paymentData = ((RequetesMySQL) requetesDB).selectWhere("payment", conditions);
            for (Map<String, Object> payment : paymentData.getListData()) {
                Object cancellationDate = payment.get("cancellationDate");
                if (cancellationDate == null || ((java.sql.Date)cancellationDate).toLocalDate().isAfter(LocalDate.now())) {
                    return true;
                }
            }
            return false;
        } catch (ExceptionDB e) {
            e.printStackTrace();
            throw new ExceptionDB("Erreur lors de la vérification de l'abonnement de l'artiste", e);
        }
    }

    /**
     * Méthode pour récupérer un utilisateur par son pseudo
     * @param pseudo String, le pseudo de l'utilisateur
     * @return User, l'utilisateur récupéré
     * @throws Exception si problème pendant la récupération du user
     */
    @Override
    public User getUserByPseudo(String pseudo) throws Exception {
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("pseudo", pseudo);

        try {
            MapperResultSet userData = ((RequetesMySQL) requetesDB).selectWhere(table, conditions);
            if (!userData.getListData().isEmpty()) {
                Map<String, Object> userDetails = userData.getListData().getFirst();
                User user = setUserRole(userDetails);
                user.setPrivatePlaylist(getPrivatePlaylist(user.getId()));
                return user;
            } else {
                throw new ExceptionDB("Aucun utilisateur trouvé avec le pseudo " + pseudo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Erreur lors de la récupération de l'utilisateur par pseudo", e);
        }
    }


}