package com.sw.dao;

import com.sw.classes.Music;

public abstract class DAOMusic extends DAO {

    public DAOMusic() {super("music");}

    public abstract Music getMusicByName(String name);


    public abstract Music getMusicById(int id) throws Exception;

    public abstract Music getNextMusic(int id) throws Exception;

    public abstract Music getPreviousMusic(int id) throws Exception;

    public void addPrivatePlaylist(String name) {
    }
}
