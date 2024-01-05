package com.sw.controllers.search;

import com.sw.classes.Playlist;
import com.sw.classes.PlaylistMusic;
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
import com.sw.controllers.publicPlaylist.ControllerPlaylistMusic;

import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
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

    final FacadeSearch searchFacade = FacadeSearch.getInstance(); // Utilisez la méthode singleton pour obtenir l'instance

    @FXML
    public void initialize() {
        super.hideError(errorText);
        comboBoxType.getItems().addAll("Artiste", "Musique", "Playlist");
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
                        setText(((Playlist) item).getPlaylistName());
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

    @FXML
    private void performSearch() {
        searchResultsListView.getItems().clear(); // Assurez-vous de nettoyer la ListView
        String type = comboBoxType.getValue().toLowerCase(); // Convertir en minuscule pour correspondre aux valeurs attendues
        String term = searchTerm.getText();
        SearchCriteria criteria = new SearchCriteria(term, type);
        List<Object> searchResult = searchFacade.performSearch(criteria);

        if (searchResult.isEmpty()) {
            errorText.setText("Aucun résultat trouvé");
            errorText.setVisible(true);
        } else {
            errorText.setVisible(false);

            // Utilisez une seule ListView pour afficher les résultats
            ListView<Object> listViewToUse;

            if (type.equals("musique")) {
                // Si le type de recherche est artiste ou musique, utilisez musicListView
                listViewToUse = searchResultsListView;
            }
            else if (type.equals("artiste")) {
                displayArtistInfo((Artist) searchResult.get(0));
                listViewToUse = searchResultsListView;
            }
            else if (type.equals("playlist")) {
                listViewToUse = searchResultsListView;
                ;
            } else {
                // Gérez d'autres types de recherche ici si nécessaire
                listViewToUse = null; // Utilisez null ou toute autre logique appropriée
            }

            if (listViewToUse != null) {
                for (Object result : searchResult) {
                    listViewToUse.getItems().add(result);
                }
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

    private void displayPlaylist(Playlist playlist) {
        searchResultsListView.getItems().add(playlist);
    }

    @FXML
    private void closeSearch() {
        Stage stage = (Stage) conteneurSearch.getScene().getWindow();
        stage.close();
    }
}
