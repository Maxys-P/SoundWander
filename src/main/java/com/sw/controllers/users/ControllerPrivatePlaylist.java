package com.sw.controllers.users;

import com.sw.classes.Music;
import com.sw.classes.User;
import com.sw.facades.FacadeMusic;
import com.sw.facades.FacadePrivatePlaylist;
import com.sw.facades.FacadeUser;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;
// ControllerPrivatePlaylist.java
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class ControllerPrivatePlaylist {

    @FXML
    private ListView<Music> playlistListView;

    private FacadeUser userFacade;
    private FacadeMusic musicFacade;

    public void initialize() throws Exception {
        userFacade = FacadeUser.getInstance();
        musicFacade = FacadeMusic.getInstance();
        List<Music> privatePlaylist = getPrivatePlaylist();

        // Set the items in the ListView
        playlistListView.setItems(FXCollections.observableArrayList(privatePlaylist));

        // Define how to render each item with a CellFactory
        playlistListView.setCellFactory(new Callback<ListView<Music>, ListCell<Music>>() {
            @Override
            public ListCell<Music> call(ListView<Music> listView) {
                return new ListCell<Music>() {
                    @Override
                    protected void updateItem(Music music, boolean empty) {
                        super.updateItem(music, empty);
                        if (empty || music == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            HBox musicItemHBox = new HBox(10);
                            Label musicTitleLabel = new Label(music.getName());
                            Label musicArtistLabel = null;
                            try {
                                musicArtistLabel = new Label(String.valueOf((userFacade.getUserById(music.getArtist())).getPseudo()));
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }

                            Button playButton = new Button("Play");
                            playButton.getStyleClass().add("play-button");
                            playButton.setOnAction(event -> {
                                // Logique pour lire la musique ici (utilisez la méthode playSelectedMusic)
                                playSelectedMusic(music);
                            });

                            musicItemHBox.getChildren().addAll(musicTitleLabel, musicArtistLabel, playButton);
                            setGraphic(musicItemHBox);
                        }
                    }
                };
            }
        });
    }


    private List<Music> getPrivatePlaylist() throws Exception {
        User currentUser = userFacade.getCurrentUser();
        return userFacade.getPrivatePlaylist(currentUser.getId());
    }

    private void playSelectedMusic(Music music) {
        try {
            int musicId = music.getId();
            musicFacade.playMusic(musicId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
