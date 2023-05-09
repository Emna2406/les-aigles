/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connexion3a41.services;

import  edu.connexion3a41.entities.Candidat;
import edu.connexion3a41.interfaces.EntityCRUD;

import java.io.File;
import java.io.FileReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import edu.connexion3a41.tools.MyDB;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
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
     public void addEntity(Candidat p) {
        try {
            String requete1 = "INSERT INTO candidat (idCand,nom,prenom,email) VALUES(?,?,?,?)";
            PreparedStatement pst = MyDB.getInstance().getConnexion().prepareStatement(requete1);
            pst.setInt(1, p.getIdCand());
            pst.setString(2, p.getNom());
             pst.setString(3, p.getPrenom());
             
            //FileReader fr = new FileReader(f);
           // pst.setCharacterStream(4, fr, (int)f.length());
            pst.setString(4, p.getEmail());
            pst.executeUpdate();
            System.out.println("Bravo candidat ajouté !");
            
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
     
  /* public void addCv(File f)
     {
         try {
            FileReader fr = new FileReader(f);
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement("update candidat set cv = ? where id = ?");
            pst.setCharacterStream(1, fr, (int)f.length());
            
         }catch(Exception ex)
         {
             ex.printStackTrace();
         }
        
         
     }*/
    
     @Override
    public ObservableList<Candidat> display() {
        ObservableList<Candidat> myList = FXCollections.observableArrayList();
        try {
            
            String requete2 = "Select * FROM candidat";
            Statement st =MyDB.getInstance().getConnexion().createStatement();
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
    
    @Override
    public void supprimer(Candidat p) {
        
        try {
            String requete3 = "DELETE FROM candidat WHERE id=" + p.getIdCand();
            Statement st = MyDB.getInstance().getConnexion().createStatement();
            
            st.executeUpdate(requete3);
            System.out.println("Candidat supprimé !");
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        
    }

   
    public ObservableList<Candidat> rechercher(String entry) {
        ObservableList<Candidat> myList = FXCollections.observableArrayList();
        try {
            
            PreparedStatement ps = MyDB.getInstance().getConnexion().prepareStatement("SELECT * FROM candidat nom LIKE ? or prenom LIKE ? or email LIKE ? ");
            ps.setString(1, "%" + entry + "%");
            ps.setString(2, "%" + entry + "%");
            ps.setString(3, "%" + entry + "%");
           
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Candidat rec = new Candidat();
                rec.setIdCand(rs.getInt("id"));
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


    @Override
    public void modifier( Candidat r) {
        try {
            String requete4 = "UPDATE candidat SET nom=?,prenom=? ,email=? WHERE id=?";
            PreparedStatement pst = MyDB.getInstance().getConnexion().prepareStatement(requete4);
            pst.setString(1, r.getNom());
            pst.setString(2, r.getPrenom());
            pst.setString(3, r.getEmail());
            pst.setInt(4, r.getIdCand());
           
            pst.executeUpdate();
            System.out.println("Candidat modifié !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

       @Override
    public ArrayList<Candidat> afficher() {
        ArrayList<Candidat> myList = new ArrayList<>();
            try {
        String requete = "SELECT * FROM candidat" ;
        Statement st;
        
            st = MyDB.getInstance().getConnexion().createStatement();
        
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

    @Override
    public void ajouter(Candidat t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}