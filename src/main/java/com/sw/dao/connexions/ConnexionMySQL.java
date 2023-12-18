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
    private String url = "jdbc:mysql://6.tcp.eu.ngrok.io:19929/DBSoundWander";
    private String utilisateur = "root";
    private String motDePasse = "se123";

    /**
     * Se connecte à la base de données MySQL
     * @return Connection, un objet de connexion
     * @throws ExceptionDB en cas d'erreur lors de la connexion
     */
    @Override
    public Connection connection() throws ExceptionDB {
        try {
            // Chargement du pilote JDBC pour MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            System.out.println("Je vais me connecter à la base de données");

            // Établissement de la connexion
            this.connection = DriverManager.getConnection(url, utilisateur, motDePasse);

            if (this.connection != null) {
                System.out.println("Connexion à la base de données établie");
            } else {
                System.out.println("Connexion à la base de données échouée");
            }

        } catch (ClassNotFoundException e) {
            System.out.println("Échec de la connexion : Pilote JDBC non trouvé.");
            throw new ExceptionDB("Erreur lors de la connexion à la base de données MySQL : Pilote non trouvé", e);
        } catch (SQLException e) {
            System.out.println("Échec de la connexion : Erreur SQL.");
            throw new ExceptionDB("Erreur lors de la connexion à la base de données MySQL : Erreur SQL", e);
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