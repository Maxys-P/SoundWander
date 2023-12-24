package com.sw.controllers.musicPlay;
import com.sw.classes.Music;
import com.sw.controllers.Controller;
import com.sw.facades.FacadeMusicPlay;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;


public class ControllerMusicPlay extends Controller {

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

    private boolean isPaused = false; // Tracks whether the music is paused

    private FacadeMusicPlay musicPlayFacade;

    @FXML
    public void initialize() {
        musicPlayFacade = FacadeMusicPlay.getInstance();
    }

    @FXML
    private void handlePlay() {
        try {
            if (isPaused) {
                // Resume music if it's currently paused
                musicPlayFacade.resumeMusic();
                isPaused = false;
                boutonPlay.setText("Pause");
            } else {
                // Play music if it's not paused
                Music music = musicPlayFacade.playMusic(1); // Pass the desired music ID here
                String artistName = String.valueOf(music.getArtist());
                System.out.println("Playing music: " + artistName + " - " + music.getName());
                songTitle.setText(music.getName() + " - " + artistName);
                boutonPlay.setText("Pause");
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
    }


    @FXML
    private void handlePause() {
        if (!isPaused) {
            // Pause music if it's currently playing
            musicPlayFacade.pauseMusic();
            isPaused = true;
            boutonPlay.setText("Play");
        } else {
            // Resume music if it's currently paused
            handlePlay();
        }
    }
    @FXML
    private void handleNext() {
        try{
            musicPlayFacade.playNextMusic(1);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
    }
    @FXML
    private void handlePrevious() {
        try{
            musicPlayFacade.playPreviousMusic(1);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
    }
    @FXML
    private void handleAddPrivatePlaylist() {
    }

}
