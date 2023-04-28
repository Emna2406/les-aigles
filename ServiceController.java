/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connexion3a41.gui;

import static com.itextpdf.text.pdf.XfaXpathConstructor.XdpPackage.Pdf;
import edu.connexion3a41.entities.Service;
import edu.connexion3a41.entities.pdf;
import edu.connexion3a41.services.ServiceS;

import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.SQLException;
import static java.util.Collections.list;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterAttributes;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javax.swing.JOptionPane;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author YOSR
 */
public class ServiceController implements Initializable {

    @FXML
    private TextField nom;
    @FXML
    private TextField description;
    @FXML
    private TextField image;
    @FXML
    private TableColumn<Service, String> col_nom;
    @FXML
    private TableColumn<Service, String> col_descri;
    @FXML
    private TableColumn<Service, String> col_image;
    @FXML
    private TableView<Service> tab_service;
    @FXML
    private Button ajoutser;
    @FXML
    private Button modifser;
    @FXML
    private Button suppser;
    @FXML
    private TableColumn<Service, Integer> col_id;
    @FXML
    private RadioButton radiomodif;
    @FXML
    private TextField id;
      private Integer index = -1;
    @FXML
    private Label errornom;
    @FXML
    private Label errordes;
    @FXML
    private RadioButton radioconsult;
    @FXML
    private RadioButton radiosupp;
    @FXML
    private Label labser;
    @FXML
    private RadioButton radioajout;
    @FXML
    private Button annulerser;
    @FXML
    private Button btngotochambre;
    @FXML
    private TextField rechact;
    @FXML
    private ComboBox<String> ExporterListe;


      @FXML
    private void ajoutservice(javafx.event.ActionEvent mouseEvent) throws SQLException{
      
    if (mouseEvent.getSource() == ajoutser) {
                    
        ServiceS as = new ServiceS();
       Service ServiceExistant = as.getServiceByNom(nom.getText());
        if ( ServiceExistant != null)
        {
             JOptionPane.showMessageDialog(null, "Un service a le meme nom existant");
        }
         // Ouvrir une fenêtre de sélection de fichier
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        File file = fileChooser.showOpenDialog(null);
        String imagePath = file.getAbsolutePath();
        
        // Ajouter le nouveau service avec le chemin de l'image
        as.ajouter(new Service(nom.getText(), description.getText(), imagePath));
        JOptionPane.showMessageDialog(null, "Service Ajouté");
         
        // Mettre à jour la liste de services affichée
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        col_descri.setCellValueFactory(new PropertyValueFactory<>("description"));
        col_image.setCellValueFactory(new PropertyValueFactory<>("image"));
      
        ObservableList<Service> list = as.getServiceList();
        tab_service.setItems(list);
        nom.setText(null);
        description.setText(null);
          
    }    
}

    
    
@FXML
    private void modifcheck(ActionEvent event) {
           
   
        nom.setEditable(true);
        description.setEditable(true);
        image.setEditable(true);
        
        annulerser.setDisable(false);
        ajoutser.setDisable(true);
        modifser.setDisable(false);
        suppser.setDisable(true);
      
         nom.setText(null);
        description.setText(null);
            image.setText(null);
     
    }




@FXML
private void supprimerservice(javafx.event.ActionEvent mouseEvent) throws SQLException {
    if (mouseEvent.getSource() == suppser) {
        ServiceS serviceS = new ServiceS();
        Service selectedService = tab_service.getSelectionModel().getSelectedItem();
        if (selectedService != null) {
            Alert alert = new Alert(AlertType.CONFIRMATION, "Êtes-vous sûr de vouloir supprimer ce service ?");
            alert.setHeaderText(null);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                serviceS.supprimer(new Service(selectedService.getId(), "", "", ""));
                JOptionPane.showMessageDialog(null, "Service supprimé !");
                ObservableList<Service> list = serviceS.getServiceList();
                tab_service.setItems(list);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Veuillez sélectionner un service à supprimer !");
        }
    }
}


