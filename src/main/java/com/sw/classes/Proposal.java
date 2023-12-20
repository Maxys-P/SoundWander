package com.sw.classes;

import com.sw.facades.FacadeProposal;
import com.sw.facades.FacadeUser;

public class Proposal {
    /**
     * La facade de la proposal pour accéder aux méthodes de la facade
     */
    private FacadeProposal facadeProposal;
    private int id;
    //private Music music;
    //private Artist artist;
    private String country;
    private String description;

    /**
     * Default constructor
     */
    public Proposal(int id, String country, String description) {
        this.id = id;
        this.country = country;
        this.description = description;
    }

    //Getters :

    /**
     * Getter de la facade de la proposal
     * @return la facade de la proposal
     */
    public FacadeProposal getFacadeProposal() {
        return facadeProposal;
    }

    /**
     * Getter de l'id de la proposal
     * @return l'id de la proposal
     */
    public int getId() {return id;}
    /**
     * Getter du country de la proposal
     * @return le country de la proposal
     */
    public String getCountry() {return country;}
    /**
     * Getter de la description de la proposal
     * @return la description de la proposal
     */
    public String getDescription() {return description;}

    //Setters :

    /**
     * Setter de la facade de la proposal
     * @param facadeProposal la facade de la proposal
     */
    public void setFacadeProposal(FacadeProposal facadeProposal) {
        this.facadeProposal = facadeProposal;
    }
    /**
     * Setter de l'id de la proposal
     * @param id l'id de la proposal
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Setter du country de la proposal
     * @param country le country de la proposal
     */
    public void setCountry(String country) {
        this.country = country;
    }
    /**
     * Setter de la description de la proposal
     * @param description la description de la proposal
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
