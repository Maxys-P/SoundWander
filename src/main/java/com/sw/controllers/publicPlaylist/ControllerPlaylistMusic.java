package com.sw.controllers.publicPlaylist;

import com.sw.classes.Music;
import com.sw.classes.PlaylistMusic;
import com.sw.commons.DataHolder;
import com.sw.controllers.Controller;
import com.sw.facades.FacadePlaylistMusic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ControllerPlaylistMusic extends Controller {
    private FacadePlaylistMusic playlistMusicFacade;
    @FXML private ListView<String> playlistListView = new ListView<>();
    @FXML private ListView<String> countryListView = new ListView<>();
    @FXML private ListView<String> playlistMusicListView = new ListView<>();
    @FXML private Text continentNameText;

    public ControllerPlaylistMusic() {
        this.playlistMusicFacade = FacadePlaylistMusic.getInstance();
    }

    /**
     * Initializes the controller after its root element has been completely processed.
     * Sets up listeners to capture and use the stage information to fetch and display the playlist
     * based on the continent name set as the stage title.
     */
    public void initialize() {
        continentNameText.sceneProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                newValue.windowProperty().addListener((obs, oldWindow, newWindow) -> {
                    if (newWindow instanceof Stage){
                        Stage stage = (Stage) newWindow;
                        String continentName = stage.getTitle();
                        System.out.println("ControllerPlaylistMusic continentName vaut : " + continentName);
                        if(continentName != null) {
                            setContinentName(continentName);
                        }
                    }
                });
            }
        });
    }

    /**
     * Displays the playlists by country in the ListView.
     * Each playlist entry shows the country, playlist name, and music names.
     *
     * @param playlistsByCountry a map where the key is the country and the value is a list of PlaylistMusic objects.
     */
    public void displayPlaylist(Map<String, List<PlaylistMusic>> playlistsByCountry) {
        ObservableList<String> displayList = FXCollections.observableArrayList();
        for (Map.Entry<String, List<PlaylistMusic>> entry : playlistsByCountry.entrySet()) {
            String country = entry.getKey();
            List<PlaylistMusic> playlists = entry.getValue();
            for (PlaylistMusic playlist : playlists) {
                displayList.add(country + ": " + playlist.getPlaylist().getPlaylistName() + " - " + musicToString(playlist.getMusic()));
            }
        }
        playlistListView.setItems(displayList);
    }

    private String musicToString(List<Music> musics) {
        return musics.stream().map(Music::getName).collect(Collectors.joining(", "));
    }
    /**
     * Loads and displays playlists for a given continent.
     * Fetches playlist data and updates the country and playlist ListViews.
     *
     * @param continent the continent name to load playlists for.
     */
    private void loadPlaylistMusic(String continent) {
        try {
            Map<String, List<PlaylistMusic>> playlistMusicsByCountry = playlistMusicFacade.getPlaylistMusicByContinent(continent);

            ObservableList<String> countries = FXCollections.observableArrayList(playlistMusicsByCountry.keySet());
            countryListView.setItems(countries);
            countryListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                displayPlaylists(newValue, playlistMusicsByCountry.get(newValue));
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Displays playlists for a given country in the ListView.
     * Each playlist entry shows the playlist name and associated music.
     *
     * @param country the selected country.
     * @param playlists the playlists associated with the selected country.
     */
    private void displayPlaylists(String country, List<PlaylistMusic> playlists) {
        ObservableList<String> playlistNames = FXCollections.observableArrayList();
        for (PlaylistMusic pm : playlists) {
            playlistNames.add(pm.getPlaylist().getPlaylistName() + " - " + musicToString(pm.getMusic()));
        }
        playlistListView.setItems(playlistNames);
    }
    /**
     * Sets the continent name and triggers the loading and display of playlists for that continent.
     *
     * @param name the name of the continent to load playlists for.
     */
    public void setContinentName(String name) {
        continentNameText.setText(name);
        loadPlaylistMusic(name);
    }

    /**
     * Navigates to the playlist view for the given country.
     * Loads the FXML for the playlist view, sets the current playlist ID in the data holder,
     * and initializes the playlist view with the selected country.
     *
     * @param country the country to display playlists for.
     */
    @FXML
    public void goToPlaylist(String country) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/views/public-playlist/playlist-country-view.fxml"));
            Parent root = loader.load();
            DataHolder.setCurrentPlaylistId(playlistMusicFacade.getPlaylistMusicByCountry(country).getPlaylist().getPlaylistId());

            // Access the controller of the playlist country view
            ControllerPlaylistCountry playlistCountryController = loader.getController();
            // Pass the selected country to the playlist country view controller
            playlistCountryController.setCountry(country);

            // Create a new scene and stage for the playlist country view
            Scene playlistCountryScene = new Scene(root);
            Stage playlistCountryStage = new Stage();
            playlistCountryStage.setScene(playlistCountryScene);
            playlistCountryStage.show();
            Stage stage = (Stage) continentNameText.getScene().getWindow();
            stage.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Handles the selection of a country from the country ListView.
     * If a country is selected, it triggers navigation to the corresponding playlist view.
     */
    @FXML
    public void handleCountrySelection() {
        String selectedCountry = countryListView.getSelectionModel().getSelectedItem();
        if (selectedCountry != null) {
            goToPlaylist(selectedCountry);
        }
    }


    @FXML
    public void displayAllPlaylist() {
        try {
            List<PlaylistMusic> playlistMusics = playlistMusicFacade.getAllPlaylistMusic();
            ObservableList<String> playlistNames = FXCollections.observableArrayList();
            for (PlaylistMusic pm : playlistMusics) {
                playlistNames.add(pm.getPlaylist().getPlaylistName() + " - " + musicToString(pm.getMusic()));
            }
            playlistMusicListView.setItems(playlistNames);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
