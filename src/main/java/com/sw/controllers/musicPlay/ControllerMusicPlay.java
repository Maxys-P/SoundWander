package com.sw.controllers.musicPlay;

import com.sw.classes.Music;
import com.sw.classes.Artist;
import com.sw.classes.User;
import com.sw.exceptions.ExceptionBadPage;
import com.sw.facades.FacadeArtist;
import com.sw.controllers.Controller;
import com.sw.facades.FacadeMusic;
import com.sw.dao.boiteAOutils.PlayMusicFromBD;
import com.sw.facades.FacadePrivatePlaylist;
import com.sw.facades.FacadeUser;
import javafx.animation.AnimationTimer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;


public class ControllerMusicPlay extends Controller {
    private StringProperty currentSongTitle = new SimpleStringProperty("Aucune chanson à l'écoute");
    @FXML private Label songTitle;
    @FXML private Button boutonPlay;
    @FXML private Button boutonPause;
    @FXML private Button boutonNext;
    @FXML private Button boutonPrevious;
    @FXML private Button boutonAddPrivatePlaylist;
    @FXML private Button boutonRetour;
    @FXML private ProgressBar songProgressBar;
    @FXML private VBox musicFooter;
    @FXML private Button boutonAddRemovePrivatePlaylist;
    @FXML private ImageView imageAddRemovePrivatePlaylist;
    private AnimationTimer progressTimer;
    private boolean isPlaying = false; // Tracks whether the music is paused
    private boolean isMusicInPrivatePlaylist(Music music) {
        return userFacade.getCurrentUser().getPrivatePlaylist().contains(music);
    }

    private FacadeMusic musicPlayFacade;
    private FacadeArtist artistFacade;
    private FacadeUser userFacade;
    private FacadePrivatePlaylist privatePlaylistFacade;

    /**
     * Initializes the music play controller. It sets up listeners for changes to the playing status, song details,
     * and user interactions with the music progress bar. It also initializes the progress timer for the song.
     */
    @FXML public void initialize() {

        musicPlayFacade = FacadeMusic.getInstance();
        artistFacade = FacadeArtist.getInstance();
        userFacade = FacadeUser.getInstance();
        privatePlaylistFacade = FacadePrivatePlaylist.getInstance();
        musicPlayFacade.isPlayingProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            isPlaying = newValue; // Update local isPlaying status
        });
        songTitle.textProperty().bind(currentSongTitle);
        musicPlayFacade.currentMusicProperty().addListener((obs, oldMusic, newMusic) -> {
            updateSongDetails(newMusic);
            updateAddRemoveButton();
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
        updateAddRemoveButton();


    }

    /**
     * Handles the play button click event. It toggles the music play and pause status.
     * It plays the first song if no music is currently playing, or resumes the current music if it's paused.
     * It updates the UI elements to reflect the current state of music playback.
     *
     * @throws Exception if there is an issue controlling the music playback.
     */
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
    /**
     * Updates the song details displayed in the UI, including the artist and song name.
     * This method is called whenever the current song changes.
     *
     * @param music the Music object for which details are to be displayed.
     */
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

    /**
     * Handles the next button click event to play the next song in the queue.
     * It retrieves the next song, updates the song progress, and updates the UI to reflect the new song.
     * It handles the case where there is no next song to play.
     */
    @FXML
    private void handleNext() {
        try {
            // Get the ID of the currently playing music
            int currentMusicId = musicPlayFacade.getCurrentMusic() != null ? musicPlayFacade.getCurrentMusic().getId() : 0;
            // Call playNextMusic with the correct ID
            Music nextMusic = musicPlayFacade.playNextMusic(currentMusicId);
            updateProgress(0, 0);
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

    /**
     * Handles the previous button click event to play the previous song in the queue.
     * It retrieves the previous song, updates the song progress, and updates the UI to reflect the new song.
     * It handles the case where there is no previous song to play.
     */
    @FXML
    private void handlePrevious() {
        try {
            // Get the ID of the currently playing music
            int currentMusicId = musicPlayFacade.getCurrentMusic() != null ? musicPlayFacade.getCurrentMusic().getId() : 0;
            // Call playPreviousMusic with the correct ID
            Music previousMusic = musicPlayFacade.playPreviousMusic(currentMusicId);
            updateProgress(0, 0);
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

    /**
     * Updates the progress bar based on the current time and total duration of the song.
     *
     * @param currentTime the current time of the song in seconds.
     * @param totalDuration the total duration of the song in seconds.
     */
    public void updateProgress(double currentTime, double totalDuration) {
        if (songProgressBar != null && totalDuration > 0) {
            songProgressBar.setProgress(currentTime / totalDuration);
        }
    }
    /**
     * Starts the timer that updates the progress bar based on the current playback position of the song.
     * The timer continuously checks and updates the song progress.
     */
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
    /**
     * Handles the return button click event. It navigates back to the previous scene or window.
     *
     * @throws Exception if there is an issue returning to the previous scene or window.
     */
    @FXML
    private void handleRetour() throws Exception {
        System.out.println("click sur retour");
        returnToLastScene(boutonRetour);
    }
    /**
     * Updates the add/remove from private playlist button based on whether the current song is in the private playlist.
     * It changes the button's image to indicate the current status.
     */
    private void updateAddRemoveButton() {
        System.out.println("[controller] updateAddRemoveButton");
        Music currentMusic = musicPlayFacade.getCurrentMusic();
        User currentUser = userFacade.getCurrentUser();

        if (currentMusic != null && currentUser != null) {
            boolean musicInPlaylist = currentUser.getPrivatePlaylist().stream()
                    .anyMatch(music -> music.getId() == currentMusic.getId());

            Image image;
            if (musicInPlaylist) {
                System.out.println("[controller] La musique est dans la playlist privée : il faut afficher Supprimer");
                image = new Image(getClass().getResourceAsStream("/images/likePlein.png"));
            } else {
                System.out.println("[controller] La musique n'est pas dans la playlist privée : il faut afficher Ajouter");
                image = new Image(getClass().getResourceAsStream("/images/likeVide.png"));
            }
            imageAddRemovePrivatePlaylist.setImage(image);
        }
    }
    /**
     * Handles the add/remove from private playlist button click event.
     * It adds the current song to the user's private playlist if it's not already in it,
     * or removes it if it is in the playlist. It updates the button's status accordingly.
     */
    @FXML
    private void handleAddRemovePrivatePlaylist() {
        Music currentMusic = musicPlayFacade.getCurrentMusic();
        User currentUser = userFacade.getCurrentUser();
        if (currentMusic != null && currentUser != null) {
            try {
                boolean musicInPlaylist = currentUser.getPrivatePlaylist().stream()
                        .anyMatch(music -> music.getId() == currentMusic.getId());

                if (musicInPlaylist) {
                    System.out.println("[controller] Suppression de la musique " + currentMusic + " de la playlist privée de " + currentUser);
                    privatePlaylistFacade.deleteMusicInPrivatePlaylist(currentUser, currentMusic);
                    System.out.println("[controller] Musique supprimée de la playlist privée");
                } else {
                    System.out.println("[controller] Ajout de la musique " + currentMusic + " à la playlist privée de " + currentUser);
                    privatePlaylistFacade.addMusicToPrivatePlaylist(currentUser, currentMusic);
                    System.out.println("[controller] Musique ajoutée à la playlist privée");
                }
                System.out.println("[controller] Mise à jour du bouton");
                updateAddRemoveButton();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}