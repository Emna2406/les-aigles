/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktop.services;

import desktop.entities.Candidat;
import desktop.entities.Offre;
import desktop.interfaces.EntityCRUD;
import desktop.tools.MyConnection;
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
    public void AddEntity(Offre o) {
        try {
            String requete1 = "INSERT INTO offre (nbrplaces,idservice,description) VALUES(?,?,?)";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete1);
            pst.setInt(2, o.getIdservice());
            pst.setInt(1, o.getNbrplaces());
            pst.setString(3, o.getDescription());

            pst.executeUpdate();
            System.out.println("Bravo offre ajouté !");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.out.println("test");
        }
    }
    
    public void update(int id, Offre r) {
        try {
            String requete4 = "UPDATE offre SET nbrplaces=?,description=? WHERE id=?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete4);
            pst.setInt(1, r.getNbrplaces());
            pst.setString(2, r.getDescription());
            pst.setInt(3, id);
           
            pst.executeUpdate();
            System.out.println("Offre modifié !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void delete(Offre p) {
        
        try {
            String requete3 = "DELETE FROM offre WHERE id=" + p.getId();
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            
            st.executeUpdate(requete3);
            System.out.println("Offre supprimé !");
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        
    }

  public ObservableList<Offre> listerCandidats() {
        ObservableList<Offre> myList = FXCollections.observableArrayList();
        try {
            
            String requete2 = "Select * FROM Offre";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
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
            Statement st = MyConnection.getInstance().getCnx().createStatement();
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
}

    


