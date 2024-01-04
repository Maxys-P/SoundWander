package com.sw.facades;

import com.sw.dao.DAOPlaylistMusic;
import com.sw.classes.PlaylistMusic;
import com.sw.dao.factories.FactoryDAO;

public class FacadePlaylistMusic extends Facade{
    protected FactoryDAO f = FactoryDAO.getInstanceofFactoryDAO();
    private DAOPlaylistMusic daoPlaylistMusic = f.getDAOPlaylistMusic();

    private static FacadePlaylistMusic instance = null;

    public static FacadePlaylistMusic getInstance() {
        if (instance == null) {
            instance = new FacadePlaylistMusic();
        }
        return instance;
    }

    public PlaylistMusic getPlaylistMusicByName(String name) throws Exception {
        try {
            return daoPlaylistMusic.getPlaylistMusicByName(name);
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean addPlaylistMusic(String playlistName, int music) throws Exception {
        try {
            return daoPlaylistMusic.addMusicToPlaylist(playlistName, music);
        } catch (Exception e) {
            throw e;
        }
    }

}
