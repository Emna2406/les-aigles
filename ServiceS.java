/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connexion3a41.services;

import edu.connexion3a41.entities.Service;
import edu.connexion3a41.interfaces.EntityCRUD;
import edu.connexion3a41.tools.MyConnection;
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
 * @author YOSR
 */
public class ServiceS implements  EntityCRUD <Service> {
    
        Connection connexion;
    Statement stm;
public ServiceS()
{
    connexion = MyConnection.getInstance().getCnx();
}
 @Override
  
    public void addEntity( Service s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Service> display() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
   @Override
    public void ajouter( Service s) throws SQLException {
         String req = "INSERT INTO `service` (`nom`,`description`,`image`) "
                + "VALUES ( ?, ?, ?) ";
        PreparedStatement ps = connexion.prepareStatement(req);
        ps.setString(1,s.getNom());
        ps.setString(2, s.getDescription());
        ps.setString(3,s.getImage());
      
       
     
        
        ps.executeUpdate();
    }
     @Override
     public void supprimer(Service s) throws SQLException {
        String requete = "DELETE FROM service WHERE id =?";
            PreparedStatement pst = connexion.prepareStatement(requete);
            pst.setInt(1, s.getId());
             //System.out.println(t.getIda()+"/"+t.getIdu()+"/"+t.getPrix()+"/"+t.getEmplacement()+"/"+t.getDescription()+"/"+t.getDate()+"/"+t.getType()+"/"+t.getAvis());

            pst.executeUpdate();
            System.out.println("reglement supprimée !");
    }
 @Override
   
    public void modifier(Service s) throws SQLException {
        String requete = "UPDATE service SET  nom=?,description=?,image=?  WHERE id=?";
            PreparedStatement pst = connexion.prepareStatement(requete);
            
            pst.setInt(4, s.getId());
            pst.setString(1, s.getNom());
            pst.setString(2, s.getDescription());
            pst.setString(3, s.getImage());
       
           
            
            pst.executeUpdate();
            System.out.println("service modifiée !");
    }
    
    


    @Override
    public List<Service> afficher() throws SQLException {
             List<Service> service = new ArrayList<>();
        String req = "select * from service ";
        stm = connexion.createStatement();
        //ensemble de resultat
        ResultSet rst = stm.executeQuery(req);
            
        while (rst.next()) {
            
            Service a = new Service( rst.getInt("id"),
                    rst.getString("nom"),
                    rst.getString("description"),
                    rst.getString("image"));
            rst.getInt("id");
            service.add(a);
        }
        return service;
    }
           public ObservableList<Service> getServiceList()throws SQLException{
         
                   ObservableList<Service> service = FXCollections.observableArrayList();

       
            String requete = "SELECT * FROM service";
            PreparedStatement pst = connexion.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
           while (rs.next()) {
                service.add(new Service(rs.getInt(1), rs.getString("nom"),rs.getString("description"),rs.getString("image")));
            }

        
        for(Service A : service)
            System.out.println(A.getId()+"/"+A.getNom()+"/"+A.getDescription()+"/"+A.getImage());


        return service; 
        }
            public Service getServiceByNom(String nomService) throws SQLException {
    Connection con = MyConnection.getInstance().getCnx();
    String query = "SELECT * FROM Service WHERE nom = ?";
    PreparedStatement ps = con.prepareStatement(query);
    ps.setString(1, nomService);
    ResultSet rs = ps.executeQuery();
    if (rs.next()) {
        Service Service = new Service(rs.getInt("id"), rs.getString("nom"), rs.getString("description"), rs.getString("image"));
        return Service;
    } else {
        return null;
    }
}


  
}
