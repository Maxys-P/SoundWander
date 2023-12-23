package com.sw.facades;

import com.sw.classes.Music;
import com.sw.dao.DAOMusic;
import com.sw.dao.boiteAOutils.PlayMusicFromBD;
import com.sw.dao.factories.FactoryDAO;

public class FacadeMusicPlay extends Facade{

    protected DAOMusic daoMusic;

    private static FacadeMusicPlay instance = null;

    public static FacadeMusicPlay getInstance(){
        if(instance == null){
            instance = new FacadeMusicPlay();
        }
        return instance;
    }

    public void playMusic(int id) throws Exception{
        Music music = daoMusic.getMusicById(id);
        if (music != null){
            byte[] musicData = music.getMusicFile();
            if (musicData != null) {
                PlayMusicFromBD.playMusicFromBD(musicData);
            } else {
                System.out.println("No music data found for the selected music.");
            }
        } else {
            System.out.println("Music not found for the selected ID.");
        }
    }

    public Music playNextMusic(int id) throws Exception{
        return daoMusic.getNextMusic(id);
    }

    public Music playPreviousMusic(int id){
        return daoMusic.getPreviousMusic(id);
    }

    public void addPrivatePlaylist(String name){
        daoMusic.addPrivatePlaylist(name);
    }
}
