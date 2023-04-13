/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connexion3a41.services;

import edu.connexion3a41.entities.Certificat;
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
public class CertificatCRUD  implements EntityCRUD<Certificat>{
    
     Connection connexion;
    Statement stm;

    public CertificatCRUD() {
                connexion = MyDB.getInstance().getConnexion();
    }

    @Override
    public void addEntity(Certificat t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Certificat> display() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void ajouter(Certificat t) throws SQLException {
         String req = "INSERT INTO `certificat` (`id`,`patient_id`,`medecin_id`,`datedeb`,`datefin`, `image`) "
                + "VALUES ( ?, ?, ?, ?, ?, ?) ";
        PreparedStatement ps = connexion.prepareStatement(req);
        ps.setInt(1,t.getId());
        ps.setInt(2, t.getPatient_id());
        ps.setInt(3, t.getMedecin_id());
        ps.setString(4,t.getDatedeb());
         ps.setString(5,t.getDatefin());
        ps.setString(6, t.getImage());
       
       
       
        ps.executeUpdate();
     
    }

    @Override
    public void supprimer(Certificat t) throws SQLException {
       String requete = "DELETE FROM certificat WHERE id=?";
            PreparedStatement pst = connexion.prepareStatement(requete);
            pst.setInt(1, t.getId());
             //System.out.println(t.getIda()+"/"+t.getIdu()+"/"+t.getPrix()+"/"+t.getEmplacement()+"/"+t.getDescription()+"/"+t.getDate()+"/"+t.getType()+"/"+t.getAvis());

            pst.executeUpdate();
            System.out.println("certificat supprimée !");
    }

    @Override
    public void modifier(Certificat t) throws SQLException {
          String requete = "UPDATE certificat SET patient_id=?,medecin_id=?,datedeb=? ,datefin=? , image=? WHERE id=?";
            PreparedStatement pst = connexion.prepareStatement(requete);
            pst.setInt(6, t.getId());
            pst.setInt(1, t.getPatient_id());
            pst.setInt(2, t.getMedecin_id());
            pst.setString(3,t.getDatedeb());
            pst.setString(4,t.getDatefin());
            pst.setString(5, t.getImage());
           
           
            pst.executeUpdate();
            System.out.println("activite modifiée !");
    }

    @Override
    public List<Certificat> afficher() throws SQLException {
        List<Certificat> activite = new ArrayList<>();
        String req = "select * from certificat";
        stm = connexion.createStatement();
        //ensemble de resultat
        ResultSet rst = stm.executeQuery(req);
            
        while (rst.next()) {
            
            Certificat a = new Certificat(rst.getInt("patient_id"),
                    rst.getInt("id"),
                    rst.getInt("medecin_id"),
                    rst.getString("datedeb"),
                    rst.getString("datefin"),
                    rst.getString("image"));
            rst.getInt("id");
            activite.add(a);
        }
        return activite;
    }
    
     public ObservableList<Certificat> getCertificatList()throws SQLException{
         
                   ObservableList<Certificat> certificat = FXCollections.observableArrayList();

       
            String requete = "SELECT * FROM certificat";
            PreparedStatement pst = connexion.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
           while (rs.next()) {
                certificat.add(new Certificat(rs.getInt("id"),rs.getInt("patient_id"),rs.getInt("medecin_id"), rs.getString("datedeb"),rs.getString("datefin"),rs.getString("image")));
            }

       
        for(Certificat A : certificat)
            System.out.println(A.getId()+"/"+A.getPatient_id()+"/"+A.getMedecin_id()+"/"+A.getDatedeb()+"/"+A.getDatefin()+"/"+A.getImage()+"/");
        


        return certificat;
        }
    
}
