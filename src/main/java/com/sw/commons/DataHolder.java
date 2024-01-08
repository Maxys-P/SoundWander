package com.sw.commons;

import com.sw.classes.Music;

public class DataHolder {
    private static Music currentMusic;
    private static int currentPlaylistId;

    public static Music getCurrentMusic() {
        return currentMusic;
    }
    public static int getCurrentPlaylistId() {
        return currentPlaylistId;
    }

    public static void setCurrentMusic(Music currentMusic) {
        DataHolder.currentMusic = currentMusic;
    }
    public static void setCurrentPlaylistId(int currentPlaylistId) {
        DataHolder.currentPlaylistId = currentPlaylistId;
    }
}
