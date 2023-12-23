package com.sw.dao.boiteAOutils;

import java.io.ByteArrayInputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
public class PlayMusicFromBD {
    public static void playMusicFromBD(byte[] musicData) {
        try {
            // Convert the byte array to an audio file format (e.g., WAV)
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(musicData);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(byteArrayInputStream);

            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();

            // Close the audio input stream when done playing
            audioInputStream.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
