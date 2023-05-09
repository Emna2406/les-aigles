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
import edu.connexion3a41.tools.MyDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
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
    connexion = MyDB.getInstance().getConnexion();
}
 @Override
  
    public void addEntity( Reservation s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Reservation> display() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
 public void ajouter(Reservation r) throws SQLException {
    String req = "INSERT INTO reservation (id_medecin, id_patient, id_chambre, date_deb) VALUES (?, ?, ?, ?)";
    PreparedStatement pst = connexion.prepareStatement(req);
    pst.setInt(1, r.getId_medecin());
    pst.setInt(2, r.getId_patient());
    pst.setInt(3, r.getId_chambre());
    pst.setDate(4, r.getDate_deb());

    // Récupération du prix de la chambre
    String reqChambre = "SELECT prix FROM chambre WHERE id = ?";
    PreparedStatement pstChambre = connexion.prepareStatement(reqChambre);
    pstChambre.setInt(1, r.getId_chambre());
    ResultSet rsChambre = pstChambre.executeQuery();
    rsChambre.next();
   double prixChambre = Double.parseDouble(rsChambre.getString("prix"));

    // Calcul de la remise de 10% si la réservation est entre décembre et avril
    double prix = prixChambre;
    LocalDate date = r.getDate_deb().toLocalDate();
    int mois = date.getMonthValue();
    if (mois >= 12 || mois <= 4) {
        prix = 0.9 * prixChambre;
        System.out.println("Le prix avec remise est : " + prix);
    }

    pst.executeUpdate();
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
    List<Reservation> reservations = new ArrayList<>();
    String req = "SELECT r.*, c.prix FROM reservation r JOIN chambre c ON r.id_chambre = c.id";
    stm = connexion.createStatement();
    ResultSet rst = stm.executeQuery(req);

    while (rst.next()) {
        Reservation r = new Reservation(
            rst.getInt("id"),
            rst.getInt("id_medecin"),
            rst.getInt("id_patient"),
            rst.getInt("id_chambre"),
            rst.getDate("date_deb"),
            null
        );
        
        // Récupération du prix de la chambre
        double prixChambre = Double.parseDouble(rst.getString("prix"));
        
        // Calcul de la remise de 10% si la réservation est entre décembre et avril
        LocalDate date = r.getDate_deb().toLocalDate();
        int mois = date.getMonthValue();
        double prix = prixChambre;
        if (mois >= 12 || mois <= 4) {
            prix = 0.9 * prixChambre;
        }
        
        // Mettre à jour le prix dans l'objet Reservation
        r.setPrix(String.valueOf(prix));
        
        reservations.add(r);
    }
    return reservations;
}


 public ObservableList<Reservation> getReservationList() throws SQLException {
    ObservableList<Reservation> reservations = FXCollections.observableArrayList();

    String requete = "SELECT r.id, r.id_medecin, r.id_patient, r.id_chambre, r.date_deb, c.prix FROM reservation r JOIN chambre c ON r.id_chambre = c.id";
    PreparedStatement pst = connexion.prepareStatement(requete);
    ResultSet rs = pst.executeQuery();
    
    while (rs.next()) {
        Reservation r = new Reservation(
            rs.getInt("id"),
            rs.getInt("id_medecin"),
            rs.getInt("id_patient"),
            rs.getInt("id_chambre"),
            rs.getDate("date_deb"),
            null
        );
        
        // Récupération du prix de la chambre
        double prixChambre = Double.parseDouble(rs.getString("prix"));
        
        // Calcul de la remise de 10% si la réservation est entre décembre et avril
        LocalDate date = r.getDate_deb().toLocalDate();
        int mois = date.getMonthValue();
        double prix = prixChambre;
        if (mois >= 12 || mois <= 4) {
            prix = 0.9 * prixChambre;
        }
        
        // Mettre à jour le prix dans l'objet Reservation
        r.setPrix(String.valueOf(prix));
        
        reservations.add(r);
    }

    for (Reservation r : reservations) {
        System.out.println(r.getId() + "/" + r.getId_medecin() + "/" + r.getId_patient() + "/" + r.getId_chambre() + "/" + r.getDate_deb() + "/" + r.getPrix());
    }

    return reservations;
}


  
    
}
