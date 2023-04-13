/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connexion3a41.tests;

import edu.connexion3a41.entities.Chambre;
import edu.connexion3a41.entities.Personne;
import edu.connexion3a41.entities.Reservation;
import edu.connexion3a41.entities.Service;
import edu.connexion3a41.services.ChambreService;
import edu.connexion3a41.services.PersonneCRUD;
import edu.connexion3a41.services.ReservationService;
import edu.connexion3a41.services.ServiceS;
import edu.connexion3a41.tools.MyConnection;
import java.sql.SQLException;

/**
 *
 * @author karra
 */
public class MainClass {
    public static void main(String[] args) {
     //   MyConnection mc = new MyConnection();
        Service s = new Service("urgence", "24h", "urg");
        ServiceS ser = new ServiceS();
        
        Chambre c = new Chambre("201", "1", "150", "disponible" );
        ChambreService chs = new ChambreService();
        
          Reservation r = new Reservation(1,1,111);
          ReservationService res = new ReservationService();
          
        
        
     /* try {
           ser.ajouter(s);
          //  as.ajouter(aa);
            System.out.println("ajout avec succes");
       } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    } */
    
        /*try {
            ser.supprimer(new Service(100));
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } */
       
       /*   try {
            ser.modifier(new Service(109, "maternite",  "enfant" , "aa"));
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } */
          /*  try {
            System.out.println(ser.afficher());
             System.out.println("afficher avec succes");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }  */
       /*  try {
           chs.ajouter(c);
          //  as.ajouter(aa);
            System.out.println("ajout avec succes");
       } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }  */
        
         /* try {
            chs.modifier(new Chambre(111 ,"500", "1",  "200" , "dispo"));
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } */
         /*  try {
            System.out.println(chs.afficher());
             System.out.println("afficher avec succes");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }*/
        
         
       /* try {
            chs.supprimer(new Chambre(115));
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } */
      /*  try {
           res.ajouter(r);
          //  as.ajouter(aa);
            System.out.println("ajout avec succes");
       } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }*/
       try {
            res.modifier(new Reservation(19, 1,  1 , 112));
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
    
    }
}
}

