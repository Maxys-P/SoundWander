package com.sw.controllers.publicPlaylist;

import com.neovisionaries.i18n.CountryCode;
import com.sw.controllers.Controller;
import com.sw.exceptions.ExceptionBadPage;
import com.sw.facades.FacadePlaylist;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ControllerPlaylist extends Controller {
    private FacadePlaylist playlistFacade;

    @FXML private Button boutonCreatePublicPlaylist;
    @FXML public TextField PlaylistName;
    @FXML public ComboBox<String> Country;
    @FXML public ComboBox<String> Continent;
    @FXML public Text errorText;
    @FXML public Button boutonRetour;

    public ControllerPlaylist() {
        super();
        playlistFacade = FacadePlaylist.getInstance();
    }

    @FXML
    public void initialize() {

        super.hideError(errorText);

        List<String> countries = new ArrayList<>();

        for (CountryCode code : CountryCode.values()) {
            countries.add(code.getName());
        }

        Collections.sort(countries);
        Country.getItems().addAll(countries);

        Continent.getItems().addAll("Afrique", "Amérique du Nord", "Amérique du Sud", "Asie", "Europe", "Océanie"); //ligne 47
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
        if (PlaylistName.getText().isEmpty() || Country.getValue().isEmpty() || Continent.getValue().isEmpty()) {
            super.displayError(errorText, "Veuillez remplir tous les champs");
        } else {
            try {
                String name = PlaylistName.getText();
                String country = Country.getValue();
                String continent = Continent.getValue();
                playlistFacade.addPublicPlaylist(name, country, continent);
                Stage currentStage = (Stage) PlaylistName.getScene().getWindow();
                currentStage.close();
                goToPage("musical-experts/home-musical-expert.fxml", "Mon profil");
            } catch (Exception e) {
                e.printStackTrace();
                throw new ExceptionBadPage("Erreur lors de la création de la playlist publique");
            }
        }
    }

    @FXML
    private void goBack() throws ExceptionBadPage {
        goToPage(boutonRetour,"musical-experts/home-musical-expert.fxml", "Mon profil");
    }

}
