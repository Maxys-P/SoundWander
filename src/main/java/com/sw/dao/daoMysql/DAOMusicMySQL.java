package com.sw.dao.daoMysql;

import com.sw.classes.Music;
import com.sw.dao.DAOMusic;
import com.sw.dao.boiteAOutils.MapperResultSet;
import com.sw.dao.requetesDB.RequetesMySQL;
import java.util.HashMap;
import java.util.Map;

public class DAOMusicMySQL extends DAOMusic {

    public DAOMusicMySQL() {
        super();
        this.requetesDB = new RequetesMySQL();
    }

    public Music createMusic(String name, String Artist, int duration){
        return null;
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


}