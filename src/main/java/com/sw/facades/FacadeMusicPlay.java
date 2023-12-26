package com.sw.facades;

import com.sw.classes.Music;
import com.sw.dao.DAOMusic;
import com.sw.dao.boiteAOutils.PlayMusicFromBD;
import com.sw.dao.factories.FactoryDAO;

public class FacadeMusicPlay extends Facade{
    private static FacadeMusicPlay instance = null;
    private DAOMusic daoMusic;
    private Music currentMusic;

    private FacadeMusicPlay(){
        this.daoMusic = FactoryDAO.getInstanceofFactoryDAO().getInstanceofDAOMusic();
    }

    public static FacadeMusicPlay getInstance(){
        if(instance == null){
            instance = new FacadeMusicPlay();
        }
        return instance;
    }
    public Music playMusic(int id) throws Exception {
        try {
            Music music = daoMusic.getMusicById(id); // Retrieve music based on ID
            if (music != null) {
                byte[] musicData = music.getMusicFile(); // Get the music file data
                if (musicData != null) {
                    PlayMusicFromBD.playMusicFromBD(musicData); // Play music
                    currentMusic = music; // Update the current music
                } else {
                    System.out.println("No music data found for the selected music.");
                }
            } else {
                System.out.println("Music not found for the selected ID.");
            }
            return music; // Return the current music object
        } catch (Exception e) {
            throw new Exception("Error during music playback", e);
        }
    }


    public void stopMusic(){
        PlayMusicFromBD.stopMusic();
    }
    public Music playNextMusic(int currentId) throws Exception {
        try {
            Music nextMusic = daoMusic.getNextMusic(currentId);  // Attempt to get the next music
            if (nextMusic != null) {
                byte[] musicData = nextMusic.getMusicFile();
                if (musicData != null) {
                    PlayMusicFromBD.playMusicFromBD(musicData); // Play the next music
                    currentMusic = nextMusic; // Update current music
                    return nextMusic;
                } else {
                    System.out.println("No music data found for the next music.");
                }
            } else {
                System.out.println("Next music not found for the selected ID.");
            }
            return null; // Return null or handle it as required
        } catch (Exception e) {
            throw new Exception("Error when playing the next music", e);
        }
    }


    public Music playPreviousMusic(int id) throws Exception {
        try {
            Music previousMusic = daoMusic.getPreviousMusic(id);  // Attempt to get the previous music
            if (previousMusic != null) {
                byte[] musicData = previousMusic.getMusicFile();
                if (musicData != null) {
                    PlayMusicFromBD.playMusicFromBD(musicData); // Play the previous music
                    currentMusic = previousMusic; // Update current music
                    return previousMusic;
                } else {
                    System.out.println("No music data found for the previous music.");
                }
            } else {
                System.out.println("Previous music not found for the selected ID.");
            }
            return null; // Return null or handle it as required
        } catch (Exception e) {
            throw new Exception("Error when playing the previous music", e);
        }
    }

    public Music getCurrentMusic(){
        return currentMusic;
    }

    public void pauseMusic(){
        PlayMusicFromBD.pauseMusic();
    }

    public void resumeMusic(){
        PlayMusicFromBD.resumeMusic();
    }



    public void addPrivatePlaylist(String name){
        daoMusic.addPrivatePlaylist(name);
    }
}
