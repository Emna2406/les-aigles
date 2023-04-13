/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktop.test;

import desktop.entities.Offre;
import desktop.entities.Candidat;
import desktop.services.OffreCRUD;
import desktop.services.CandidatCRUD;
import desktop.tools.MyConnection;
import java.util.List;

public class main {
    public static void main(String[] args) {
        MyConnection mc =MyConnection.getInstance();
        // Instantiate OffreCRUD
        OffreCRUD offreCRUD = new OffreCRUD();
        
        // Add a new Offre
     //   Offre offre = new Offre("offre", "john.doe@example.com");
      //  offreCRUD.AddEntity(offre);
        
        // Display all Offres
        List<Offre> offres = offreCRUD.display();
        for (Offre p : offres) {
            System.out.println(p.getId() + " - " + p.getDescription() + " - " + p.getNbrplaces()+ " - " + p.getIdservice());
        }
    }
}

