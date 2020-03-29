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
public class PanneauPraticiens extends Pane {
    private GridPane vuePraticien;
    private VBox vbox;

    public PanneauPraticiens() {
        vuePraticien = new GridPane();
        vuePraticien.setHgap(10);
        vuePraticien.setVgap(10);
        vuePraticien.setPadding(new Insets(20, 150, 10, 10));

        vbox = new VBox();
        vbox.setPadding(new Insets(15, 12, 15, 12));
        vbox.setSpacing(10);
        vbox.setStyle("-fx-background-color: white;");
        
        Text category = new Text("Praticiens");
        category.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        vbox.getChildren().add(category);
        vuePraticien.add(vbox, 1, 0);
        
        
        //vbox.getChildren().add(b);
        
        //vueAccueil.add(vbox, 0, 0);
    }

    public GridPane getVuePraticien() {
        return vuePraticien;
    }    

    public VBox getVbox() {
        return vbox;
    }
    
    
}

