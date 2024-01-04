package com.sw.dao.daoMysql;

import com.sw.classes.Music;
import com.sw.classes.Playlist;
import com.sw.classes.PlaylistMusic;
import com.sw.dao.DAOPlaylist;
import com.sw.dao.DAOPlaylistMusic;
import com.sw.dao.boiteAOutils.MapperResultSet;
import com.sw.dao.requetesDB.RequetesMySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DAOPlaylistMusicMySQL extends DAOPlaylistMusic {

    private DAOPlaylistMySQL DAOPlaylistMySQL;
    private DAOMusicMySQL DAOMusicMySQL;
    private RequetesMySQL requetesDB;
    public DAOPlaylistMusicMySQL() {
        super();
        this.requetesDB = new RequetesMySQL();
        this.DAOPlaylistMySQL = new DAOPlaylistMySQL();
        this.DAOMusicMySQL = new DAOMusicMySQL();
    }


    @Override
    public int getPlaylistMusicIdByCountry(String country) throws Exception {
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("country", country);
        try {
            MapperResultSet playlistData = ((RequetesMySQL) requetesDB).selectWhere(table, conditions);
            if (!playlistData.getListData().isEmpty()) {
                Map<String, Object> playlistDetails = playlistData.getListData().getFirst();
                int id = (int) playlistDetails.get("id");
                return id;
            } else {
                System.out.println("Aucune PlaylistMusic trouvée pour ce pays");
            }
            return 0;
        } catch (Exception e) {
            System.out.println("Erreur lors de la récupération de la PlaylistMusic par pays");
            return 0;
        }


    }
    /**
     * Méthode pour récupérer une PlaylistMusic par son ID.
     * @param id int, l'ID de la PlaylistMusic
     * @return PlaylistMusic, l'objet PlaylistMusic récupéré
     * @throws Exception si problème pendant la récupération de l'objet PlaylistMusic
     */
    public PlaylistMusic getPlaylistMusicById(int id) throws Exception {
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("id", id);
        try {
            MapperResultSet playlistData = ((RequetesMySQL) requetesDB).selectWhere(table, conditions);
            if (!playlistData.getListData().isEmpty()) {
                Map<String, Object> playlistDetails = playlistData.getListData().getFirst();

                int playlistId = (int) playlistDetails.get("playlist_id");
                Playlist playlist = DAOPlaylistMySQL.getPlaylistById(playlistId); // Remplacez par votre méthode de récupération de Playlist

                List<Music> musicList = getAllMusicByPlaylist(playlistId); // Remplacez par votre méthode de récupération de Music

                // Créez l'objet PlaylistMusic
                PlaylistMusic playlistMusic = new PlaylistMusic(playlist, musicList);
                return playlistMusic;
            } else {
                System.out.println("Aucune PlaylistMusic trouvée pour cet ID");
            }
            return null;
        } catch (Exception e) {
            System.out.println("Erreur lors de la récupération de la PlaylistMusic par ID");
            return null;
        }
    }

    @Override
    public List<Music> getAllMusicByPlaylist(int playlistId) {
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

    @Override
    public PlaylistMusic addMusicToPlaylist(String playlistCountry, int musicId) throws Exception {
        Playlist playlist = DAOPlaylistMySQL.getPlaylistByCountry(playlistCountry);
        if (playlist == null) {
            System.out.println("Il n'existe pas de playlist pour ce pays");
            return null;
        } else {System.out.println("Playlist récupérée: " + playlist);}

        Music music = DAOMusicMySQL.getMusicById(musicId);
        if (music == null) {
            System.out.println("Il n'existe pas de musique pour cet id");
            return null;
        } else {System.out.println("Musique récupérée: " + music);}

        String sql = "INSERT INTO PlaylistMusic (playlist_id, music_id) VALUES (?, ?)";
        try (Connection connection = this.requetesDB.getConnexion(); // utilisez getConnexion ou une méthode équivalente
             PreparedStatement requete = connection.prepareStatement(sql)) {
            requete.setInt(1, playlist.getPlaylistId());
            requete.setInt(2, music.getId());

            int affectedRows = requete.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("L'ajout de la musique à la playlist a échoué, aucune ligne n'a été modifiée.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Erreur lors de l'ajout de la musique à la playlist: " + e.getMessage(), e);
        }
        System.out.println("Musique ajoutée avec succès à la playlist.");
        return new PlaylistMusic(playlist, getAllMusicByPlaylist(playlist.getPlaylistId()));
    }


}
