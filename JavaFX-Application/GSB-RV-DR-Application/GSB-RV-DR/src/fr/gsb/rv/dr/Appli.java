/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.gsb.rv.dr;

import java.util.Optional;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author developpeur
 */
public class Appli extends Application {
    
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
        
        //Menu Praticiens
        Menu menuPraticiens = new Menu("Praticiens");
        MenuItem hesitantsItem = new MenuItem("Hésitants");
        menuPraticiens.getItems().add(hesitantsItem);
        
        // Add Menus to the MenuBar
        barreMenus.getMenus().addAll(menuFichier,menuRapport,menuPraticiens);
        
        BorderPane root = new BorderPane();
        root.setTop(barreMenus);
        Scene scene = new Scene(root, 800, 700);
        
        primaryStage.setTitle("GSB-RV-DR");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        
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
        
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
