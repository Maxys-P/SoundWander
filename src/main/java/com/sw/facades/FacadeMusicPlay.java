package com.sw.facades;

import com.sw.classes.Music;
import com.sw.dao.DAOMusic;
import com.sw.dao.daoMysql.DAOMusicMySQL;

public class FacadeMusicPlay extends Facade {

    protected DAOMusic daoMusic;

    private static FacadeMusicPlay instance = null;

    public static FacadeMusicPlay getInstance() {
        if (instance == null) {
            instance = new FacadeMusicPlay();
        }
        return instance;
    }

    public Music playMusic(String name) {
        return daoMusic.getMusicByName(name);
    }

    public Music playNextMusic(int id) throws Exception {
        return daoMusic.getNextMusic(id);
    }

    public Music playPreviousMusic(int id) {
        return daoMusic.getPreviousMusic(id);
    }

    public void addPrivatePlaylist(String name) {
        daoMusic.addPrivatePlaylist(name);
    }

}
