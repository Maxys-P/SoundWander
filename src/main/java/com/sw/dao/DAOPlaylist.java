package com.sw.dao;


import com.sw.classes.Playlist;
import com.sw.commons.Searchable;

public abstract class DAOPlaylist extends DAO implements Searchable {
    public DAOPlaylist() {super("playlist");}

    public abstract Playlist getPLaylistByName(String name) throws Exception;
    public abstract Playlist getPlaylistByCountry(String country) throws Exception;
    public abstract Playlist getPlaylistById(int id) throws Exception;
    public abstract void addPlaylist(String name, String country, String continent) throws Exception;





}
