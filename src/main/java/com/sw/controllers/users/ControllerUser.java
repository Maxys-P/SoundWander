package com.sw.controllers.users;

import com.sw.controllers.Controller;
import com.sw.facades.FacadeUser;
import com.sw.exceptions.ExceptionBadPage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.text.Text;


import java.net.URL;

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

    /**
     * Methode permettant de se rendre sur la page d'accueil visiteur
     * @param controlEl Control, élément de contrôle de la page
     * @throws ExceptionBadPage si la vue n'existe pas
     */
    void goToVisitor(Control controlEl) throws ExceptionBadPage {
        goToPage(controlEl, path + "visitor-view.fxml", "Bienvenue sur SoundWander");
    }

    /**
     * Méthode pour aller à la page d'accueil appropriée en fonction de l'email de l'utilisateur.
     * @param controlEl Le composant de contrôle à partir duquel l'action est initiée.
     * @param userEmail L'email de l'utilisateur.
     * @throws ExceptionBadPage si problème pendant le chargement de la page.
     */
    void goToHome(Control controlEl, String userEmail) throws ExceptionBadPage {
        String viewPath;
        if ("expert@musical.fr".equals(userEmail)) {
            viewPath = "users/homeMusicalExpert-view.fxml"; // Chemin pour les experts musicaux
        } else {
            viewPath = "users/home-view.fxml"; // Chemin par défaut pour les autres utilisateurs
        }
        goToPage(controlEl,  viewPath, "Accueil");
    }

    /**
     * Méthode pour aller à la page de profil
     * @param actionEvent ActionEvent, l'événement de l'action qui a été déclenchée
     * @throws ExceptionBadPage si problème pendant le chargement de la page
     */
    public void goToProfile(ActionEvent actionEvent) throws ExceptionBadPage {
        // Vous devez adapter la méthode goToPage pour qu'elle prenne un ActionEvent
        // Ou trouver une autre façon de récupérer le control à partir de l'ActionEvent si nécessaire
        Control controlEl = (Control) actionEvent.getSource();
        goToPage(controlEl, path + "profile-view.fxml", "Votre Profil");
    }




}
