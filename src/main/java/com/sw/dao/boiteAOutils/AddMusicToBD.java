package com.sw.dao.boiteAOutils;
import com.sw.dao.connexions.ConnexionMySQL;
import com.sw.exceptions.ExceptionDB;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddMusicToBD {
    public static void main(String[] args) {
        // Chemin vers le fichier MP3
        String filePath = "src/main/java/com/sw/dao/boiteAOutils/Premier_son.mp3";

        // Utilisation de la classe de connexion pour obtenir une connexion à la base de données
        ConnexionMySQL connexionMySQL = new ConnexionMySQL();

        try (Connection conn = connexionMySQL.getConnection()) { // Try-with-resources pour assurer la fermeture de la connexion
            // Créer un objet Path pour le fichier MP3
            Path path = Paths.get(filePath);
            // Lire le contenu du fichier dans un tableau d'octets
            byte[] fileContent = Files.readAllBytes(path);

            // Préparer la requête SQL pour insérer les données
            String sql = "INSERT INTO music (musicFile, name, artist, duration) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            // Remplir les paramètres de la requête préparée
            pstmt.setBytes(1, fileContent);
            pstmt.setString(2, "Premier Son Bis");
            pstmt.setInt(3, 2);
            pstmt.setInt(4, 10);

            // Exécuter la requête
            pstmt.executeUpdate();

            System.out.println("Le fichier audio a été inséré avec succès.");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Une erreur SQL s'est produite.");
            e.printStackTrace();
        } catch (ExceptionDB e) {
            System.out.println("Une erreur de base de données s'est produite.");
            e.printStackTrace();
        } finally {
            // Fermeture explicite de la connexion n'est pas nécessaire ici si on utilise try-with-resources
            try {
                connexionMySQL.closeConnection();
            } catch (ExceptionDB e) {
                System.out.println("Erreur lors de la fermeture de la connexion à la base de données.");
                e.printStackTrace();
            }
        }
    }
}
