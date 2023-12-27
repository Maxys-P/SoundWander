package com.sw.dao.boiteAOutils;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.nio.file.Files;
import java.nio.file.Path;

public class PlayMusicFromBD {
    private static MediaPlayer mediaPlayer = null; // Initialize to null
    private static Path tempMp3 = null; // Track temporary file

    public static void playMusicFromBD(byte[] musicData) {
        try {
            // Dispose existing media player and create a new one only if needed
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.dispose();
            }
            tempMp3 = Files.createTempFile("play", ".mp3");
            Files.write(tempMp3, musicData);
            Media hit = new Media(tempMp3.toUri().toString());
            mediaPlayer = new MediaPlayer(hit);
            mediaPlayer.play();
        } catch (Exception ex) {
            System.err.println("Error playing music: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void pauseMusic() {
        if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.pause();
        }
    }

    public static void resumeMusic() {
        if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PAUSED) {
            mediaPlayer.play();
        }
    }

    public static void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
            mediaPlayer = null;
            tempMp3 = null; // Reset the temp file reference
        }
    }
}
