package com.sw.controllers.publicPlaylist;

import com.sw.classes.Music;
import com.sw.classes.PlaylistMusic;
import com.sw.controllers.Controller;
import com.sw.dao.daoMysql.DAOPlaylistMusicMySQL;
import com.sw.facades.FacadePlaylistMusic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;

public class ControllerPlaylistMusic extends Controller {
    private FacadePlaylistMusic playlistMusicFacade;
    @FXML
    private ListView<Music> musicListView;
    @FXML
    private Text continentNameText;

    public ControllerPlaylistMusic() {
        this.playlistMusicFacade = FacadePlaylistMusic.getInstance();
    }

    public void initialize() {
        continentNameText.sceneProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("ContinentController scene vaut : " + newValue);
            if (newValue != null) {
                newValue.windowProperty().addListener((obs, oldWindow, newWindow) -> {
                    System.out.println("ContinentController stage vaut : " + newWindow);
                    if (newWindow instanceof Stage){
                        Stage stage = (Stage) newWindow;
                        String continentName = stage.getTitle();
                        System.out.println("ContinentController continentName vaut : " + continentName);
                        if(continentName != null) {
                            setContinentName(continentName);
                        }
                    }
                });
            }
        });
    }

    public void displayPlaylist(PlaylistMusic playlistMusic) {
        List<Music> musics = playlistMusic.getMusic();

        // Mettre à jour le ListView avec les musiques
        ObservableList<Music> musicList = FXCollections.observableArrayList(musics);
        musicListView.setItems(musicList);

        // Assurez-vous que le ListView affiche correctement les informations des musiques
        musicListView.setCellFactory(param -> new ListCell<Music>() {
            @Override
            protected void updateItem(Music music, boolean empty) {
                super.updateItem(music, empty);
                if (empty || music == null) {
                    setText(null);
                } else {
                    setText(music.getName()); // Vous pouvez personnaliser l'affichage ici
                }
            }
        });
    }


    private void loadPlaylistMusic(String continent) {
        try {
            List<PlaylistMusic> playlistMusics = playlistMusicFacade.getPlaylistMusicByContinent(continent);
            System.out.println(playlistMusics);
            for (PlaylistMusic playlistMusic : playlistMusics) {
                displayPlaylist(playlistMusic);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setContinentName(String name) {
        continentNameText.setText(name);
        loadPlaylistMusic(name);
    }



}
