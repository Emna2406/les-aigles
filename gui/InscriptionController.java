/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connexion3a41.gui;

import edu.connexion3a41.entities.Personne;
import edu.connexion3a41.services.PersonneCRUD;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author karra
 */
public class InscriptionController implements Initializable {

    @FXML
    private TextField tfNom;
    @FXML
    private TextField tfPrenom;
    @FXML
    private Button btnValider;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void savePerson(ActionEvent event) {
        //SAVE PERSON
        
        String resNom = tfNom.getText();
        String resPrenom = tfPrenom.getText();
        PersonneCRUD pcd =new PersonneCRUD();
        Personne t = new Personne(resNom, resPrenom);
        pcd.addEntity(t);
        
        //REDIRECTION
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("DetailsPerson.fxml"));
            Parent root = loader.load();
            DetailsPersonController controller = loader.getController();
            controller.settNom(resNom);
            controller.settPrenom(resPrenom);
            tfNom.getScene().setRoot(root);
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
