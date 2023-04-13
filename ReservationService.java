/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connexion3a41.services;

import edu.connexion3a41.entities.Chambre;
import edu.connexion3a41.entities.Reservation;
import edu.connexion3a41.entities.Service;
import edu.connexion3a41.interfaces.EntityCRUD;
import edu.connexion3a41.tools.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author YOSR
 */
public class ReservationService implements  EntityCRUD <Reservation> {
     Connection connexion;
    Statement stm;
public ReservationService()
{
    connexion = MyConnection.getInstance().getCnx();
}
 @Override
  
    public void addEntity( Reservation s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Reservation> display() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
   @Override
    public void ajouter( Reservation s) throws SQLException {
         String req = "INSERT INTO `reservation` (`id_medecin`,`id_patient`,`id_chambre` ,`date_deb`    ) "
                + "VALUES ( ?, ?, ?, ? ) ";
        PreparedStatement ps = connexion.prepareStatement(req);
        ps.setInt(1,s.getId_medecin());
        ps.setInt(2, s.getId_patient());
        ps.setInt(3,s.getId_chambre());
         ps.setDate(4, s.getDate_deb());
         
        
       
     
        
        ps.executeUpdate();
    }
     @Override
     public void supprimer(Reservation s) throws SQLException {
        String requete = "DELETE FROM reservation WHERE id =?";
            PreparedStatement pst = connexion.prepareStatement(requete);
            pst.setInt(1, s.getId());
             //System.out.println(t.getIda()+"/"+t.getIdu()+"/"+t.getPrix()+"/"+t.getEmplacement()+"/"+t.getDescription()+"/"+t.getDate()+"/"+t.getType()+"/"+t.getAvis());

            pst.executeUpdate();
            System.out.println("reglement supprimée !");
    }
 @Override
   
    public void modifier(Reservation s) throws SQLException {
        String requete = "UPDATE reservation SET id_medecin=?,id_patient=?,id_chambre=? ,date_deb=?  WHERE id=?";
            PreparedStatement pst = connexion.prepareStatement(requete);
            
            pst.setInt(5, s.getId());
            pst.setInt(1, s.getId_medecin());
            pst.setInt(2, s.getId_patient());
            pst.setInt(3, s.getId_chambre());
        pst.setDate(4, s.getDate_deb());
         
       
           
            
            pst.executeUpdate();
            System.out.println("service modifiée !");
    }
    
    


    @Override
    public List<Reservation> afficher() throws SQLException {
             List<Reservation> reservation = new ArrayList<>();
        String req = "select * from reservation ";
        stm = connexion.createStatement();
        //ensemble de resultat
        ResultSet rst = stm.executeQuery(req);
            
        while (rst.next()) {
            
            Reservation c  = new Reservation(
                     rst.getInt("id"),
                    rst.getInt("id_medecin"),
                    rst.getInt("id_patient"),
                    rst.getInt("id_chambre"),
               rst.getDate("date_deb"));
              
            rst.getInt("id");
            reservation.add(c);
        }
        return reservation;
    }
 public ObservableList<Reservation> getReservationList()throws SQLException{
         
                   ObservableList<Reservation> reservation = FXCollections.observableArrayList();

       
            String requete = "SELECT * FROM reservation";
            PreparedStatement pst = connexion.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
           while (rs.next()) {
                reservation.add(new Reservation(rs.getInt(1), rs.getInt("id_medecin"), rs.getInt("id_patient"), rs.getInt("id_chambre"), rs.getDate("date_deb")));
            }

        
        for(Reservation A : reservation)
            System.out.println(A.getId()+"/"+A.getId_medecin()+"/"+A.getId_patient()+"/"+A.getId_chambre()+"/"+A.getDate_deb());


        return reservation; 
        } 
    
  
    
}
