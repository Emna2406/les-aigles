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
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author aouadh
 */
public class SmsController implements Initializable {

    @FXML
    private TextField too;
    @FXML
    private TextArea msgg;
    @FXML
    private Button sendd;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void send(MouseEvent event) {
        Maingui ap = new Maingui ();
        ap.sms("hamouchmed ", "med123@A", too.getText(), msgg.getText());
    }
    
}
