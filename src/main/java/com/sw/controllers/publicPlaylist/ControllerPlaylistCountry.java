package com.sw.controllers.publicPlaylist;

import com.sw.classes.Artist;
import com.sw.classes.Music;
import com.sw.classes.PlaylistMusic;
import com.sw.controllers.Controller;
import com.sw.facades.FacadeArtist;
import com.sw.facades.FacadeMusic;
import com.sw.facades.FacadeUser;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import com.sw.facades.FacadePlaylistMusic;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.util.List;

public class ControllerPlaylistCountry extends Controller {
    @FXML
    private ListView<Music> musicListView;


    private FacadePlaylistMusic playlistMusicFacade;

    public ControllerPlaylistCountry() {
        this.playlistMusicFacade = FacadePlaylistMusic.getInstance();
    }

    @FXML
    public void initialize() {
        musicListView.setCellFactory(listView -> new ListCell<Music>() {
            private final Button playButton = new Button("Play");
            private final HBox content = new HBox();
            private final Label label = new Label();

            {
                content.getChildren().addAll(label, playButton);
                content.setSpacing(10);
                playButton.getStyleClass().add("play-button");
                HBox.setHgrow(label, Priority.ALWAYS);
                label.setMaxWidth(Double.MAX_VALUE);
                content.setAlignment(Pos.CENTER_RIGHT);
                playButton.setOnAction(event -> {
                    Music music = getItem();
                    if (music != null) {
                        playSelectedMusic(music.getId());
                    }
                });
            }

            @Override
            protected void updateItem(Music music, boolean empty) {
                super.updateItem(music, empty);

                if (empty || music == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    label.setText(music.getName() + " - " + getArtistName(music.getArtist())); // You need to implement getArtistName
                    setGraphic(content);
                }
            }
        });
    }


    private void playSelectedMusic(int musicId) {
        try {
            // Implement playMusic in FacadeMusic to play the selected music
            FacadeMusic.getInstance().playMusic(musicId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getArtistName(int artistId) {
        try {
            Artist artist = FacadeArtist.getInstance().getArtistById(artistId);
            return artist != null ? artist.getPseudo() : "Unknown Artist";
        } catch (Exception e) {
            e.printStackTrace();
            return "Unknown Artist";
        }
    }

    public void setCountry(String country) {
        try {
            PlaylistMusic playlistMusic = getPlaylistMusicByCountry(country);
            if (playlistMusic != null) {
                List<Music> musics = playlistMusic.getMusic();
                musicListView.getItems().clear(); // Nettoyez la liste avant de l'ajouter
                musicListView.getItems().addAll(musics); // Ajoutez directement les objets Music à la ListView
            } else {
                System.out.println("No playlist found for the country: " + country);
                musicListView.getItems().clear();
                musicListView.getItems().add(null); // Vous devrez gérer les objets null dans votre cell factory
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Implement a method to retrieve music data for the selected country
    private PlaylistMusic getPlaylistMusicByCountry(String country) throws Exception {
        System.out.println("getMusicByCountry - country demander : "+country);
        System.out.println(playlistMusicFacade.getPlaylistMusicByCountry(country));
        return playlistMusicFacade.getPlaylistMusicByCountry(country);
    }
}
