package com.sw.dao.daoMysql;

import com.sw.classes.Music;
import com.sw.dao.DAOMusic;
import com.sw.dao.requetesDB.RequetesMySQL;

public class DAOMusicMySQL extends DAOMusic {

    public DAOMusicMySQL() {
        super();
        this.requetesDB = new RequetesMySQL();
    }

    public Music createMusic(String name, String Artiste, int duration){
        return null;
    }
    @Override
    public Music getMusicByName(String name) {
        return null;
    }

    @Override
    public Music getNextMusic(int id) {
        return null;
    }

    @Override
    public Music getPreviousMusic(int id) {
        return null;
    }

    @Override
    public void addPrivatePlaylist(String name) {
    }


}
