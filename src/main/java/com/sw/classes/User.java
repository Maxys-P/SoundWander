package com.sw.classes;

import com.sw.facades.FacadeUser;

import java.util.*;

/**
 *
 */
public class User {

    /**
     * La facade du user pour accéder aux méthodes de la facade
     */
    private FacadeUser facadeUser;

    private int id;
    private String pseudo;

    private String mail;

    private String motDePasse;

    private Date dateNaissance;

    //public List<Musique> liste_perso;

    /**
     *
     */
    //public List<Notification> notifs;

    /**
     *
     */
    //public <List<Discussion> discussions;

    /**
     *
     */
    //public Image photo_profil;


    /**
     * Default constructor
     */
    public User(String pseudo, String mail, String motDePasse, Date dateNaissance) {
        this.pseudo = pseudo;
        this.mail = mail;
        this.motDePasse = motDePasse;
        this.dateNaissance = dateNaissance;
    }

    @Override
    public String toString() {
        return "User{" +
                "pseudo='" + pseudo + '\'' +
                ", mail='" + mail + '\'' +
                ", motDePasse='" + motDePasse + '\'' +
                ", dateNaissance=" + dateNaissance + '\'' +
                '}';
    }

    //Getters :

    /**
     * Getter de la facade du user
     * @return la facade du user
     */
    public FacadeUser getFacadeUser() {
        return facadeUser;
    }

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
    public Date getDateNaissance() {
        return dateNaissance;
    }

    //Setters :


    /**
     * Setter de la facade du user
     * @param facadeUser la facade du user
     */
    public void setFacadeUser(FacadeUser facadeUser) {
        this.facadeUser = facadeUser;
    }

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
    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }


}