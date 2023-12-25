package com.sw.controllers.proposal;

import com.sw.classes.Proposal;
import com.sw.exceptions.ExceptionFormIncomplete;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import com.sw.classes.Music;
import com.sw.dao.DAOMusic;
import com.sw.classes.User;
import com.sw.facades.Facade;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class ControllerCreateProposal extends ControllerProposal {

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

    @FXML
    private Button closeButton;

    // Cette méthode permet de mettre à jour le texte du Label en fonction de la musique sélectionnée
    public void setSelectedMusic(String musicTitle) {
        musicLabel.setText(musicTitle);
        //TODO: méthode handleMusicSelection() qui appelle setSelectedMusicTitle dans page profil à implémenter
    }


    private Music getSelectedMusic() {
        // TODO: Implémentez la méthode getSelectedMusic() qui retourne un objet Music
          /*  int id = 1;
            String name = "Premier Son Bis";
            int artist = 1;
            int duration = 10;
            return new Music(id, name, artist, duration);*/
    return null;
    }

    @FXML
    public void initialize() {
        List<String> countries = new ArrayList<>();
        countries.add("Afghanistan");
        countries.add("Albania");
        countries.add("Algeria");
        countries.add("Angola");
        countries.add("Argentina");
        countries.add("Armenia");
        countries.add("Australia");
        countries.add("Austria");
        countries.add("Azerbaijan");
        countries.add("Bangladesh");
        countries.add("Barbados");
        countries.add("Belgium");
        countries.add("Belize");
        countries.add("Benin");
        countries.add("Bolivia");
        countries.add("Bosnia and Herzegovina");
        countries.add("Botswana");
        countries.add("Brazil");
        countries.add("Bulgaria");
        countries.add("Burkina Faso");
        countries.add("Cambodia");
        countries.add("Cameroon");
        countries.add("Canada");
        countries.add("Central African Republic");
        countries.add("Chad");
        countries.add("Chile");
        countries.add("China");
        countries.add("Colombia");
        countries.add("Congo");
        countries.add("Costa Rica");
        countries.add("Croatia");
        countries.add("Cuba");
        countries.add("Cyprus");
        countries.add("Czech Republic");
        countries.add("Denmark");
        countries.add("Dominican Republic");
        countries.add("Ecuador");
        countries.add("Egypt");
        countries.add("Estonia");
        countries.add("Ethiopia");
        countries.add("Finland");
        countries.add("France");
        countries.add("Gabon");
        countries.add("Germany");
        countries.add("Ghana");
        countries.add("Greece");
        countries.add("Guatemala");
        countries.add("Guinea");
        countries.add("Haiti");
        countries.add("Hungary");
        countries.add("Iceland");
        countries.add("India");
        countries.add("Indonesia");
        countries.add("Iran");
        countries.add("Iraq");
        countries.add("Ireland");

        countryField.getItems().addAll(countries);

        Music selectedMusic = getSelectedMusic();
        musicLabel.setText(selectedMusic.getName());
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
            Music music = getSelectedMusic();

            Proposal proposal = super.proposalFacade.createProposal(artist, music, country, description);

            closeWindow();

        } catch (ExceptionFormIncomplete e) {
            // Afficher un message d'erreur spécifique à l'utilisateur
            displayError(errorText, e.getMessage());
        } catch (Exception e) {
            // Afficher un message d'erreur générique à l'utilisateur
            displayError(errorText, "Une erreur inattendue est survenue.");
        }
    }

    @FXML
    private void closeWindow() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }



}
