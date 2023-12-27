package com.sw.controllers.proposal;

import com.sw.classes.Proposal;
import com.sw.exceptions.ExceptionBadPage;
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
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;

import java.util.ArrayList;
import java.util.List;


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

    @FXML
    private Button closeButton;

    // Cette méthode permet de mettre à jour le texte du Label en fonction de la musique sélectionnée
    public void setSelectedMusic(String musicTitle) {
        musicLabel.setText(musicTitle);
        //TODO: méthode handleMusicSelection() qui appelle setSelectedMusicTitle dans page profil à implémenter
    }


    private Music getSelectedMusic(String name) {
        Music selectedMusic = null;
        try {
            // Establish a database connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/DBSoundWander", "root", "se-database");

            // SQL query to select the music
            String sql = "SELECT id, name, artist, duration, musicFile FROM music WHERE name = ?";

            // Prepare the statement
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // Process the result
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String musicName = resultSet.getString("name");
                int artist = resultSet.getInt("artist");
                int duration = resultSet.getInt("duration");
                byte[] musicFile = resultSet.getBytes("musicFile");

                selectedMusic = new Music(id, musicName, artist, duration, musicFile);
            }

            // Close resources
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions such as no driver, no connection, etc.
        }

        return selectedMusic;
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

        // Assuming you have the name of the music you want to get
        String musicName = "Premier Son"; // Replace with the actual name or retrieve it dynamically
        Music selectedMusic = getSelectedMusic(musicName);

        // Check if a music object was returned and update the label accordingly
        if (selectedMusic != null) {
            musicLabel.setText(selectedMusic.getName());
        } else {
            musicLabel.setText("Music not found");
        }
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

            // Specify the music name to retrieve
            String musicName = "Premier Son"; // Replace with the actual music name or get it dynamically
            Music music = getSelectedMusic(musicName);

            // Ensure that a music object was actually returned
            if (music == null) {
                throw new ExceptionFormIncomplete("Music not found");
            }

            Proposal proposal = super.proposalFacade.createProposal(artist, music, country, description);

            closeWindow();

        } catch (ExceptionFormIncomplete e) {
            // Display a specific error message to the user
            displayError(errorText, e.getMessage());
        } catch (Exception e) {
            // Display a generic error message to the user
            e.printStackTrace();
            displayError(errorText, "Une erreur inattendue est survenue.");
            // Ajoutez un bloc catch pour l'InvocationTargetException pour l'analyser
            if (e instanceof InvocationTargetException) {
                Throwable targetException = ((InvocationTargetException) e).getTargetException();
                targetException.printStackTrace();
            }
        }
    }

    @FXML
    private void closeWindow() throws ExceptionBadPage {
        super.goToPage(closeButton, "users/home-view.fxml", "Accueil");
        //TODO: modifier pour que ça ramène à la page profil
    }



}
