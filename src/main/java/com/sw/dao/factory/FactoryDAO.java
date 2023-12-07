package com.sw.dao.factory;

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
     *  Constructeur de la FactoryDAO
     */
    protected FactoryDAO(){}


    /**
     * Méthode permettant de récupérer l'instance de la FactoryDAO
     * @return FactoryDAO, l'instance de la FactoryDAO
     */
    public static FactoryDAO getInstance(){
        if (instance == null){
            if (databaseType.equals("MySQL")){
                instance = new FactoryDAOMySQL();
            }
            else {
                //mettre une erreur ici
                System.out.println("Le type de base de données n'existe pas");
            }
        }
        return instance;
    }

    /**
     * Méthode permettant de récupérer une instance de la DAOClient
     * @return l'instance du DAOClient
     */
    public abstract DAOUser getDAOUser();

}


//Autres méthodes ici plus tard pour les autres classes
