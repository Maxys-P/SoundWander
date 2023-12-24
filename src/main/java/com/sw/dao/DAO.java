package com.sw.dao;

import com.sw.dao.requetesDB.RequetesDB;

/**
 *  Classe abstraite non instanciable
 *  Parente de tous les modèles qui interagissent avec la base de données, indépendamment du SGBD.
 */
public abstract class DAO {

    /**
     * Le nom de la table dans la base de données
     */
    protected String table;

    /**
     * L'instance pour accéder aux méthodes de base de données
     */
    protected RequetesDB requetesDB;

    public DAO(String table){
        this.table = table;
    }

}