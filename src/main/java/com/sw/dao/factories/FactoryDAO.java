package com.sw.dao.factories;

import com.sw.dao.*;

/**
 * Classe parente de toutes les factories de modèles qui générent les modèles en fonction du type de base de donnée
 * Classe abstraite que l'on ne peut pas instancier (singleton)
 * Fournit un point d'accès centralisé pour obtenir des instances de DAO, garantissant l'utilisation cohérente du modèle de base de données à travers l'application.
 */
public abstract class FactoryDAO {

    /**
     *  Instance du singleton
     */
    private static FactoryDAO instance = null;

    /**
     * Type de la base de donnée
     */
    public static final String databaseType = "MySQL";


    /**
     * Méthode permettant de récupérer l'instance de la FactoryDAO
     * @return FactoryDAO, l'instance de la FactoryDAO
     */
    public static FactoryDAO getInstanceofFactoryDAO(){
        if (instance == null){
            if (databaseType.equals("MySQL")){
                instance = new FactoryDAOMySQL();
            }
            else {
                try {
                    throw new Exception("Le type de base de données n'existe pas");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return instance;
    }

    /**
     * Méthode permettant de récupérer une instance de la DAOClient
     * @return l'instance du DAOClient
     */
    public abstract DAOUser getInstanceofDAOUser();

    public abstract DAOProposal getInstanceofDAOProposal();

    public abstract DAOMusic getInstanceofDAOMusic();

    public abstract DAOPayment getInstanceofDAOPayment();

    public abstract DAOPlaylistMusic getDAOPlaylistMusic();

    public abstract DAOPlaylist getDAOPlaylist();

    public abstract DAOMessage getInstanceofDAOMessage();

    public abstract DAOConversation getInstanceofDAOConversation();
}



