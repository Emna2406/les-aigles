/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktop.services;

import desktop.entities.Partenaire;
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
 * @author msi
 */
public class PartenaireCRUD implements EntityCRUD<Partenaire> {
 
    
    /**
     *
     * @param p
     */
    @Override
     public void AddEntity(Partenaire p) {
        try {
            String requete1 = "INSERT INTO partenaire (id,nom,email) VALUES(?,?,?)";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete1);
            pst.setInt(1, p.getId());
            pst.setString(2, p.getNom());
            pst.setString(3, p.getEmail());
            pst.executeUpdate();
            System.out.println("Bravo partenaire ajouté !");
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public ObservableList<Partenaire> listerPartenaires() {
        ObservableList<Partenaire> myList = FXCollections.observableArrayList();
        try {
            
            String requete2 = "Select * FROM partenaire";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete2);
            while (rs.next()) {
               Partenaire rec = new Partenaire();
                
                rec.setId(rs.getInt("id"));
                rec.setNom(rs.getString("nom"));
                rec.setEmail(rs.getString("email"));

                
                myList.add(rec);
                
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            
        }
        return myList;
    }
    
    public void delete(Partenaire p) {
        
        try {
            String requete3 = "DELETE FROM partenaire WHERE id=" + p.getId();
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            
            st.executeUpdate(requete3);
            System.out.println("Partenaire supprimé !");
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        
    }

    public ObservableList<Partenaire> SearchPart(String entry) {
        ObservableList<Partenaire> myList = FXCollections.observableArrayList();
        try {
            
            PreparedStatement ps = MyConnection.getInstance().getCnx().prepareStatement("SELECT * FROM partenaire  nom LIKE ? or email LIKE ? ");
            ps.setString(1, "%" + entry + "%");
            ps.setString(2, "%" + entry + "%");
           
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Partenaire rec = new Partenaire();
                rec.setId(rs.getInt("id"));
                rec.setNom(rs.getString("nom"));
                rec.setEmail(rs.getString("email"));
                
                myList.add(rec);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }
/*
    public void update(Partenaire r, String nom, String email) {
        try {
            String requete4 = "UPDATE partenaire SET nom=?, email=? WHERE id=?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete4);
            pst.setString(1, nom);
            pst.setString(2, email);
          
            pst.executeUpdate();
            System.out.println("Partenaire modifié !");
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
*/

    
    public void update(int id, Partenaire r) {
        try {
            String requete4 = "UPDATE partenaire SET nom=?,email=? WHERE id=?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete4);
            pst.setString(1, r.getNom());
            pst.setString(2, r.getEmail());
            pst.setInt(3, id);
           
            pst.executeUpdate();
            System.out.println("Partenaire modifié !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

       @Override
    public ArrayList<Partenaire> display() {
        ArrayList<Partenaire> myList = new ArrayList<>();
            try {
        String requete = "SELECT * FROM partenaire" ;
        Statement st;
        
            st = MyConnection.getInstance().getCnx().createStatement();
        
        ResultSet rs = st.executeQuery(requete);
        while (rs.next())
        {
            Partenaire p = new Partenaire();
            p.setId(rs.getInt(1));
            p.setNom(rs.getString("nom"));
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

