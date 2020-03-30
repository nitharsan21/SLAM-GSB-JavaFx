/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.gsb.rv.dr.entites;

import java.time.LocalDate;

/**
 *
 * @author developpeur
 */
public class Praticien {
    private String numero;
    private String nom;
    private String ville;
    private float coefNotoriete;
    private LocalDate dateDernierVisite;
    private int dernierCoefConfiance;

    public Praticien() {
    }

    public Praticien(String numero, String nom, String ville, int coefNotoriete, LocalDate dateDernierVisite, int dernierCoefConfiance) {
        this.numero = numero;
        this.nom = nom;
        this.ville = ville;
        this.coefNotoriete = coefNotoriete;
        this.dateDernierVisite = dateDernierVisite;
        this.dernierCoefConfiance = dernierCoefConfiance;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public float getCoefNotoriete() {
        return coefNotoriete;
    }

    public void setCoefNotoriete(float coefNotoriete) {
        this.coefNotoriete = coefNotoriete;
    }

    public LocalDate getDateDernierVisite() {
        return dateDernierVisite;
    }

    public void setDateDernierVisite(LocalDate dateDernierVisite) {
        this.dateDernierVisite = dateDernierVisite;
    }

    public int getDernierCoefConfiance() {
        return dernierCoefConfiance;
    }

    public void setDernierCoefConfiance(int dernierCoefConfiance) {
        this.dernierCoefConfiance = dernierCoefConfiance;
    }

    @Override
    public String toString() {
        return "Praticien{" + "numero=" + numero + ", nom=" + nom + ", ville=" + ville + ", coefNotoriete=" + coefNotoriete + ", dateDernierVisite=" + dateDernierVisite + ", dernierCoefConfiance=" + dernierCoefConfiance + '}';
    }
    
    
    
}
