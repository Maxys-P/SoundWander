package com.sw.dao.mysql;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTableUserScript {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/DBSoundWander";
        String utilisateur = "root";
        String motDePasse = "se123";

        try (Connection connexion = DriverManager.getConnection(url, utilisateur, motDePasse);
             Statement statement = connexion.createStatement()) {

            // Requête SQL pour créer la table
            String requeteSQL = "CREATE TABLE IF NOT EXISTS user (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "pseudo VARCHAR(255) NOT NULL," +
                    "email VARCHAR(255) NOT NULL," +
                    "date_naissance DATE," +
                    "mot_de_passe VARCHAR(255) NOT NULL" +
                    ");";

            // Exécution de la requête SQL
            statement.executeUpdate(requeteSQL);

            System.out.println("La table 'user' a été créée avec succès.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
