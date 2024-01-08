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
    @FXML private Text continentNameText;

    public ControllerPlaylistMusic() {
        this.playlistMusicFacade = FacadePlaylistMusic.getInstance();
    }

    public void initialize() {
        continentNameText.sceneProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("ControllerPlaylistMusic scene vaut : " + newValue);
            if (newValue != null) {
                newValue.windowProperty().addListener((obs, oldWindow, newWindow) -> {
                    System.out.println("ControllerPlaylistMusic stage vaut : " + newWindow);
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
    private void displayPlaylists(String country, List<PlaylistMusic> playlists) {
        ObservableList<String> playlistNames = FXCollections.observableArrayList();
        for (PlaylistMusic pm : playlists) {
            playlistNames.add(pm.getPlaylist().getPlaylistName() + " - " + musicToString(pm.getMusic()));
        }
        playlistListView.setItems(playlistNames);
    }
    public void setContinentName(String name) {
        continentNameText.setText(name);
        loadPlaylistMusic(name);
    }

    @FXML
    public void goToPlaylist(String country) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/views/public-playlist/playlist-country-view.fxml"));
            Parent root = loader.load();
            DataHolder.setCurrentPlaylistId(playlistMusicFacade.getPlaylistMusicByCountry(country).getPlaylist().getPlaylistId());

            // Access the controller of the playlist country view
            ControllerPlaylistCountry playlistCountryController = loader.getController();
            if (playlistCountryController == null) {
                System.out.println("ControllerPlaylistMusic playlistCountryController est null");
            } else {
                System.out.println("ControllerPlaylistMusic playlistCountryController n'est pas null");
            }
            // Pass the selected country to the playlist country view controller
            playlistCountryController.setCountry(country);
            System.out.println("ControllerPlaylistMusic country vaut : " + country);

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
    @FXML
    public void handleCountrySelection() {
        String selectedCountry = countryListView.getSelectionModel().getSelectedItem();
        if (selectedCountry != null) {
            goToPlaylist(selectedCountry);
        }
    }

}
