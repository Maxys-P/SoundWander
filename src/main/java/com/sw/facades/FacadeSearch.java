package com.sw.facades;

import com.sw.dao.DAOUser;
import com.sw.dao.factories.FactoryDAO;
import com.sw.dao.DAOMusic;
import com.sw.commons.SearchCriteria;
import java.util.List;

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
     * Instance de la facade pour le singleton
     */
    private static FacadeSearch instance = null;

    public List<Object> performSearch(SearchCriteria criteria) {
        switch (criteria.getType()) {
            case "music":
                // Recherche par titre de musique
                return daoMusic.search(criteria);
            case "artist":
                // Recherche par pseudo d'artiste
                return daoUser.search(criteria);
            default:
                throw new IllegalArgumentException("Type de recherche non valide");
        }
    }
}
