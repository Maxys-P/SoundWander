package com.sw.dao;

import com.sw.classes.Music;
import com.sw.classes.PlaylistMusic;

import java.util.List;

public abstract class DAOPlaylistMusic extends DAO {
    public DAOPlaylistMusic() {super("PlaylistMusic");}

    public abstract PlaylistMusic getPlaylistMusicById(int id) throws Exception;
    public abstract PlaylistMusic addMusicToPlaylist(String playlistName, int music) throws Exception;
    public abstract int getPlaylistMusicIdByCountry(String country) throws Exception;

    public abstract List<Music> getAllMusicByPlaylist(int playlistId) throws Exception;
}
