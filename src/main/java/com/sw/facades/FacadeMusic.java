package com.sw.facades;

import com.sw.classes.Music;
import com.sw.commons.DataHolder;
import com.sw.dao.DAOMusic;
import com.sw.dao.DAOPlaylistMusic;
import com.sw.dao.boiteAOutils.PlayMusicFromBD;
import com.sw.dao.factories.FactoryDAO;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;



import java.util.List;

public class FacadeMusic extends Facade{
    protected FactoryDAO f = FactoryDAO.getInstanceofFactoryDAO();
    private ObjectProperty<Music> currentMusicProperty = new SimpleObjectProperty<>();
    private SimpleBooleanProperty isPlayingProperty = new SimpleBooleanProperty(false);
    private static FacadeMusic instance = null;
    private DAOMusic daoMusic;
    private DAOPlaylistMusic daoPlaylistMusic;
    private Music currentMusic;
    private double lastPosition = 0.0;

    private FacadeMusic(){
        this.daoMusic = f.getInstanceofDAOMusic();
        this.daoPlaylistMusic = f.getDAOPlaylistMusic();
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
    public Music playNextMusic(int currentMusicId) throws Exception {
        try {
            int playlistId = DataHolder.getCurrentPlaylistId();  // Retrieve the current playlist id
            List<Music> playlistMusics = daoPlaylistMusic.getAllMusicByPlaylist(playlistId); // Fetch all the songs in the playlist
            // Find the current song's index in the playlist
            int currentIndex = -1;
            for (int i = 0; i < playlistMusics.size(); i++) {
                if (playlistMusics.get(i).getId() == currentMusicId) {
                    currentIndex = i;
                    break;
                }
            }
            // Determine the next song to play
            if (currentIndex >= 0 && currentIndex < playlistMusics.size() - 1) { // Ensure there is a next song
                Music nextMusic = playlistMusics.get(currentIndex + 1);
                byte[] musicData = nextMusic.getMusicFile();

                if (musicData != null) {
                    currentMusicProperty.set(nextMusic); // Update the current music property
                    PlayMusicFromBD.playMusicFromBD(musicData); // Play the next music
                    currentMusic = nextMusic; // Update current music
                    return nextMusic;
                } else {
                    System.out.println("No music data found for the next music.");
                }
            } else {
                // Handle the end of the playlist or no next song found
                System.out.println("Reached end of playlist or next music not found.");
            }
            return null; // Return null or handle it as required
        } catch (Exception e) {
            throw new Exception("Error when playing the next music", e);
        }
    }
    public Music playPreviousMusic(int currentMusicId) throws Exception {
        try {
            int playlistId = DataHolder.getCurrentPlaylistId(); // Retrieve the current playlist id
            List<Music> playlistMusics = daoPlaylistMusic.getAllMusicByPlaylist(playlistId); // Fetch all the songs in the playlist
            // Find the current song's index in the playlist
            int currentIndex = -1;
            for (int i = 0; i < playlistMusics.size(); i++) {
                if (playlistMusics.get(i).getId() == currentMusicId) {
                    currentIndex = i;
                    break;
                }
            }
            // Determine the previous song to play
            if (currentIndex > 0) { // Ensure there is a previous song
                Music previousMusic = playlistMusics.get(currentIndex - 1);
                byte[] musicData = previousMusic.getMusicFile();

                if (musicData != null) {
                    currentMusicProperty.set(previousMusic); // Update the current music property
                    PlayMusicFromBD.playMusicFromBD(musicData); // Play the previous music
                    currentMusic = previousMusic; // Update current music
                    return previousMusic;
                } else {
                    System.out.println("No music data found for the previous music.");
                }
            } else {
                // Handle the beginning of the playlist or no previous song found
                System.out.println("Reached beginning of playlist or previous music not found.");
            }
            return null; // Return null or handle it as required
        } catch (Exception e) {
            throw new Exception("Error when playing the previous music", e);
        }
    }
    public void pauseMusic() {
        if (currentMusic != null) {
            lastPosition = PlayMusicFromBD.getCurrentTime(); // Save the current time before pausing
            PlayMusicFromBD.pauseMusic();
            isPlayingProperty.set(false);
        }
    }

    public void resumeMusic() {
        if (currentMusic != null) {
            // Only seek if the player is not at the correct position already
            if (Math.abs(PlayMusicFromBD.getCurrentTime() - lastPosition) > 1) {
                PlayMusicFromBD.seek(lastPosition);
            }
            PlayMusicFromBD.resumeMusic();
            isPlayingProperty.set(true);
        }
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
    /*
    public List<Music> getMusicByUserId() throws Exception {
        int userId = currentUser.getId();
        return daoMusic.getMusicByUserId(userId);
    }*/
    public List<Music> getMusicByUserId(int userId) throws Exception {
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
