/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connexion3a41.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author karra
 */
public class DetailsPersonController implements Initializable {

    @FXML
    private TextField tNom;
    @FXML
    private TextField tPrenom;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void settNom(String message) {
        this.tNom.setText(message);
    }

    public void settPrenom(String message) {
        this.tPrenom.setText(message);
    }
    
    
    
}
