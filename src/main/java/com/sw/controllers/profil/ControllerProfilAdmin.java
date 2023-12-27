package com.sw.controllers.profil;

import com.sw.classes.Admin;
import com.sw.classes.MusicalExpert;
import com.sw.classes.User;
import com.sw.facades.Facade;
import com.sw.facades.FacadeAdmin;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;

import java.util.List;

public class ControllerProfilAdmin extends ControllerProfil{

    final FacadeAdmin adminFacade = FacadeAdmin.getInstance();

    @FXML private Button becomeMusicalExpertButton;
    @FXML private Button becomeAdminButton;


    @FXML private ListView<User> userListView;

    public void initialize() {
        try {
            List<User> users = adminFacade.getAllUsers(); // Remplacer par la méthode appropriée pour obtenir les utilisateurs
            userListView.setItems(FXCollections.observableArrayList(users));
            userListView.setCellFactory(lv -> new ListCell<User>() {
                private final Button buttonExpert = new Button("Promouvoir en Expert Musical");
                private final Button buttonAdmin = new Button("Promouvoir en Admin");
                private final Pane spacer1 = new Pane();
                private final Pane spacer2 = new Pane();
                private final HBox content = new HBox(new Text(), spacer1, buttonExpert, spacer2, buttonAdmin);
                {
                    // Configurez les espaces pour qu'ils grandissent et remplissent l'espace disponible
                    HBox.setHgrow(spacer1, Priority.ALWAYS);
                    HBox.setHgrow(spacer2, Priority.ALWAYS);
                    spacer1.setMinWidth(10);
                    spacer2.setMinWidth(10);
                    content.setSpacing(10);
                    content.setAlignment(Pos.CENTER_LEFT);
                }

                @Override
                protected void updateItem(User user, boolean empty) {
                    super.updateItem(user, empty);
                    if (user != null && !empty) {
                        Text text = (Text) content.getChildren().get(0);
                        text.setText(user.getPseudo() + " (" + user.getRole() + ")");
                        buttonExpert.setVisible("user".equals(user.getRole()));
                        buttonAdmin.setVisible("user".equals(user.getRole())|| "musicalExpert".equals(user.getRole()));

                        // Si un bouton est invisible, l'espaceur correspondant prendra tout l'espace
                        spacer1.setVisible(!buttonExpert.isVisible());
                        spacer2.setVisible(!buttonAdmin.isVisible());

                        buttonExpert.setOnAction(event -> {
                            try {
                                promoteToMusicalExpert(user);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                        buttonAdmin.setOnAction(event -> {
                            try {
                                promoteToAdmin(user);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });

                        setGraphic(content);
                    } else {
                        setGraphic(null);
                    }
                }

            });
        } catch (Exception e) {
            e.printStackTrace(); // Afficher l'erreur dans la console ou la gérer autrement
        }
    }


    /**
     * Méthode pour promouvoir un utilisateur en expert musical depuis la page profil admin
     * @param user User, utilisateur à promouvoir
     * @throws Exception si problème pendant la promotion
     */
    private void promoteToMusicalExpert(User user) throws Exception {
        try {
            MusicalExpert newMusicalExpert = adminFacade.userBecomeMusicalExpert(user.getId());
            refreshUserList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Méthode pour promouvoir un utilisateur en admin depuis la page profil admin
     * @param user User, utilisateur à promouvoir
     * @throws Exception si problème pendant la promotion
     */
    private void promoteToAdmin(User user) throws Exception {
        try {
            Admin newAdmin = adminFacade.userBecomeAdmin(user.getId());
            refreshUserList();
        } catch (Exception e) {
            e.printStackTrace();
            // Gestion de l'erreur
        }
    }

    private void refreshUserList() throws Exception {
        // Obtenir la liste mise à jour des utilisateurs
        List<User> users = adminFacade.getAllUsers();
        userListView.setItems(FXCollections.observableArrayList(users));
        userListView.refresh(); // Assurez-vous que la vue est mise à jour
    }

}
