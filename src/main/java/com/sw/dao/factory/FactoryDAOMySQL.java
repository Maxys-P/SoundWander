package com.sw.dao.factory;

import com.sw.dao.*;
import com.sw.dao.mysql.*;

/**
 *  Classe de la factory de model MySQL qui permet de créer des models MySQL
 *   Implémentation concrète de FactoryDAO pour créer des instances de DAO spécifiques à MySQL.
 */
public class FactoryDAOMySQL extends FactoryDAO {

    /**
     * Constructor
     */
    public FactoryDAOMySQL() {
        super();
    }

    /**
     * Méthode permettant de récupérer une instance de la DAOClient
     * @return l'instance du model UserMySQL
     */
    @Override
    public DAOUser getDAOUser() {
        return new DAOUserMySQL();
    }


    //Autres méthodes ici plus tard pour les autres classes

}