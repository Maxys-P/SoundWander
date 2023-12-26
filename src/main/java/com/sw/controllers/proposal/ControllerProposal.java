package com.sw.controllers.proposal;

import com.sw.classes.Music;
import com.sw.controllers.Controller;
import com.sw.exceptions.ExceptionBadPage;
import com.sw.facades.FacadeProposal;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class ControllerProposal extends Controller{

    @FXML
    private Label ajoutProposal;
    @FXML
    private Text errorText;

    /**
     * Facade pour les propositions
     */
    final FacadeProposal proposalFacade = FacadeProposal.getInstance();

    /**
     * Chemin du dossier dans lequel se trouve les ressources pour les pages liés aux propositions
     */
    private final String path = "proposals/";
    protected Music selectedMusic;

    /**
     * Méthode pour aller à la page de propositions
     * @throws ExceptionBadPage si problème pendant le chargement de la page
     */
    @FXML
    private void goToAddProposal() throws ExceptionBadPage{
        super.goToPage(ajoutProposal, path + "createProposal-view.fxml", "Ajouter une proposition");
    }

}
