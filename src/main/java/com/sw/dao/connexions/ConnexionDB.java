package com.sw.dao.connexions;

import com.sw.exceptions.ExceptionDB;
import java.sql.Connection;

public abstract class ConnexionDB {

    /**
     * Se connecte à la base de données
     * @return un objet de connexion
     */
    public abstract Connection connection() throws ExceptionDB;

    /**
     * Récupère la connexion à la base de donnée
     * @return Connection, un objet de connection permettant d'exécuter des requêtes sur la base de donnée
     * @throws ExceptionDB en cas d'erreur
     */
    public abstract Connection getConnection() throws ExceptionDB;

    /**
     * Ferme la connexion à la base de données
     * @throws ExceptionDB en cas d'erreur lors de la fermeture de la connexion
     */
    public abstract void closeConnection() throws ExceptionDB;
}
