package com.sw.controllers.artists;

import com.sw.classes.Music;
import com.sw.commons.DataHolder;
import com.sw.controllers.Controller;
import com.sw.controllers.proposal.ControllerCreateProposal;
import com.sw.facades.Facade;
import com.sw.facades.FacadeArtist;
import com.sw.facades.FacadeMusic;
import com.sw.dao.boiteAOutils.MP3Utils;
import com.sw.facades.FacadePayment;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import java.net.URL;

import java.io.File;
import java.util.List;

public class ControllerArtist extends Controller {
    final FacadeArtist facadeArtist = FacadeArtist.getInstance();
    final FacadePayment facadePayment = FacadePayment.getInstance();
    @FXML public Label noSelectionLabel;
    @FXML private TextField musicPathField, titleField;
    @FXML private ListView<Music> musicListView;
    @FXML private Button deleteButton;
    @FXML private Text errorText;

    private final int itemHeight = 35;

    @FXML
    public void initialize() {
        super.hideError(errorText);
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
                    setAddButtonState(addButton, music);
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/views/artists/createProposal-view.fxml"));
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
                try {
                    FacadeMusic.getInstance().addMusic(title, (int) duration, selectedFile.getAbsolutePath());
                    updateMusicList();
                    titleField.setText("");
                    musicPathField.setText("");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
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


    // Cette méthode sera appelée pour chaque item de la liste pour définir si le bouton "+" doit être actif ou non.
    private void setAddButtonState(Button addButton, Music music) {
        try {
            boolean canPropose = facadePayment.canArtistProposeMusic(Facade.currentUser.getId());
            if (!canPropose) {
                addButton.getStyleClass().add("button-disabled");
                addButton.setOnAction(event -> super.displayError(errorText,"Vous ne pouvez pas proposer de musiques aux playlists sans un abonnement actif."));
            } else {
                addButton.getStyleClass().remove("button-disabled");
                addButton.setOnAction(event -> goToPageCreateProposal(music));
            }
        } catch (Exception e) {
            e.printStackTrace();
            super.displayError(errorText, "Une erreur est survenue lors de la vérification de votre abonnement.");
        }
    }
}
