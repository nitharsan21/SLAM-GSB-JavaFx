/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.gsb.rv.panneau;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author developpeur
 */
public class PanneauRapports extends Pane {

    private GridPane vueRapport;
    private VBox vbox;

    public PanneauRapports() {
        vueRapport = new GridPane();
        vueRapport.setHgap(10);
        vueRapport.setVgap(10);
        vueRapport.setPadding(new Insets(20, 150, 10, 10));

        vbox = new VBox();
        vbox.setPadding(new Insets(15, 12, 15, 12));
        vbox.setSpacing(10);
        vbox.setStyle("-fx-background-color: white;");
        
        Text category = new Text("Rapport");
        category.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        vbox.getChildren().add(category);
        vueRapport.add(vbox, 1, 0); 
       
        
        //vbox.getChildren().add(b);
        
        //vueAccueil.add(vbox, 0, 0);
    }

    public GridPane getVueRapportl() {
        return vueRapport;
    } 

    public VBox getVbox() {
        return vbox;
    }
    
}
