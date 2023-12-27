package com.sw.controllers.proposal;

import com.sw.classes.Proposal;
import com.sw.classes.Music;
import com.sw.facades.FacadeMusic;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import com.sw.classes.User;
import com.sw.facades.FacadeUser;
import javafx.scene.Node;
import java.util.ArrayList;

import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class ControllerListAllProposals extends ControllerProposal {

    @FXML
    private VBox proposalsContainer;

    @FXML
    private ComboBox<String> countryFilterComboBox;

    @FXML
    public void initialize() {
        try {
            List<Proposal> allProposals = proposalFacade.getAllProposals();
            for (int i = 0; i < allProposals.size(); i++) {
                Proposal proposal = allProposals.get(i);
                VBox proposalItem = setProposalItem(proposal);
                proposalsContainer.getChildren().add(proposalItem);
                // Ajouter un séparateur uniquement s'il y a une proposition suivante
                if (i < allProposals.size() - 1) {
                    proposalsContainer.getChildren().add(new Separator());
                }
            }

            Set<String> countries = proposalFacade.getAllProposals().stream().map(Proposal::getCountry).collect(Collectors.toCollection(TreeSet::new));
            countryFilterComboBox.getItems().add("All Countries");
            countryFilterComboBox.getItems().addAll(countries);
            countryFilterComboBox.setValue("All Countries");

            countryFilterComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
                if ("All Countries".equals(newVal)) {
                    filterProposalsByCountry(null);
                } else {
                    filterProposalsByCountry(newVal);
                }
            });
            filterProposalsByCountry(null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void filterProposalsByCountry(String country) {
        proposalsContainer.getChildren().clear(); // Clear all current proposals
        List<Proposal> proposals;
        if (country == null || country.isEmpty()) {
            try {
                proposals = proposalFacade.getAllProposals(); // Get all proposals if no country is selected
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                proposals = proposalFacade.getAllProposalsByCountry(country); // Filter by country
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        for (Proposal proposal : proposals) {
            VBox proposalItem = setProposalItem(proposal);
            proposalsContainer.getChildren().add(proposalItem);
            // Add separators between proposal items
            proposalsContainer.getChildren().add(new Separator());
        }
        // Remove the last separator
        if (!proposals.isEmpty()) {
            proposalsContainer.getChildren().remove(proposalsContainer.getChildren().size() - 1);
        }
    }


    private VBox setProposalItem(Proposal proposal) {
        VBox proposalVBox = new VBox(10);
        proposalVBox.setPadding(new Insets(10)); // Ajuster l'espacement autour de la VBox
        proposalVBox.setUserData(proposal);

        Music titre = null;
        try {
            titre = FacadeMusic.getInstance().getMusicById(proposal.getMusic());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        String nomMusique = titre != null ? titre.getName() : "Musique inconnu";
        Label musicLabel = new Label(nomMusique);
        musicLabel.getStyleClass().addAll("label-bold", "h2");
        musicLabel.setWrapText(true);

        Button playButton = new Button("Play");
        playButton.getStyleClass().add("boutonBleu");
        playButton.setUserData(proposal);
        playButton.setOnAction(this::handlePlay);
        System.out.println("Setting UserData for Play Button with Proposal ID: " + proposal.getId());

        // HBox pour aligner le titre et le bouton Play horizontalement
        HBox titleAndPlayBox = new HBox(10, musicLabel, playButton);
        titleAndPlayBox.setAlignment(Pos.CENTER_LEFT); // Aligner les éléments à gauche

        // Récupérez l'artiste associé à l'ID de l'artiste dans la proposition
        User artist = null;
        try {
            artist = FacadeUser.getInstance().getUserById(proposal.getArtist());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        String artistPseudo = artist != null ? artist.getPseudo() : "Artiste inconnu";

        String country = proposal.getCountry();
        String description = proposal.getDescription();


        Label userLabel = new Label("De : " + artistPseudo);
        userLabel.getStyleClass().addAll("h3");
        Label countryLabel = new Label("pour la playlist : " + country);
        countryLabel.getStyleClass().addAll("h3");
        Label descriptionLabel = new Label(description);
        descriptionLabel.getStyleClass().add("label-description");

        userLabel.setWrapText(true);
        countryLabel.setWrapText(true);
        descriptionLabel.setWrapText(true);

        Button acceptButton = new Button("Accepter");
        acceptButton.getStyleClass().add("boutonBleu");
        acceptButton.setUserData(proposal); // Attache la proposition au bouton
        acceptButton.setOnAction(this::handleAccept);
        System.out.println("Setting UserData for Accept Button with Proposal ID: " + proposal.getId());

        Button refuseButton = new Button("Refuser");
        refuseButton.getStyleClass().add("boutonBleu");
        refuseButton.setUserData(proposal); // Attache la proposition au bouton
        refuseButton.setOnAction(this::handleRefuse);
        System.out.println("Setting UserData for Refuse Button with Proposal ID: " + proposal.getId());

        HBox acceptAndRefuseBox = new HBox(10, acceptButton, refuseButton);
        acceptAndRefuseBox.setAlignment(Pos.CENTER); // Centrer les boutons dans la HBox

        proposalVBox.getChildren().addAll(titleAndPlayBox, userLabel, countryLabel, descriptionLabel, acceptAndRefuseBox);

        return proposalVBox;
    }

    @FXML
    private void handleAccept(ActionEvent event) {
        // Récupérer le bouton qui a déclenché l'action
        Button btn = (Button) event.getSource();
        // Récupérer la proposition associée à ce bouton
        Proposal proposal = (Proposal) btn.getUserData();
        try {
            boolean success = proposalFacade.acceptProposal(proposal.getId());
            if (success) {
                removeProposalFromUI(proposal.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleRefuse(ActionEvent event) {
        // Même logique que pour handleAccept
        Button btn = (Button) event.getSource();
        Proposal proposal = (Proposal) btn.getUserData();
        try {
            boolean success = proposalFacade.refuseProposal(proposal.getId());
            if (success) {
                removeProposalFromUI(proposal.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void removeProposalFromUI(int proposalId) {
        List<Node> toRemove = new ArrayList<>();
        Proposal removedProposal = null; // To keep track of the removed proposal's country
        boolean found = false;

        // Iterate over the proposals to find and remove the one with the given ID
        for (Node node : proposalsContainer.getChildren()) {
            if (node instanceof VBox) {
                VBox proposalVBox = (VBox) node;
                Proposal proposal = (Proposal) proposalVBox.getUserData();
                if (proposal != null && proposal.getId() == proposalId) {
                    removedProposal = proposal; // Save the removed proposal's details
                    toRemove.add(proposalVBox);
                    found = true;
                }
            } else if (node instanceof Separator && found) {
                toRemove.add(node);
                break;
            }
        }
        proposalsContainer.getChildren().removeAll(toRemove);

        // If a proposal was removed, update the ComboBox
        if (removedProposal != null) {
            try {
                // Check if there are any remaining proposals for the removed proposal's country
                List<Proposal> remainingProposals = proposalFacade.getAllProposalsByCountry(removedProposal.getCountry());
                if (remainingProposals.isEmpty()) {
                    // If there are no remaining proposals, remove the country from the ComboBox
                    countryFilterComboBox.getItems().remove(removedProposal.getCountry());

                    // If the removed country was the one selected, reset the ComboBox to "All Countries"
                    if (countryFilterComboBox.getValue().equals(removedProposal.getCountry())) {
                        countryFilterComboBox.setValue("All Countries");
                        filterProposalsByCountry(null); // Reload all proposals
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    private void handlePlay(ActionEvent event) {
        try {
            Button playButton = (Button) event.getSource();
            Proposal proposal = (Proposal) playButton.getUserData();
            int musicId = proposal.getMusic(); // Assuming 'getMusic()' returns the music ID
            Music music = FacadeMusic.getInstance().getCurrentMusic();

            // Utiliser une variable pour suivre si la musique est actuellement en train de jouer
            boolean isPlaying = "Pause".equals(playButton.getText());

            if (isPlaying) {
                // Si la musique est en train de jouer, la mettre en pause
                FacadeMusic.getInstance().pauseMusic();
                playButton.setText("Play");
            } else {
                // Si la musique est en pause ou n'a jamais été jouée, la jouer ou la reprendre
                if(music != null && music.getId() == musicId) {
                    // La musique est déjà chargée, la reprendre
                    FacadeMusic.getInstance().resumeMusic();
                } else {
                    // La musique n'est pas chargée ou est différente, la charger et jouer
                    music = FacadeMusic.getInstance().playMusic(musicId);
                    if(music != null) {
                        System.out.println("Playing music: " + music.getName());
                    } else {
                        System.out.println("Music could not be played for ID: " + musicId);
                    }
                }
                playButton.setText("Pause");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}

