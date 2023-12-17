package com.sw.dao;

import com.sw.dao.requetesDB.RequetesDB;
import com.sw.dao.requetesDB.RequetesMySQL;
import com.sw.exceptions.ExceptionDB;

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

    /**
     * Méthode pour savoir si une donnée existe dans la base de données
     * @param data Object, la donnée à chercher
     * @param name String, le nom de la colonne dans laquelle chercher
     * @return boolean, true si la donnée existe, false sinon
     * @throws ExceptionDB si la requête SQL est incorrecte
     */
    public boolean dataExist(Object data, String name) throws ExceptionDB {
        return (((RequetesMySQL)this.requetesDB).exist(data, name, this.table));
        //pas fou que ça dépende de MySQL mais sinon il faudra le mettre dans chaque DAO
    }
}