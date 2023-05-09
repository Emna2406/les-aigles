
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connexion3a41.services;

import  edu.connexion3a41.entities.Candidat;
import edu.connexion3a41.entities.Offre;
import edu.connexion3a41.interfaces.EntityCRUD;
import edu.connexion3a41.tools.MyDB;
import java.io.FileReader;
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
 * @author USER
 */
public class OffreCRUD implements EntityCRUD<Offre> {

    /**
     *
     * @param o
     *
     */
    @Override
    public void addEntity(Offre o) {
        System.out.println(o.toString());
        try {
            String requete1 = "INSERT INTO offre (id,nbrplaces,idservice,description) VALUES(?,?,?,?)";
            PreparedStatement pst = MyDB.getInstance().getConnexion().prepareStatement(requete1);
            pst.setInt(1, o.getId());
            pst.setInt(2, o.getIdservice());
            pst.setInt(3, o.getNbrplaces());
            pst.setString(4, o.getDescription());
           
 

            pst.executeUpdate();
            System.out.println("Bravo offre ajouté !");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            /*System.out.println("test");*/
        }
    }
    
    @Override
    public void modifier( Offre r) {
        try {
            String requete4 = "UPDATE offre SET nbrplaces=?,description=? WHERE id=?";
            PreparedStatement pst = MyDB.getInstance().getConnexion().prepareStatement(requete4);
            pst.setInt(1, r.getNbrplaces());
            pst.setString(2, r.getDescription());
            pst.setInt(3, r.getId());
           
            pst.executeUpdate();
            System.out.println("Offre modifié !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    @Override
    public void supprimer(Offre p) {
        
        try {
            String requete3 = "DELETE FROM offre WHERE id=" + p.getId();
            Statement st = MyDB.getInstance().getConnexion().createStatement();
            
            st.executeUpdate(requete3);
            System.out.println("Offre supprimé !");
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        
    }

    @Override
    public ObservableList<Offre> afficher() {
        ObservableList<Offre> myList = FXCollections.observableArrayList();
        try {
            
            String requete2 = "Select * FROM Offre";
            Statement st = MyDB.getInstance().getConnexion().createStatement();
            ResultSet rs = st.executeQuery(requete2);
            while (rs.next()) {
               Offre rec = new Offre();
                
                rec.setId(rs.getInt("id"));
                rec.setNbrplaces(rs.getInt("nbrplaces"));
                rec.setDescription(rs.getString("description"));
                rec.setIdservice(rs.getInt("Idservice"));

                
                myList.add(rec);
                
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            
        }
        return myList;
    }

    @Override
    public List<Offre> display() {
        List<Offre> myList = new ArrayList();
        try {
            
            String requete2 = "Select * FROM Offre";
            Statement st = MyDB.getInstance().getConnexion().createStatement();
            ResultSet rs = st.executeQuery(requete2);
            while (rs.next()) {
               Offre rec = new Offre();
                
                rec.setId(rs.getInt("id"));
                rec.setNbrplaces(rs.getInt("nbrplaces"));
                rec.setDescription(rs.getString("description"));
                rec.setIdservice(rs.getInt("idservice"));

                
                myList.add(rec);
                
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            
        }
        return myList;
    }

    @Override
    public void ajouter(Offre t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

    


