package com.sw.controllers.admins;

import com.sw.classes.Admin;
import com.sw.controllers.Controller;
import com.sw.facades.Facade;
import com.sw.facades.FacadeAdmin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ControllerAdmin extends Controller {
    final FacadeAdmin adminFacade = FacadeAdmin.getInstance();

    @FXML private Button becomeAdminButton;
    /**
     * Méthode pour promouvoir un utilisateur en admin depuis la page profil user
     * @param actionEvent ActionEvent, évènement de l'action
     * @throws Exception si problème pendant la promotion
     */
    @FXML
    private void handleBecomeAdmin(ActionEvent actionEvent) throws Exception {
        try {
            Admin newAdmin = adminFacade.userBecomeAdmin(Facade.currentUser.getId());
            goToPage(becomeAdminButton, "admins/home-admin.fxml", "Mon Profil d'Admin");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
