/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.gsb.rv.panneau;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.*;
/**
 *
 * @author developpeur
 */
public class PanneauAccueil extends Pane {
    private GridPane vueAccueil;
    private VBox vbox;
/*
    public PanneauAccueil(GridPane vueAccueil, VBox vbox) {
        this.vueAccueil = vueAccueil;
        this.vbox = vbox;
    }

    public PanneauAccueil(GridPane vueAccueil, VBox vbox, Node... children) {
        super(children);
        this.vueAccueil = vueAccueil;
        this.vbox = vbox;
    }
    */
    

    public PanneauAccueil() {
        vueAccueil = new GridPane();
        vueAccueil.setHgap(10);
        vueAccueil.setVgap(10);
        vueAccueil.setPadding(new Insets(20, 150, 10, 10));

        vbox = new VBox();
        vbox.setPadding(new Insets(15, 12, 15, 12));
        vbox.setSpacing(10);
        vbox.setStyle("-fx-background-color: white;");
        
        Text category = new Text("Accueil");
        category.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        vbox.getChildren().add(category);
        vueAccueil.add(vbox, 1, 0); 
        
        //vbox.getChildren().add(b);
        
        //vueAccueil.add(vbox, 0, 0);
    }

    public VBox getVbox() {
        return vbox;
    }

    public GridPane getVueAccueil() {
        return vueAccueil;
    }
    
    
    
    
    
    
}
