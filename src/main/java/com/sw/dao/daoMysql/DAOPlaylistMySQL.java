package com.sw.dao.daoMysql;

import com.sw.classes.Artist;
import com.sw.classes.Music;
import com.sw.classes.Playlist;
import com.sw.commons.SearchCriteria;
import com.sw.dao.DAOPlaylist;
import com.sw.dao.boiteAOutils.MapperResultSet;
import com.sw.dao.requetesDB.RequetesMySQL;
import com.sw.exceptions.ExceptionDB;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DAOPlaylistMySQL extends DAOPlaylist {
    public DAOPlaylistMySQL() {
        super();
        this.requetesDB = new RequetesMySQL();
    }

    @Override
    public Playlist getPLaylistByName(String name) throws Exception {
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("name", name);
        try{
            MapperResultSet playlistData = ((RequetesMySQL) requetesDB).selectWhere(table, conditions);
            if (!playlistData.getListData().isEmpty()) {
                Map<String, Object> playlistDetails = playlistData.getListData().getFirst();

                int id = (int) playlistDetails.get("id");
                String Playlistname = (String) playlistDetails.get("name");
                String country = (String) playlistDetails.get("country");
                String continent = (String) playlistDetails.get("continent");

                return new Playlist(id, Playlistname, country, continent);
            } else {
                System.out.println("Pb quand on appelle selectWhere");
            }
            return null;
        } catch (Exception e) {
            System.out.println("Erreur lors de la récupération de la playlist par nom");
            return null;
        }
    }
    @Override
    public Playlist getPlaylistById(int id) throws Exception {
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("id", id);
        try{
            MapperResultSet playlistData = ((RequetesMySQL) requetesDB).selectWhere(table, conditions);
            if (!playlistData.getListData().isEmpty()) {
                Map<String, Object> playlistDetails = playlistData.getListData().getFirst();

                String Playlistname = (String) playlistDetails.get("name");
                String country = (String) playlistDetails.get("country");
                String continent = (String) playlistDetails.get("continent");

                return new Playlist(id, Playlistname, country, continent);
            } else {
                System.out.println("Pb quand on appelle selectWhere");
            }
            return null;
        } catch (Exception e) {
            System.out.println("Erreur lors de la récupération de la playlist par nom");
            return null;
        }
    }

    @Override
    public Playlist getPlaylistByCountry(String country) throws Exception {
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("country", country);
        try{
            MapperResultSet playlistData = ((RequetesMySQL) requetesDB).selectWhere(table, conditions);
            if (!playlistData.getListData().isEmpty()) {
                Map<String, Object> playlistDetails = playlistData.getListData().getFirst();

                int id = (int) playlistDetails.get("id");
                String Playlistname = (String) playlistDetails.get("name");
                String countryName = (String) playlistDetails.get("country");
                String continent = (String) playlistDetails.get("continent");

                return new Playlist(id, Playlistname, countryName, continent);
            } else {
                System.out.println("Pb quand on appelle selectWhere");
            }
            return null;
        } catch (Exception e) {
            System.out.println("Erreur lors de la récupération de la playlist par nom");
            return null;
        }
    }

    @Override
    public void addPlaylist(String name, String country) throws Exception {
        try {

            Map<String, Object> values = new HashMap<>();
            values.put("name", name);
            values.put("country", country);

            int insertedId = ((RequetesMySQL) requetesDB).create("playlist", values);
            System.out.println("Playlist ajoutée avec l'id : " + insertedId);

        } catch (Exception e) {
            System.out.println("Erreur lors de l'ajout de la playlist");
        }
    }

    @Override
    public List<Object> search(SearchCriteria criteria) {
        List<Object> matchingPlaylist = new ArrayList<>();
        Map<String, Object> whereConditions = new HashMap<>();
        whereConditions.put("name", criteria.getSearchTerm());

        return null;
    }
}
