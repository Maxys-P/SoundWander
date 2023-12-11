package com.sw.dao.methodesDB;

import com.sw.dao.connexions.ConnexionDB;
import com.sw.exceptions.ExceptionDB;

/**
 * Classe abstraite, parente de toutes les classes de methodesDB
 */
public abstract class MethodesDB {
    /**
     * Une instance de la connexionDB pour exécuter les requêtes
     */
    protected ConnexionDB connexionDB;


    /**
     * Constructeur de la classe MethodesDB
     * @param connexionDB une instance de la connexionDB
     */
    public MethodesDB(ConnexionDB connexionDB){
        this.connexionDB = connexionDB;
    }

    /**
     * Se connecte à la base de donnée
     */
    public void connect() throws ExceptionDB {
        this.connexionDB.connection();
    }

    /**
     * Ferme la connexion à la base de donnée
     * @throws Exception
     */
    public abstract void close() throws Exception;

}
