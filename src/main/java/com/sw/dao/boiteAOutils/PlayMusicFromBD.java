package com.sw.dao.boiteAOutils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javazoom.jl.player.Player;

public class PlayMusicFromBD {
    private static Player player;
    private static Thread playerThread;
    private static InputStream musicStream;
    private static byte[] musicData; // Store music data here
    private static long pausePosition; // Position where the playback was paused
    private static long totalLength; // Total length of the stream

    public static void playMusicFromBD(byte[] incomingData) {
        stopMusic(); // Stop previous music if any
        musicData = incomingData; // Assign the incoming data to the class-level musicData
        musicStream = new ByteArrayInputStream(musicData);
        totalLength = musicData.length;

        playerThread = new Thread(() -> {
            try {
                player = new Player(musicStream);
                player.play();
            } catch (Exception ex) {
                System.out.println("Error playing music.");
                ex.printStackTrace();
            }
        });
        playerThread.start();
    }

    public static void pauseMusic() {
        if (player != null) {
            try {
                pausePosition = totalLength - musicStream.available(); // Updates pausePosition
                player.close(); // Close the player to stop
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void resumeMusic() {
        stopMusic(); // Ensure all previous resources are cleared
        long skipAmount = pausePosition;

        musicStream = new ByteArrayInputStream(musicData); // Re-create the stream with the original data
        playerThread = new Thread(() -> {
            try {
                player = new Player(musicStream);
                musicStream.skip(skipAmount); // Attempt to skip to the approximate pause position
                player.play();
            } catch (Exception ex) {
                System.out.println("Error resuming music.");
                ex.printStackTrace();
            }
        });
        playerThread.start();
    }

    public static void stopMusic() {
        if (player != null) {
            player.close();
            player = null;
        }
        if (playerThread != null && playerThread.isAlive()) {
            playerThread.interrupt();
            playerThread = null;
        }
        pausePosition = 0;
    }
}