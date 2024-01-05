package com.sw.controllers.home;

import com.sw.classes.PlaylistMusic;
import com.sw.controllers.Controller;
import javafx.fxml.FXML;

import javafx.scene.text.Text;

import java.awt.*;

public class ContinentController extends Controller {
    @FXML
    private Text continentNameText;

    public void setContinentName(String name) {
        continentNameText.setText(name);
    }
}
