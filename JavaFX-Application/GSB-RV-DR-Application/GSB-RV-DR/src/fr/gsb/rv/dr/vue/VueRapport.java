/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.gsb.rv.dr.vue;

import fr.gsb.rv.dr.entites.RapportVisite;
import java.time.format.DateTimeFormatter;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

/**
 *
 * @author developpeur
 */
public class VueRapport {
    
    private Dialog<Integer> dialog = new Dialog<>();
    public VueRapport(RapportVisite rv){
        this.dialog.setTitle("Le Rapport n°"+ rv.getNumero());
        this.dialog.setHeaderText("Nom : "+rv.getLeVisiteur().getNom()+"   Prénom : "+rv.getLeVisiteur().getPrenom());
        // Set the button types.
        this.dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
        
        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        

        grid.add(new Label("Nom praticien :"), 0, 0);
        grid.add(new Label(""+rv.getLePraticien().getNom()+""), 1, 0);
        grid.add(new Label("Prenom praticien :"), 0, 1);
        grid.add(new Label(""+rv.getLePraticien().getPrenom()+""), 1, 1);
        grid.add(new Label("Numéro du praticien :"), 0, 2);
        grid.add(new Label(""+rv.getLePraticien().getNumero()+""), 1, 2);
        grid.add(new Label("Bilan :"), 0, 3);
        grid.add(new Label(""+rv.getBilan()+""), 1, 3);
        grid.add(new Label("Motif du rv :"), 0, 4);
        grid.add(new Label(""+rv.getMotif()+""), 1, 4);
        grid.add(new Label("coefficient de confiance :"), 0, 5);
        grid.add(new Label(""+rv.getCoefConfiance()+""), 1, 5);
       // grid.add(password, 1, 1);
        
        
        this.dialog.getDialogPane().setContent(grid);
    }

    public Dialog<Integer> getDialog() {
        return dialog;
    }

  
    
    
}
