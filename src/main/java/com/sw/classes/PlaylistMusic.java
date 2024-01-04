package com.sw.classes;


import java.util.List;


// Classe jointure entre Playlist et Music
public class PlaylistMusic {
    private Playlist playlist;
    private List<Music> music;
    public PlaylistMusic(Playlist playlist, List<Music> music) {
        this.playlist = playlist;
        this.music = music;
    }

    public Playlist getPlaylist() {return playlist;}
    public List<Music> getMusic() {return music;}
    public void setPlaylist(Playlist playlist) {this.playlist = playlist;}
    public void setMusic(List<Music> music) {this.music = music;}


}
