/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connexion3a41.services;

import edu.connexion3a41.entities.Rdv;
import edu.connexion3a41.entities.Visite;
import edu.connexion3a41.interfaces.EntityCRUD;
import edu.connexion3a41.tools.MyDB;
import java.sql.SQLException;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.Statement;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author tha3leb
 */
public class VisiteCRUD implements  EntityCRUD<Visite>{
      Connection connexion;
    Statement stm;

    public VisiteCRUD() {
                connexion = MyDB.getInstance().getConnexion();
    }

    @Override
    public void addEntity(Visite t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Visite> display() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void ajouter(Visite t) throws SQLException {
        String req = "INSERT INTO `visite` (`idvisite`,`nomvisiteur`,`nompatient`,`date`, `numerotele` , `message`  , `patient_id` ) "
                + "VALUES ( ?, ?, ?, ?, ?, ?, ?) ";
        PreparedStatement ps = connexion.prepareStatement(req);
        ps.setFloat(1,t.getIdvisite());
        ps.setString(2, t.getNomvisiteur());
        ps.setString(3, t.getNompatient());
        ps.setDate(4,t.getDate());
        ps.setString(5, t.getNumerotele());
        ps.setString(6, t.getMessage());
   
        ps.setFloat(7, t.getPatient_id());
       
        
        ps.executeUpdate();
    }

    @Override
    public void supprimer(Visite t) throws SQLException {
      String requete = "DELETE FROM visite WHERE idvisite=?";
            PreparedStatement pst = connexion.prepareStatement(requete);
            pst.setInt(1, t.getIdvisite());
             //System.out.println(t.getIda()+"/"+t.getIdu()+"/"+t.getPrix()+"/"+t.getEmplacement()+"/"+t.getDescription()+"/"+t.getDate()+"/"+t.getType()+"/"+t.getAvis());

            pst.executeUpdate();
            System.out.println("visite supprimée !");
    }

    @Override
    public void modifier(Visite t) throws SQLException {
        String requete = "UPDATE visite SET nomvisiteur=?, nompatient=?,date=? , numerotele=? , message=? ,patient_id=? WHERE idvisite=?";
            PreparedStatement pst = connexion.prepareStatement(requete);
           pst.setString(1, t.getNomvisiteur());
            pst.setString(2, t.getNompatient());
            pst.setDate(3, t.getDate());
            pst.setString(4, t.getNumerotele());
            pst.setString(5, t.getMessage());
            
          
            pst.setInt(6, t.getPatient_id());
               pst.setInt(7, t.getIdvisite());
           
            
            pst.executeUpdate();
            System.out.println("visite modifiée !");
    }

    @Override
    public List<Visite> afficher() throws SQLException {
         List<Visite> visite = new ArrayList<>();
        String req = "select * from visite";
        stm = connexion.createStatement();
        //ensemble de resultat
        ResultSet rst = stm.executeQuery(req);
            
        while (rst.next()) {
            
           Visite a = new Visite(
                  
                   rst.getString("nomvisiteur"),
                   
                    rst.getString("nompatient"),
                  
                    rst.getDate("date"),
                     rst.getString("numerotele"),
                    rst.getString("message"),
                  
                      rst.getInt("patient_id")
                 
                 );
            rst.getInt("idvisite");
            visite.add(a);
        }
        return visite;
    }
    
     public ObservableList<Visite> getVisiteList()throws SQLException{
         
                   ObservableList<Visite> visite = FXCollections.observableArrayList();

       
            String requete = "SELECT * FROM visite";
            PreparedStatement pst = connexion.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
           while (rs.next()) {
                visite.add(new Visite(rs.getInt("idvisite"),rs.getString("nomvisiteur"),rs.getString("nompatient"), rs.getDate("date"),rs.getString("numerotele"),rs.getString("message"),rs.getInt("patient_id")));
            }

        
        for(Visite A : visite)
            System.out.println(A.getIdvisite()+"/"+A.getNomvisiteur()+"/"+A.getNompatient()+"/"+A.getDate()+"/"+A.getNumerotele()+"/"+A.getMessage()+"/"+A.getPatient_id()+"/");


        return visite; 
        } 
       

   
}
