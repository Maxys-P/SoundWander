package com.sw.dao.boiteAOutils;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import java.io.File;
import java.util.Map;

public class MP3Utils {

    /**
     * Gets the duration of an MP3 file in seconds.
     *
     * @param file the MP3 file
     * @return the duration in seconds, or -1 if the duration cannot be determined
     */
    public static long getMP3Duration(File file) {
        AudioFileFormat fileFormat;
        try {
            fileFormat = AudioSystem.getAudioFileFormat(file);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

        if (fileFormat.properties().containsKey("duration")) {
            Long microseconds = (Long) fileFormat.properties().get("duration");
            return microseconds / 1_000_000L; // convert to seconds
        } else {
            System.out.println("Duration not available");
            return -1;
        }
    }

}
