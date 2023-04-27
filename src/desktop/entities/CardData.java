/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktop.entities;

/**
 *
 * @author msi
 */
public class CardData {
    private String nom;
    private String email;
    private String lien;

    public CardData(String nom, String email, String lien) {
        this.nom = nom;
        this.email = email;
        this.lien = lien;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLien() {
        return lien;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }

    @Override
    public String toString() {
        return "CardData{" + "nom=" + nom + ", email=" + email + ", lien=" + lien + '}';
    }

    
}
