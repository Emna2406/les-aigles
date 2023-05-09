/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connexion3a41.services;

import edu.connexion3a41.entities.Consultation;
import edu.connexion3a41.entities.Ordonnance;
import edu.connexion3a41.entities.Personne;
import edu.connexion3a41.interfaces.EntityCRUD;
import edu.connexion3a41.tools.MyConnection;
import edu.connexion3a41.tools.MyDB;
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
 * @author aouadh
 */
public class OrdonnanceCRUD implements EntityCRUD<Ordonnance>  {
     Connection connexion;
    Statement stm;

    public OrdonnanceCRUD() {
                connexion = MyDB.getInstance().getConnexion();
    }

    @Override
    public void addEntity(Ordonnance t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Ordonnance> display() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void ajouter(Ordonnance t) throws SQLException {
         String req = "INSERT INTO `ordonance` (`id`,`consultation_id`,`date`, `description` , `image`) "
                + "VALUES ( ?, ?, ?, ?, ?) ";
        PreparedStatement ps = connexion.prepareStatement(req);
        ps.setInt(1,t.getId());
        ps.setInt(2, t.getConsultation_id());
        ps.setString(3,t.getDate());
        ps.setString(4, t.getDescription());
        ps.setString(5, t.getImage());
       
       
       
        ps.executeUpdate();
     
    }

    @Override
    public void supprimer(Ordonnance t) throws SQLException {
       String requete = "DELETE FROM ordonance WHERE id=?";
            PreparedStatement pst = connexion.prepareStatement(requete);
            pst.setInt(1, t.getId());
             //System.out.println(t.getIda()+"/"+t.getIdu()+"/"+t.getPrix()+"/"+t.getEmplacement()+"/"+t.getDescription()+"/"+t.getDate()+"/"+t.getType()+"/"+t.getAvis());

            pst.executeUpdate();
            System.out.println("ordonnance supprimée !");
    }

    @Override
    public void modifier(Ordonnance t) throws SQLException {
          String requete = "UPDATE ordonance SET consultation_id=?,date=? , description=? , image=? WHERE id=?";
            PreparedStatement pst = connexion.prepareStatement(requete);
            pst.setInt(5, t.getId());
            pst.setInt(1, t.getConsultation_id());
            pst.setString(2, t.getDate());
            pst.setString(3, t.getDescription());
            pst.setString(4, t.getImage());
           
           
            pst.executeUpdate();
            System.out.println("activite modifiée !");
    }

    @Override
    public List<Ordonnance> afficher() throws SQLException {
        List<Ordonnance> activite = new ArrayList<>();
        String req = "select * from ordonance";
        stm = connexion.createStatement();
        //ensemble de resultat
        ResultSet rst = stm.executeQuery(req);
            
        while (rst.next()) {
            
            Ordonnance a = new Ordonnance(rst.getInt("consultation_id"),
                    rst.getInt("id"),
                    rst.getString("date"),
                    rst.getString("description"),
                    rst.getString("image"));
            rst.getInt("id");
            activite.add(a);
        }
        return activite;
    }
    
     public ObservableList<Ordonnance> getOrdonnanceList()throws SQLException{
         
                   ObservableList<Ordonnance> ordonnance = FXCollections.observableArrayList();

       
            String requete = "SELECT * FROM ordonance";
            PreparedStatement pst = connexion.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
           while (rs.next()) {
                ordonnance.add(new Ordonnance(rs.getInt("id"),rs.getInt("consultation_id"),rs.getString("date"), rs.getString("description"),rs.getString("image")));
            }

       
        for(Ordonnance A : ordonnance)
            System.out.println(A.getId()+"/"+A.getConsultation_id()+"/"+A.getDate()+"/"+A.getDescription()+"/"+A.getImage()+"/");
        


        return ordonnance;
        }
   
 
   
}
