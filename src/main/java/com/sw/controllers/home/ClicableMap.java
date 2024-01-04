package com.sw.controllers.home;

import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

import java.net.URL;

public class ClicableMap {
    @FXML
    private WebView webView;

    public void initialize() {
        WebEngine webEngine = webView.getEngine();

        // Récupère l'URL de la ressource map.html dans le dossier des ressources
        URL url = getClass().getResource("/images/map.html"); // Assurez-vous que le chemin est correct
        webEngine.load(url.toExternalForm());

        // Attendre que la page soit chargée avant d'injecter les méthodes Java
        webEngine.getLoadWorker().stateProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                // Exposer le contrôleur Java au contexte JavaScript
                System.out.println("[home] Page chargée");
                JSObject jsobj = (JSObject) webEngine.executeScript("window");
                jsobj.setMember("javaController", this);
            }
        });
    }

    public void onCountryClicked(String countryId) {
        System.out.println("Pays cliqué: " + countryId);
    }

}
