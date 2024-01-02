package com.sw.classes;

import com.sw.facades.FacadeUser;
import javafx.scene.image.Image;

import java.time.LocalDate;
import java.util.*;

/**
 * Classe représentant un user
 */
public class User {

    private int id;

    private String pseudo;

    private String mail;

    private String motDePasse;

    private LocalDate dateNaissance;

    private String photo;

    //il faudra créer une table user_music pour la playlist privée avec un truc du genre
    //pas ici bien sûr hein
    /*
    CREATE TABLE IF NOT EXISTS user_music (
        userId INT,
        musicId INT,
        PRIMARY KEY (userId, musicId),
        FOREIGN KEY (userId) REFERENCES user(id),
        FOREIGN KEY (musicId) REFERENCES music(id)
    );
    */
    private List<Music> privatePlaylist;

    private String role;



    //public List<Notification> notifs;

    //public <List<Discussion> discussions;

    /**
     * Default constructor
     */
    public User(int id, String pseudo, String mail, String motDePasse, LocalDate dateNaissance, String photo, String role) {
        this.id = id;
        this.pseudo = pseudo;
        this.mail = mail;
        this.motDePasse = motDePasse;
        this.dateNaissance = dateNaissance;
        this.photo = photo;
        this.role = role;
        this.privatePlaylist = new ArrayList<>();
    }

    /**
     * Constructeur avec les données du user
     */
    public User(int id, String pseudo, String mail, String motDePasse, LocalDate dateNaissance, String photo, String role, List<Music> privatePlaylist) {
        this.id = id;
        this.pseudo = pseudo;
        this.mail = mail;
        this.motDePasse = motDePasse;
        this.dateNaissance = dateNaissance;
        this.photo = photo;
        this.role = role;
        this.privatePlaylist = privatePlaylist;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id + '\'' +
                ", pseudo='" + pseudo + '\'' +
                ", mail='" + mail + '\'' +
                ", motDePasse='" + motDePasse + '\'' +
                ", dateNaissance=" + dateNaissance + '\'' +
                ", photo=" + photo + '\'' +
                ", role=" + role + '\'' +
                ", privatePlaylist=" + privatePlaylist +
                '}';
    }


    //Getters :


    /**
     * Getter de l'id du user
     * @return l'id du user
     */
    public int getId() {
        return id;
    }

    /**
     * Getter du pseudo du user
     * @return le pseudo du user
     */
    public String getPseudo() {
        return pseudo;
    }

    /**
     * Getter de l'email du user
     * @return l'email du user
     */
    public String getMail() {
        return mail;
    }

    /**
     * Getter du mot de passe du user
     * @return le mot de passe du user
     */
    public String getMotDePasse() {
        return motDePasse;
    }

    /**
     * Getter de la date de naissance du user
     * @return la date de naissance du user
     */
    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    /**
     * Getter de la photo du user
     * @return la photo du user
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * Getter de la playlist privée du user
     * @return la playlist privée du user
     */
    public List<Music> getPrivatePlaylist() {
        return privatePlaylist;
    }

    /**
     * Getter du rôle du user
     * @return le rôle du user
     */
    public String getRole() {
        return role;
    }

    //Setters :

    /**
     * Setter de l'id du user
     * @param id l'id du user
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Setter du pseudo du user
     * @param pseudo le pseudo du user
     */
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    /**
     * Setter de l'email du user
     * @param mail l'email du user
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * Setter du mot de passe du user
     * @param motDePasse le mot de passe du user
     */
    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    /**
     * Setter de la date de naissance du user
     * @param dateNaissance la date de naissance du user
     */
    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    /**
     * Setter de la photo du user
     * @param photo la photo du user
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     * Setter de la playlist privée du user
     * @param privatePlaylist la playlist privée du user
     */
    public void setPrivatePlaylist(List<Music> privatePlaylist) {
        this.privatePlaylist = privatePlaylist;
    }

    /**
     * Setter du rôle du user
     * @param role le rôle du user
     */
    public void setRole(String role) {
        this.role = role;
    }

}