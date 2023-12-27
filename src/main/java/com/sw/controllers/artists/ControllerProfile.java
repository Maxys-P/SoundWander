package com.sw.controllers.artists;

import com.sw.facades.FacadeMusic;
import com.sw.dao.boiteAOutils.MP3Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.sw.classes.MusicInfo;
import javafx.util.Callback;

import java.io.File;
import java.util.List;

public class ControllerProfile {
    @FXML
    private TextField musicPathField, titleField;

    @FXML
    private ListView<MusicInfo> musicListView;

    @FXML
    private Button deleteButton;

    @FXML
    public void initialize() {
        musicListView.setCellFactory(new Callback<ListView<MusicInfo>, ListCell<MusicInfo>>() {
            @Override
            public ListCell<MusicInfo> call(ListView<MusicInfo> listView) {
                return new ListCell<MusicInfo>() {
                    @Override
                    protected void updateItem(MusicInfo music, boolean empty) {
                        super.updateItem(music, empty);
                        if (empty || music == null) {
                            setText(null);
                        } else {
                            setText(music.getName());
                        }
                    }
                };
            }
        });

        try {
            List<MusicInfo> musics = FacadeMusic.getInstance().getMusicByUserId();
            musicListView.getItems().addAll(musics);
        } catch (Exception e) {
            e.printStackTrace();
        }

        musicListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            deleteButton.setVisible(newSelection != null);
        });
    }
    @FXML
    private void handleUploadMusic() {
        String title = titleField.getText();

        File selectedFile = new File(musicPathField.getText());
        if (selectedFile.exists()) {
            long duration = MP3Utils.getMP3Duration(selectedFile);
            if (duration != -1) {
                FacadeMusic.getInstance().addMusic(title, (int) duration, selectedFile.getAbsolutePath());
                updateMusicList();
            } else {
                System.out.println("Impossible de calculer la durée de la musique.");
            }
        } else {
            System.out.println("Veuillez sélectionner un fichier valide !");
        }
    }

    @FXML
    private void handlePath() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("MP3 Files", "*.mp3")
        );
        Stage stage = (Stage) musicPathField.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            musicPathField.setText(selectedFile.getAbsolutePath());
        }
    }

    @FXML
    private void handleDeleteMusic() {
        MusicInfo selectedMusic = musicListView.getSelectionModel().getSelectedItem();
        if (selectedMusic != null) {
            FacadeMusic.getInstance().removeMusic(selectedMusic.getId());
            updateMusicList();
        }
    }

    private void updateMusicList() {
        musicListView.getItems().clear();
        try {
            List<MusicInfo> musics = FacadeMusic.getInstance().getMusicByUserId();
            musicListView.getItems().addAll(musics);
        } catch (Exception e) {
            e.printStackTrace();}
    }
}
