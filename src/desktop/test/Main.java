/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktop.test;

import desktop.entities.Partenaire;
import desktop.entities.Produit;
import desktop.services.PartenaireCRUD;
import desktop.services.ProduitCRUD;
import desktop.tools.MyConnection;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        MyConnection mc =MyConnection.getInstance();
        // Instantiate PartenaireCRUD
        PartenaireCRUD partenaireCRUD = new PartenaireCRUD();
        
        // Add a new Partenaire
        Partenaire partenaire = new Partenaire("John Doe", "john.doe@example.com");
        partenaireCRUD.AddEntity(partenaire);
        
        // Display all Partenaires
        List<Partenaire> partenaires = partenaireCRUD.display();
        for (Partenaire p : partenaires) {
            System.out.println(p.getId() + " - " + p.getNom() + " - " + p.getEmail());
        }
    }
}

