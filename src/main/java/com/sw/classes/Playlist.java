package com.sw.classes;

public class Playlist {
    private int id;
    private String name;
    private String country;

    private String continent;

    public Playlist(int id, String name, String country, String continent) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.continent = continent;
    }

    public int getPlaylistId() {return id;}
    public String getPlaylistName() {return name;}
    public String getPlaylistCountry() {return country;}

    public void setPlaylistId(int id) {this.id = id;}
    public void setPlaylistName(String name) {this.name = name;}
    public void setPlaylistCountry(String country) {this.country = country;}


}