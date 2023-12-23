package com.sw.dao.daoMysql;

import com.sw.classes.Music;
import com.sw.dao.DAOMusic;
import com.sw.dao.requetesDB.RequetesMySQL;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        return null;
    }

    @Override
    public Music getMusicById(int musicId) throws Exception {
        String table = "music";
        String[] columns = {"id", "name", "artist", "duration", "musicFile"};
        String[] whereColumns = {"id"};
        Object[] whereValues = {musicId};

        try (ResultSet rs = ((RequetesMySQL)requetesDB).selectWhere(table, columns, whereColumns, whereValues)) {
            if (rs.next()) {
                int retrievedId = rs.getInt("id");
                String name = rs.getString("name");
                int artist = rs.getInt("artist");
                int duration = rs.getInt("duration");
                byte[] musicFile = rs.getBytes("musicFile");

                Music music = new Music(retrievedId, name, artist, duration, musicFile);
                return music;
            }
            return null;
        } catch (SQLException e) {
            throw new Exception("Erreur lors de la récupération de la musique par ID", e);
        }
    }


    @Override
    public Music getNextMusic(int currentId) throws Exception {
        String table = "music";
        String[] columns = {"id", "name", "artist", "duration"};
        String[] whereColumns = {"id"};
        Object[] whereValues = {currentId + 1}; // Suppose que l'ID suivant est simplement l'actuel + 1

        try (ResultSet rs = ((RequetesMySQL)requetesDB).selectWhere(table, columns, whereColumns, whereValues)) {
            if (rs.next()) {
                int nextId = rs.getInt("id");
                String name = rs.getString("name");
                int artist = rs.getInt("artist");
                int duration = rs.getInt("duration");
                byte[] musicFile = rs.getBytes("musicFile");

                Music music = new Music(nextId, name, artist, duration, musicFile);
                return music;
            }
            return null;
        } catch (SQLException e) {
            throw new Exception("Erreur lors de la récupération de la musique suivante", e);
        }
    }

    @Override
    public Music getPreviousMusic(int id) {
        return null;
    }

    @Override
    public void addPrivatePlaylist(String name) {
    }


}
