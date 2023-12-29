package com.sw.controllers.proposal;

import com.sw.classes.Music;
import com.sw.controllers.Controller;
import com.sw.exceptions.ExceptionBadPage;
import com.sw.facades.FacadeProposal;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class ControllerProposal extends Controller{

    /**
     * Facade pour les propositions
     */
    final FacadeProposal proposalFacade = FacadeProposal.getInstance();

    /**
     * Chemin du dossier dans lequel se trouve les ressources pour les pages liés aux propositions
     */
    private final String path = "";

}
