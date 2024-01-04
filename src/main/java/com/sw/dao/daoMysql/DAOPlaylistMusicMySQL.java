package com.sw.dao.daoMysql;

import com.sw.classes.Music;
import com.sw.classes.Playlist;
import com.sw.classes.PlaylistMusic;
import com.sw.dao.DAOPlaylist;
import com.sw.dao.DAOPlaylistMusic;
import com.sw.dao.boiteAOutils.MapperResultSet;
import com.sw.dao.requetesDB.RequetesMySQL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DAOPlaylistMusicMySQL extends DAOPlaylistMusic {

    private DAOPlaylistMySQL DAOPlaylistMySQL;
    private DAOMusicMySQL DAOMusicMySQL;
    public DAOPlaylistMusicMySQL() {
        super();
        this.requetesDB = new RequetesMySQL();
        this.DAOPlaylistMySQL = new DAOPlaylistMySQL();
        this.DAOMusicMySQL = new DAOMusicMySQL();
    }

    @Override
    public PlaylistMusic getPlaylistMusicByName(String name) throws Exception {
        try {
            Playlist playlist = DAOPlaylistMySQL.getPLaylistByName(name);
            if (playlist == null) {
                return null; // ou gérer autrement
            }
            List<Music> musics = getAllMusicByPlaylist(playlist.getPlaylistId());
            return new PlaylistMusic(playlist, musics);
        } catch (Exception e) {
            System.out.println("Erreur lors de la récupération de la playlist par nom");
            return null;
        }
    }
    private List<Music> getAllMusicByPlaylist(int playlistId) {
        List<Music> musicList = new ArrayList<>();
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("playlist_id", playlistId);

        try {
            MapperResultSet playlistMusicData = ((RequetesMySQL) requetesDB).selectWhere(table, conditions);
            // Itérez sur les résultats pour créer des objets Music et les ajouter à la liste
            for (Map<String, Object> playlistMusicDetails : playlistMusicData.getListData()) {
                int musicId = (int) playlistMusicDetails.get("music_id");
                musicList.add(DAOMusicMySQL.getMusicById(musicId));
            }

        } catch (Exception e) {
            System.out.println("Erreur lors de la récupération des musiques pour la playlist : " + e.getMessage());
        }

        return musicList;
    }


}
