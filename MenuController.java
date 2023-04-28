/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connexion3a41.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author YOSR
 */
public class MenuController implements Initializable {

    @FXML
    private MenuButton consultationbtn;
    @FXML
    private MenuButton rdvbtn;
    @FXML
    private MenuButton candidatbtn;
    @FXML
    private MenuButton offrebtn;
    @FXML
    private MenuButton partenairebtn;
    @FXML
    private MenuButton produitbtn;
    @FXML
    private Button decbtn;
    @FXML
    private Button btnreservation;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void gestres(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("service.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("clinique");
        stage.show();
    }
    
}
