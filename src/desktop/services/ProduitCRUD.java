package desktop.services;

import desktop.entities.Partenaire;
import desktop.entities.Produit;
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
 * Service class for CRUD operations on Produit entity.
 * Implements EntityCRUD interface.
 */
public class ProduitCRUD implements EntityCRUD<Produit> {

    /**
     * Adds a Produit entity to the database.
     *
     * @param p The Produit entity to be added.
     */
    @Override
    public void AddEntity(Produit p) {
        try {
            String requete1 = "INSERT INTO produit (id,nom,stock) VALUES(?,?,?)";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete1);
            pst.setInt(1, p.getId());
            pst.setString(2, p.getNom());
            pst.setInt(3, p.getStock());
            pst.executeUpdate();
            System.out.println("Bravo produit ajouté !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Retrieves a list of all Produit entities from the database.
     *
     * @return The list of Produit entities.
     */
    public ObservableList<Produit> listerProduits() {
        ObservableList<Produit> myList = FXCollections.observableArrayList();
        try {
            String requete2 = "SELECT * FROM produit";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete2);
            while (rs.next()) {
                Produit rec = new Produit();
                rec.setId(rs.getInt("id"));
                rec.setNom(rs.getString("nom"));
                rec.setStock(rs.getInt("stock"));
                myList.add(rec);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    /**
     * Deletes a Produit entity from the database.
     *
     * @param p The Produit entity to be deleted.
     */
    public void delete(Produit p) {
        try {
            String requete3 = "DELETE FROM produit WHERE id=?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete3);
            pst.setInt(1, p.getId());
            pst.executeUpdate();
            System.out.println("Produit supprimé !");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * Searches for Produit entities in the database based on their nom.
     *
     * @param nom The nom to search for.
     * @return The list of matching Produit entities.
     */

    
    
    public void update(int id, Produit r) {
        try {
            String requete4 = "UPDATE produit SET nom=?,stock=? WHERE id=?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete4);
            pst.setString(1, r.getNom());
            pst.setInt(2, r.getStock());
            pst.setInt(3, id);
           
            pst.executeUpdate();
            System.out.println("Produit modifié !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public ArrayList<Produit> display() {
        ArrayList<Produit> myList = new ArrayList<>();
            try {
        String requete = "SELECT * FROM produit" ;
        Statement st;
        
            st = MyConnection.getInstance().getCnx().createStatement();
        
        ResultSet rs = st.executeQuery(requete);
        while (rs.next())
        {
            Produit p = new Produit();
            p.setId(rs.getInt(1));
            p.setNom(rs.getString("nom"));
               p.setStock(rs.getInt("stock"));
            myList.add(p);
            
            
        }
            }
        
       
    
        catch (SQLException ex) {
            System.out.println(ex.getMessage());    
        }
    
return myList;
}
}

  