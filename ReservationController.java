/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connexion3a41.gui;

import edu.connexion3a41.entities.Reservation;
import edu.connexion3a41.services.ReservationService;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author YOSR
 */
public class ReservationController implements Initializable {

    @FXML
    private Label labelres;
    @FXML
    private TextField id;
    @FXML
    private TextField idc;
    @FXML
    private TextField idp;
    @FXML
    private TextField idm;
    @FXML
    private DatePicker datedeb;
   
    @FXML
    private Button ajoutres;
    @FXML
    private Button modres;
    @FXML
    private Button suppres;
    @FXML
    private Button annuler;
    @FXML
    private TableView<Reservation> tab_res;
    @FXML
    private TableColumn<Reservation, Integer> col_idc;
    @FXML
    private TableColumn<Reservation, Integer> col_ipd;
    @FXML
    private TableColumn<Reservation, Integer> col_idm;
    @FXML
    private TableColumn<Reservation, Integer> col_id;
    @FXML
    private TableColumn<Reservation, Date> col_datedeb;
   
    @FXML
    private RadioButton radioajout;
    @FXML
    private RadioButton radiomodr;
    @FXML
    private RadioButton radiosuppr;
    @FXML
    private RadioButton radioconsultr;
    @FXML
    private Button btngservice;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         try {
            ReservationService sa = new ReservationService();
            col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
           
             col_idc.setCellValueFactory(new PropertyValueFactory<>("id_chambre"));
                        col_ipd.setCellValueFactory(new PropertyValueFactory<>("id_patient"));
                         col_idm.setCellValueFactory(new PropertyValueFactory<>("id_medecin"));
                          col_datedeb.setCellValueFactory(new PropertyValueFactory<>("date_deb"));
                          
                           
                         

            
            ObservableList<Reservation> list = sa.getReservationList();
            tab_res.setItems(list);
        } catch (SQLException ex) {
           
        }
        // TODO
    }    

    @FXML
    private void ajoutereservation(javafx.event.ActionEvent mouseEvent)throws SQLException {
         if (mouseEvent.getSource() == ajoutres) {
      
           
            ReservationService as = new ReservationService();
     as.ajouter(new Reservation(  Integer.parseInt(idm.getText()), Integer.parseInt(idp.getText()),Integer.parseInt(idc.getText()),Date.valueOf(datedeb.getValue())));
            JOptionPane.showMessageDialog(null, " Reservation Ajoutée");
             

           
            
            col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
             // col_idser.setCellValueFactory(new PropertyValueFactory<>("service_id"));
            col_idc.setCellValueFactory(new PropertyValueFactory<>("id_chambre"));
          col_idm.setCellValueFactory(new PropertyValueFactory<>("id_medecin"));
            col_ipd.setCellValueFactory(new PropertyValueFactory<>("id_patient"));
            col_datedeb.setCellValueFactory(new PropertyValueFactory<>("date_deb"));
             
       
            ObservableList<Reservation> list = as.getReservationList();
            tab_res.setItems(list);
            
            

          id.setText(null);
         
           idc.setText(null);
            idm.setText(null);
            idp.setText(null);
            datedeb.setValue(null);
          
            
        
        
        
    }
    }

    @FXML
    private void modifiereservation(javafx.event.ActionEvent mouseEvent) throws SQLException{
         if (mouseEvent.getSource() == modres) {
Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Voulez-vous vraiment modifier cette reservation ?");
        alert.setContentText("Cliquez sur OK pour confirmer.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            ReservationService as = new ReservationService();
            as.modifier(new Reservation(Integer.parseInt(id.getText()),Integer.parseInt(idm.getText()),Integer.parseInt(idp.getText()),Integer.parseInt(idc.getText()),Date.valueOf(datedeb.getValue())));
            JOptionPane.showMessageDialog(null, "Reservation Modifiée");
            
            col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
             // col_idser.setCellValueFactory(new PropertyValueFactory<>("service_id"));
            col_idc.setCellValueFactory(new PropertyValueFactory<>("id_chambre"));
          col_idm.setCellValueFactory(new PropertyValueFactory<>("id_medecin"));
            col_ipd.setCellValueFactory(new PropertyValueFactory<>("id_patient"));
            col_datedeb.setCellValueFactory(new PropertyValueFactory<>("date_deb"));
             
            ObservableList<Reservation> list = as.getReservationList();
            tab_res.setItems(list);
                    
 id.setText(null);
           idc.setText(null);
            idm.setText(null);
            idp.setText(null);
    }
         }
    }

    @FXML
    private void supprimereservation(javafx.event.ActionEvent mouseEvent)throws SQLException {
         if (mouseEvent.getSource() == suppres) {
        ReservationService ReservationService = new ReservationService();
        Reservation selectedService = tab_res.getSelectionModel().getSelectedItem();
        if (selectedService != null) {
            Alert alert = new Alert(AlertType.CONFIRMATION, "Êtes-vous sûr de vouloir supprimer cette reservation ?");
            alert.setHeaderText(null);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                ReservationService.supprimer(new Reservation(selectedService.getId(), 0, 0, 0 , null));
                JOptionPane.showMessageDialog(null, "Reservation supprimé !");
                ObservableList<Reservation> list = ReservationService.getReservationList();
                tab_res.setItems(list);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Veuillez sélectionner une reservation à supprimer !");
        }
    }
    }

    @FXML
    private void annuleres(javafx.event.ActionEvent mouseEvent) {
        id.setText(null);
            
             
            idc.setText(null);
            idm.setText(null);
             idp.setText(null);
             datedeb.setValue(null);
           
    }

    @FXML
    private void ajoutrchheck(ActionEvent event) {
         labelres.setText(" Ajouter  ");
        labelres.setLayoutX(120);
        id.setEditable(true);
         
        datedeb.setEditable(false);
        idc.setEditable(false);
        idm.setEditable(false);
         idp.setEditable(false);
        
       
        
        ajoutres.setDisable(false);
        modres.setDisable(true);
        suppres.setDisable(true);
        annuler.setDisable(false);
        radioconsultr.setSelected(false);
          radiosuppr.setSelected(false);
        radiomodr.setSelected(false);
        radioajout.setSelected(true);
    id.setText(null);
           
            
            datedeb.setValue(null);
            idc.setText(null);
            idp.setText(null);
            idm.setText(null);
           
    }

    @FXML
    private void modifrchheck(ActionEvent event) {
         labelres.setText(" Modifier  ");
        labelres.setLayoutX(120);
        id.setEditable(true);
         
        datedeb.setEditable(false);
        idc.setEditable(false);
        idm.setEditable(false);
         idp.setEditable(false);
       
       
        
        ajoutres.setDisable(true);
        modres.setDisable(false);
        suppres.setDisable(true);
        annuler.setDisable(false);
        radioconsultr.setSelected(false);
          radiosuppr.setSelected(false);
        radiomodr.setSelected(true);
        radioajout.setSelected(false);
    id.setText(null);
           
            
            datedeb.setValue(null);
            idc.setText(null);
            idp.setText(null);
            idm.setText(null);
            
    }

    @FXML
    private void supprchheck(ActionEvent event) {
         labelres.setText(" Supprimer  ");
        labelres.setLayoutX(120);
        id.setEditable(true);
         
        datedeb.setEditable(false);
        idc.setEditable(false);
        idm.setEditable(false);
         idp.setEditable(false);
      
       
        
        ajoutres.setDisable(true);
        modres.setDisable(true);
        suppres.setDisable(false);
        annuler.setDisable(true);
        radioconsultr.setSelected(false);
       radiosuppr.setSelected(true);
        radiomodr.setSelected(false);
        radioajout.setSelected(false);
    id.setText(null);
           
            
            datedeb.setValue(null);
            idc.setText(null);
            idp.setText(null);
            idm.setText(null);
           
    }

    @FXML
    private void consulterchheck(ActionEvent event) {
               labelres.setText(" Liste Reservation  ");
        labelres.setLayoutX(120);
        id.setEditable(true);
         
        datedeb.setEditable(false);
        idc.setEditable(false);
        idm.setEditable(false);
         idp.setEditable(false);
      
       
        
        ajoutres.setDisable(true);
        modres.setDisable(true);
        suppres.setDisable(true);
        annuler.setDisable(true);
        radioconsultr.setSelected(false);
       radiosuppr.setSelected(true);
        radiomodr.setSelected(false);
        radioajout.setSelected(false);
    id.setText(null);
           
            
            datedeb.setValue(null);
            idc.setText(null);
            idp.setText(null);
            idm.setText(null);
    }

    @FXML
    private void getselected(MouseEvent event) {
        int index = tab_res.getSelectionModel().getSelectedIndex();
            if (index <= -1) {

                return;
            }
         // idprop.setText(col_idprop.getCellData(index).toString());
            id.setText(col_id.getCellData(index).toString());
          // comidpromp.promptTextProperty(col_idprop.getCellData.toString());
          idm.setText(col_idm.getCellData(index).toString());
          idc.setText(col_idc.getCellData(index).toString());
          idp.setText(col_ipd.getCellData(index).toString());
          datedeb.setValue(col_datedeb.getCellData(index).toLocalDate());
         
           
          //  idser.setText(col_idser.getCellData(index).toString());
    }

    @FXML
    private void gotoservice(ActionEvent event) throws IOException {
       
        
       
                Parent root = FXMLLoader.load(getClass().getResource("service.fxml"));
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("clinique");
                stage.show();

            
    
    }
    
}
