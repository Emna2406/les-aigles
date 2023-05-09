/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connexion3a41.gui;

import edu.connexion3a41.entities.Rdv;
import edu.connexion3a41.entities.Visite;
import edu.connexion3a41.services.RdvCRUD;
import edu.connexion3a41.services.VisiteCRUD;
import edu.connexion3a41.tools.MyDB;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author tha3leb
 */
public class VisiteController implements Initializable {

    @FXML
    private TextField tidvisite;
    @FXML
    private TextField tnomvisiteur;
    @FXML
    private TextField tnompatient;
    @FXML
    private TextField tnumerotele;
    @FXML
    private TextField tmessage;
    @FXML
    private DatePicker tdate;
    @FXML
    private ComboBox<String> tpatient;
    @FXML
    private TableView<  Visite> tabvisite;
    @FXML
    private TableColumn<Visite, Integer> col_idvisite;
    @FXML
    private TableColumn<Visite, String> col_nomvisite;
    @FXML
    private TableColumn<Visite, String> col_nompatient;
    @FXML
    private TableColumn<Visite, Date> col_date;
    @FXML
    private TableColumn<Visite, String> col_numerotele;
    @FXML
    private TableColumn<Visite, String> col_message;
    @FXML
    private TableColumn<Visite, Integer> col_patient;
    @FXML
    private Button btnajout;
    @FXML
    private Button btnsupp;
    @FXML
    private Button btnmodif;
    @FXML
    private TextField trecherche;
    @FXML
    private Label labelgestionvisite;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
           try {
               FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), labelgestionvisite);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
         fadeTransition.setCycleCount(Animation.INDEFINITE);
        fadeTransition.play();
          
       VisiteCRUD sa = new    VisiteCRUD();
        col_idvisite.setCellValueFactory(new PropertyValueFactory<>("idvisite"));
          col_nomvisite.setCellValueFactory(new PropertyValueFactory<>("nomvisiteur"));
        col_nompatient.setCellValueFactory(new PropertyValueFactory<>("nompatient"));
            col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
            col_numerotele.setCellValueFactory(new PropertyValueFactory<>("numerotele"));
            col_message.setCellValueFactory(new PropertyValueFactory<>("message"));
           
            
                     col_patient.setCellValueFactory(new PropertyValueFactory<>("patient_id"));
           ObservableList<Visite> list = sa.getVisiteList();
             tabvisite.setItems(list);
             // partie affichage mte combobox yani kol mthel page yjiblk affichage fi tableau w fi combo
         } catch (SQLException ex) {
            Logger.getLogger(RdvCRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
            PreparedStatement pst = null;
        Connection cnx = MyDB.getInstance().getConnexion();
        ObservableList<String> liste_patient = FXCollections.observableArrayList();
        String req = "SELECT  nom from patient";
      
         
        
      
        try {
            pst = cnx.prepareStatement(req);
        } catch (SQLException ex) {
            Logger.getLogger(VisiteController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
        ResultSet rs = null;
       
        
       
        try {
            rs = pst.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(VisiteController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       
      
        try {
            while (rs.next()) {
                
                liste_patient .add(rs.getString("nom"));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(VisiteController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       
        
        tpatient.setItems(liste_patient );
        
          
    } 

    @FXML
    private void ajoutervisite(javafx.event.ActionEvent mouseEvent) throws SQLException {
         if (mouseEvent.getSource() == btnajout) {
          

           VisiteCRUD as = new   VisiteCRUD();
            as.ajouter(new Visite(tnomvisiteur.getText(),tnompatient.getText(),Date.valueOf(tdate.getValue()),tnumerotele.getText(),tmessage.getText(),tpatient.getSelectionModel().getSelectedIndex()));
           
       
          
        
   
           
     JOptionPane.showMessageDialog(null, " visite ajouter ");
             
 col_idvisite.setCellValueFactory(new PropertyValueFactory<>("idvisite"));
          col_nomvisite.setCellValueFactory(new PropertyValueFactory<>("nomvisiteur"));
        col_nompatient.setCellValueFactory(new PropertyValueFactory<>("nompatient"));
            col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
            col_numerotele.setCellValueFactory(new PropertyValueFactory<>("numerotele"));
            col_message.setCellValueFactory(new PropertyValueFactory<>("message"));
           
            
                     col_patient.setCellValueFactory(new PropertyValueFactory<>("patient_id"));
           ObservableList<Visite> list = as.getVisiteList();
             tabvisite.setItems(list);
          
            
            
tnomvisiteur.setText(null);
          tnompatient.setText(null);

            
            tdate.setValue(null);
            tnumerotele.setText(null);
            tmessage.setText(null);
   
                        tpatient.setPromptText(null);
            
           }
        
    }

    @FXML
    private void getSelected(MouseEvent event) {
     
         int index = tabvisite.getSelectionModel().getSelectedIndex();
            if (index <= -1) {

                return;
            }
         
            tidvisite.setText(col_idvisite.getCellData(index).toString());
          
           tnomvisiteur.setText(col_nomvisite.getCellData(index).toString());
          tnompatient.setText(col_nompatient.getCellData(index).toString());
          tnumerotele.setText(col_numerotele.getCellData(index).toString());
          tmessage.setText(col_message.getCellData(index).toString());
       
          tpatient.setPromptText(col_patient.getCellData(index).toString());
           tdate.setPromptText(col_date.getCellData(index).toLocalDate().toString());
      
    }

       @FXML
    private void supprimer_visite(javafx.event.ActionEvent mouseEvent) throws SQLException {
         if (mouseEvent.getSource() == btnsupp) {
            
Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
alert.setTitle("Confirmation de supprimer");
alert.setHeaderText("Voulez-vous vraiment effacer ?");
alert.setContentText("Cliquez sur Oui ou Non.");

ButtonType buttonTypeYes = new ButtonType("Oui");
ButtonType buttonTypeNo = new ButtonType("Non");

alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

// Attendre la réponse de l'utilisateur
Optional<ButtonType> result = alert.showAndWait();
if (result.get() == buttonTypeYes){
    VisiteCRUD as = new  VisiteCRUD();
             as.supprimer(new Visite(Integer.parseInt(tidvisite.getText())));
            JOptionPane.showMessageDialog(null, "Visite Supprimée");
           
          col_idvisite.setCellValueFactory(new PropertyValueFactory<>("idvisite"));
          col_nomvisite.setCellValueFactory(new PropertyValueFactory<>("nomvisiteur"));
        col_nompatient.setCellValueFactory(new PropertyValueFactory<>("nompatient"));
            col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
            col_numerotele.setCellValueFactory(new PropertyValueFactory<>("numerotele"));
            col_message.setCellValueFactory(new PropertyValueFactory<>("message"));
           
            
                     col_patient.setCellValueFactory(new PropertyValueFactory<>("patient_id"));
           ObservableList<Visite> list = as.getVisiteList();
             tabvisite.setItems(list);
          
            
            tnomvisiteur.setText(null);

          tnompatient.setText(null);

            
            tdate.setValue(null);
            tnumerotele.setText(null);
            tmessage.setText(null);
     
                        tpatient.setPromptText(null);
} else {
    col_idvisite.setCellValueFactory(new PropertyValueFactory<>("idvisite"));
          col_nomvisite.setCellValueFactory(new PropertyValueFactory<>("nomvisiteur"));
        col_nompatient.setCellValueFactory(new PropertyValueFactory<>("nompatient"));
            col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
            col_numerotele.setCellValueFactory(new PropertyValueFactory<>("numerotele"));
            col_message.setCellValueFactory(new PropertyValueFactory<>("message"));
           
            
                     col_patient.setCellValueFactory(new PropertyValueFactory<>("patient_id"));
       
            
}  } }

    @FXML
    private void modifier(javafx.event.ActionEvent mouseEvent) throws SQLException {
       
   
         if (mouseEvent.getSource() == btnmodif) {
          

            VisiteCRUD as = new  VisiteCRUD();
            as.modifier(new Visite(Integer.parseInt(tidvisite.getText()),tnomvisiteur.getText(),tnompatient.getText(),Date.valueOf(tdate.getValue()),tnumerotele.getText(),tmessage.getText(),tpatient.getSelectionModel().getSelectedIndex()));
           
        System.out.println("jawek behy tadiiit ?");
          
        
   
           
     JOptionPane.showMessageDialog(null, " Visite modifier ");
             

            col_idvisite.setCellValueFactory(new PropertyValueFactory<>("idvisite"));
          col_nomvisite.setCellValueFactory(new PropertyValueFactory<>("nomvisiteur"));
        col_nompatient.setCellValueFactory(new PropertyValueFactory<>("nompatient"));
            col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
            col_numerotele.setCellValueFactory(new PropertyValueFactory<>("numerotele"));
            col_message.setCellValueFactory(new PropertyValueFactory<>("message"));
           
            
                     col_patient.setCellValueFactory(new PropertyValueFactory<>("patient_id"));
           ObservableList<Visite> list = as.getVisiteList();
             tabvisite.setItems(list);
          
            
            
 tnomvisiteur.setText(null);

          tnompatient.setText(null);

            
            tdate.setValue(null);
            tnumerotele.setText(null);
            tmessage.setText(null);
     
                        tpatient.setPromptText(null);
            
           }
    
    }

     @FXML
    private void recherche() throws SQLException {
         VisiteCRUD sa = new  VisiteCRUD();
     
      col_idvisite.setCellValueFactory(new PropertyValueFactory<>("idvisite"));
          col_nomvisite.setCellValueFactory(new PropertyValueFactory<>("nomvisiteur"));
        col_nompatient.setCellValueFactory(new PropertyValueFactory<>("nompatient"));
            col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
            col_numerotele.setCellValueFactory(new PropertyValueFactory<>("numerotele"));
            col_message.setCellValueFactory(new PropertyValueFactory<>("message"));
           
            
                     col_patient.setCellValueFactory(new PropertyValueFactory<>("patient_id"));
           ObservableList<Visite> list = sa.getVisiteList();
             tabvisite.setItems(list);
      

        // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Visite> filteredData = new FilteredList<>(list, b -> true);
        // 2. Set the filter Predicate whenever the filter changes.
        trecherche.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate((Visite visite) -> {
                // If filter text is empty, display all persons.

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                // recherche par nom viisteuuur

                if (String.valueOf(visite.getNomvisiteur()).indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches libelle
                } 
               
                else {
                    return false; // Does not match.
                }
            });
        });
        // 3. Wrap the FilteredList in a SortedList. 
        SortedList<Visite> sortedData = new SortedList<>(filteredData);
        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(tabvisite.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        tabvisite.setItems(sortedData);
    }
    
    
    
    
    

    
    
    
    
}
