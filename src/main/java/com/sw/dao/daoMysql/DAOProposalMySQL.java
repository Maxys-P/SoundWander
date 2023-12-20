package com.sw.dao.daoMysql;

import com.sw.dao.requetesDB.RequetesMySQL;
import com.sw.classes.Proposal;
import com.sw.dao.DAOProposal;

public class DAOProposalMySQL extends DAOProposal {
    public DAOProposalMySQL() {
        super();
        this.requetesDB = new RequetesMySQL();
    }
}
