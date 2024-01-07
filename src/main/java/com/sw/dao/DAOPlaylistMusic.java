package com.sw.dao;

import com.sw.classes.Music;
import com.sw.classes.PlaylistMusic;

import java.util.List;
import java.util.Map;

public abstract class DAOPlaylistMusic extends DAO {
    public DAOPlaylistMusic() {super("playlistMusic");}

    public abstract PlaylistMusic getPlaylistMusicById(int id) throws Exception;
    public abstract PlaylistMusic addMusicToPlaylist(String playlistName, int music) throws Exception;
    public abstract Map<String, List<PlaylistMusic>> getPlaylistMusicByContinent(String continent) throws Exception;

    public abstract List<Music> getAllMusicByPlaylist(int playlistId) throws Exception;
    public abstract PlaylistMusic getPlaylistMusicByCountry(String country) throws Exception;
}
