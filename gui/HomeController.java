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
 * @author aouadh
 */
public class HomeController implements Initializable {

    @FXML
    private Button btnconsu;
    @FXML
    private Button btnrdv;
    @FXML
    private Button btnservice;
    @FXML
    private Button btnservice1;
    @FXML
    private Button btnservice2;
    @FXML
    private Button offrebtn;
    @FXML
    private Button candidatbtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void gotoconsu(ActionEvent event)throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("Consultation.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Ordonnance");
        stage.show();
    }

    @FXML
    private void gotordv(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("rdv.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("rendez_vous");
        stage.show();
    }

    @FXML
    private void gotosrv(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Service.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("rendez_vous");
        stage.show();
    }

    @FXML
    private void gotoprd(ActionEvent event) throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("GestionProduit.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("rendez_vous");
        stage.show();
    }

    @FXML
    private void gotopart(ActionEvent event) throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("GestionPartenaire.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("rendez_vous");
        stage.show();
    }

    @FXML
    private void gotoorf(ActionEvent event) throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("GestionOffre.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("rendez_vous");
        stage.show();
    }

    @FXML
    private void gotocon(ActionEvent event) throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("GestionCandidat.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("rendez_vous");
        stage.show();
    }
    
}
