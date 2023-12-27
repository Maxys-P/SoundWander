package com.sw.facades;

import com.sw.classes.MusicInfo;
import com.sw.dao.DAOMusic;
import com.sw.dao.factories.FactoryDAO;

import java.util.List;

public class FacadeMusicBankManagement extends Facade {

    protected DAOMusic daoMusic;

    private static FacadeMusicBankManagement instance = null;

    private FacadeMusicBankManagement(){
        this.daoMusic = FactoryDAO.getInstanceofFactoryDAO().getInstanceofDAOMusic();
    }

    public static FacadeMusicBankManagement getInstance() {
        if (instance == null) {
            instance = new FacadeMusicBankManagement();
        }

        return instance;
    }

    public void addMusic(String name, int duration, String filePath) {
        int artist = currentUser.getId();
        daoMusic.addMusic(name, artist, duration, filePath);
    }

    public void removeMusic(int id) {
        daoMusic.removeMusic(id);
    }

    public List<MusicInfo> getMusicByUserId() throws Exception {
        int userId = currentUser.getId();
        return daoMusic.getMusicByUserId(userId);
    }

}
