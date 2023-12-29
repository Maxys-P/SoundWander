package com.sw.commons;

import com.sw.classes.Music;

public class DataHolder {
    private static Music currentMusic;

    public static Music getCurrentMusic() {
        return currentMusic;
    }

    public static void setCurrentMusic(Music currentMusic) {
        DataHolder.currentMusic = currentMusic;
    }
}
