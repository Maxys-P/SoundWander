package com.sw.controllers.artists;

import com.sw.classes.Artist;
import com.sw.controllers.profil.ControllerProfilArtist;
import com.sw.facades.Facade;
import com.sw.facades.FacadeArtist;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ControllerTransformationArtist extends ControllerProfilArtist {

    @FXML private Button becomeArtistButton;

    FacadeArtist artistFacade = FacadeArtist.getInstance();

    @FXML
    private void initialize() throws Exception {
    }

    @FXML private void handleBecomeArtist(ActionEvent actionEvent) throws Exception {
        try {
            Artist newArtist = artistFacade.userBecomeArtist(Facade.currentUser.getId());
            goToPage(becomeArtistButton, "artists/profil-artist.fxml", "Mon Profil d'Artiste");        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
