package com.sw.dao.daoMysql;

import com.sw.classes.Music;
import com.sw.classes.Playlist;
import com.sw.classes.PlaylistMusic;
import com.sw.dao.DAOPlaylist;
import com.sw.dao.DAOPlaylistMusic;
import com.sw.dao.boiteAOutils.MapperResultSet;
import com.sw.dao.requetesDB.RequetesMySQL;
import com.sw.exceptions.ExceptionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

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


    /**
     * Retrieves a list of PlaylistMusic for a specific continent.
     * @param continent The continent for which to retrieve the PlaylistMusic.
     * @return A list of PlaylistMusic objects.
     * @throws ExceptionDB If a database exception occurs.
     */
    public List<PlaylistMusic> getPlaylistMusicByContinent(String continent) throws Exception {
        // Set up the JOIN and WHERE conditions for the SQL query
        String mainTable = "PlaylistMusic";
        List<String> joinTables = Arrays.asList("Playlist", "Music");
        List<String> onConditions = Arrays.asList(
                "PlaylistMusic.playlist_id = Playlist.id",
                "PlaylistMusic.music_id = Music.id"
        );
        Map<String, Object> whereConditions = new HashMap<>();
        whereConditions.put("Playlist.continent", continent);

        // Execute the query
        MapperResultSet result;
        try {
            result = requetesDB.selectWithJoin(mainTable, joinTables, onConditions, whereConditions);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionDB("Error retrieving PlaylistMusic by continent: " + e.getMessage(), e);
        }

        // Map to hold Playlist and its associated list of Music
        Map<Integer, PlaylistMusic> playlistMusicMap = new HashMap<>();
        for (Map<String, Object> row : result.getListData()) {
            int playlistId = (int) row.get("playlist_id");
            // Assuming you have methods to create playlist and music objects from the row data
            Playlist playlist = DAOPlaylistMySQL.getPlaylistById(playlistId);
            Music music = DAOMusicMySQL.getMusicById((int) row.get("music_id"));

            // Check if the playlist already has an entry in the map
            PlaylistMusic playlistMusic = playlistMusicMap.get(playlistId);
            if (playlistMusic == null) {
                playlistMusic = new PlaylistMusic(playlist, new ArrayList<>());
                playlistMusicMap.put(playlistId, playlistMusic);
            }
            // Add the music to the playlist's music list
            playlistMusic.getMusic().add(music);
        }

        // Extract the PlaylistMusic objects from the map
        List<PlaylistMusic> playlistMusics = new ArrayList<>(playlistMusicMap.values());

        return playlistMusics;
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
            MapperResultSet playlistData = requetesDB.selectWhere(table, conditions);
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
            MapperResultSet playlistMusicData = requetesDB.selectWhere(table, conditions);
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
