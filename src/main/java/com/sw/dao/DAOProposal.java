package com.sw.dao;

import com.sw.classes.Music;
import com.sw.classes.Proposal;
import com.sw.classes.User;

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
     * @param music String, la music de la proposition
     * @param artist Date, l'auteur de la proposition
     * @return Proposal, la proposition créé
     * @throws Exception si problème pendant la création de la proposal
     */
    public abstract Proposal createProposal(String country, String description, Music music, User artist) throws Exception;

}
