package fr.gsb.rv.technique;


public class ConnexionException extends Exception {
    
    @Override
    public String getMessage(){
        return "Erreur de connexion BD" ;
    }
    
}
