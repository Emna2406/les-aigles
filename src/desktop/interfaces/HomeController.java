/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktop.interfaces;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class HomeController  {

    @FXML
    private MenuButton servicebtn;
    @FXML
    private MenuButton consultationbtn;
    @FXML
    private MenuButton rdvbtn;
    @FXML
    private Button candidatbtn;
    @FXML
    private MenuButton partenairebtn;
    @FXML
    private MenuButton produitbtn;
    @FXML
    private Button offrebtn;
    @FXML
    private Button decbtn;
    @FXML
    private Button langSelect;

    /*   @FXML
    EventHandler<ActionEvent> e = new EventHandler<ActionEvent>()
    {
            public void handle (ActionEvent e)
            {
                langSelect.setText(((MenuItem) e.getSource()).getText());
            }
     };*/
    
    @FXML  
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML
    private void redirectToAddOffre(ActionEvent event) {
        try {
            
            String path = "/desktop/interfaces/";
        	if(Home.lang == 1)
        		path += "eng/";
        	else
        		path += "fr/";
            Parent root = FXMLLoader.load(getClass().getResource(path + "GestionOffre.fxml"));
            //Parent root = FXMLLoader.load(getClass().getResource("GestionCandidat.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    @FXML
    private void redirectToAddCandidat(ActionEvent event) {
        try {
            
            String path = "/desktop/interfaces/";
        	if(Home.lang == 1)
        		path += "eng/";
        	else
        		path += "fr/";
            Parent root = FXMLLoader.load(getClass().getResource(path + "GestionCandidat.fxml"));
            //Parent root = FXMLLoader.load(getClass().getResource("GestionCandidat.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
    
    public void switchLang()
    {
    	if(Home.lang == 1)
    	{
    		Home.lang = 2;
    		langSelect.setText("Français");
    	}
    	else
    	{
    		Home.lang = 1;
    		langSelect.setText("English");
                
    	}
    	
    }
}
