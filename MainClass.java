/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connexion3a41.tests;

import edu.connexion3a41.entities.Certificat;
import edu.connexion3a41.entities.Consultation;
import edu.connexion3a41.entities.Ordonnance;
import edu.connexion3a41.entities.Personne;
import edu.connexion3a41.services.CertificatCRUD;
import edu.connexion3a41.services.ConsultationCRUD;
import edu.connexion3a41.services.OrdonnanceCRUD;
import edu.connexion3a41.services.PersonneCRUD;
import edu.connexion3a41.tools.MyConnection;
import java.sql.SQLException;

/**
 *
 * @author karra
 */
public class MainClass {
    public static void main(String[] args) {
     //   MyConnection mc = new MyConnection();
Ordonnance a=new Ordonnance(4,25,"2023-04-25 08:30:00", "ayshou rojla", "lina@esprit.tn");
OrdonnanceCRUD as = new OrdonnanceCRUD();
Certificat b=new Certificat(2,2,1,"2023-04-25 08:30:00","2024-04-25 08:30:00","lina@esprit.tn");
CertificatCRUD ab = new CertificatCRUD();
Consultation c=new Consultation(4,2,1,"2023-04-25 08:30:00","lina@esprit.tn");
ConsultationCRUD ac = new ConsultationCRUD();

/*try {
           as.ajouter(a);
       
            System.out.println("ajout avec succes");
       } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }*/
       
   /* try {
            as.supprimer(new Ordonnance(2));
           
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }*/
    
    /* try {
            System.out.println(as.afficher());
             System.out.println("afficher avec succes");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }*/
    /* try {
            as.modifier(new Ordonnance(39,25, "2018-01-01","ellefaut","ordi.jpg"));
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }*/
         
      /* try {
           ab.ajouter(b);
       
            System.out.println("ajout avec succes");
       } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }*/
     
    /* try {
            System.out.println(ab.afficher());
             System.out.println("afficher avec succes");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }*/
    /* try {
            System.out.println(ac.afficher());
             System.out.println("afficher avec succes");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }*/

   try {
           ac.ajouter(c);
       
            System.out.println("ajout avec succes");
       } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
   
   /* try {
            ab.supprimer(new Certificat(11));
           
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }*/
   
   /* try {
            ab.modifier(new Certificat(36,1,2, "2018-01-01","2018-02-02","certi.jpg"));
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }*/
  /*  try {
            ac.modifier(new Consultation(25,1,2, "2018-01-01","certi.jpg"));
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }*/
    }
}
