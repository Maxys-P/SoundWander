package com.sw.dao.requetesDB;

import com.sw.dao.connexions.ConnexionMySQL;
import com.sw.exceptions.ExceptionDB;
import java.sql.*;
import java.util.Collections;

/**
 * Classe qui donne les méthodes pour interagir avec la base de données MySQL
 */
public class RequetesMySQL extends RequetesDB {

    public RequetesMySQL() {
        super(new ConnexionMySQL());
    }

    /**
     * Ferme la connexion à la base de donnée
     * @throws ExceptionDB, SQLException si la connexion ne peut pas être fermée
     */
    @Override
    public void close() throws ExceptionDB, SQLException {
        this.getConnexion().close();
    }

    /**
     * Méthode pour récupérer une connexion à la base de données
     * @return Connection, la connexion à la base de données
     * @throws ExceptionDB si la connexion ne peut pas être établie
     */
    private Connection getConnexion() throws ExceptionDB {
        System.out.println("Je suis dans la méthode getConnexion de RequetesMySQL");
        return this.connexionDB.getConnection();
    }

    // On mets les méthodes pour interagir avec la base de données ici :
    //Les CRUD de base quoi avec des paramètres (qu'on utilisera dans les fonctions de DAOMySQL)


    /**
     * Read du CRUD, équivaut à un select * from table
     * @param table La table dans laquelle lire les données
     * @return un objet ResultSet qui contient les données de chaque tuple
     * @throws ExceptionDB
     */
    public ResultSet selectAll(String table) throws ExceptionDB {
        System.out.println("Je suis dans la méthode selectAll de RequetesMySQL");
        String sql = "SELECT * FROM " + table;
        try (Connection connection = this.getConnexion()) {
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                ResultSet result = pstmt.executeQuery();
                return result;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ExceptionDB("Erreur lors de la sélection des données", e);
        }
    }


    /**
     * Insère des données dans une table spécifiée.
     * @param table Le nom de la table dans laquelle insérer les données.
     * @param columns Les colonnes dans lesquelles insérer les données.
     * @param values Les valeurs à insérer dans les colonnes correspondantes.
     * @throws SQLException si la requête SQL ne peut pas être exécutée.
     */
    public void create(String table, String[] columns, Object[] values) throws Exception {
        if (columns.length != values.length) {
            throw new Exception("Le nombre de colonnes et de valeurs doit être égal.");
        }
        String columnPart = String.join(", ", columns);
        String valuePart = String.join(", ", Collections.nCopies(columns.length, "?"));
        String sql = "INSERT INTO " + table + " (" + columnPart + ") VALUES (" + valuePart + ")";

        try(Connection connection = this.getConnexion();
            PreparedStatement pstmt = connection.prepareStatement(sql)) {
            for (int i = 0; i < values.length; i++) {
                pstmt.setObject(i + 1, values[i]);
            }

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Erreur lors de l'insertion des données", e);
        }
    }


    /**
     * Read du CRUD, équivaut à un select * from table avec une condition where
     * @param table La table dans laquelle lire les données
     * @param columns  Les colonnes à lire
     * @param whereColumns Les colonnes sur lesquelles appliquer la condition
     * @param whereValues Les valeurs à chercher dans les colonnes
     * @return ResultSet, le résultat de la requête
     * @throws ExceptionDB si la requête SQL ne peut pas être exécutée
     */
    public ResultSet selectWhere(String table, String[] columns, String[] whereColumns, Object[] whereValues) throws ExceptionDB {
        if (whereColumns.length != whereValues.length) {
            throw new ExceptionDB("Le nombre de colonnes WHERE et de valeurs doit être égal.");
        }

        String columnPart = String.join(", ", columns);
        String wherePart = "";
        for (int i = 0; i < whereColumns.length; i++) {
            wherePart += whereColumns[i] + " = ?";
            if (i < whereColumns.length - 1) {
                wherePart += " AND ";
            }
        }

        String sql = "SELECT " + columnPart + " FROM " + table + " WHERE " + wherePart;

        try (Connection connection = this.getConnexion();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            for (int i = 0; i < whereValues.length; i++) {
                pstmt.setObject(i + 1, whereValues[i]);
            }

            return pstmt.executeQuery();
        } catch (SQLException e) {
            throw new ExceptionDB("Erreur lors de la sélection des données", e);
        }
    }



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
