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
public class Produit {
     private int id;
    private String nom;
    private int stock ;

    public Produit(int id, String nom, int stock) {
        this.id = id;
        this.nom = nom;
        this.stock = stock;
    }

    public Produit(String nom, int stock) {
        this.nom = nom;
        this.stock = stock;
    }

    public Produit() {
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

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Produit{" + "id=" + id + ", nom=" + nom + ", stock=" + stock + '}';
    }
    
    
}
