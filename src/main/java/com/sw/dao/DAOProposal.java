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

    public abstract Proposal createProposal(String country, String description, Music music, User artist) throws Exception;

}
