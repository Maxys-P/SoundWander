package com.sw.controllers.publicPlaylist;

import com.sw.classes.Music;
import com.sw.classes.PlaylistMusic;
import com.sw.controllers.Controller;
import com.sw.facades.FacadePlaylistMusic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import java.util.List;

public class ControllerPlaylistMusic extends Controller {
    private FacadePlaylistMusic publicPlaylistFacade;
    @FXML
    private ListView<Music> musicListView;

    public ControllerPlaylistMusic() {
        this.publicPlaylistFacade = FacadePlaylistMusic.getInstance();
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


}
