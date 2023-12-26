package com.sw.dao;

import com.sw.classes.Music;
import com.sw.classes.MusicInfo;

import java.util.List;

public abstract class DAOMusic extends DAO {

    public DAOMusic() {super("music");}

    public abstract Music getMusicByName(String name);


    public abstract Music getMusicById(int id) throws Exception;

    public abstract Music getNextMusic(int id) throws Exception;

    public abstract Music getPreviousMusic(int id);

    public void addPrivatePlaylist(String name) {
    }
    public void addMusic(String name, int artist, int duration, String filePath) {
    }
    public void removeMusic(int id) {
    }
    public abstract List<MusicInfo> getMusicByUserId(int userId) throws Exception; {
    }

}
