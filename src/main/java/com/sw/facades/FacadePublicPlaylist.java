package com.sw.facades;

import com.sw.dao.DAOPlaylistMusic;
import com.sw.classes.PlaylistMusic;
import com.sw.dao.factories.FactoryDAO;

public class FacadePublicPlaylist extends Facade{
    protected FactoryDAO f = FactoryDAO.getInstanceofFactoryDAO();
    private DAOPlaylistMusic daoPlaylistMusic = f.getDAOPlaylistMusic();

    private static FacadePublicPlaylist instance = null;

    public static FacadePublicPlaylist getInstance() {
        if (instance == null) {
            instance = new FacadePublicPlaylist();
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

}
