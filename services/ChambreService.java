/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connexion3a41.services;

import edu.connexion3a41.entities.Chambre;
import edu.connexion3a41.entities.Service;
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
import javax.activation.DataSource;



/**
 *
 * @author YOSR
 */
public class ChambreService  implements  EntityCRUD <Chambre> {
    
        Connection connexion;
    Statement stm;
public ChambreService()
{
    connexion = MyDB.getInstance().getConnexion();
}
 @Override
  
    public void addEntity( Chambre s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Chambre> display() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
   @Override
    public void ajouter( Chambre s) throws SQLException {
         String req = "INSERT INTO `chambre` (`id`  ,`num`,`capacite`,`prix` ,`etat`   ) "
                + "VALUES ( ? , ?, ?, ?, ?) ";
        PreparedStatement ps = connexion.prepareStatement(req);
         ps.setInt(1,s.getId());
       //  ps.setInt(2,s.getId_service());
        ps.setString(2,s.getNum());
        ps.setString(3, s.getCapacite());
        ps.setString(4,s.getPrix());
          ps.setString(5,s.getEtat());
         
      
       
     
        
        ps.executeUpdate();
    }
     @Override
     public void supprimer(Chambre s) throws SQLException {
        String requete = "DELETE FROM chambre WHERE id =?";
            PreparedStatement pst = connexion.prepareStatement(requete);
            pst.setInt(1, s.getId());
             //System.out.println(t.getIda()+"/"+t.getIdu()+"/"+t.getPrix()+"/"+t.getEmplacement()+"/"+t.getDescription()+"/"+t.getDate()+"/"+t.getType()+"/"+t.getAvis());

            pst.executeUpdate();
            System.out.println("reglement supprimée !");
    }
 @Override
   
    public void modifier(Chambre s) throws SQLException {
        String requete = "UPDATE chambre SET  num=?,capacite=?,prix=? ,etat=?  WHERE id=?";
            PreparedStatement pst = connexion.prepareStatement(requete);
            
            pst.setInt(5, s.getId());
             //pst.setInt(1, s.getId_service());
            pst.setString(1, s.getNum());
            pst.setString(2, s.getCapacite());
            pst.setString(3, s.getPrix());
             pst.setString(4, s.getEtat());
            
       
           
            
            pst.executeUpdate();
            System.out.println("service modifiée !");
    }
    
    


    @Override
    public List<Chambre> afficher() throws SQLException {
             List<Chambre> chambre = new ArrayList<>();
        String req = "select * from chambre ";
        stm = connexion.createStatement();
        //ensemble de resultat
        ResultSet rst = stm.executeQuery(req);
            
        while (rst.next()) {
            
            Chambre c  = new Chambre(
                     rst.getInt("id"),
                    //rst.getInt("service_id"),
                    rst.getString("num"),
                    rst.getString("capacite"),
                     rst.getString("prix"),
                    rst.getString("etat"));
             
            rst.getInt("id");
            chambre.add(c);
        }
        return chambre;
    }
 public ObservableList<Chambre> getChambreList()throws SQLException{
         
                   ObservableList<Chambre> activite = FXCollections.observableArrayList();

       
            String requete = "SELECT * FROM chambre";
            PreparedStatement pst = connexion.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
           while (rs.next()) {
                activite.add(new Chambre(rs.getInt("id"), rs.getString("num"),rs.getString("capacite"),rs.getString("prix"),rs.getString("etat")));
            }

        
        for(Chambre A : activite)
            System.out.println(A.getId()+"/"+A.getNum()+"/"+A.getCapacite()+"/"+A.getPrix()+"/"+A.getEtat()+"/");


        return activite; 
        } 
 public Chambre getChambreByNum(String numChambre) throws SQLException {
    Connection con = MyDB.getInstance().getConnexion();
    String query = "SELECT * FROM chambre WHERE num = ?";
    PreparedStatement ps = con.prepareStatement(query);
    ps.setString(1, numChambre);
    ResultSet rs = ps.executeQuery();
    if (rs.next()) {
        Chambre chambre = new Chambre(rs.getInt("id"), rs.getString("num"), rs.getString("capacite"), rs.getString("prix"), rs.getString("etat"));
        return chambre;
    } else {
        return null;
    }
}
 
 public Chambre getChambreById(int id) throws SQLException {
    Connection con = MyDB.getInstance().getConnexion();
    PreparedStatement ps = con.prepareStatement("SELECT * FROM chambre WHERE id = ?");
    ps.setInt(1, id);
    ResultSet rs = ps.executeQuery();
    Chambre chambre = null;
    if(rs.next()) {
        chambre = new Chambre(rs.getInt("id"), rs.getString("num"), rs.getString("capacite"), rs.getString("prix"), rs.getString("etat"));
    }
    ps.close();
    return chambre;
}

    



    
}
