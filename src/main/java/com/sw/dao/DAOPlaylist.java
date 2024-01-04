package com.sw.dao;


import com.sw.classes.Playlist;

public abstract class DAOPlaylist extends DAO{
    public DAOPlaylist() {super("Playlist");}

    public abstract Playlist getPLaylistByName(String name) throws Exception;
    public abstract void addPublicPlaylist(String name, String country) throws Exception;




}
