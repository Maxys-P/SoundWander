package com.sw.dao;

import com.sw.classes.Music;

public abstract class DAOMusic extends DAO {

    public DAOMusic() {super("music");}

    public abstract Music getMusicByName(String name);


    public abstract Music getNextMusic(int id);

    public abstract Music getPreviousMusic(int id);

    public void addPrivatePlaylist(String name) {
    }
}
