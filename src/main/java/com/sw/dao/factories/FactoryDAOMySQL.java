package com.sw.dao.factories;

import com.sw.dao.*;
import com.sw.dao.daoMysql.*;

/**
 *  Classe de la factory de model MySQL qui permet de créer des models MySQL
 *   Implémentation concrète de FactoryDAO pour créer des instances de DAO spécifiques à MySQL.
 */
public class FactoryDAOMySQL extends FactoryDAO {

    /**
     * Constructor
     */
    public FactoryDAOMySQL() {
        super();
    }

    /**
     * Méthode permettant de récupérer une instance de la DAOClient
     * @return l'instance du model UserMySQL
     */
    @Override
    public DAOUser getInstanceofDAOUser() {
        return new DAOUserMySQL();
    }

    @Override
    public DAOProposal getInstanceofDAOProposal() {return new DAOProposalMySQL();}


    @Override
    public DAOMusic getInstanceofDAOMusic() {return new DAOMusicMySQL();}


    @Override
    public DAOPayment getInstanceofDAOPayment() {return new DAOPaymentMySQL();}

    @Override
    public DAOPlaylistMusic getDAOPlaylistMusic() {return new DAOPlaylistMusicMySQL();}

    @Override
    public DAOPlaylist getDAOPlaylist() {return new DAOPlaylistMySQL();}

}