      @FXML
    private void modifierservice( javafx.event.ActionEvent mouseEvent) throws SQLException {
         if (mouseEvent.getSource() == modifser) {
Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Voulez-vous vraiment modifier ce service ?");
        alert.setContentText("Cliquez sur OK pour confirmer.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            ServiceS as = new ServiceS();
            as.modifier(new Service( Integer.parseInt(id.getText()), nom.getText(),description.getText(),image.getText()));
            JOptionPane.showMessageDialog(null, "activite Modifiée");
            
            col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
           col_nom.setCellValueFactory(new PropertyValueFactory<>("nom "));
            col_descri.setCellValueFactory(new PropertyValueFactory<>("description"));
            col_image.setCellValueFactory(new PropertyValueFactory<>("image"));
            
            ObservableList<Service> list = as.getServiceList();
            tab_service.setItems(list);
            nom.setText(null);
            description.setText(null);
            image.setText(null);
      
        }
        }
    }

    
   
   
    @Override
public void initialize(URL url, ResourceBundle rb) {
    try {
        ServiceS sa = new ServiceS();
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        col_descri.setCellValueFactory(new PropertyValueFactory<>("description"));
        col_image.setCellValueFactory(new PropertyValueFactory<>("image"));

        // Ajouter la cell factory pour la colonne d'image
       col_image.setCellFactory(column -> {
    return new TableCell<Service, String>() {
        final ImageView imageView = new ImageView();

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (item == null || empty) {
                imageView.setImage(null);
                setGraphic(null);
            } else {
                // charger l'image et l'afficher dans l'ImageView
                Image image = new Image(new File(item).toURI().toString());
                imageView.setImage(image);
                imageView.setFitWidth(200); // ajuster la largeur
                imageView.setFitHeight(100); // ajuster la hauteur
                setGraphic(imageView);
                    }
                }
            };
        });

        ObservableList<Service> list = sa.getServiceList();
        tab_service.setItems(list);
    } catch (SQLException ex) {
        Logger.getLogger(ServiceController.class.getName()).log(Level.SEVERE, null, ex);
    }
     ObservableList<String> list4 = FXCollections.observableArrayList("PDF", "Excel", "Imprimer");
        ExporterListe.setItems(list4);
        
