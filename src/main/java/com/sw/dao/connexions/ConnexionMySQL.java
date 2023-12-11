package com.sw.dao.connexions;

import com.sw.exceptions.ExceptionDB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe qui permet de se connecter à une base de données de type MySQL et d'y exécuter des requêtes
 */
public class ConnexionMySQL extends ConnexionDB {

    private Connection connection;
    private String url;
    private String username;
    private String password;

    @Override
    public Connection connection() throws ExceptionDB {
        try {
            // Chargement du pilote JDBC pour MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Établissement de la connexion
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            // Gestion des exceptions et encapsulation dans une ExceptionDB
            throw new ExceptionDB("Erreur lors de la connexion à la base de données MySQL", e);
        }
        return this.connection;
    }

    @Override
    public Connection getConnection() throws ExceptionDB {
        // Vérification si la connexion est déjà établie
        if (this.connection == null) {
            connection();
        }
        return this.connection;
    }

    // Méthode pour fermer la connexion
    @Override
    public void closeConnection() throws ExceptionDB {
        if (this.connection != null) {
            try {
                this.connection.close();
            } catch (SQLException e) {
                throw new ExceptionDB("Erreur lors de la fermeture de la connexion", e);
            }
        }
    }
}