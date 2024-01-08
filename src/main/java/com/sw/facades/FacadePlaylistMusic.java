package com.sw.facades;

import com.sw.classes.Music;
import com.sw.dao.DAOPlaylistMusic;
import com.sw.classes.PlaylistMusic;
import com.sw.dao.factories.FactoryDAO;

import java.util.List;
import java.util.Map;

public class FacadePlaylistMusic extends Facade{
    private static FacadePlaylistMusic instance = null;
    protected FactoryDAO f = FactoryDAO.getInstanceofFactoryDAO();
    private DAOPlaylistMusic daoPlaylistMusic = f.getDAOPlaylistMusic();
    public static FacadePlaylistMusic getInstance() {
        if (instance == null) {
            instance = new FacadePlaylistMusic();
        }
        return instance;
    }

    public PlaylistMusic addPlaylistMusic(String playlistName, int music) throws Exception {
        try {
            return daoPlaylistMusic.addMusicToPlaylist(playlistName, music);
        } catch (Exception e) {
            throw e;
        }
    }

    public List<PlaylistMusic> loadPlaylistMusic(String continent) throws Exception {
        try {
            daoPlaylistMusic.getPlaylistMusicByContinent(continent);
        } catch (Exception e) {
            throw e;
        }
        return null;
    }

    public Map<String, List<PlaylistMusic>> getPlaylistMusicByContinent(String continent) throws Exception {
        try {
            // Call the DAO method to get the playlist music by continent
            return daoPlaylistMusic.getPlaylistMusicByContinent(continent);
        } catch (Exception e) {
            // Handle or throw the exception as needed
            throw new Exception("Error retrieving playlist music by continent: " + e.getMessage(), e);
        }
    }

    public PlaylistMusic getPlaylistMusicByCountry(String country) throws Exception {
        try {
            // Call the DAO method to get the music by country
            return daoPlaylistMusic.getPlaylistMusicByCountry(country);
        } catch (Exception e) {
            // Handle or throw the exception as needed
            throw new Exception("Error retrieving music by country: " + e.getMessage(), e);
        }
    }

    public List<PlaylistMusic> getAllPlaylistMusic() throws Exception {
        try {
            // Call the DAO method to get all the playlist music
            return daoPlaylistMusic.getAllPlaylistMusic();
        } catch (Exception e) {
            // Handle or throw the exception as needed
            throw new Exception("Error retrieving all playlist music: " + e.getMessage(), e);
        }
    }
}
