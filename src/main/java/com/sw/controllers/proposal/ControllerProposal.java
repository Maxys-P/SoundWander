package com.sw.controllers.proposal;

import com.sw.classes.Music;
import com.sw.controllers.Controller;
import com.sw.facades.FacadeProposal;
import com.sw.exceptions.ExceptionBadPage;
import javafx.scene.control.Control;

public class ControllerProposal extends Controller{
    /**
     * Facade pour les propositions
     */
    final FacadeProposal proposalFacade = FacadeProposal.getInstance();

    /**
     * Chemin du dossier dans lequel se trouve les ressources pour les pages liés aux propositions
     */
    private final String path = "proposals/";
    protected Music selectedMusic;

    void goToAddProposal(Control controlEl) throws ExceptionBadPage {
        goToPage(controlEl, path + "createProposal-view.fxml", "Création d'une proposition");
    }
}
