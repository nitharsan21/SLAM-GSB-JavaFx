/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.gsb.rv.dr;

import fr.gsb.rv.dr.entites.Visiteur;
import fr.gsb.rv.dr.modeles.ModeleGsbRv;
import fr.gsb.rv.dr.technique.Session;
import fr.gsb.rv.dr.vue.VueConnexion;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
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
    public void start(Stage primaryStage) throws FileNotFoundException {
        
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
    // create a input stream 
        FileInputStream input = new FileInputStream("/home/developpeur/Images/lionking.jpg"); 
        
        // create a image 
        Image image = new Image(input); 

        // create a background image 
        BackgroundImage backgroundimage = new BackgroundImage(image,  
                                         BackgroundRepeat.NO_REPEAT,  
                                         BackgroundRepeat.NO_REPEAT,  
                                         BackgroundPosition.CENTER,  
                                            BackgroundSize.DEFAULT); 

        // create Background 
        Background background = new Background(backgroundimage); 
        
        FileInputStream input2 = new FileInputStream("/home/developpeur/Images/logo.png"); 
        // create a image 
        Image logo = new Image(input2);
        // create a background image 
        BackgroundImage backgroundlogo = new BackgroundImage(logo,  
                                         BackgroundRepeat.NO_REPEAT,  
                                         BackgroundRepeat.NO_REPEAT,  
                                         BackgroundPosition.CENTER,  
                                            BackgroundSize.DEFAULT);
        

        // create Background 
        Background background1 = new Background(backgroundlogo);
        // set background 
        //hbox.setBackground(background); 
        
        
        
        BorderPane root = new BorderPane();
        root.setTop(barreMenus);
        root.setBackground(background1);
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
            ButtonType alertOKType = new ButtonType("oui", ButtonBar.ButtonData.OK_DONE);
             
            
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK){
                System.exit(0);
            }
        });
        
        // function de l'item seConnecter
        connectItem.setOnAction((ActionEvent event) -> {
            
            // la gestion de la session
            try{
                VueConnexion vue = new VueConnexion();
                Optional<Pair<String, String>> result = vue.getDialog().showAndWait();
                // teste de la classe Connexion BD et de la methode seConnecter()
                if(result.isPresent()){
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
                        System.out.println("Session Ouvert:"+Session.getSession().getLeVisiteur().toString());
                        root.setBackground(background);
                        
                        
                        
                    }

                    else {
                        int usernameattempts = 1;
                        int tentative = 2;
                        while (Session.getSession().getLeVisiteur() == null && usernameattempts < 3 ) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setContentText("Saisir Incorrect. Vous avez "+ tentative +" tentative restante");
                            alert.showAndWait();
                            Optional<Pair<String, String>> result1 = vue.getDialog().showAndWait();
                            System.out.println(result1);
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
                            System.out.println("Session Ouvert:"+Session.getSession().getLeVisiteur().toString());
                        }
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
                    root.setBackground(background1);
                    root.setTop(barreMenus);
                    primaryStage.setScene(scene);
                    primaryStage.show();
                    System.out.println("Session Fermer");
                }
            }
        });
        
        
        
        
        
        
        
        
        consulterItem.setOnAction((ActionEvent event) ->{
            System.out.println("'[Rapports]'"+Session.getSession().getLeVisiteur().getPrenom()+' '+Session.getSession().getLeVisiteur().getNom() );
        });
        
        hesitantsItem.setOnAction((ActionEvent event) ->{
            System.out.println("'[Praticiens]'"+Session.getSession().getLeVisiteur().getPrenom()+' '+Session.getSession().getLeVisiteur().getNom() );
        });
        
        
       
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
