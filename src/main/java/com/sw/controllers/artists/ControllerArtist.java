package com.sw.controllers.artists;

import com.sw.controllers.Controller;
import com.sw.facades.FacadeArtist;
import com.sw.classes.Music;
import com.sw.commons.DataHolder;
import com.sw.controllers.proposal.ControllerCreateProposal;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Controller générique pour les pages artistes
 */
public abstract class ControllerArtist extends Controller{
    final FacadeArtist facadeArtist = FacadeArtist.getInstance();


}
