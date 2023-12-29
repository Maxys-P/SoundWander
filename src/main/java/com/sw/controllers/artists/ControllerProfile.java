package com.sw.controllers.artists;

import com.sw.classes.Music;
import com.sw.commons.DataHolder;
import com.sw.controllers.proposal.ControllerCreateProposal;
import com.sw.facades.FacadeMusic;
import com.sw.dao.boiteAOutils.MP3Utils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import java.net.URL;

import java.io.File;
import java.util.List;

public class ControllerProfile extends ControllerArtist {
    @FXML
    private TextField musicPathField, titleField;

    @FXML
    private ListView<Music> musicListView;

    @FXML
    private Button deleteButton;

    private final int itemHeight = 35;

    @FXML
    public void initialize() {
        musicListView.setCellFactory(listView -> new ListCell<Music>() {
            private final Button playButton = new Button("Play");
            private final HBox content = new HBox();
            private final Label label = new Label();
            private final Button addButton = new Button("+"); // Créer le bouton "+"

            {
                content.getChildren().addAll(label, playButton, addButton);
                content.setSpacing(10);
                playButton.getStyleClass().add("play-button");
                addButton.getStyleClass().add("play-button");
                //obliger de mettre ça pour que le bouton play soit à droite
                HBox.setHgrow(label, Priority.ALWAYS);  // This will make the label grow and push the button to the right
                label.setMaxWidth(Double.MAX_VALUE);  // Allow the label to grow as needed
                content.setAlignment(Pos.CENTER_RIGHT);  // Align content to the right
                playButton.setOnAction(event -> {
                    Music music = getItem();
                    if (music != null) {
                        playSelectedMusic(music.getId());
                    }
                });
                addButton.setOnAction(event -> {
                    Music music = getItem();
                    if (music != null) {
                        goToPageCreateProposal(music);
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
                    label.setText(music.getName());
                    setGraphic(content);
                }
            }
        });

        try {
            List<Music> musics = FacadeMusic.getInstance().getMusicByUserId();
            musicListView.getItems().addAll(musics);
        } catch (Exception e) {
            e.printStackTrace();
        }

        musicListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            deleteButton.setVisible(newSelection != null);
        });
    }

    void goToPageCreateProposal(Music music) {
        try {
            DataHolder.setCurrentMusic(music); // CORRECTED: Stocker la musique sélectionnée
            Stage stage = (Stage) musicListView.getScene().getWindow();
            URL url = getClass().getResource("/com/views/artists/createProposal-view.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            //FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/views/artists/createProposal-view.fxml"));
            Parent root = loader.load();

            // Passer l'information à ControllerCreateProposal si nécessaire
            ControllerCreateProposal controller = loader.getController();
            controller.setMusicInfo(music);

            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            // Gérer les exceptions
        }
    }

    private void playSelectedMusic(int musicId) {
        try {
            // You might need to communicate with ControllerMusicPlay or directly use FacadeMusic here
            FacadeMusic.getInstance().playMusic(musicId);
            // Update the UI as necessary
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions
        }
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
        Music selectedMusic = musicListView.getSelectionModel().getSelectedItem();
        if (selectedMusic != null) {
            FacadeMusic.getInstance().removeMusic(selectedMusic.getId());
            updateMusicList();
        }
    }

    private void updateMusicList() {
        musicListView.getItems().clear();
        try {
            List<Music> musics = FacadeMusic.getInstance().getMusicByUserId();
            musicListView.getItems().addAll(musics);
        } catch (Exception e) {
            e.printStackTrace();}
    }

    private void updateListViewHeight() {
        int size = musicListView.getItems().size();
        musicListView.setMinHeight(size * itemHeight);
    }
}
