package com.sw.classes;

import com.sw.facades.FacadeMusicPlay;

public class Music {

    private FacadeMusicPlay facadeMusicPlay;
    private int id;
    private String name;
    private String artist;
    private int duration;

    public Music(int id, String name, String artist, int duration){
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.duration = duration;
    }

    @Override
    public String toString(){
        return "Music{" +
                "id=" + id + '\'' +
                ", name=" + name + '\'' +
                 ", artist=" + artist + '\'' +
                ", duration=" + '\'' + duration +
                '}';
    }

    public FacadeMusicPlay getFacadeMusicPlay(){
        return facadeMusicPlay;
    }

    public int getId(){return id;}
    public String getName(){return name;}
    public String getArtist(){return artist;}
    public int getDuration(){return duration;}

    public void setFacadeMusicPlay(FacadeMusicPlay facadeMusicPlay){
        this.facadeMusicPlay = facadeMusicPlay;
    }

    public void setId(int id){this.id = id;}

    public void setName(String name){this.name = name;}

    public void setArtist(String artist){this.artist = artist;}

    public void setDuration(int duration){this.duration = duration;}
}
