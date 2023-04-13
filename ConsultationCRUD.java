/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connexion3a41.services;

import edu.connexion3a41.entities.Consultation;
import edu.connexion3a41.entities.Ordonnance;
import edu.connexion3a41.interfaces.EntityCRUD;
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
public class ConsultationCRUD implements EntityCRUD<Consultation>  {
     Connection connexion;
    Statement stm;

    public ConsultationCRUD() {
                connexion = MyDB.getInstance().getConnexion();
    }

    @Override
    public void addEntity(Consultation t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Consultation> display() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void ajouter(Consultation t) throws SQLException {
         String req = "INSERT INTO `consultation` (`id`,`patient_id`,`medecin_id`,`datedeconsultation`,`image`)"
                + "VALUES ( ?, ?, ?, ?, ?) ";
        PreparedStatement ps = connexion.prepareStatement(req);
        ps.setInt(1,t.getId());
         ps.setInt(2, t.getPatient_id());
        ps.setInt(3, t.getMedecin_id());
        ps.setString(4,t.getDatedeconsultation());
        ps.setString(5, t.getImage());
       
       
        ps.executeUpdate();
     
    }

    @Override
    public void supprimer(Consultation t) throws SQLException {
       String requete = "DELETE FROM consultation WHERE id=?";
            PreparedStatement pst = connexion.prepareStatement(requete);
            pst.setInt(1, t.getId());
             //System.out.println(t.getIda()+"/"+t.getIdu()+"/"+t.getPrix()+"/"+t.getEmplacement()+"/"+t.getDescription()+"/"+t.getDate()+"/"+t.getType()+"/"+t.getAvis());

            pst.executeUpdate();
            System.out.println("Consultation supprimée !");
    }

    @Override
    public void modifier(Consultation t) throws SQLException {
          String requete = "UPDATE consultation SET patient_id=?,medecin_id=?,datedeconsultation=? ,image=? WHERE id=?";
            PreparedStatement pst = connexion.prepareStatement(requete);
            pst.setInt(5, t.getId());
            pst.setInt(1, t.getPatient_id());
        pst.setInt(2, t.getMedecin_id());
        pst.setString(3,t.getDatedeconsultation());
        pst.setString(4, t.getImage());
           
            pst.executeUpdate();
            System.out.println("activite modifiée !");
    }

    @Override
    public List<Consultation> afficher() throws SQLException {
        List<Consultation> activite = new ArrayList<>();
        String req = "select * from consultation";
        stm = connexion.createStatement();
        //ensemble de resultat
        ResultSet rst = stm.executeQuery(req);
            
        while (rst.next()) {
            
            Consultation a = new Consultation(rst.getInt("patient_id"),
                    rst.getInt("id"),
                   rst.getInt("medecin_id"),
                    rst.getString("datedeconsultation"),
                    rst.getString("image"));
            rst.getInt("id");
            activite.add(a);
        }
        return activite;
    }
    
     public ObservableList<Consultation> getConsultationList()throws SQLException{
         
                   ObservableList<Consultation> consultation = FXCollections.observableArrayList();

       
            String requete = "SELECT * FROM consultation";
            PreparedStatement pst = connexion.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
           while (rs.next()) {
                consultation.add(new Consultation(rs.getInt("id"),rs.getInt("patient_id"),rs.getInt("medecin_id"), rs.getString("datedeconsultation"),rs.getString("image")));
            }

       
        for(Consultation A : consultation)
            System.out.println(A.getId()+"/"+A.getPatient_id()+"/"+A.getMedecin_id()+"/"+A.getDatedeconsultation()+"/"+A.getImage()+"/");
        


        return consultation;
        }
}
