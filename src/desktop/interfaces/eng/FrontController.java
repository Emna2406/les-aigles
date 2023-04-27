/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktop.interfaces.eng;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author msi
 */
public class FrontController  {

    @FXML
    private Button CandCheck;
    @FXML
    private Button PartCheck;

    @FXML
    private void redirectToCheckPartenaire(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("CheckPartenaire.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage()+ ex.getStackTrace());
        }
    }
    
    @FXML
    private void redirectToCheckCandidatures(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("GestionCandidat.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage()+ ex.getStackTrace());
        }
    }
    
}
