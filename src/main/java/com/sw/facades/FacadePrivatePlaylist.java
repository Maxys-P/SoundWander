package com.sw.facades;

import com.sw.classes.Music;
import com.sw.classes.User;
import com.sw.dao.DAOUser;
import com.sw.dao.factories.FactoryDAO;
import com.sw.exceptions.ExceptionDB;

import java.util.List;

/**
 * Facade for managing private playlists.
 */
public class FacadePrivatePlaylist extends Facade {

    /**
     * Factory pour les DAO
     */
    protected FactoryDAO f = FactoryDAO.getInstanceofFactoryDAO();

    /**
     * DAO pour les utilisateurs
     */
    protected DAOUser daoUser = f.getInstanceofDAOUser();

    /**
     * Instance de la facade pour le singleton
     */
    private static FacadePrivatePlaylist instance = null;


    /**
     * Getter de l'instance de la facade pour le singleton
     * @return FacadeUser, l'instance de la facade
     */
    public static FacadePrivatePlaylist getInstance() {
        if (instance == null) {
            instance = new FacadePrivatePlaylist();
        }
        return instance;
    }

    /**
     * Adds a music to a private playlist.
     *
     * @param user     The user ID of the playlist owner.
     * @param music    The ID of the music to be added.
     * @throws Exception If an error occurs during the process.
     */
    public void addMusicToPrivatePlaylist(User user, Music music) throws Exception {
        System.out.println("[facade] J'essaie d'ajouter la musique " + music + " à la playlist de l'utilisateur " + user);
        try {
            List<Music> updatedPlaylist = daoUser.addMusicToPrivatePlaylist(user, music);
            if (updatedPlaylist != null) {
                user.setPrivatePlaylist(updatedPlaylist);
            }
            System.out.println("[facade] J'ai ajouté la musique " + music + " à la playlist de l'utilisateur " + user);
        } catch (Exception e) {
            throw new Exception("[facade] Error adding music to private playlist: " + e.getMessage(), e);
        }
    }

    /**
     * Deletes a music from a private playlist.
     *
     * @param user     The user ID of the playlist owner.
     * @param music    The ID of the music to be deleted.
     * @throws Exception If an error occurs during the process.
     */
    public void deleteMusicInPrivatePlaylist(User user, Music music) throws Exception {
        System.out.println("[facade] J'essaie de supprimer la musique " + music + " de la playlist de l'utilisateur " + user);
        try {
            List<Music> updatedPlaylist = daoUser.deleteMusicInPrivatePlaylist(user, music);
            if (updatedPlaylist != null) {
                user.setPrivatePlaylist(updatedPlaylist);
            }
            System.out.println("[facade] J'ai supprimé la musique " + music + " de la playlist de l'utilisateur " + user);
        } catch (Exception e) {
            throw new Exception("[facade] Error deleting music in private playlist: " + e.getMessage(), e);
        }
    }
}
