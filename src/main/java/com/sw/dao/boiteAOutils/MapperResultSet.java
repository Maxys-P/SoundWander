package com.sw.dao.boiteAOutils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
/**
 * Classe utilitaire pour mapper les résultats d'un ResultSet.
 */
public class MapperResultSet {

    // Stocke les données mappées avec le nom des colonnes comme clé.
    private final List<Map<String, Object>> listData;

    // J'ai aussi ajouté par index pour plus de flexibilité car on ne connaitra pas forcément le nom des colonnes.
    // Stocke les données mappées avec l'index des colonnes comme clé.
    private final List<Map<Integer, Object>> listDataByIndex;

    /**
     * Construit un nouveau MapperResultSet en mappant les résultats du ResultSet fourni.
     * @param resultSet Le ResultSet à mapper.
     * @throws SQLException en cas d'erreur lors de la récupération des données du ResultSet.
     */
    public MapperResultSet(ResultSet resultSet) throws SQLException {
        this.listData = new ArrayList<>();
        this.listDataByIndex = new ArrayList<>();
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int columnCount = rsmd.getColumnCount();

        while (resultSet.next()) {
            Map<String, Object> rowDataByName = new LinkedHashMap<>();
            Map<Integer, Object> rowDataByIndex = new LinkedHashMap<>();
            for (int i = 1; i <= columnCount; i++) {
                rowDataByName.put(rsmd.getColumnName(i), resultSet.getObject(i));
                rowDataByIndex.put(i, resultSet.getObject(i));
            }
            listData.add(rowDataByName);
            listDataByIndex.add(rowDataByIndex);
        }
    }

    /**
     * Retourne la liste des données mappées, avec chaque ligne représentée par une Map.
     * Les clés de la Map sont les noms des colonnes.
     * @return La liste des données mappées par nom de colonne.
     */
    public List<Map<String, Object>> getListData() {
        return listData;
    }

    /**
     * Retourne la liste des données mappées, avec chaque ligne représentée par une Map.
     * Les clés de la Map sont les index des colonnes, commençant à 1.
     * @return La liste des données mappées par index de colonne.
     */
    public List<Map<Integer, Object>> getListDataByIndex() {
        return listDataByIndex;
    }
}
