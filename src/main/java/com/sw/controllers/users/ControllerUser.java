package com.sw.controllers.users;

import com.sw.controllers.Controller;
import com.sw.facades.FacadeUser;

/**
 * Controller générique pour les pages accessibles aux visiteurs
 * @see Controller
 */
public class ControllerUser extends Controller {

    /**
     * Facade pour les utilisateurs non connectés
     */
    final FacadeUser userFacade = FacadeUser.getInstance();

    /**
     * Chemin du dossier dans lequel se trouve les ressources pour les pages accessibles aux utilisateurs
     */
    private final String path = "users/";

}
