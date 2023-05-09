/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connexion3a41.services;


import edu.connexion3a41.entities.Rdv;
import edu.connexion3a41.interfaces.EntityCRUD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import edu.connexion3a41.tools.MyDB;
import java.sql.Date;
import java.time.LocalDate;
/**
 *
 * @author thebejaoui
 */
public class RdvCRUD implements  EntityCRUD<Rdv> {
    Connection connexion;
    Statement stm;

    public RdvCRUD() {
                connexion = MyDB.getInstance().getConnexion();
    }

    @Override
    public void addEntity(Rdv t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Rdv> display() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void ajouter(Rdv t) throws SQLException {
         String req = "INSERT INTO `rdv` (`idrdv`,`nompatient`,`date`, `numtel` , `message` , `adr` , `medecin_id` ) "
                + "VALUES ( ?, ?, ?, ?, ?, ?, ?) ";
        PreparedStatement ps = connexion.prepareStatement(req);
        ps.setFloat(1,t.getIdrdv());
        ps.setString(2, t.getNompatient());
        ps.setDate(3, t.getDate());
        ps.setString(4, t.getNumtel());
        ps.setString(5, t.getMessage());
        ps.setString(6, t.getAdr());
        ps.setFloat(7, t.getMedecin_id());
       
        
        ps.executeUpdate();
      
    }

    @Override
    public void supprimer(Rdv t) throws SQLException {
       String requete = "DELETE FROM rdv WHERE idrdv=?";
            PreparedStatement pst = connexion.prepareStatement(requete);
            pst.setInt(1, t.getIdrdv());
             //System.out.println(t.getIda()+"/"+t.getIdu()+"/"+t.getPrix()+"/"+t.getEmplacement()+"/"+t.getDescription()+"/"+t.getDate()+"/"+t.getType()+"/"+t.getAvis());

            pst.executeUpdate();
            System.out.println("rdv supprimée !");
    }

    @Override
    public void modifier(Rdv t) throws SQLException {
          String requete = "UPDATE rdv SET nompatient=?,date=? , numtel=? , message=? ,adr=?,medecin_id=? WHERE idrdv=?";
            PreparedStatement pst = connexion.prepareStatement(requete);
           
            pst.setString(1, t.getNompatient());
            pst.setDate(2, t.getDate());
            pst.setString(3, t.getNumtel());
            pst.setString(4, t.getMessage());
            pst.setString(5, t.getAdr());
          
            pst.setInt(6, t.getMedecin_id());
               pst.setInt(7, t.getIdrdv());
           
            
            pst.executeUpdate();
            System.out.println("rdv modifiée !");
    }

    @Override
    public List<Rdv> afficher() throws SQLException {
         List<Rdv> rdv = new ArrayList<>();
        String req = "select * from rdv";
        stm = connexion.createStatement();
        //ensemble de resultat
        ResultSet rst = stm.executeQuery(req);
            
        while (rst.next()) {
            
            Rdv a = new Rdv(
                   
                    rst.getString("nompatient"),
                  
                    rst.getDate("date"),
                     rst.getString("numtel"),
                    rst.getString("message"),
                    rst.getString("adr"),
                      rst.getInt("medecin_id")
                 
                 );
            rst.getInt("idrdv");
            rdv.add(a);
        }
        return rdv;
    }
       public ObservableList<Rdv> getRdvList()throws SQLException{
         
                   ObservableList<Rdv> rdv = FXCollections.observableArrayList();

       
            String requete = "SELECT * FROM rdv";
            PreparedStatement pst = connexion.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
           while (rs.next()) {
                rdv.add(new Rdv(rs.getInt("idrdv"),rs.getString("nompatient"), rs.getDate("date"),rs.getString("numtel"),rs.getString("message"),rs.getString("adr"),rs.getInt("medecin_id")));
            }

        
        for(Rdv A : rdv)
            System.out.println(A.getIdrdv()+"/"+A.getNompatient()+"/"+A.getDate()+"/"+A.getNumtel()+"/"+A.getMessage()+"/"+A.getAdr()+"/"+A.getMedecin_id()+"/");


        return rdv; 
        } 
       
    public ObservableList<Rdv> triedate() throws SQLException {

       
    
        ObservableList<Rdv> rdv = FXCollections.observableArrayList();

       
            String requete = " SELECT * FROM rdv  order by nompatient asc ";
            PreparedStatement pst = connexion.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
           while (rs.next()) {
                rdv.add(new Rdv(rs.getInt(1),rs.getString(2), rs.getDate(3), rs.getString(4),rs.getString(5),rs.getString(6),rs.getInt(6)));
            }

        
        for(Rdv A : rdv)
              System.out.println(A.getIdrdv()+"/"+A.getNompatient()+"/"+A.getDate()+"/"+A.getNumtel()+"/"+A.getMessage()+"/"+A.getAdr()+"/"+A.getMedecin_id()+"/");


        return rdv; 
    }
      private boolean isValidDate(LocalDate date) {
    return date != null && date.isBefore(LocalDate.now().plusDays(1));
}
      
     

    
  
    
    
}
