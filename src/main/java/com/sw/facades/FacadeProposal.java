package com.sw.facades;

public class FacadeProposal extends Facade{
    /**
     * Instance de la facade pour le singleton
     */
    private static FacadeProposal instance = null;

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
