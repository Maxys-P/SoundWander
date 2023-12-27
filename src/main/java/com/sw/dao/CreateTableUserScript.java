package com.sw.dao;
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
            String requeteSQL = "CREATE TABLE IF NOT EXISTS user (\n" +
                    "    id INT AUTO_INCREMENT PRIMARY KEY,\n" +
                    "    pseudo VARCHAR(255) NOT NULL,\n" +
                    "    mail VARCHAR(255) NOT NULL UNIQUE,\n" +
                    "    motDePasse VARCHAR(255) NOT NULL,\n" +
                    "    dateNaissance DATE,\n" +
                    "    photo VARCHAR(255)\n" +
                    ");\n";

            // Exécution de la requête SQL
            statement.executeUpdate(requeteSQL);

            System.out.println("La table 'user' a été créée avec succès.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
