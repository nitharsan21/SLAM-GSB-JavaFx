/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.gsb.rv.panneau;

import fr.gsb.rv.dr.entites.Praticien;
import fr.gsb.rv.dr.entites.RapportVisite;
import fr.gsb.rv.dr.entites.Visiteur;
import fr.gsb.rv.dr.modeles.ModeleGsbRv;
import fr.gsb.rv.dr.technique.Mois;
import fr.gsb.rv.dr.technique.Session;
import fr.gsb.rv.dr.utilitaires.ComparateurCoefConfiance;
import fr.gsb.rv.dr.utilitaires.ComparateurCoefNotoriete;
import fr.gsb.rv.dr.utilitaires.ComparateurDateVisite;
import fr.gsb.rv.dr.vue.VueRapport;
import static fr.gsb.rv.panneau.PanneauPraticiens.CRITERE_COEF_CONFIANCE;
import static fr.gsb.rv.panneau.PanneauPraticiens.CRITERE_COEF_NOTORIETE;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Callback;

/**
 *
 * @author developpeur
 */
public class PanneauRapports extends Pane {

    private GridPane vueRapport;
    private VBox vbox;
    
    private ComboBox<Visiteur> cbVisiteur = new ComboBox<Visiteur>();
    private ComboBox<Mois> cbMois = new ComboBox<Mois>();
    private ComboBox<Integer> cbAnnee = new ComboBox<Integer>();
    
    private TableView<RapportVisite> tabRapports = new TableView<RapportVisite>();
    
    private VueRapport leRapport;

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
        
       
        //Liste des visiteurs
        ObservableList<Visiteur> visiteurs = null;
        try{
            visiteurs = FXCollections.observableArrayList(ModeleGsbRv.getVisiteurs());
        }
        catch( Exception e ){
            System.out.println(e) ;
        } 
        
        cbVisiteur.setItems(visiteurs);
        cbVisiteur.setPromptText("-*-Sélectionner une Visiteur-*-");
        
        
        //Liste des mois
        ObservableList<Mois> months = null;
        try{
            months = FXCollections.observableArrayList(Mois.values());
        }
        catch( Exception e ){
            System.out.println(e) ;
        } 
        
        cbMois.setItems(months);
        cbMois.setPromptText("Mois");
        
        //Liste des années
        List<Integer> annee = new ArrayList<Integer>();
        LocalDate aujourdhui = LocalDate.now();
        int anneeCourant = aujourdhui.getYear();
        annee.add(anneeCourant);
        
        for(int i = 1; i<6;i++){
            annee.add(anneeCourant - i);
        }
        
        ObservableList<Integer> years = null;
        try{
            years = FXCollections.observableArrayList(annee);
        }
        catch( Exception e ){
            System.out.println(e) ;
        }
        
