package com.sw.facades;

import com.sw.classes.*;
import com.sw.dao.DAOUser;
import com.sw.dao.factories.FactoryDAO;



/**
 * Classe de facade pour les artistes
 * @see Facade
 */
public class FacadeArtist extends Facade {

        /**
         * Factory pour les DAO
         */
        protected FactoryDAO f = FactoryDAO.getInstanceofFactoryDAO();

        /**
         * DAO pour les artists
         */
        protected DAOUser daoUser = f.getInstanceofDAOUser();

        /**
         * Instance de la facade pour le singleton
         */
        private static FacadeArtist instance = null;


        /**
         * Getter de l'instance de la facade pour le singleton
         * @return FacadeUser, l'instance de la facade
         */
        public static FacadeArtist getInstance() {
            if (instance == null) {
                instance = new FacadeArtist();
            }
            return instance;
        }

        public Artist userBecomeArtist(int idUser) throws Exception {
            return daoUser.userBecomeArtist(idUser);
        }

        public Artist getArtistById(int id) throws Exception {
            return (Artist) daoUser.getUserById(id);
        }
}
