package com.sw.controllers.search;

import com.neovisionaries.i18n.CountryCode;
import com.sw.classes.Playlist;
import com.sw.classes.PlaylistMusic;
import com.sw.controllers.publicPlaylist.ControllerPlaylistCountry;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import com.sw.facades.FacadeSearch;
import com.sw.commons.SearchCriteria;
import com.sw.classes.Music;
import com.sw.facades.FacadeMusic;
import com.sw.controllers.Controller;
import com.sw.classes.Artist;
import com.sw.controllers.publicPlaylist.ControllerPlaylistMusic;

import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ListCell;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ControllerSearch extends Controller {

    @FXML
    private ComboBox<String> comboBoxType;

    @FXML
    private TextField searchTerm;

    @FXML
    private ListView<Object> searchResultsListView;

    @FXML
    private VBox conteneurSearch;

    @FXML
    private Button doSearch;

    @FXML
    private VBox result;

    @FXML
    private Text errorText;

    @FXML
    private ComboBox<String> searchComboBox;

    final FacadeSearch searchFacade = FacadeSearch.getInstance(); // Utilisez la méthode singleton pour obtenir l'instance

    @FXML
    public void initialize() {
        super.hideError(errorText);
        comboBoxType.getItems().addAll("Artiste", "Musique", "Playlist");
        comboBoxType.valueProperty().addListener((obs, oldItem, newItem) -> toggleSearchInput(newItem));
        doSearch.setOnAction(event -> performSearch());

        searchResultsListView.setCellFactory(param -> new ListCell<Object>() {
            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    // Vous devrez personnaliser l'affichage en fonction du type d'objet (Musique ou Playlist)
                    if (item instanceof Music) {
                        setText(((Music) item).getName());
                    } else if (item instanceof Playlist) {
                        setText(((Playlist) item).getPlaylistCountry());
                    }
                }
            }
        });

        searchResultsListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                if (newSelection instanceof Music) {
                    // L'utilisateur a sélectionné une musique
                    Music selectedMusic = (Music) newSelection;
                    launchMusicInControllerMusicPlay(selectedMusic);
                } else if (newSelection instanceof Playlist) {
                    // L'utilisateur a sélectionné une playlist
                    Playlist selectedPlaylist = (Playlist) newSelection;
                    displayPlaylist(selectedPlaylist);
                }
            }
        });
    }

    private void toggleSearchInput(String type) {
        if ("Playlist".equals(type)) {
            searchTerm.setVisible(false);
            searchTerm.setManaged(false);
            searchComboBox.setVisible(true);
            searchComboBox.setManaged(true);
            searchComboBox.getItems().clear();

            List<String> countries = new ArrayList<>();
            for (CountryCode code : CountryCode.values()) {
                countries.add(code.getName());
            }
            Collections.sort(countries);
            searchComboBox.getItems().addAll(countries);

        } else {
            searchTerm.setVisible(true);
            searchTerm.setManaged(true);
            searchComboBox.setVisible(false);
            searchComboBox.setManaged(false);
        }
    }

    @FXML
    private void performSearch() {
        searchResultsListView.getItems().clear(); // Nettoyer la ListView avant d'afficher de nouveaux résultats
        String type = comboBoxType.getValue().toLowerCase(); // Convertir en minuscule pour correspondre aux valeurs attendues

        // Déterminer le terme de recherche en fonction du type sélectionné (TextField ou ComboBox)
        String term;
        if ("playlist".equals(type)) {
            term = searchComboBox.getValue(); // Utilisez la valeur de la ComboBox pour les playlists
        } else {
            term = searchTerm.getText(); // Utilisez la valeur du TextField pour les artistes et les musiques
        }

        SearchCriteria criteria = new SearchCriteria(term, type);
        List<Object> searchResult = searchFacade.performSearch(criteria);

        if (searchResult.isEmpty()) {
            errorText.setText("Aucun résultat trouvé");
            errorText.setVisible(true);
        } else {
            errorText.setVisible(false);

            // Utilisez une seule ListView pour afficher les résultats
            ListView<Object> listViewToUse = searchResultsListView;

            for (Object result : searchResult) {
                listViewToUse.getItems().add(result);
            }
        }
    }


    private void displayArtistInfo(Artist artist) {
        String pseudoInfo = "Pseudo: " + artist.getPseudo();
        String dateInfo = "Date de naissance: " + artist.getDateNaissance();
        int artistId = artist.getId();

        // Vider la liste des musiques
        searchResultsListView.getItems().clear();

        // Utilisez FacadeMusic pour obtenir les musiques de l'artiste
        List<Music> musics = null;
        try {
            musics = FacadeMusic.getInstance().getMusicByUserId(artistId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (!musics.isEmpty()) {
            // Si des musiques sont trouvées, ajoutez-les à la ListView
            searchResultsListView.getItems().addAll(musics);
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

    public void displayPlaylist(Playlist selectedPlaylist) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/views/public-playlist/playlist-country-view.fxml")); // Ajustez le chemin d'accès
            Parent root = loader.load();
            ControllerPlaylistCountry playlistCountryController = loader.getController();

            // Puisque vous voulez récupérer les musiques par pays, vous utilisez le pays de la playlist
            String countryOfPlaylist = selectedPlaylist.getPlaylistCountry();

            // Maintenant, utilisez le ControllerPlaylistCountry pour afficher les musiques de la playlist
            playlistCountryController.setCountry(countryOfPlaylist);

            // Affichez la vue dans une nouvelle fenêtre ou comme vous le souhaitez
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void closeSearch() {
        Stage stage = (Stage) conteneurSearch.getScene().getWindow();
        stage.close();
    }
}
