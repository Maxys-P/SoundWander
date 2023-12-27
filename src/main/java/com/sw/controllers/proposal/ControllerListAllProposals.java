package com.sw.controllers.proposal;

import com.sw.classes.Proposal;
import com.sw.facades.Facade;
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

import java.util.List;

public class ControllerListAllProposals extends ControllerProposal {

    @FXML
    private VBox proposalsContainer;

    @FXML
    public void initialize() {
        try {
            List<Proposal> allProposals = proposalFacade.getAllProposals();
            for (Proposal proposal : allProposals) {
                HBox proposalItem = setProposalItem(proposal);
                proposalsContainer.getChildren().add(proposalItem);
                proposalsContainer.getChildren().add(new Separator());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private HBox setProposalItem(Proposal proposal) {
        HBox proposalHBox = new HBox(10);
        proposalHBox.setPadding(new Insets(5)); // Un peu de padding pour l'espacement

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
        userLabel.getStyleClass().addAll("label-bold", "h3");
        Label countryLabel = new Label("pour la playlist : " + country);
        countryLabel.getStyleClass().addAll("label-bold", "h3");
        Label descriptionLabel = new Label(description);
        descriptionLabel.getStyleClass().add("label-description");

        userLabel.setWrapText(true);
        countryLabel.setWrapText(true);
        descriptionLabel.setWrapText(true);

        VBox labelContainer = new VBox(5); // Espacement entre les labels
        labelContainer.getChildren().addAll(userLabel, countryLabel, descriptionLabel);

        Button acceptButton = new Button("Accepter");
        acceptButton.getStyleClass().add("boutonBleu");
        acceptButton.setUserData(proposal); // Attache la proposition au bouton
        acceptButton.setOnAction(this::handleAccept);

        Button refuseButton = new Button("Refuser");
        refuseButton.getStyleClass().add("boutonBleu");
        refuseButton.setUserData(proposal); // Attache la proposition au bouton
        refuseButton.setOnAction(this::handleRefuse);

        proposalHBox.getChildren().addAll(labelContainer, acceptButton, refuseButton);
        proposalHBox.setUserData(proposal);

        return proposalHBox;
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
        proposalsContainer.getChildren().removeIf(node -> {
            if (node instanceof HBox) { // Assurez-vous que le node est une instance de HBox avant de caster
                HBox proposalHBox = (HBox) node;
                Proposal proposal = (Proposal) proposalHBox.getUserData();
                // L'utilisation de getUserData() ici ne concerne pas les informations de
                // l'utilisateur (User) au sens d'une personne ou d'un compte d'utilisateur,
                // mais est une méthode de la classe Node de JavaFX qui permet de stocker et de
                // récupérer des données supplémentaires associées à un élément d'interface graphique
                // (dans ce cas, un Node qui est en fait un HBox)
                return proposal.getId() == proposalId;
            }
            return false; // Si ce n'est pas une instance de HBox, ne pas le retirer
        });
    }

    @FXML
    private void handlePlay() {} // a faire plus tard

}

