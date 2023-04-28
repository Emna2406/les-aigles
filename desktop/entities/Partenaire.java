/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktop.entities;

import javafx.collections.ObservableList;

/**
 *
 * @author msi
 */
public class Partenaire {

    public static void add(Partenaire partenaire) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static void setItems(ObservableList<Partenaire> list) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    private int id;
    private String nom;
    private String email;

    public Partenaire(int id, String nom, String email) {
        this.id = id;
        this.nom = nom;
        this.email = email;
    }

    public Partenaire(String nom, String email) {
        this.nom = nom;
        this.email = email;
    }

    public Partenaire() {
    }

   
  

    public Partenaire(int aInt, int aInt0, String string, String string0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Partenaire{" + "id=" + id + ", nom=" + nom + ", email=" + email + '}';
    }

  
  

   
    
    
}
