package com.sw.dao.requetesDB;

import com.sw.dao.boiteAOutils.MapperResultSet;
import com.sw.dao.connexions.ConnexionMySQL;
import com.sw.exceptions.ExceptionDB;
import java.sql.*;
import java.util.*;

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
    public Connection getConnexion() throws ExceptionDB, SQLException {
        return this.connexionDB.getConnection();
    }

    // On mets les méthodes pour interagir avec la base de données ici :
    //Les CRUD de base quoi avec des paramètres (qu'on utilisera dans les fonctions de DAOMySQL)

    /**
     * Read du CRUD, équivaut à un select * from table
     * @param table La table dans laquelle lire les données
     * @return un objet ResultSet qui contient les données de chaque tuple
     * @throws ExceptionDB si une exception SQL se produit.
     */
    //Je pense qu'on pourrait juste faire un selectWhere avec une map vide maintenant qu'on l'a mais je laisse au cas où et ça doit être plus rapide
    public MapperResultSet selectAll(String table) throws ExceptionDB {
        String sql = "SELECT * FROM " + table;
        try (Connection connection = this.getConnexion();
            PreparedStatement requete = connection.prepareStatement(sql)) {

                ResultSet result = requete.executeQuery();
                return new MapperResultSet(result);
        } catch (SQLException e) {
        e.printStackTrace();
        throw new ExceptionDB("Erreur lors de la sélection des données", e);
        }
    }


    /**
     * Méthode générique pour effectuer des requêtes SELECT avec des conditions WHERE.
     * @param table Le nom de la table.
     * @param whereConditions Liste des paires colonne-valeur pour les conditions WHERE.
     * @return MapperResultSet, le résultat de la requête.
     * @throws ExceptionDB si une exception SQL se produit.
     */
    public MapperResultSet selectWhere(String table, Map<String, Object> whereConditions) throws ExceptionDB {
        StringBuilder wherePart = new StringBuilder();
        int i = 0;
        for (Map.Entry<String, Object> entry : whereConditions.entrySet()) {
            wherePart.append(entry.getKey()).append(" = ?");
            if (i < whereConditions.size() - 1) {
                wherePart.append(" AND ");
            }
            i++;
        }

        String sql = "SELECT * FROM " + table + " WHERE " + wherePart;
        try (Connection connection = this.getConnexion();
             PreparedStatement requete = connection.prepareStatement(sql)) {
            int index = 1;
            for (Object value : whereConditions.values()) {
                requete.setObject(index++, value);
            }
            ResultSet rs = requete.executeQuery();
            return new MapperResultSet(rs);

        } catch (SQLException e) {
            throw new ExceptionDB("Erreur lors de la sélection des données", e);
        }
    }


    /**
     * Méthode générique pour insérer une seule ligne de données dans n'importe quelle table.
     * @param table Le nom de la table dans laquelle insérer les données.
     * @param data Map des données à insérer, où chaque clé est le nom de la colonne et chaque valeur est la valeur à insérer.
     * @throws ExceptionDB, SQLException si une exception SQL se produit.
     */
    public int create(String table, Map<String, Object> data) throws ExceptionDB, SQLException {
        if (data.isEmpty()) {
            throw new ExceptionDB("Aucune donnée à insérer");
        }

        String columnPart = String.join(", ", data.keySet());
        String valuePart = String.join(", ", Collections.nCopies(data.size(), "?"));
        String sql = "INSERT INTO " + table + " (" + columnPart + ") VALUES (" + valuePart + ")";

        try (Connection connection = this.getConnexion();
             PreparedStatement requete = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int index = 1;
            for (Object value : data.values()) {
                requete.setObject(index++, value);
            }

            int affectedRows = requete.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("La création a échoué, aucune ligne n'a été modifiée.");
            }

            try (ResultSet generatedKeys = requete.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("La création a échoué, aucun ID n'a été retourné.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ExceptionDB("Erreur lors de l'insertion des données" + e.getMessage(), e);
        }
    }


    /**
     * Méthode générique pour modifier les données d'une table.
     * @param table Le nom de la table dans laquelle modifier les données.
     * @param data Map des données à modifier, où chaque clé est le nom de la colonne et chaque valeur est la valeur à modifier.
     * @param whereConditions Map des conditions WHERE, où chaque clé est le nom de la colonne et chaque valeur est la valeur à modifier.
     * @throws ExceptionDB si une exception SQL se produit.
     */
    public void update(String table, Map<String, Object> data, Map<String, Object> whereConditions) throws ExceptionDB {
        if (data.isEmpty()) {
            throw new ExceptionDB("Aucune donnée à mettre à jour");
        }
        if (whereConditions.isEmpty()) {
            throw new ExceptionDB("Aucune condition WHERE spécifiée. Mise à jour annulée pour éviter les modifications de masse.");
        }

        // Construction de la partie UPDATE de la requête
        StringBuilder setPart = new StringBuilder();
        int index = 0;
        for (String key : data.keySet()) {
            setPart.append(key).append(" = ?");
            if (index < data.size() - 1) setPart.append(", ");
            index++;
        }

        // Construction de la partie WHERE de la requête
        StringBuilder wherePart = new StringBuilder();
        index = 0;
        for (String key : whereConditions.keySet()) {
            wherePart.append(key).append(" = ?");
            if (index < whereConditions.size() - 1) wherePart.append(" AND ");
            index++;
        }

        String sql = "UPDATE " + table + " SET " + setPart + " WHERE " + wherePart;

        try (Connection connection = this.getConnexion();
             PreparedStatement requete = connection.prepareStatement(sql)) {

            index = 1; // Réinitialisation de l'index pour la préparation des paramètres

            // Affectation des valeurs à mettre à jour
            for (Object value : data.values()) {
                requete.setObject(index++, value);
            }
            // Affectation des valeurs des conditions WHERE
            for (Object value : whereConditions.values()) {
                requete.setObject(index++, value);
            }

            int affectedRows = requete.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("La mise à jour a échoué, aucune ligne n'a été modifiée.");
            }
        } catch (SQLException e) {
            throw new ExceptionDB("Erreur lors de la mise à jour des données" + e.getMessage(), e);
        }
    }

    /**
     * Méthode générique pour supprimer des données d'une table.
     * @param table Le nom de la table dans laquelle supprimer les données.
     * @param whereConditions Map des conditions WHERE, où chaque clé est le nom de la colonne et chaque valeur est la valeur à modifier.
     * @throws ExceptionDB si une exception SQL se produit.
     */
    public int delete(String table, Map<String, Object> whereConditions) throws ExceptionDB {
        if (whereConditions.isEmpty()) {
            throw new ExceptionDB("Aucune condition WHERE spécifiée. Suppression annulée pour éviter les suppressions de masse.");
        }

        // Construction de la partie WHERE de la requête
        StringBuilder wherePart = new StringBuilder();
        int i = 0;
        for (Map.Entry<String, Object> entry : whereConditions.entrySet()) {
            wherePart.append(entry.getKey()).append(" = ?");
            if (i < whereConditions.size() - 1) {
                wherePart.append(" AND ");
            }
            i++;
        }

        String sql = "DELETE FROM " + table + " WHERE " + wherePart;

        try (Connection connection = this.getConnexion();
             PreparedStatement requete = connection.prepareStatement(sql)) {

            int index = 1;
            for (Object value : whereConditions.values()) {
                requete.setObject(index++, value);
            }

            return requete.executeUpdate();
        } catch (SQLException e) {
            throw new ExceptionDB("Erreur lors de la suppression des données", e);
        }
    }


    /**
     * Méthode générique pour effectuer des requêtes SELECT avec des jointures.
     * @param mainTable Le nom de la table principale.
     * @param joinTables Liste des tables à joindre avec la table principale.
     * @param onConditions Liste des conditions pour les jointures (par exemple, "mainTable.id = joinTable.foreignId").
     * @param whereConditions Map des conditions WHERE, où chaque clé est le nom de la colonne et chaque valeur est la valeur à utiliser dans la condition.
     * @return MapperResultSet, le résultat de la requête.
     * @throws ExceptionDB si une exception SQL se produit.
     */
    public MapperResultSet selectWithJoin(String mainTable, List<String> joinTables, List<String> onConditions, Map<String, Object> whereConditions) throws ExceptionDB {
        StringBuilder sql = new StringBuilder("SELECT * FROM ").append(mainTable);

        // Ajouter les jointures
        for (int i = 0; i < joinTables.size(); i++) {
            sql.append(" JOIN ").append(joinTables.get(i)).append(" ON ").append(onConditions.get(i));
        }

        // Ajouter les conditions WHERE
        if (!whereConditions.isEmpty()) {
            sql.append(" WHERE ");
            int i = 0;
            for (Map.Entry<String, Object> entry : whereConditions.entrySet()) {
                sql.append(entry.getKey()).append(" = ?");
                if (i < whereConditions.size() - 1) {
                    sql.append(" AND ");
                }
                i++;
            }
        }

        try (Connection connection = this.getConnexion();
             PreparedStatement requete = connection.prepareStatement(sql.toString())) {

            int index = 1;
            for (Object value : whereConditions.values()) {
                requete.setObject(index++, value);
            }

            ResultSet rs = requete.executeQuery();
            return new MapperResultSet(rs);
        } catch (SQLException e) {
            throw new ExceptionDB("Erreur lors de la sélection des données avec jointure", e);
        }
    }


    /**
     * Executes a SQL query with the given parameters and returns the result.
     * @param sql the SQL query to execute.
     * @param params the parameters for the SQL query.
     * @return a MapperResultSet containing the result of the query.
     * @throws ExceptionDB if an error occurs during query execution.
     */
    //Pas compris l'intérêt mais je laisse au cas où
    public MapperResultSet executeQueryWithParams(String sql, Object... params) throws ExceptionDB {
        try (Connection connection = this.getConnexion();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Set the parameters on the PreparedStatement
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }

            ResultSet resultSet = statement.executeQuery();
            return new MapperResultSet(resultSet);
        } catch (SQLException e) {
            throw new ExceptionDB("Erreur lors de l'exécution de la requête avec paramètres", e);
        }
    }

    public void createNoReturn(String table, Map<String, Object> data) throws ExceptionDB, SQLException {
        if (data.isEmpty()) {
            throw new ExceptionDB("Aucune donnée à insérer");
        }

        String columnPart = String.join(", ", data.keySet());
        String valuePart = String.join(", ", Collections.nCopies(data.size(), "?"));
        String sql = "INSERT INTO " + table + " (" + columnPart + ") VALUES (" + valuePart + ")";

        try (Connection connection = this.getConnexion();
             PreparedStatement requete = connection.prepareStatement(sql)) {
            int index = 1;
            for (Object value : data.values()) {
                requete.setObject(index++, value);
            }

            int affectedRows = requete.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("La création a échoué, aucune ligne n'a été modifiée.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ExceptionDB("Erreur lors de l'insertion des données: " + e.getMessage(), e);
        }
    }



}
