package com.sw.controllers.home;

import com.sw.controllers.Controller;
import com.sw.exceptions.ExceptionBadPage;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

public class ClickableMapController extends Controller {
    @FXML
    private WebView webView;

    public void initialize() {
        WebEngine webEngine = webView.getEngine();
        webEngine.load(getClass().getResource("/images/World-html.html").toExternalForm());

        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                JSObject jsobj = (JSObject) webEngine.executeScript("window");
                jsobj.setMember("javaController", this);
            }
        });
    }

    public void onContinentClicked(String continentName) {
        System.out.println("Continent cliqué: " + continentName);
        try {
            goToPage("public-playlist/public-playlist.fxml", continentName);
        } catch (ExceptionBadPage e) {
            e.printStackTrace();
        }
    }

}
