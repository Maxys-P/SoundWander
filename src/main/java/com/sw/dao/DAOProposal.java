package com.sw.dao;

import com.sw.classes.Music;
import com.sw.classes.Proposal;
import com.sw.classes.User;

import java.util.List;

public abstract class DAOProposal extends DAO{
    /**
     * Default constructor
     */
    public DAOProposal() {
        super("proposal");
    }
    /**
     * Méthode pour créer une proposition
     * @param country String, le country de la proposition
     * @param description String, la description de la proposition
<<<<<<< HEAD
     * @param music String, la music de la proposition
     * @param artist Date, l'auteur de la proposition
     * @return Proposal, la proposition créé
     * @throws Exception si problème pendant la création de la proposal
=======
     * @param music Music, la music de la  proposition
     * @param artist User, l'artist de la proposition
     * @return Proposal, la proposition créé
     * @throws Exception si problème pendant la création de la proposition
>>>>>>> auriane
     */
    public abstract Proposal createProposal(String country, String description, Music music, User artist) throws Exception;

    /**
     * Méthode pour récupérer une proposition par son id
     * @param id int, l'id de la proposition
     * @return Proposal, la proposition récupérée
     * @throws Exception si problème pendant la récupération de la proposition
     */
    public abstract Proposal getProposalById(int id) throws Exception;

    /**
     * Méthode pour récupérer toutes les propositions
     * @return List<Proposal>, la liste des propositions
     * @throws Exception si problème pendant la récupération des propositions
     */
    public abstract List<Proposal> getAllProposals() throws Exception;

    /**
     * Méthode pour récupérer une proposition par son pays
     * @param country String, le country de la proposition
     * @return Proposal, la proposition récupérée
     * @throws Exception si problème pendant la récupération de la proposition
     */
    public abstract List<Proposal> getProposalByCountry(String country) throws Exception;

    /**
     * Méthode pour supprimer une proposition
     * @param id int, l'id de la proposition
     * @throws Exception si problème pendant la suppression de la proposition
     */
    public abstract boolean deleteProposal(int id) throws Exception;

    /**
     * Méthode pour accepter une proposition
     * @param id int, l'id de la proposition
     * @throws Exception si problème pendant la validation de la proposition
     */
    public abstract boolean acceptProposal(int id) throws Exception;

    /**
     * Méthode pour refuser une proposition
     * @param id int, l'id de la proposition
     * @throws Exception si problème pendant le rejet de la proposition
     */
    public abstract boolean refuseProposal(int id) throws Exception;

}
