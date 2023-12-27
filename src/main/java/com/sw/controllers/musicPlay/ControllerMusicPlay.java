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
    private StringProperty currentSongTitle = new SimpleStringProperty("No song playing");
    @FXML
    private Label songTitle;

    @FXML
    private Button boutonPlay;

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
    private void handlePlay() {
        try {
            Music music = musicPlayFacade.getCurrentMusic();
            // No need to reload music if we are just pausing or resuming
            if (isPlaying) {
                musicPlayFacade.pauseMusic();
                boutonPlay.setText("Play");
                System.out.println("Music paused.");
                isPlaying = false;
            } else {
                if(music == null) {
                    //pour tester puise que l'on a pas de musique affichée
                    music = musicPlayFacade.playMusic(1); // Load the first song if nothing is loaded
                } else {
                    musicPlayFacade.resumeMusic(); // Resume the current song
                }
                boutonPlay.setText("Pause");
                isPlaying = true;
            }
            updateSongDetails(music);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exception as needed
        }
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
            currentSongTitle.set("No song playing");
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
                boutonPlay.setText("Pause");  // Change the button text to indicate playing state
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
                boutonPlay.setText("Pause");  // Change the button text to indicate playing state
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
