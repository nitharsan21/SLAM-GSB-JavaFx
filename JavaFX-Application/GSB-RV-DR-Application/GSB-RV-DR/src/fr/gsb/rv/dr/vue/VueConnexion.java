
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.gsb.rv.dr.vue;

import java.util.Optional;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import javafx.util.Pair;



/**
 *
 * @author developpeur
 */
public class VueConnexion {
    private Dialog<Pair<String, String>> dialog = new Dialog<>();

    public VueConnexion() {
        this.dialog.setTitle("Authentification");
        this.dialog.setHeaderText("Saisir vos données de connexion");
        // Set the button types.
        ButtonType loginButtonType = new ButtonType("Se connecter", ButtonBar.ButtonData.OK_DONE);
        this.dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
        
        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField username = new TextField();
        username.setPromptText("Username");
        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        grid.add(new Label("Username:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(password, 1, 1);
        
         // Enable/Disable login button depending on whether a username was entered.
        Node loginButton = this.dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        // Do some validation (using the Java 8 lambda syntax).
        username.textProperty().addListener((observable, oldValue, newValue) -> {
        loginButton.setDisable(newValue.trim().isEmpty());
        });
        
        this.dialog.getDialogPane().setContent(grid);
        // Request focus on the username field by default.
        Platform.runLater(() -> username.requestFocus());

        // Convert the result to a username-password-pair when the login button is clicked.
        /*this.dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(username.getText(), password.getText());
            }
            return null;
        });*/
        
        this.dialog.setResultConverter(
                new Callback<ButtonType, Pair<String,String>>(){
                    @Override
                    public Pair<String,String> call( ButtonType typeBouton){
                        if (typeBouton == loginButtonType) {
                            return new Pair<String,String>(username.getText(), password.getText());
                        }
                        return null;
                    }
                }
        );

    }

    public Dialog<Pair<String, String>> getDialog() {
        return dialog;
    }

    

}