        tab_service.setStyle("-fx-background-color: red;");
}



 /*   @FXML
    private void getSelected(MouseEvent event) {
           int index = tab_service.getSelectionModel().getSelectedIndex();
            if (index <= -1) {

                return;
            }
         // idprop.setText(col_idprop.getCellData(index).toString());
            id.setText(col_id.getCellData(index).toString());
          // comidpromp.promptTextProperty(col_idprop.getCellData.toString());
          nom.setText(col_nom.getCellData(index).toString());
          description.setText(col_descri.getCellData(index).toString());
          image.setText(col_image.getCellData(index).toString());
    }*/

    @FXML
    private void yosr(javafx.scene.input.MouseEvent event) {
         int index = tab_service.getSelectionModel().getSelectedIndex();
            if (index <= -1) {

                return;
            }
         // idprop.setText(col_idprop.getCellData(index).toString());
            id.setText(col_id.getCellData(index).toString());
          // comidpromp.promptTextProperty(col_idprop.getCellData.toString());
          nom.setText(col_nom.getCellData(index).toString());
          description.setText(col_descri.getCellData(index).toString());
          image.setText(col_image.getCellData(index).toString());
    }


    

    @FXML
    private void nomvalid(KeyEvent event) {
         if ((nom.getText().matches("[0-9]")) || (nom.getText().length() == 0)) {
            nom.setStyle("-fx-border-color:red ; -fx-border-width:2px;");
           new animatefx.animation.Shake(nom).play();
            errornom.setText("Le nom est un chaine  ");

        } else if ((nom.getText().matches("[A-Z,a-z]"))) {
            nom.setStyle(null);
            errornom.setText(null);
        }
    }

    @FXML
    private void desvalid(KeyEvent event) {
         if (description.getText().length() < 7 || description.getText().matches("[0-9]+")) {
        description.setStyle("-fx-border-color:red ; -fx-border-width:2px;");
         new animatefx.animation.Shake(description).play();
        errordes.setText("La description doit contenir au moins 7 caractères.");

    } else if (description.getText().matches("[A-Za-z]+")) {
        description.setStyle(null);
        errordes.setText(null);
    }
    }

    @FXML
    private void consultcheck(ActionEvent event) {
       labser.setText("Liste des  Services ");
        labser.setLayoutX(120);
        id.setEditable(false);
        //idprop.setEditable(false);
        nom.setEditable(false);
        description.setEditable(false);
        image.setEditable(false);
      
        ajoutser.setDisable(true);
        modifser.setDisable(true);
        suppser.setDisable(true);
        annulerser.setDisable(true);
        radiosupp.setSelected(false);
        radioajout.setSelected(false);
        radiomodif.setSelected(false);
         id.setText(null);
           
            nom.setText(null);
            description.setText(null);
            image.setText(null);
            
    }

    @FXML
    private void suppcheck(ActionEvent event) {
          labser.setText("Supprimer ");
        labser.setLayoutX(120);
        id.setEditable(true);
        nom.setEditable(false);
        description.setEditable(false);
        image.setEditable(false);
       
        ajoutser.setDisable(true);
        modifser.setDisable(true);
        suppser.setDisable(false);
        
        
        annulerser.setDisable(true);
        radioajout.setSelected(false);
        radiosupp.setSelected(true);
        radioconsult.setSelected(false);
        radiomodif.setSelected(false);
         id.setText(null);
 
           
            nom.setText(null);
            description.setText(null);
            image.setText(null);
            

        
    }

    @FXML
    private void ajoutcheck(ActionEvent event) {
         labser.setText("Ajouter ");
        labser.setLayoutX(120);
        id.setEditable(true);
        nom.setEditable(false);
        description.setEditable(false);
        image.setEditable(false);
       
       
        
         radioajout.setSelected(false);
        annulerser.setDisable(true);
        radiosupp.setSelected(false);
       
        radioconsult.setSelected(false);
        radiomodif.setSelected(false);
        
        annulerser.setDisable(false);
        ajoutser.setDisable(false);
        modifser.setDisable(true);
        suppser.setDisable(true);
        
         id.setText(null);
            nom.setText(null);
            description.setText(null);
            image.setText(null);
            
    }

    @FXML
    private void annulerser(ActionEvent event) {
         id.setText(null);
            nom.setText(null);
            description.setText(null);
            image.setText(null);
        
    }

    @FXML
    private void gotochambre(ActionEvent event) throws IOException {
      
        
       
                Parent root = FXMLLoader.load(getClass().getResource("reservation.fxml"));
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("clinique");
                stage.show();

            
    
    }

    @FXML
    private void rechercheractivite()  throws IOException, SQLException {
        ServiceS sa = new ServiceS();
      col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
          col_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
            col_descri.setCellValueFactory(new PropertyValueFactory<>("description"));
            col_image.setCellValueFactory(new PropertyValueFactory<>("image"));
          
          
       
            ObservableList<Service> list = sa.getServiceList();
            tab_service.setItems(list);
        //ObservableList<Article> list = FXCollections.observableArrayList();

        // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Service> filteredData = new FilteredList<>(list, b -> true);
        // 2. Set the filter Predicate whenever the filter changes.
        rechact.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate((Service reglement) -> {
                // If filter text is empty, display all persons.

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(reglement.getNom()).indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches libelle
                } 
               
                else {
                    return false; // Does not match.
                }
            });
        });
        // 3. Wrap the FilteredList in a SortedList. 
        SortedList<Service> sortedData = new SortedList<>(filteredData);
        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(tab_service.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        tab_service.setItems(sortedData);
        
    }
   
    }
    

   

    
    
  



