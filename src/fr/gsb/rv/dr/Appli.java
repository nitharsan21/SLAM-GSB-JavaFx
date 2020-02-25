/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.gsb.rv.dr;

import fr.gsb.rv.modeles.ModeleGsbRv; 
import fr.gsb.rv.technique.ConnexionBD;
import fr.gsb.rv.technique.ConnexionException;
import java.util.Optional;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
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
        
        MenuBar barreMenus = new MenuBar() ; 
        
        
        Menu menuFichier = new Menu( "Fichier" ) ;
        
        MenuItem itemSeConnecter = new MenuItem ( "Se connecter" ) ;
        MenuItem itemSeDeconnecter = new MenuItem ( "Se déconnecter" ) ;
        MenuItem itemQuitter = new MenuItem ( "Quitter" ) ;
        menuFichier.getItems().addAll( itemSeConnecter, itemSeDeconnecter, itemQuitter ) ;
        
        
        Menu menuRapports = new Menu( "Rapports" ) ;
        MenuItem itemConsulter = new MenuItem ( "Consulter" ) ;
        menuRapports.getItems().add( itemConsulter );
        
        Menu menuPraticiens = new Menu( "Praticiens" ) ;
        MenuItem itemHesitants = new MenuItem ( "Hésitants" ) ;
        menuPraticiens.getItems().add( itemHesitants );
        
        barreMenus.getMenus().addAll( menuFichier, menuRapports, menuPraticiens ) ;
        
        
        
        
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        
        itemSeDeconnecter.setDisable(true);
        
        
        itemQuitter.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                Alert alertQuitter = new Alert(Alert.AlertType.CONFIRMATION);
                alertQuitter.setTitle("Quitter");
                alertQuitter.setHeaderText("Demande de confirmation");
                alertQuitter.setContentText("Voulez-vous quitter l'application ?") ;
                Button btnOui = new Button("oui");
                Button btnNon = new Button("annuler");
                alertQuitter.getButtonTypes().add(ButtonType.OK); 
                
                Optional<ButtonType> reponse = alertQuitter.showAndWait() ;
                reponse.get() ; 
                
            }
        });
        
        BorderPane root = new BorderPane();
        root.setTop(barreMenus);
        
        Scene scene = new Scene(root, 500, 350);
        
        primaryStage.setTitle("Application");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
