package com.sw.dao;

import com.sw.classes.PlaylistMusic;

public abstract class DAOPlaylistMusic extends DAO {
    public DAOPlaylistMusic() {super("PlaylistMusic");}

    public abstract PlaylistMusic getPlaylistMusicByName(String name) throws Exception;

}
