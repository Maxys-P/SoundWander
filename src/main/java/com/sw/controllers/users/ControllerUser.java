package com.sw.controllers.users;

import com.sw.controllers.Controller;
import com.sw.facades.FacadeUser;
import com.sw.exceptions.ExceptionBadPage;
import javafx.scene.control.Control;

/**
 * Controller générique pour les pages accessibles aux visiteurs
 * @see Controller
 */
public abstract class ControllerUser extends Controller {

    /**
     * Facade pour les users
     */
    final FacadeUser userFacade = FacadeUser.getInstance();

    /**
     * Chemin du dossier dans lequel se trouve les ressources pour les pages accessibles aux utilisateurs
     */
    private final String path = "users/";

    /**
     * Methode permettant de se rendre sur la page d'accueil visiteur
     * @param controlEl Control, élément de contrôle de la page
     * @throws ExceptionBadPage si la vue n'existe pas
     */
    void goToVisitor(Control controlEl) throws ExceptionBadPage {
        goToPage(controlEl, path + "visitor-view.fxml", "Bienvenue sur SoundWander");
    }

    /**
     * Méthode pour aller à la page d'accueil
     * @param controlEl Control, élément de contrôle de la page
     * @param scope String, le scope de l'utilisateur (role en gros)
     * @throws ExceptionBadPage si problème pendant le chargement de la page
     */
    void goToHome(Control controlEl, String scope) throws ExceptionBadPage {
        String pathUser = scope + "s/";
        goToPage(controlEl, pathUser + "profil-" + scope + ".fxml", "Mon Profil");
    }
}
