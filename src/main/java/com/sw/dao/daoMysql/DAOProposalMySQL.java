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
        //TODO
        System.out.println("TODO");
        return null;
    }
}
