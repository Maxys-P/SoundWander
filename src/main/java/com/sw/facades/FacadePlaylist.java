package com.sw.facades;

import com.sw.classes.Playlist;
import com.sw.dao.DAOPlaylist;
import com.sw.dao.factories.FactoryDAO;

public class FacadePlaylist extends Facade{

    protected FactoryDAO f = FactoryDAO.getInstanceofFactoryDAO();
    private DAOPlaylist daoPlaylist = f.getDAOPlaylist();
    private static FacadePlaylist instance = null;
    public static FacadePlaylist getInstance() {
        if (instance == null) {
            instance = new FacadePlaylist();
        }
        return instance;
    }

    public Playlist getPlaylistByName(String name) throws Exception {
        try {
            return daoPlaylist.getPLaylistByName(name);
        } catch (Exception e) {
            throw e;
        }
    }

    public void addPublicPlaylist(String name, String country, String continent) throws Exception {
        try {
            daoPlaylist.addPlaylist(name, country);
        } catch (Exception e) {
            throw e;
        }
    }
}
