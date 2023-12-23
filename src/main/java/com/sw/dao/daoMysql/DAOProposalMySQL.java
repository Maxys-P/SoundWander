package com.sw.dao.daoMysql;

import com.sw.classes.Music;
import com.sw.classes.User;
import com.sw.dao.requetesDB.RequetesMySQL;
import com.sw.classes.Proposal;
import com.sw.dao.DAOProposal;

import java.util.Date;

public class DAOProposalMySQL extends DAOProposal {
    /**
     * Default constructor
     */
    public DAOProposalMySQL() {
        super();
        this.requetesDB = new RequetesMySQL();
    }

    /**
     * Méthode pour créer une proposal
     * @param country String, le country de la proposal
     * @param description String, la description de la proposal
     * @param music Music, la music de la proposal
     * @param artist User, l'artist de la proposal
     * @return Proposal, la proposal créé
     * @throws Exception si problème pendant la création de la proposal
     */
    @Override
    public Proposal createProposal(String country, String description, Music music, User artist) throws Exception {
        // Préparer les valeurs à insérer, en respectant le type des colonnes de la table proposal
        Object[] values = {country, description, music.getId(), artist.getId()};

        // Créer une instance de RequetesMySQL
        RequetesMySQL requetePourCreer = new RequetesMySQL();

        // Appeler la méthode create de RequetesMySQL
        // Pour l'instant, cette méthode ne fait rien, donc nous simulerons l'appel sans effectuer d'insertion réelle
        try {
            requetePourCreer.create("proposal", new String[]{"country", "description", "music", "artist"}, values);
        } catch (Exception e) {
            System.out.println("Erreur lors de la création de la proposition : " + e.getMessage());
            throw e;
        }

        Proposal proposal_created = new Proposal(country, description, music, artist);
        return proposal_created;
    }
}