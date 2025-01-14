package com.sw.dao;

import com.sw.classes.Music;
import com.sw.commons.SearchCriteria;
import com.sw.commons.Searchable;
import java.util.List;

public abstract class DAOMusic extends DAO implements Searchable {

    public DAOMusic() {super("music");}

    public abstract Music getMusicByName(String name);


    public abstract Music getMusicById(int id) throws Exception;

    public abstract Music getNextMusic(int id) throws Exception;

    public abstract Music getPreviousMusic(int id) throws Exception;

    public void addPrivatePlaylist(String name) {
    }
    public void addMusic(String name, int artist, int duration, String filePath) {
    }
    public void removeMusic(int id) {
    }
    public abstract List<Music> getMusicByUserId(int userId) throws Exception; {
    }

    @Override
    public abstract List<Object> search(SearchCriteria criteria);

}
