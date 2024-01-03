package com.sw.controllers.proposal;

import com.sw.classes.Proposal;
import com.sw.exceptions.ExceptionBadPage;
import com.sw.exceptions.ExceptionFormIncomplete;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import com.sw.classes.Music;
import com.sw.commons.DataHolder;
import com.sw.classes.User;
import com.sw.facades.Facade;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import com.neovisionaries.i18n.CountryCode;
import java.util.ArrayList;
import java.util.Collections;


public class ControllerCreateProposal extends ControllerProposal {

    @FXML
    private VBox conteneurCreateProposal;

    private User getCurrentUser() {
        return Facade.currentUser;
    }

    @FXML
    private ComboBox<String> countryField;
    @FXML
    private TextArea descriptionField;
    @FXML
    private Button submitButton;
    @FXML
    private Label musicLabel;
    @FXML
    private Text errorText;
    @FXML public Button boutonRetour;

    @FXML
    private Button closeButton;

    public void setMusicInfo (Music music) {
        if (music != null) {
            musicLabel.setText(music.getName());
        }
    }

    @FXML
    public void initialize() {
        // Initialiser la liste des pays
        initializeCountries();

        // Récupérer la musique sélectionnée et mettre à jour le label
        Music music = DataHolder.getCurrentMusic();
        if (music != null) {
            musicLabel.setText(music.getName()); // Assurez-vous que Music a une méthode getTitle ou l'équivalent
        } else {
            musicLabel.setText("Select a music");
        }
    }

    private void initializeCountries() {
        List<String> countries = new ArrayList<>();

        for (CountryCode code : CountryCode.values()) {
            countries.add(code.getName()); // getName() retourne le nom du pays en anglais
        }

        // Trie la liste des pays par ordre alphabétique
        Collections.sort(countries);

        // Ajoute la liste des pays à votre ComboBox
        countryField.getItems().addAll(countries);
    }

    private void verifForm() throws ExceptionFormIncomplete {
        String selectedCountry = countryField.getSelectionModel().getSelectedItem();

        if (selectedCountry == null || selectedCountry.isEmpty()) {
            throw new ExceptionFormIncomplete("Veuillez sélectionner un pays");
        }
    }

    /**
     * Méthode appelée lors de la tentative de proposition
     */
    @FXML
    private void handleCreateProposal() {
        super.hideError(errorText);
        try {
            verifForm();

            String country = countryField.getValue();
            String description = descriptionField.getText();
            User artist = getCurrentUser();

            // Récupérer la musique sélectionnée à partir de DataHolder
            Music music = DataHolder.getCurrentMusic();

            if (music == null) {
                throw new ExceptionFormIncomplete("Music not found");
            }

            Proposal proposal = super.proposalFacade.createProposal(artist, music, country, description);

            // Il est généralement recommandé de nettoyer après utilisation si l'objet n'est plus nécessaire.
            DataHolder.setCurrentMusic(null);

            closeWindow();

        } catch (ExceptionFormIncomplete e) {
            displayError(errorText, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof InvocationTargetException) {
                Throwable targetException = ((InvocationTargetException) e).getTargetException();
                targetException.printStackTrace();
            }
        }
    }

    @FXML
    private void closeWindow() throws ExceptionBadPage {
        super.goToPage("artists/profil-artist.fxml", "Profil Artiste");
    }


    @FXML private void goBack(ActionEvent actionEvent) throws ExceptionBadPage {
        goToPage(boutonRetour,"artists/profil-artist.fxml", "Mon Profil d'Artiste");
    }

}
