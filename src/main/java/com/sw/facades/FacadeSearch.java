package com.sw.facades;

import com.sw.dao.DAOUser;
import com.sw.dao.factories.FactoryDAO;
import com.sw.dao.DAOMusic;
import com.sw.commons.SearchCriteria;
import java.util.List;
import com.sw.dao.DAOPlaylist;

public class FacadeSearch extends Facade {
    /**
     * Factory pour les DAO
     */
    protected FactoryDAO f = FactoryDAO.getInstanceofFactoryDAO();

    /**
     * DAO pour les artist
     */
    protected DAOUser daoUser = f.getInstanceofDAOUser();

    /**
     * DAO pour les users
     */
    protected DAOMusic daoMusic = f.getInstanceofDAOMusic();
    /**
     * DAO pour les playlists
     */
    protected DAOPlaylist daoPlaylist = f.getDAOPlaylist();

    /**
     * Instance de la facade pour le singleton
     */
    private static FacadeSearch instance = null;

    /**
     * Getter de l'instance de la facade pour le singleton
     * @return FacadeSearch, l'instance de la facade
     */
    public static FacadeSearch getInstance() {
        if (instance == null) {
            instance = new FacadeSearch();
        }
        return instance;
    }

    public List<Object> performSearch(SearchCriteria criteria) {
        switch (criteria.getType()) {
            case "musique":
                // Recherche par titre de musique
                return daoMusic.search(criteria);
            case "artiste":
                // Recherche par pseudo d'artiste
                return daoUser.search(criteria);
            case "playlist":
                // Recherche par nom de playlist
                return daoPlaylist.search(criteria);
            default:
                throw new IllegalArgumentException("Type de recherche non valide");
        }
    }
}
