package com.sw.facades;

import com.sw.classes.Music;
import com.sw.dao.DAOMusic;
import com.sw.dao.boiteAOutils.PlayMusicFromBD;
import com.sw.dao.factories.FactoryDAO;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;



import java.util.List;

public class FacadeMusic extends Facade{
    private ObjectProperty<Music> currentMusicProperty = new SimpleObjectProperty<>();
    private SimpleBooleanProperty isPlayingProperty = new SimpleBooleanProperty(false);
    private static FacadeMusic instance = null;
    private DAOMusic daoMusic;
    private Music currentMusic;

    private FacadeMusic(){
        this.daoMusic = FactoryDAO.getInstanceofFactoryDAO().getInstanceofDAOMusic();
    }

    public static FacadeMusic getInstance() {
        if (instance == null) {
            instance = new FacadeMusic();
        }
        return instance;
    }

    public Music getCurrentMusic(){
        return currentMusicProperty.get();
    }
    public ReadOnlyObjectProperty<Music> currentMusicProperty() {
        return currentMusicProperty;
    }
    // Method to get the isPlayingProperty for listeners
    public SimpleBooleanProperty isPlayingProperty() {
        return isPlayingProperty;
    }

    public Music playMusic(int id) throws Exception {
        try {
            Music music = daoMusic.getMusicById(id); // Retrieve music based on ID
            if (music != null) {
                byte[] musicData = music.getMusicFile(); // Get the music file data
                currentMusicProperty.set(music); // Update the current music property
                isPlayingProperty.set(true); // Update the isPlaying property
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
                currentMusicProperty.set(nextMusic); // Update the current music property
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
                currentMusicProperty.set(previousMusic); // Update the current music property
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
    public void pauseMusic(){
        PlayMusicFromBD.pauseMusic();
        isPlayingProperty.set(false);
    }

    public void resumeMusic(){
        PlayMusicFromBD.resumeMusic();
    }

    public void addPrivatePlaylist(String name){daoMusic.addPrivatePlaylist(name);}

    public void seek(double seconds) {
        if (currentMusic != null) {
            PlayMusicFromBD.seek(seconds);
        }
    }





    public void addMusic(String name, int duration, String filePath) {
        int artist = currentUser.getId();
        daoMusic.addMusic(name, artist, duration, filePath);
    }

    public void removeMusic(int id) {
        daoMusic.removeMusic(id);
    }

    public List<Music> getMusicByUserId() throws Exception {
        int userId = currentUser.getId();
        return daoMusic.getMusicByUserId(userId);
    }

    /**
     * Récupère une music par son ID.
     * @param id L'identifiant de la music à récupérer.
     * @return music correspondant, ou null si aucune proposition avec cet ID n'existe.
     * @throws Exception si une erreur survient pendant la récupération.
     */
    public Music getMusicById(int id) throws Exception {
        try {
            return daoMusic.getMusicById(id);
        } catch (Exception e) {
            // Gérer l'exception ou la propager
            throw new Exception("Erreur lors de la récupération de la musique : " + e.getMessage(), e);
        }
    }

}
