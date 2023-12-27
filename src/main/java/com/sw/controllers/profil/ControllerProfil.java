package com.sw.controllers.profil;

import com.sw.classes.Admin;
import com.sw.classes.Artist;
import com.sw.classes.MusicalExpert;
import com.sw.controllers.Controller;
import com.sw.exceptions.ExceptionBadPage;
import com.sw.facades.FacadeUser;
import javafx.scene.control.Control;

public abstract class ControllerProfil extends Controller{

    /**
     * Facade pour les users
     */
    final FacadeUser userFacade = FacadeUser.getInstance();


    /**
     * Méthode pour aller vers la page de profil en fonction du role de l'utilisateur
     * @param controlEl Control, élément de contrôle de la page
     * @throws ExceptionBadPage si problème pendant le chargement de la page
     */
    protected void goToProfil(Control controlEl) throws ExceptionBadPage {
        if (userFacade.currentUser instanceof Artist) {
            goToPage(controlEl, "artists/profil-artist.fxml", "Mon Profil d'Artiste");
        } else if (userFacade.currentUser instanceof MusicalExpert) {
            goToPage(controlEl, "musical-experts/profil-musical-expert.fxml", "Mon Profil d'expert musical");
        } else if (userFacade.currentUser instanceof Admin) {
            goToPage(controlEl, "admins/profil-admin.fxml", "Mon Profil d'admin");
        } else {
            goToPage(controlEl, "users/profil-user.fxml", "Mon Profil de wander");
        }
    }

}
