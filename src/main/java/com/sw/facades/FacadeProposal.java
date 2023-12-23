package com.sw.facades;

import com.sw.dao.DAOProposal;
import com.sw.dao.DAOUser;

public class FacadeProposal extends Facade{
    /**
     * Instance de la facade pour le singleton
     */
    private static FacadeProposal instance = null;
    /**
     * DAO pour les propositions
     */
    protected DAOProposal daoProposal;
    /**
     * Getter de l'instance de la facade pour le singleton
     * @return FacadeProposal, l'instance de la facade
     */
    public static FacadeProposal getInstance() {
        if (instance == null) {
            instance = new FacadeProposal();
        }
        return instance;
    }
}
