package com.sw.controllers.search;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import com.sw.facades.FacadeSearch;
import com.sw.commons.SearchCriteria;
import com.sw.classes.Music;
import com.sw.facades.FacadeMusic;
import com.sw.controllers.Controller;
import com.sw.classes.Artist;
import com.sw.controllers.musicPlay.ControllerMusicPlay;

import javafx.scene.control.Button;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ListCell;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ControllerSearch extends Controller{

    @FXML
    private ComboBox<String> comboBoxType;

    @FXML
    private TextField searchTerm;

    @FXML
    private VBox conteneurSearch;
    @FXML
    private Button doSearch;
    @FXML
    private VBox result; // Utilisez la VBox pour afficher les résultats
    @FXML
    private ListView<Music> musicListView;
    @FXML
    private Text errorText;

    final FacadeSearch searchFacade = FacadeSearch.getInstance(); // Utilisez la méthode singleton pour obtenir l'instance

    @FXML
    public void initialize() {
        super.hideError(errorText);
        comboBoxType.getItems().addAll("Artiste", "Musique");
        doSearch.setOnAction(event -> performSearch());
        musicListView.setCellFactory(param -> new ListCell<Music>() {
            @Override
            protected void updateItem(Music music, boolean empty) {
                super.updateItem(music, empty);
                if (empty || music == null || music.getName() == null) {
                    setText(null);
                } else {
                    setText(music.getName());
                }
            }
        });
        musicListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Récupérez la musique sélectionnée
                Music selectedMusic = newSelection;

                // Appelez une méthode pour lancer la musique dans ControllerMusicPlay
                launchMusicInControllerMusicPlay(selectedMusic);
            }
        });

    }

    @FXML
    private void performSearch() {
        musicListView.getItems().clear();
        result.getChildren().clear();
        String type = comboBoxType.getValue().toLowerCase(); // Convertir en minuscule pour correspondre aux valeurs attendues
        String term = searchTerm.getText();
        SearchCriteria criteria = new SearchCriteria(term, type);
        List<Object> searchResult = searchFacade.performSearch(criteria);

if (searchResult.isEmpty()) {
            errorText.setText("Aucun résultat trouvé");
            errorText.setVisible(true);
        } else {
            errorText.setVisible(false);
    for (Object result : searchResult) {
        if (result instanceof Artist) {
            displayArtistInfo((Artist) result);
        } else if (result instanceof Music) {
            musicListView.getItems().add((Music) result); // Utilisez "result" au lieu de "searchResult"
        }
    }
        }
    }


    private void displayArtistInfo(Artist artist) {
        String pseudoInfo = "Pseudo: " + artist.getPseudo();
        String dateInfo = "Date de naissance: " + artist.getDateNaissance();
        int artistId = artist.getId();

        // Vider la liste des musiques
        musicListView.getItems().clear();

        // Utilisez FacadeMusic pour obtenir les musiques de l'artiste
        List<Music> musics = null;
        try {
            musics = FacadeMusic.getInstance().getMusicByUserId(artistId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (!musics.isEmpty()) {
            // Si des musiques sont trouvées, ajoutez-les à la ListView
            musicListView.getItems().addAll(musics);
        } else {
            errorText.setText("Aucune musique trouvée pour cet artiste");
            errorText.setVisible(false);
        }
        result.getChildren().addAll(new Label(pseudoInfo), new Label(dateInfo));
    }

    private void launchMusicInControllerMusicPlay(Music selectedMusic) {

        try {
            // You might need to communicate with ControllerMusicPlay or directly use FacadeMusic here
            FacadeMusic.getInstance().playMusic(selectedMusic.getId());
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions
        }

        // Fermez la fenêtre de recherche si nécessaire
        Stage stage = (Stage) conteneurSearch.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void closeSearch() {
        Stage stage = (Stage) conteneurSearch.getScene().getWindow();
        stage.close();
    }
}
