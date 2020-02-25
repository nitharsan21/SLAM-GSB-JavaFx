/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.gsb.rv.dr;

import fr.gsb.rv.dr.entites.Visiteur;
import fr.gsb.rv.dr.modeles.ModeleGsbRv;
import fr.gsb.rv.dr.technique.Session;
import java.util.Optional;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Pair;

/**
 *
 * @author developpeur
 */
public class Appli extends Application {
    Visiteur visiteur;
    
    
    @Override
    public void start(Stage primaryStage) {
        
    /*
     * Vue Acceuil Barre de menus de l'application.
     */    
        //Creation de la barre d menus 
        MenuBar barreMenus = new MenuBar();
        
        //Menu Fichier 
        Menu menuFichier = new Menu("Fichier");
        MenuItem connectItem = new MenuItem("Se connecter");
        MenuItem deconnectItem = new MenuItem("Se déconnecter");
        deconnectItem.setDisable(true);
        // create exit
        MenuItem exitItem = new MenuItem("Quitter");
        exitItem.setAccelerator(KeyCombination.keyCombination("Ctrl+X"));
        menuFichier.getItems().addAll(connectItem, deconnectItem, exitItem);
        
        //Menu Rapport
        Menu menuRapport = new Menu("Rapport");
        MenuItem consulterItem = new MenuItem("Consulter");
        menuRapport.getItems().add(consulterItem);
        menuRapport.setDisable(true);
        
        //Menu Praticiens
        Menu menuPraticiens = new Menu("Praticiens");
        MenuItem hesitantsItem = new MenuItem("Hésitants");
        menuPraticiens.getItems().add(hesitantsItem);
        menuPraticiens.setDisable(true);
        // Add Menus to the MenuBar
        barreMenus.getMenus().addAll(menuFichier,menuRapport,menuPraticiens);
        
        BorderPane root = new BorderPane();
        root.setTop(barreMenus);
        Scene scene = new Scene(root, 800, 700);
        
        primaryStage.setTitle("GSB-RV-DR");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // function de l'item exit ou Quitter
        exitItem.setOnAction((ActionEvent event) -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Quitter");
            alert.setHeaderText("Demande de confirmation");
            alert.setContentText("Voulez-vous quitter l'application ?");
            
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK){
                System.exit(0);
            }
        });
        
        // function de l'item seConnecter
        connectItem.setOnAction((ActionEvent event) -> {
            Dialog<Pair<String, String>> dialog = new Dialog<>();
            dialog.setTitle("Authentification");
            dialog.setHeaderText("Saisir vos données de connexion");

            // Set the button types.
            ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

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
            Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
            loginButton.setDisable(true);

            // Do some validation (using the Java 8 lambda syntax).
            username.textProperty().addListener((observable, oldValue, newValue) -> {
                loginButton.setDisable(newValue.trim().isEmpty());
            });

            dialog.getDialogPane().setContent(grid);

            // Request focus on the username field by default.
            Platform.runLater(() -> username.requestFocus());

            // Convert the result to a username-password-pair when the login button is clicked.
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == loginButtonType) {
                    return new Pair<>(username.getText(), password.getText());
                }
                return null;
            });
            
            
            try{
                Optional<Pair<String, String>> result = dialog.showAndWait();
                Session.ouvrir(ModeleGsbRv.seConnecter((result.get()).getKey(),(result.get()).getValue()));
                
                if(Session.getSession().getLeVisiteur() != null){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Vous vous etes Connecter avec Succés");
                    alert.setContentText("Bonjour " + Session.getSession().getLeVisiteur().getNom());
                    alert.showAndWait();
                    deconnectItem.setDisable(false);
                    connectItem.setDisable(true);
                    menuRapport.setDisable(false);
                    menuPraticiens.setDisable(false);
                }

                else {
                    int usernameattempts = 1;
                    int tentative = 2;
                    while (Session.getSession().getLeVisiteur() == null && usernameattempts < 3) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("Saisir Incorrect. Vous avez "+ tentative +" tentative restante");
                        alert.showAndWait();
                        Optional<Pair<String, String>> result1 = dialog.showAndWait();
                        Session.ouvrir(ModeleGsbRv.seConnecter((result1.get()).getKey(),(result1.get()).getValue()));
                        usernameattempts++;
                        tentative--;
                    }
                    if(Session.getSession().getLeVisiteur() == null){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("Vous avez dépassé la tentative de connexion !!!");
                        alert.showAndWait();
                        System.exit(0);
                    }
                    else{
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText("Vous vous etes Connecter avec Succés");
                        alert.setContentText("Bonjour " + Session.getSession().getLeVisiteur().getNom());
                        alert.showAndWait();
                        deconnectItem.setDisable(false);
                        connectItem.setDisable(true);
                        menuRapport.setDisable(false);
                        menuPraticiens.setDisable(false);
                    }
                }
            }
            catch(Exception e){
                System.out.println(e);

            }
            System.out.println(Session.getSession().getLeVisiteur());
        });
            
        
        deconnectItem.setOnAction((ActionEvent event) -> {
            if (Session.getSession() != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Déconnexion");
                alert.setContentText("Voulez-vous déconnecter "+ Session.getSession().getLeVisiteur().getNom() +" ?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    Session.fermer();
                    connectItem.setDisable(false);
                    deconnectItem.setDisable(true);
                    menuRapport.setDisable(true);
                    menuPraticiens.setDisable(true);
                    BorderPane root1 = new BorderPane();
                    root1.setTop(barreMenus);
                    Scene scene1 = new Scene(root1, 800, 700);
                    primaryStage.setScene(scene1);
                }
            }
        });    
        
       
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
