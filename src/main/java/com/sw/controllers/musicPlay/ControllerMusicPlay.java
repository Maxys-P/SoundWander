package com.sw.controllers.musicPlay;
import com.sw.classes.Music;
import com.sw.classes.Artist;
import com.sw.facades.FacadeArtist;
import com.sw.controllers.Controller;
import com.sw.facades.FacadeMusic;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;



public class ControllerMusicPlay extends Controller {
    private StringProperty currentSongTitle = new SimpleStringProperty("Aucune chanson à l'écoute");
    @FXML
    private Label songTitle;

    @FXML
    private Button boutonPlay;

    @FXML
    private Button boutonPause;

    @FXML
    private Button boutonNext;

    @FXML
    private Button boutonPrevious;

    @FXML
    private Button boutonAddPrivatePlaylist;

    private boolean isPlaying = false; // Tracks whether the music is paused

    private FacadeMusic musicPlayFacade;
    private FacadeArtist artistFacade;

    @FXML
    public void initialize() {

        musicPlayFacade = FacadeMusic.getInstance();
        artistFacade = FacadeArtist.getInstance();
        musicPlayFacade.isPlayingProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            isPlaying = newValue; // Update local isPlaying status
            boutonPlay.setText(newValue ? "Pause" : "Play"); // Update button text
        });
        songTitle.textProperty().bind(currentSongTitle);
        musicPlayFacade.currentMusicProperty().addListener((obs, oldMusic, newMusic) -> {
            updateSongDetails(newMusic);
        });

        updateSongDetails(musicPlayFacade.getCurrentMusic());

    }
    @FXML
    private void handlePlay() throws Exception {
        Music music = musicPlayFacade.getCurrentMusic();
        if (isPlaying) {
            musicPlayFacade.pauseMusic();
            boutonPlay.setVisible(true);
            boutonPause.setVisible(false);
            isPlaying = false;
        } else {
            if (music == null) {
                // Charger et jouer la première chanson
                music = musicPlayFacade.playMusic(1);
            } else {
                musicPlayFacade.resumeMusic();
            }
            boutonPlay.setVisible(false);
            boutonPause.setVisible(true);
            isPlaying = true;
        }
        updateSongDetails(music);
    }
    private void updateSongDetails(Music music) {
        if (music != null) {
            try {
                int artistId = music.getArtist();
                Artist artist = artistFacade.getArtistById(artistId);
                String artistName = artist.getPseudo();
                System.out.println("Playing music: artiste :" + artistName + " - nom de la music : " + music.getName());
                currentSongTitle.set(music.getName() + " - " + artistName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            currentSongTitle.set("Aucune musique à l'écoute");
        }
    }

    @FXML
    private void handleNext() {
        try {
            // Get the ID of the currently playing music
            int currentMusicId = musicPlayFacade.getCurrentMusic() != null ? musicPlayFacade.getCurrentMusic().getId() : 0;
            // Call playNextMusic with the correct ID
            Music nextMusic = musicPlayFacade.playNextMusic(currentMusicId);
            if (nextMusic != null) {
                updateSongDetails(nextMusic);  // Update the song details on UI
                isPlaying = true;  // Set the state to playing
                boutonPlay.setVisible(false);
                boutonPause.setVisible(true);;  // Change the button text to indicate playing state
            } else {
                System.out.println("No next music to play.");  // Handle end of playlist or error
            }
        } catch (Exception e) {
            e.printStackTrace();  // Handle the exception
            // You might want to update the UI or user about the error
        }
    }
    @FXML
    private void handlePrevious() {
        try {
            // Get the ID of the currently playing music
            int currentMusicId = musicPlayFacade.getCurrentMusic() != null ? musicPlayFacade.getCurrentMusic().getId() : 0;
            // Call playPreviousMusic with the correct ID
            Music previousMusic = musicPlayFacade.playPreviousMusic(currentMusicId);
            if (previousMusic != null) {
                updateSongDetails(previousMusic);  // Update the song details on UI
                isPlaying = true;  // Set the state to playing
                boutonPlay.setVisible(false);
                boutonPause.setVisible(true);  // Change the button text to indicate playing state
            } else {
                System.out.println("No previous music to play.");  // Handle end of playlist or error
            }
        } catch (Exception e) {
            e.printStackTrace();  // Handle the exception
            // You might want to update the UI or user about the error
        }
    }
    @FXML
    private void handleAddPrivatePlaylist() {}

}