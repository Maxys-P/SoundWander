package com.sw.controllers.home;

import com.sw.classes.PlaylistMusic;
import com.sw.controllers.Controller;
import com.sw.dao.daoMysql.DAOPlaylistMusicMySQL;
import javafx.fxml.FXML;

import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;

public class ContinentController extends Controller {
    @FXML
    private Text continentNameText;
    private List<PlaylistMusic> playlistMusics;
    private DAOPlaylistMusicMySQL daoPlaylistMusicMySQL = new DAOPlaylistMusicMySQL();

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

    private void loadPlaylistMusic(String continent) {
        try {
            playlistMusics = daoPlaylistMusicMySQL.getPlaylistMusicByContinent(continent);
            System.out.println(playlistMusics);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setContinentName(String name) {
        continentNameText.setText(name);
        loadPlaylistMusic(name);
    }
}
