/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktop.interfaces.fr;

import desktop.entities.Offre;
import desktop.interfaces.Home;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author USER
 */


public class DetailOffreController implements Initializable {
 
    
    @FXML
    Label title;
    @FXML
    Label idService;
    @FXML
    Label nbrPlaces;
    @FXML
    Label descri;

    
    
    
 
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
        
       title.setText("Offre NO " + Home.of.getId());
       nbrPlaces.setText(Home.of.getNbrplacesS());
       idService.setText(Home.of.getIdserviceS());
       descri.setText(Home.of.getDescription());
       
       
       
           
   
    }
}