        cbAnnee.setItems(years);
        cbAnnee.setPromptText("Année");
        
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);
        
        final Pane spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        spacer.setMinSize(20, 1);
        
        Button btnValider = new Button();
        btnValider.setText("Valider");
        btnValider.setDisable(true);
        
        hbox.getChildren().addAll(cbVisiteur,cbMois,cbAnnee,spacer,btnValider);
        //hbox.getChildren().
        vbox.getChildren().add(hbox); 
        
        
        cbAnnee.setOnAction((ActionEvent event) -> {
            if( cbVisiteur.getSelectionModel().getSelectedItem() != null && cbMois.getSelectionModel().getSelectedItem() != null && cbAnnee.getSelectionModel().getSelectedItem() != null){
                btnValider.setDisable(false);
            }
        });
        cbMois.setOnAction((ActionEvent event) -> {
            if( cbVisiteur.getSelectionModel().getSelectedItem() != null && cbMois.getSelectionModel().getSelectedItem() != null && cbAnnee.getSelectionModel().getSelectedItem() != null){
                btnValider.setDisable(false);
            }
        });
        cbVisiteur.setOnAction((ActionEvent event) -> {
            if( cbVisiteur.getSelectionModel().getSelectedItem() != null && cbMois.getSelectionModel().getSelectedItem() != null && cbAnnee.getSelectionModel().getSelectedItem() != null){
                btnValider.setDisable(false);
            }
        });
        
        
        
         //TableView
        //TableView<Praticien> tabPraticiens = new TableView<Praticien>();
        
        TableColumn<RapportVisite,Integer> colNumero = new TableColumn<RapportVisite,Integer>("Numéro");
        colNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        tabRapports.getColumns().add(colNumero);
        ////colNumero.setStyle("-fx-pref-width: 8em");
        
        TableColumn<RapportVisite,String> colNom = new TableColumn<RapportVisite,String>("Nom");
        colNom.setCellValueFactory((CellDataFeatures<RapportVisite,String> param) -> {
            String nom = param.getValue().getLePraticien().getNom();
            return new SimpleStringProperty(nom);
        });
        tabRapports.getColumns().add(colNom);
        //colNom.setStyle("-fx-pref-width: 8em");
        
        TableColumn<RapportVisite,String> colVille = new TableColumn<RapportVisite,String>("Ville");
        colVille.setCellValueFactory((CellDataFeatures<RapportVisite,String> param) -> {
            String ville = param.getValue().getLePraticien().getVille();
            return new SimpleStringProperty(ville);
        });
        tabRapports.getColumns().add(colVille);
        //colVille.setStyle("-fx-pref-width: 8em");
        
        TableColumn<RapportVisite,LocalDate> colVisite = new TableColumn<RapportVisite,LocalDate>("Visite");
        colVisite.setCellValueFactory(new PropertyValueFactory<>("dateVisite"));
        colVisite.setCellFactory( 
            colonne -> {
                return new TableCell<RapportVisite,LocalDate>(){
                    @Override
                    protected void updateItem( LocalDate item, boolean empty){
                        super.updateItem(item, empty);

                        if(empty){
                            setText("");
                        }
                        else{
                            DateTimeFormatter formateur = DateTimeFormatter.ofPattern("dd/MM/uuuu");
                            setText(item.format( formateur));
                        }
                    }
                };
        });
        tabRapports.getColumns().add(colVisite);
        
        TableColumn<RapportVisite,LocalDate> colRedaction = new TableColumn<RapportVisite,LocalDate>("Rédaction");
        colRedaction.setCellValueFactory(new PropertyValueFactory<>("dateRedaction"));
        colRedaction.setCellFactory( 
            colonne -> {
                return new TableCell<RapportVisite,LocalDate>(){
                    @Override
                    protected void updateItem( LocalDate item, boolean empty){
                        super.updateItem(item, empty);

                        if(empty){
                            setText("");
                        }
                        else{
                            if(item ==  null){
                                setText("");
                            }
                            else{
                                DateTimeFormatter formateur = DateTimeFormatter.ofPattern("dd/MM/uuuu");
                                setText(item.format( formateur));
                            }
                        }
                    }
                };
        });
        tabRapports.getColumns().add(colRedaction);
        //colVille.setStyle("-fx-pref-width: 8em");
        
        
        tabRapports.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        
        tabRapports.setOnMouseClicked((MouseEvent event) -> {
            if( event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2){
                int indiceRapport = tabRapports.getSelectionModel().getSelectedIndex();
                RapportVisite rv = tabRapports.getItems().get(indiceRapport);
                
                try{
                    ModeleGsbRv.setRapportVisiteLu(rv.getLeVisiteur().getMatricule(), rv.getNumero());
                }
                catch(Exception e){
                    System.out.println(e);
                }
                rafraichir();
                
                leRapport = new VueRapport(rv);
                leRapport.getDialog().showAndWait();
                  
            }
        
        });
        
        
        vbox.getChildren().add(tabRapports);
        
        
        
        vueRapport.add(vbox, 1, 0); 
        
        btnValider.setOnAction((ActionEvent event) -> {
            rafraichir();
        });
    }

    public GridPane getVueRapportl() {
        return vueRapport;
    } 

    public VBox getVbox() {
        return vbox;
    }
    
    public void rafraichir(){
        List<RapportVisite> rapportVisite;
        try{
            String matricule = cbVisiteur.getSelectionModel().getSelectedItem().getMatricule();
            int mois = Mois.valueOf(String.valueOf(cbMois.getSelectionModel().getSelectedItem())).ordinal() + 1;
            int annee = cbAnnee.getSelectionModel().getSelectedItem();
            rapportVisite = ModeleGsbRv.getRapportsVisite(matricule, mois, annee);
            ObservableList<RapportVisite> list;
            if(rapportVisite == null){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Ce Visiteur n'a effectué aucune visite durant cette période.");
                alert.showAndWait();
                
                
            }
            else{
                list = FXCollections.observableArrayList(rapportVisite);
                tabRapports.setItems(list);
                
                tabRapports.setRowFactory(ligne -> {
                    return new TableRow<RapportVisite>(){
                        @Override
                        protected void updateItem( RapportVisite item, boolean empty){
                            super.updateItem(item, empty);
                            
                            if(item != null){
                                if(item.isLu()){
                                    setStyle("-fx-background-color: gold");
                                }
                                else{
                                    setStyle("-fx-background-color: cyan");
                                }
                            }
                        }
                    };
                });
                
                
               
            }
  
            
        }
        catch(Exception e){
                System.out.println(e);

         }
    }
    
}
