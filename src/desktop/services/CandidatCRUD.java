/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktop.services;

import desktop.entities.Candidat;
import desktop.interfaces.EntityCRUD;
import desktop.tools.MyConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author USER
 */
public class CandidatCRUD implements EntityCRUD<Candidat> {

   
 
    
    /**
     *
     * @param p
     */
    @Override
     public void AddEntity(Candidat p) {
        try {
            String requete1 = "INSERT INTO candidat (id,nom,prenom,email) VALUES(?,?,?,?)";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete1);
            pst.setInt(1, p.getIdCand());
            pst.setString(2, p.getNom());
             pst.setString(3, p.getPrenom());
            pst.setString(4, p.getEmail());
            pst.executeUpdate();
            System.out.println("Bravo candidat ajouté !");
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public ObservableList<Candidat> listerCandidats() {
        ObservableList<Candidat> myList = FXCollections.observableArrayList();
        try {
            
            String requete2 = "Select * FROM candidat";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete2);
            while (rs.next()) {
               Candidat rec = new Candidat();
                
                rec.setIdCand(rs.getInt("idCand"));
                rec.setNom(rs.getString("nom"));
                rec.setPrenom(rs.getString("prenom"));
                rec.setEmail(rs.getString("email"));

                
                myList.add(rec);
                
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            
        }
        return myList;
    }
    
    public void delete(Candidat p) {
        
        try {
            String requete3 = "DELETE FROM candidat WHERE idCand=" + p.getIdCand();
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            
            st.executeUpdate(requete3);
            System.out.println("Candidat supprimé !");
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        
    }

    public ObservableList<Candidat> SearchPart(String entry) {
        ObservableList<Candidat> myList = FXCollections.observableArrayList();
        try {
            
            PreparedStatement ps = MyConnection.getInstance().getCnx().prepareStatement("SELECT * FROM candidat nom LIKE ? or prenom LIKE ? or email LIKE ? ");
            ps.setString(1, "%" + entry + "%");
            ps.setString(2, "%" + entry + "%");
            ps.setString(3, "%" + entry + "%");
           
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Candidat rec = new Candidat();
                rec.setIdCand(rs.getInt("idCand"));
                rec.setNom(rs.getString("nom"));
                rec.setPrenom(rs.getString("prenom"));
                rec.setEmail(rs.getString("email"));
                
                myList.add(rec);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }


    
    public void update(int idCand, Candidat r) {
        try {
            String requete4 = "UPDATE candidat SET nom=?,prenom=? ,email=? WHERE idCand=?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete4);
            pst.setString(1, r.getNom());
            pst.setString(2, r.getPrenom());
            pst.setString(3, r.getEmail());
            pst.setInt(4, idCand);
           
            pst.executeUpdate();
            System.out.println("Candidat modifié !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

       @Override
    public ArrayList<Candidat> display() {
        ArrayList<Candidat> myList = new ArrayList<>();
            try {
        String requete = "SELECT * FROM candidat" ;
        Statement st;
        
            st = MyConnection.getInstance().getCnx().createStatement();
        
        ResultSet rs = st.executeQuery(requete);
        while (rs.next())
        {
            Candidat p = new Candidat();
            p.setIdCand(rs.getInt(1));
            p.setNom(rs.getString("nom"));
            p.setPrenom(rs.getString("prenom"));
            p.setEmail(rs.getString("email"));
            myList.add(p);
            
            
        }
            }
        
       
    
        catch (SQLException ex) {
            System.out.println(ex.getMessage());    
        }
    
return myList;
}

}