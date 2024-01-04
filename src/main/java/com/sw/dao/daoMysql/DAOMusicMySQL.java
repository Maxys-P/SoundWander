package com.sw.dao.daoMysql;

import com.sw.classes.Music;
import com.sw.commons.SearchCriteria;
import com.sw.dao.DAOMusic;
import com.sw.dao.boiteAOutils.MapperResultSet;
import com.sw.dao.requetesDB.RequetesMySQL;
import com.sw.exceptions.ExceptionDB;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DAOMusicMySQL extends DAOMusic {

    public DAOMusicMySQL() {
        super();
        this.requetesDB = new RequetesMySQL();
    }

    @Override
    public Music getMusicByName(String name) {
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("name", name);
        try {
            MapperResultSet musicData = ((RequetesMySQL) requetesDB).selectWhere(table, conditions);
            if (!musicData.getListData().isEmpty()) {
                Map<String, Object> musicDetails = musicData.getListData().getFirst();

                int id = (int) musicDetails.get("id");
                int artist = (int) musicDetails.get("artist");
                int duration = (int) musicDetails.get("duration");
                byte[] musicFile = (byte[]) musicDetails.get("musicFile");

                return new Music(id, name, artist, duration, musicFile);
            } else {
                System.out.println("Pb quand on appelle selectWhere");
            }
            return null;
        } catch (Exception e) {
            System.out.println("Erreur lors de la récupération de la musique par nom");
            return null;
        }
    }

    @Override
    public Music getMusicById(int musicId) throws Exception {
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("id", musicId);
        try {
            MapperResultSet musicData = ((RequetesMySQL) requetesDB).selectWhere(table, conditions);
            if (!musicData.getListData().isEmpty()) {
                Map<String, Object> musicDetails = musicData.getListData().getFirst();

                String name = (String) musicDetails.get("name");
                int artist = (int) musicDetails.get("artist");
                int duration = (int) musicDetails.get("duration");
                byte[] musicFile = (byte[]) musicDetails.get("musicFile");

                return new Music(musicId, name, artist, duration, musicFile);
            } else {
                System.out.println("Pb quand on appelle selectWhere");
            }
            return null;
        } catch (Exception e) {
            throw new Exception("Erreur lors de la récupération de la musique par ID", e);
        }
    }


    @Override
    public Music getNextMusic(int currentId) throws Exception {
        try {
            // Define the SQL for fetching the next music entry
            String sql = "SELECT * FROM music WHERE id > ? ORDER BY id ASC LIMIT 1";
            MapperResultSet nextMusicData = ((RequetesMySQL) requetesDB).executeQueryWithParams(sql, currentId);
            if (!nextMusicData.getListData().isEmpty()) {
                // Assuming nextMusicData contains the result of the query
                Map<String, Object> musicDetails = nextMusicData.getListData().getFirst();

                int nextId = (int)musicDetails.get("id");
                String name = (String) musicDetails.get("name");
                int artist = (int) musicDetails.get("artist");
                int duration = (int) musicDetails.get("duration");
                byte[] musicFile = (byte[]) musicDetails.get("musicFile");

                return new Music(nextId, name, artist, duration, musicFile); // Constructing Music object
            } else {
                System.out.println("Next music not found.");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error fetching next music: " + e.getMessage());
            throw e;
        }
    }



    @Override
    public Music getPreviousMusic(int id) throws Exception{
        try {
            // Define the SQL for fetching the previous music entry
            String sql = "SELECT * FROM music WHERE id < ? ORDER BY id DESC LIMIT 1";
            MapperResultSet previousMusicData = ((RequetesMySQL) requetesDB).executeQueryWithParams(sql, id);
            if (!previousMusicData.getListData().isEmpty()) {
                // Assuming previousMusicData contains the result of the query
                Map<String, Object> musicDetails = previousMusicData.getListData().getFirst();

                int previousId = (int)musicDetails.get("id");
                String name = (String) musicDetails.get("name");
                int artist = (int) musicDetails.get("artist");
                int duration = (int) musicDetails.get("duration");
                byte[] musicFile = (byte[]) musicDetails.get("musicFile");

                return new Music(previousId, name, artist, duration, musicFile); // Constructing Music object
            } else {
                System.out.println("Previous music not found.");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error fetching previous music: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void addPrivatePlaylist(String name) {
    }

    @Override
    public void addMusic(String name, int artist, int duration, String filePath) {

        try {
            Path path = Paths.get(filePath);
            byte[] fileContent = Files.readAllBytes(path);

            Map<String, Object> data = new HashMap<>();
            data.put("musicFile", fileContent);
            data.put("name", name);
            data.put("artist", artist);
            data.put("duration", duration);

            int insertedId = ((RequetesMySQL) requetesDB).create("music", data);

            System.out.println("[DAOMusicMySQL] addMusic - fichier audio inséré avec succès. ID : " + insertedId);

        } catch (IOException e) {
            System.err.println("[DAOMusicMySQL] addMusic - Erreur lors de la lecture du fichier MP3 : " + e.getMessage());
        } catch (Exception e) {
            System.err.println("[DAOMusicMySQL] addMusic - Erreur lors de l'ajout de la musique : " + e.getMessage());
        }
    }

    @Override
    public void removeMusic(int id) {
        try {
            Map<String, Object> conditions = new HashMap<>();
            conditions.put("id", id);
            ((RequetesMySQL) requetesDB).delete("music", conditions);
            System.out.println("[DAOMusicMySQL] removeMusic - Musique avec ID " + id + " supprimée de la base de données.");
        } catch (Exception e) {
            System.err.println("[DAOMusicMySQL] removeMusic - Erreur lors de la suppression de la musique : " + e.getMessage());
        }
    }

    @Override
    public List<Music> getMusicByUserId(int userId) throws Exception {
        List<Music> userMusics = new ArrayList<>();
        try {
            Map<String, Object> conditions = new HashMap<>();
            conditions.put("artist", userId);
            MapperResultSet musicData = ((RequetesMySQL) requetesDB).selectWhere("music", conditions);

            for (Map<String, Object> row : musicData.getListData()) {
                int id = (Integer) row.get("id");
                String name = (String) row.get("name");
                int artistId = (Integer) row.get("artist"); // Si vous avez besoin de l'ID de l'artiste
                int duration = (Integer) row.get("duration");
                byte[] musicFile = (byte[]) row.get("musicFile");

                Music musicInfo = new Music(id, name, artistId, duration, musicFile);
                userMusics.add(musicInfo);
            }
        } catch (Exception e) {
            throw new Exception("Erreur lors de la récupération des musiques de l'utilisateur", e);
        }
        return userMusics;
    }

    @Override
    public List<Object> search(SearchCriteria criteria) {
        List<Object> resultList = new ArrayList<>();
        Map<String, Object> whereConditions = new HashMap<>();
        whereConditions.put("name", criteria.getSearchTerm());

        try {
            MapperResultSet mapperResultSet = ((RequetesMySQL) requetesDB).selectWhere("music", whereConditions);
            for (Map<String, Object> rowData : mapperResultSet.getListData()) {
                Music music = new Music(
                        (Integer) rowData.get("id"),
                        (String) rowData.get("name"),
                        (int) rowData.get("artist"),
                        (int) rowData.get("duration"),
                        (byte[]) rowData.get("musicFile")
                );
                resultList.add(music);
            }
        } catch (ExceptionDB e) {
            // Handle the exception properly
            e.printStackTrace();
        }
        return resultList;
    }

}