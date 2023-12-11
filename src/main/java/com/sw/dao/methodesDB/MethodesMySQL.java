package com.sw.dao.methodesDB;

import com.sw.dao.connexions.ConnexionMySQL;
import com.sw.exceptions.ExceptionDB;
import java.sql.*;

/**
 * Classe qui donne les méthodes pour interagir avec la base de données MySQL
 */
public class MethodesMySQL extends MethodesDB {

    public MethodesMySQL() {
        super(new ConnexionMySQL());
    }

    /**
     * Ferme la connexion à la base de donnée
     * @throws ExceptionDB
     */
    @Override
    public void close() throws SQLException, ExceptionDB {
        this.connexionDB.closeConnection();
    }

    // On mets les méthodes pour interagir avec la base de données ici :
    //Les CRUD de base quoi avec des paramètres (qu'on utilisera dans les fonctions de DAOMySQL)

    /**
     * Méthode pour savoir si une donnée existe dans la base de données
     * @param data La donnée à chercher
     * @param name  Le nom de la colonne dans laquelle chercher
     * @param table La table dans laquelle chercher
     * @return boolean, true si la donnée existe, false sinon
     * @throws ExceptionDB  si la requête SQL ne peut pas être exécutée
     */
    public boolean exist(Object data, String name, String table) throws ExceptionDB {
        String sql = "SELECT COUNT(*) FROM " + table + " WHERE " + name + " = ?";
        try (Connection conn = this.connexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Définit la valeur à chercher dans la requête
            pstmt.setObject(1, data);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Vérifie si le nombre d'occurrences est supérieur à 0
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new ExceptionDB("Erreur lors de la vérification de l'existence de la donnée", e);
        }
        return false;
    }
}
