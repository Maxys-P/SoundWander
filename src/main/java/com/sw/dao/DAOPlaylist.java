package com.sw.dao;


import com.sw.classes.Playlist;

public abstract class DAOPlaylist extends DAO{
    public DAOPlaylist() {super("Playlist");}

    public abstract Playlist getPLaylistByName(String name) throws Exception;
    public abstract Playlist getPlaylistByCountry(String country) throws Exception;
    public abstract void addPlaylist(String name, String country) throws Exception;





}
