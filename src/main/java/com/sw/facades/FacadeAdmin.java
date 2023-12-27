package com.sw.facades;

import com.sw.classes.Admin;
import com.sw.classes.MusicalExpert;
import com.sw.classes.User;
import com.sw.dao.DAOUser;
import com.sw.dao.factories.FactoryDAO;

import java.util.List;

public class FacadeAdmin extends Facade {

    /**
     * Factory pour les DAO
     */
    protected FactoryDAO f = FactoryDAO.getInstanceofFactoryDAO();

    /**
     * DAO pour les admin
     */
    protected DAOUser daoUser = f.getInstanceofDAOUser();

    /**
     * Instance de la facade pour le singleton
     */
    private static FacadeAdmin instance = null;


    /**
     * Getter de l'instance de la facade pour le singleton
     * @return FacadeUser, l'instance de la facade
     */
    public static FacadeAdmin getInstance() {
        if (instance == null) {
            instance = new FacadeAdmin();
        }
        return instance;
    }

    /**
     * Méthode pour récupérer tous les users
     * @return List<User>, la liste des utilisateurs
     * @throws Exception si problème pendant la récupération des users
     */
    public List<User> getAllUsers() throws Exception {
        return daoUser.getAllUsers();
    }

    /**
     * Méthode pour rendre un utilisateur expert musical
     * @param idUser int, l'id de l'utilisateur
     * @return MusicalExpert, l'expert musical
     * @throws Exception si problème pendant la création de l'expert musical
     */
    public MusicalExpert userBecomeMusicalExpert(int idUser) throws Exception {
        return daoUser.userBecomeMusicalExpert(idUser);
    }

    public Admin userBecomeAdmin(int idUser) throws Exception {
        return daoUser.userBecomeAdmin(idUser);
    }
}
