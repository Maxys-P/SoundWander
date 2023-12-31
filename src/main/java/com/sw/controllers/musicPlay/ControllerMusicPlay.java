package com.sw.controllers.musicPlay;

import com.sw.classes.Music;
import com.sw.classes.Artist;
import com.sw.exceptions.ExceptionBadPage;
import com.sw.facades.FacadeArtist;
import com.sw.controllers.Controller;
import com.sw.facades.FacadeMusic;
import com.sw.dao.boiteAOutils.PlayMusicFromBD;
import javafx.animation.AnimationTimer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;


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

    @FXML
    private Button boutonRetour;

    @FXML
    private ProgressBar songProgressBar;
    private AnimationTimer progressTimer;
    @FXML
    private VBox musicFooter;


    private boolean isPlaying = false; // Tracks whether the music is paused

    private FacadeMusic musicPlayFacade;
    private FacadeArtist artistFacade;

    @FXML
    public void initialize() {

        musicPlayFacade = FacadeMusic.getInstance();
        artistFacade = FacadeArtist.getInstance();
        musicPlayFacade.isPlayingProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            isPlaying = newValue; // Update local isPlaying status
        });
        songTitle.textProperty().bind(currentSongTitle);
        musicPlayFacade.currentMusicProperty().addListener((obs, oldMusic, newMusic) -> {
            updateSongDetails(newMusic);
        });

        updateSongDetails(musicPlayFacade.getCurrentMusic());

        try {
            songProgressBar.setOnMouseClicked(event -> {
                if (musicPlayFacade.getCurrentMusic() != null) {
                    double mousePosition = event.getX();
                    double progressBarWidth = songProgressBar.getWidth();
                    double progress = mousePosition / progressBarWidth;
                    musicPlayFacade.seek(progress * musicPlayFacade.getCurrentMusic().getDuration()); // Implement the seek method in your facade
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Démarrer le timer ici aussi si nécessaire
        startProgressTimer();

    }


    @FXML
    private void handlePlay() throws Exception {
        Music music = musicPlayFacade.getCurrentMusic();
        if (isPlaying) {
            musicPlayFacade.pauseMusic();
            boutonPlay.setVisible(true);
            boutonPause.setVisible(false);
            progressTimer.stop();
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
            progressTimer.start();
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
    private void handleAddPrivatePlaylist() {
        FacadeMusic facadeMusic = FacadeMusic.getInstance();
        facadeMusic.addPrivatePlaylist("Ma playlist");
    }

    public void updateProgress(double currentTime, double totalDuration) {
        if (songProgressBar != null && totalDuration > 0) {
            songProgressBar.setProgress(currentTime / totalDuration);
        }
    }

    public void startProgressTimer() {
        progressTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                Music currentMusic = musicPlayFacade.getCurrentMusic();
                if (currentMusic != null && isPlaying) {
                    // Ici, vous devez obtenir la position actuelle de la lecture
                    double currentTime = PlayMusicFromBD.getCurrentTime();
                    double totalDuration = currentMusic.getDuration();
                    updateProgress(currentTime, totalDuration);
                }
            }
        };
        progressTimer.start();
    }

    @FXML
    private void songProgress() {
        System.out.println("click sur la barre de progression");
        songProgressBar.setOnMouseClicked(event -> {
            double mousePosition = event.getX();
            double progressBarWidth = songProgressBar.getWidth();
            double progress = mousePosition / progressBarWidth;
            PlayMusicFromBD.seek(progress * musicPlayFacade.getCurrentMusic().getDuration());
        });
    }

    @FXML
    private void handleRetour() throws Exception {
        System.out.println("click sur retour");
        returnToLastScene(boutonRetour);
    }

}