package com.sw.dao;

import com.sw.classes.*;

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
     * Méthode pour créer un utilisateur
     * @param pseudo        String, le pseudo de l'utilisateur
     * @param mail          String, l'email de l'utilisateur
     * @param motDePasse    String, le mot de passe de l'utilisateur
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
     * Méthode qui met à jour les informations d'un user et retourne le user mis à jour.
     * @param id L'identifiant du user à mettre à jour.
     * @param updates Map contenant les champs à mettre à jour et leurs nouvelles valeurs.
     * @return User L'utilisateur mis à jour.
     * @throws Exception En cas d'erreur lors de la mise à jour ou de récupération des données.
     */
    public abstract User updateUser(int id, Map<String, Object> updates) throws Exception;


        /**
         * Met à jour le pseudo de l'utilisateur.
         * @param idUser int, l'id de l'utilisateur
         * @param newPseudo String, le nouveau pseudo de l'utilisateur
         * @throws Exception si problème pendant la mise à jour du pseudo
         */
    public abstract void updateUserPseudo(int idUser, String newPseudo) throws Exception;

    /**
     * Met à jour le mail de l'utilisateur.
     * @param idUser int, l'id de l'utilisateur
     * @param newMail String, le nouveau mail de l'utilisateur
     * @throws Exception si problème pendant la mise à jour du mail
     */
    public abstract void updateUserMail(int idUser, String newMail) throws Exception;

    /**
     * Met à jour le mot de passe de l'utilisateur.
     * @param idUser int, l'id de l'utilisateur
     * @param newMotDePasse String, le nouveau mot de passe de l'utilisateur
     * @throws Exception si problème pendant la mise à jour du mot de passe
     */
    public abstract void updateUserMotDePasse(int idUser, String newMotDePasse) throws Exception;

    /**
     * Met à jour la date de naissance de l'utilisateur.
     * @param idUser int, l'id de l'utilisateur
     * @param newDateNaissance LocalDate, la nouvelle date de naissance de l'utilisateur
     * @throws Exception si problème pendant la mise à jour de la date de naissance
     */
    public abstract void updateUserDateNaissance(int idUser, LocalDate newDateNaissance) throws Exception;

    /**
     * Met à jour la photo de l'utilisateur.
     * @param idUser int, l'id de l'utilisateur
     * @param newPhoto String, la nouvelle photo de l'utilisateur
     * @throws Exception si problème pendant la mise à jour de la photo
     */
    public abstract void updateUserPhoto(int idUser, String newPhoto) throws Exception;

    /**
     * Supprimer le user de la base de donnée
     * @param id int, l'id du user
     * @throws Exception si une erreur survient lors de la suppression du user
     */
    public abstract boolean deleteUser(int id) throws Exception;

    protected abstract User setUserRole(Map<String, Object> userData) throws Exception;

    /**
     * Méthode qui permet à un user de devenir artiste
     * @param idUser int, l'id de l'utilisateur
     * @return Artist, l'artiste créé
     * @throws Exception si une erreur survient lors de la création de l'artiste
     */
    public abstract Artist userBecomeArtist(int idUser) throws Exception;

    /**
     * Méthode qui permet à un user de devenir expert musical
     * @param idUser int, l'id de l'utilisateur
     * @return MusicalExpert, l'expert musical créé
     */
    public abstract MusicalExpert userBecomeMusicalExpert(int idUser) throws Exception;

    public abstract Admin userBecomeAdmin(int idUser) throws Exception;

    /**
     * Ajoute une musique à une playlist privée.
     *
     * @param user  int, l'ID de l'utilisateur propriétaire de la playlist.
     * @param music int, l'ID de la musique à ajouter.
     * @return
     * @throws Exception Si un problème survient lors de l'ajout.
     */
    public abstract List<Music> addMusicToPrivatePlaylist(User user, Music music) throws Exception;

    /**
     * Supprime une musique d'une playlist privée.
     *
     * @param user  int, l'ID de l'utilisateur propriétaire de la playlist.
     * @param music int, l'ID de la musique à supprimer.
     * @return
     * @throws Exception Si un problème survient lors de la suppression.
     */
    public abstract List<Music> deleteMusicInPrivatePlaylist(User user, Music music) throws Exception;

    /**
     * Méthode pour récupérer la playlist privée d'un user
     * @param idUser int, l'id de l'utilisateur
     * @return List<Music>, la playlist privée de l'utilisateur
     * @throws Exception si problème pendant la récupération de la playlist privée
     */
    public abstract List<Music> getPrivatePlaylist(int idUser) throws Exception;


}