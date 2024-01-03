package com.sw.dao.connexions;

import com.sw.exceptions.ExceptionDB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe qui permet de se connecter à une base de données de type MySQL et d'y exécuter des requêtes
 */
public class ConnexionMySQL extends ConnexionDB {

    /**
     * Objet de connexion à la base de données
     */
    private Connection connection;

    private String url = "jdbc:mysql://localhost:3306/DBSoundWander";
    private String utilisateur = "root";
    private String motDePasse = "Mateo3945";

    /*
    private String url = "jdbc:mysql://aws.connect.psdb.cloud/dbsoundwander?sslMode=VERIFY_IDENTITY";
    private String utilisateur = "dr8cwbphmc2l0gsg5tq7";
    private String motDePasse = "pscale_pw_sLdtBIZE1bPbSjcnIyzYM8378edonNRWu3Bk6sjIy00";
    */

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

            // Établissement de la connexion
            this.connection = DriverManager.getConnection(url, utilisateur, motDePasse);

            if (this.connection != null) {
                //System.out.println("Connecté à : " + this.connection.getMetaData().getURL());
            } else {
                System.out.println("Connexion échouée");
            }
        } catch (SQLException e) {
            System.out.println("Échec de la connexion : Erreur SQL.");
            throw new ExceptionDB("Erreur lors de la connexion à la base de données MySQL : Erreur SQL", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return this.connection;
    }

    @Override
    public Connection getConnection() throws ExceptionDB, SQLException {
        // Vérification si la connexion est déjà établie et n'est pas fermée
        if (this.connection == null || this.connection.isClosed()) {
            //System.out.println("Connexion non établie ou fermée, tentative de reconnexion.");
            connection();
        }
        return this.connection;
    }


    // Méthode pour fermer la connexion
    @Override
    public void closeConnection() throws ExceptionDB {
        if (this.connection != null) {
            try {
                System.out.println("Fermeture de la connexion...");
                this.connection.close();
            } catch (SQLException e) {
                throw new ExceptionDB("Erreur lors de la fermeture de la connexion", e);
            }
        }
    }
}