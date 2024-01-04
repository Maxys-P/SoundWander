package com.sw.controllers.publicPlaylist;

import com.sw.controllers.Controller;
import com.sw.exceptions.ExceptionBadPage;
import com.sw.facades.FacadePlaylist;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextFormatter;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.util.function.UnaryOperator;

public class ControllerPlaylist extends Controller {
    private FacadePlaylist playlistFacade;

    @FXML private Button boutonCreatePublicPlaylist;
    @FXML public TextField PlaylistName;
    @FXML public TextField Country;
    @FXML public TextField Continent;
    @FXML public Text errorText;
    @FXML public Button boutonRetour;

    public ControllerPlaylist() {
        super();
        playlistFacade = FacadePlaylist.getInstance();
    }

    @FXML
    public void initialize() {
        super.hideError(errorText);
    }
    @FXML
    public void gotoCreatePublicPlaylist() throws ExceptionBadPage {
        try {
            Stage currentStage = (Stage) boutonCreatePublicPlaylist.getScene().getWindow();
            goToPage("musical-experts/form-create-public-playlist.fxml", "Créer une playlist publique");
            currentStage.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionBadPage("Erreur lors du chargement de la page de création de playlist publique");
        }
    }

    @FXML
    private void createPublicPlaylist() throws ExceptionBadPage {
        if (PlaylistName.getText().isEmpty() || Country.getText().isEmpty()) {
            super.displayError(errorText, "Veuillez remplir tous les champs");
        } else {
            try {
                String name = PlaylistName.getText();
                String country = Country.getText();
                String continent = Continent.getText();
                playlistFacade.addPublicPlaylist(name, country, continent);
                Stage currentStage = (Stage) PlaylistName.getScene().getWindow();
                currentStage.close();
                goToPage("musical-experts/profil-musical-expert.fxml", "Mon profil");
            } catch (Exception e) {
                e.printStackTrace();
                throw new ExceptionBadPage("Erreur lors de la création de la playlist publique");
            }
        }
    }

    @FXML
    private void goBack() throws ExceptionBadPage {
            goToPage(boutonRetour,"musical-experts/profil-musical-expert.fxml", "Mon profil");
    }

}
