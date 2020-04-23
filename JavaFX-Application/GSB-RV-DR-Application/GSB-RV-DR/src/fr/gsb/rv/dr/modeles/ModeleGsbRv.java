package fr.gsb.rv.dr.modeles;

import fr.gsb.rv.dr.entites.Praticien;
import fr.gsb.rv.dr.entites.RapportVisite;
import fr.gsb.rv.dr.entites.Visiteur;
import fr.gsb.rv.dr.technique.ConnexionBD;
import fr.gsb.rv.dr.technique.ConnexionException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;

public class ModeleGsbRv {
    
    public static Visiteur seConnecter( String matricule , String mdp ) throws ConnexionException{
        
        Connection connexion = ConnexionBD.getConnexion() ;
        
        String requete = "select vis_nom, vis_prenom "
                + "from Visiteur "
                + "INNER JOIN Travailler "
                + "ON Visiteur.vis_matricule = Travailler.vis_matricule "
                + "where Visiteur.vis_matricule = ? "
                + "AND Travailler.tra_role like '%Délégué%'";
          
        
        try {
            PreparedStatement requetePreparee = (PreparedStatement) connexion.prepareStatement( requete ) ;
            requetePreparee.setString( 1 , matricule );
            ResultSet resultat = requetePreparee.executeQuery() ;
            
            if( resultat.next() ){
                
                Visiteur visiteur = new Visiteur() ;
                visiteur.setMatricule( matricule );
                visiteur.setNom( resultat.getString( "vis_nom" ) ) ;
                visiteur.setPrenom(resultat.getString( "vis_prenom" ) ) ;
               
                
                
                requetePreparee.close() ;
                return visiteur ;
            }
            else {
                
                return null ;
            }
        }
        catch( Exception e ){
            return null ;
        } 
    }
    
    public static List<Praticien> getPraticiensHesitants() throws ConnexionException{
        
        Connection connexion = ConnexionBD.getConnexion() ;
        
        List<Praticien> praticiens = new ArrayList<Praticien>();
        
        String requete = "select p.pra_num,p.pra_nom,p.pra_ville,p.pra_coefnotoriete,max(r.rap_date_visite) as date,r.rap_coef_confiance " 
                + "from Praticien p "
                + "inner join RapportVisite r "
                + "on p.pra_num = r.pra_num "
                + "group by p.pra_num";

        
        try {
            PreparedStatement requetePreparee = (PreparedStatement) connexion.prepareStatement( requete ) ;

            ResultSet resultat = requetePreparee.executeQuery() ;
            
            if( resultat.next() ){
               do{
                Praticien praticien = new Praticien() ;
                praticien.setNumero(resultat.getString("pra_num") );
                praticien.setNom(resultat.getString("pra_nom") );
                praticien.setVille(resultat.getString("pra_ville") );
                praticien.setCoefNotoriete(Float.valueOf(resultat.getString("pra_coefnotoriete")) );                
                praticien.setDateDernierVisite(Date.valueOf(resultat.getString("date")).toLocalDate());
                praticien.setDernierCoefConfiance(Integer.valueOf(resultat.getString("rap_coef_confiance")));
                
                
                praticiens.add(praticien);
               }while(resultat.next() == true);
               requetePreparee.close() ;
               return praticiens;
                
            }
            else {
                return null ;
            }
            
        }
        catch( Exception e ){
            return null ;
        } 
    }
    
