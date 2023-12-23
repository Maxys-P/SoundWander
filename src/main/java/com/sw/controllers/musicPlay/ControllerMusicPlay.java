package com.sw.controllers.musicPlay;
import com.sw.classes.Music;
import com.sw.controllers.Controller;
import com.sw.facades.FacadeMusicPlay;

public class ControllerMusicPlay extends Controller {

    final FacadeMusicPlay musicPlayFacade = FacadeMusicPlay.getInstance();

    private void handlePlay() {
        try {
            musicPlayFacade.playMusic(1); // You can pass the desired music ID here
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
    }

    private void handlePause() {
    }

    private void handleNext() {
    }

    private void handlePrevious() {
    }

    private void handleAddPrivatePlaylist() {
    }

}
