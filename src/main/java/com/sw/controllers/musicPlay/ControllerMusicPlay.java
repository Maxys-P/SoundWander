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
    private Button boutonNext;

    @FXML
    private Button boutonPrevious;

    @FXML
    private Button boutonAddPrivatePlaylist;

    private boolean isPlaying = false; // Tracks whether the music is paused

    private FacadeMusicPlay musicPlayFacade;

    @FXML
    public void initialize() {

        musicPlayFacade = FacadeMusicPlay.getInstance();
        boutonPlay.setText(isPlaying ? "Pause" : "Play");
    }
    @FXML
    private void handlePlay() {
        try {
            // No need to reload music if we are just pausing or resuming
            if (isPlaying) {
                musicPlayFacade.pauseMusic();
                boutonPlay.setText("Play");
                isPlaying = false;
            } else {
                if(musicPlayFacade.getCurrentMusic() == null) {
                    musicPlayFacade.playMusic(1); // Load the first song if nothing is loaded
                } else {
                    musicPlayFacade.resumeMusic(); // Resume the current song
                }
                boutonPlay.setText("Pause");
                isPlaying = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exception as needed
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