    public static List<Visiteur> getVisiteurs() throws ConnexionException{
        
        Connection connexion = ConnexionBD.getConnexion() ;
        
        List<Visiteur> visiteurs = new ArrayList<Visiteur>();
        
        String requete = "select vis_matricule, vis_nom, vis_prenom "
                + "from Visiteur ";
          
        
        try {
            PreparedStatement requetePreparee = (PreparedStatement) connexion.prepareStatement( requete ) ;
            //requetePreparee.setString( 1 , matricule );
            ResultSet resultat = requetePreparee.executeQuery() ;
            
            if( resultat.next() ){
                
                do{
                    Visiteur visiteur = new Visiteur() ;
                    visiteur.setMatricule( resultat.getString("vis_matricule") );
                    visiteur.setNom( resultat.getString( "vis_nom" ) ) ;
                    visiteur.setPrenom(resultat.getString( "vis_prenom" ) ) ;
                    
                    visiteurs.add(visiteur);
                    
                }while(resultat.next() == true);
                
                
                requetePreparee.close() ;
                return visiteurs ;
            }
            else {
                
                return null ;
            }
        }
        catch( Exception e ){
            return null ;
        } 
    }
    
    
    public static List<RapportVisite> getRapportsVisite(String matricule, int mois, int annee) throws ConnexionException{
        
        Connection connexion = ConnexionBD.getConnexion() ;
        
        List<RapportVisite> rapportVisites = new ArrayList<RapportVisite>();
        
        String requete = "select vis_matricule, rap_num, rap_date_visite, rap_date_redaction, rap_bilan, rap_motif, rap_coef_confiance, rap_lu, pra_num, mot_libelle "
                + "from RapportVisite "
                + "inner join Motif "
                + "on Motif.mot_id = RapportVisite.rap_motif "
                + "where RapportVisite.vis_matricule = ? "
                + "and month(rap_date_visite) = ? "
                + "and year(rap_date_visite) = ? ";
          
        
        try {
            PreparedStatement requetePreparee = (PreparedStatement) connexion.prepareStatement( requete ) ;
            requetePreparee.setString( 1 , matricule );
            requetePreparee.setInt(2 , mois );
            requetePreparee.setInt( 3 , annee );
            ResultSet resultat = requetePreparee.executeQuery() ;
            
            if( resultat.next() ){
                
                do{
                    RapportVisite rapportVisite = new RapportVisite() ;
                    rapportVisite.setNumero(Integer.valueOf(resultat.getString("rap_num")) );
                    rapportVisite.setDateVisite(Date.valueOf(resultat.getString( "rap_date_visite" )).toLocalDate() ) ;
                    if(resultat.getString("rap_date_redaction") != null){
                        rapportVisite.setDateRedaction(Date.valueOf(resultat.getString("rap_date_redaction")).toLocalDate());
                    }
                    rapportVisite.setBilan(resultat.getString("rap_bilan"));
                    rapportVisite.setMotif(resultat.getString("mot_libelle"));
                    rapportVisite.setCoefConfiance(Integer.valueOf(resultat.getString("rap_coef_confiance")));
                    
                    if(Integer.valueOf(resultat.getString("rap_lu")) == 1){
                        rapportVisite.setLu(true);
                    }
                    else{
                        rapportVisite.setLu(false);
                    }
                    rapportVisite.setLeVisiteur(getVisiteur(resultat.getString("vis_matricule")));
                    rapportVisite.setLePraticien(getPraticien(resultat.getString("pra_num")));
                    
                    
                    rapportVisites.add(rapportVisite);
                    
                }while(resultat.next() == true);
                
                
                requetePreparee.close() ;
                return rapportVisites ;
            }
            else {
                
                return null ;
            }
        }
        catch( Exception e ){
            return null ;
        } 
    }
    
    
     public static Visiteur getVisiteur( String matricule) throws ConnexionException{
        
        Connection connexion = ConnexionBD.getConnexion() ;
        
        String requete = "select vis_nom, vis_prenom "
                + "from Visiteur "
                + "where Visiteur.vis_matricule = ? ";
                
                
          
        
        try {
            PreparedStatement requetePreparee = (PreparedStatement) connexion.prepareStatement( requete ) ;
            requetePreparee.setString( 1 , matricule );
            ResultSet resultatVisiteur = requetePreparee.executeQuery() ;
            
            if( resultatVisiteur.next() ){
                
                Visiteur visiteur = new Visiteur() ;
                visiteur.setMatricule( matricule );
                visiteur.setNom( resultatVisiteur.getString( "vis_nom" ) ) ;
                visiteur.setPrenom(resultatVisiteur.getString( "vis_prenom" ) ) ;
               
                
                
                requetePreparee.close() ;
                return visiteur ;
            }
            else {
                
                return null ;
            }
        }
        catch( Exception e ){
            return null ;
        } 
    }
     
     public static Praticien getPraticien( String matricule) throws ConnexionException{
        
        Connection connexion = ConnexionBD.getConnexion() ;
        
        String requete = "select p.pra_num,p.pra_nom,p.pra_ville,p.pra_coefnotoriete,max(r.rap_date_visite) as date,r.rap_coef_confiance " 
                + "from Praticien p "
                + "inner join RapportVisite r "
                + "on p.pra_num = r.pra_num "
                + "where p.pra_num = ? "
                + "group by p.pra_num";

        
        try {
            PreparedStatement requetePreparee = (PreparedStatement) connexion.prepareStatement( requete ) ;
            requetePreparee.setString( 1 , matricule );
            ResultSet resultat = requetePreparee.executeQuery() ;
            
            if( resultat.next() ){
               
                Praticien praticien = new Praticien() ;
                praticien.setNumero(resultat.getString("pra_num") );
                praticien.setNom(resultat.getString("pra_nom") );
                praticien.setVille(resultat.getString("pra_ville") );
                praticien.setCoefNotoriete(Float.valueOf(resultat.getString("pra_coefnotoriete")) );                
                praticien.setDateDernierVisite(Date.valueOf(resultat.getString("date")).toLocalDate());
                praticien.setDernierCoefConfiance(Integer.valueOf(resultat.getString("rap_coef_confiance")));
                
               
               requetePreparee.close() ;
               return praticien;
                
            }
            else {
                return null ;
            }
            
        }
        catch( Exception e ){
            return null ;
        } 
    }
     
    public static void setRapportVisiteLu(String matricule,int numRapport) throws ConnexionException{
      
        Connection connexion = ConnexionBD.getConnexion() ;
        
        String requete = "update RapportVisite "
                + "set rap_lu = True "
                + "where vis_matricule = ? "
                + "and rap_num = ? ";

        
        try {
            PreparedStatement requetePreparee = (PreparedStatement) connexion.prepareStatement( requete ) ;
            requetePreparee.setString( 1 , matricule );
            requetePreparee.setInt(2, numRapport);
            int i = requetePreparee.executeUpdate();
            System.out.println(i);
            requetePreparee.close();
           
            
        }
        catch( Exception e ){
            System.out.println(e);
        } 
         
         
    }
}
