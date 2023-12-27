package com.sw.classes;

public class MusicInfo {
    private int id;
    private String name;
    private int duration;
    private int artistId;

    public MusicInfo(int id, String name, int duration, int artistId) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.artistId = artistId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public int getArtistId() {
        return artistId;
    }

}
