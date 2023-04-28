/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connexion3a41.gui;

import edu.connexion3a41.tools.MyConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.util.Duration;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author YOSR
 */
public class StatController implements Initializable {

    @FXML
    private BarChart<String, Integer> stat;
    @FXML
    private Label label;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
   
          FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), label);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
         fadeTransition.setCycleCount(Animation.INDEFINITE);
        fadeTransition.play();
       
               Connection cnx = MyConnection.getInstance().getCnx();
//SELECT * FROM activite group by libele order by id_prop
       XYChart.Series<String ,Integer> series1 =new XYChart.Series<>();
     //  String requete = "SELECT * FROM chambre order by prix";
    String requete = "SELECT c.num as num_chambre, COUNT(r.id) as nb_reservations FROM chambre c LEFT JOIN reservation r ON c.id = r.id_chambre GROUP BY c.num";


       try{
            PreparedStatement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
               
                   series1.getData().add(new XYChart.Data<>(rs.getString("num_chambre"), rs.getInt("nb_reservations")));
             
            }
            stat.getData().add(series1);
           // series1.setName("Crampons");
       } catch(Exception e) {
           
    }
      
    
    }    
    
}
