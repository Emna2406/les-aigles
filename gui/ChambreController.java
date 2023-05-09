/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connexion3a41.gui;

import edu.connexion3a41.entities.Chambre;
import edu.connexion3a41.entities.Service;
import edu.connexion3a41.services.ChambreService;
import edu.connexion3a41.services.ServiceS;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import javax.swing.JOptionPane;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author YOSR
 */
public class ChambreController implements Initializable {

    @FXML
    private TextField num;
    @FXML
    private TextField cap;
    @FXML
    private TextField prix;
    private TextField etat;
    @FXML
    private TableView<Chambre> tab_ch;
    @FXML
    private TableColumn<Chambre, Integer> col_id;
    @FXML
    private TableColumn<Chambre, String> col_num;
    @FXML
    private TableColumn<Chambre, String> col_cap;
    @FXML
    private TableColumn<Chambre, String> col_prix;
    @FXML
    private TableColumn<Chambre, String> col_etat;
    @FXML
    private Button ajoutch;
    @FXML
    private Button suppch;
    @FXML
    private TextField id;
    @FXML
    private Button modch;
    @FXML
    private Label errornum;
    @FXML
    private Button annulerch;
    @FXML
    private Label errorcap;
    @FXML
    private Label errorprix;
    @FXML
    private Label erroretat;
    @FXML
    private Label labelch;
    @FXML
    private RadioButton radioajoutc;
    @FXML
    private RadioButton radiomodif;
    @FXML
    private RadioButton radiosuppc;
    @FXML
    private RadioButton radiocconsulter;
    @FXML
    private RadioButton disponibleRadio;
    @FXML
    private RadioButton nonDispoRadio;
   // private ToggleGroup etatGroup;
    @FXML
    private TextField recham;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          try {
            System.out.println("helllloa") ;
        ChambreService sa = new  ChambreService();
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
       // col_idser.setCellValueFactory(new PropertyValueFactory<>("service_id"));
            col_num.setCellValueFactory(new PropertyValueFactory<>("num"));
            col_cap.setCellValueFactory(new PropertyValueFactory<>("capacite"));
            col_prix.setCellValueFactory(new PropertyValueFactory<>("prix"));
            col_etat.setCellValueFactory(new PropertyValueFactory<>("etat"));
           
                    
           ObservableList<Chambre> list = sa.getChambreList();
             tab_ch.setItems(list);
         } catch (SQLException ex) {
          
        }
        /*  etatGroup = new ToggleGroup();
disponibleRadio.setToggleGroup(etatGroup);
nonDispoRadio.setToggleGroup(etatGroup);*/
         
    }    

    @FXML
    private void ajouterchambre( javafx.event.ActionEvent mouseEvent)throws SQLException {
        
    if (mouseEvent.getSource() == ajoutch) {
        ChambreService as = new ChambreService();

        // Vérifier si une chambre avec le même numéro existe déjà
        Chambre chambreExistante = as.getChambreByNum(num.getText());
        if (chambreExistante != null) {
            // Une chambre avec le même numéro existe déjà
            JOptionPane.showMessageDialog(null, "Une chambre avec le même numéro existe déjà!");
            return; // Sortir de la méthode sans rien ajouter
        }
 String etat;
        if (disponibleRadio.isSelected()) {
            etat = "disponible";
        } else {
            etat = "non disponible";
        }

        as.ajouter(new Chambre(num.getText(),cap.getText(),prix.getText(),etat));
        JOptionPane.showMessageDialog(null, "Chambre ajoutée!");
  //notifier
    
     
     Notifications notificationBuilder = Notifications.create()
     .title("chambre ajouter")
     .text("ajout avec succes")
             .graphic(null)
             .hideAfter(Duration.seconds(5))
             .position(Pos.TOP_RIGHT)
             .onAction(new EventHandler<ActionEvent>()   {
                @Override
                public void handle(ActionEvent event) {
                   System.out.println("clique ici");
                }
                 
             });
    notificationBuilder.showConfirm();
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_num.setCellValueFactory(new PropertyValueFactory<>("num"));
        col_cap.setCellValueFactory(new PropertyValueFactory<>("capacite"));
        col_prix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        col_etat.setCellValueFactory(new PropertyValueFactory<>("etat"));
        
        ObservableList<Chambre> list = as.getChambreList();
        tab_ch.setItems(list);

        // Réinitialiser les champs
        num.setText(null);
        cap.setText(null);
        prix.setText(null);
      disponibleRadio.setSelected(false);
nonDispoRadio.setSelected(false);
    }
}
 @FXML
private void modifierchambre(javafx.event.ActionEvent mouseEvent) throws SQLException{
    if (mouseEvent.getSource() == modch) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Voulez-vous vraiment modifier cette chambre ?");
        alert.setContentText("Cliquez sur OK pour confirmer.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            ChambreService as = new ChambreService();
             String etat;
        if (disponibleRadio.isSelected()) {
            etat = "disponible";
        } else {
            etat = "non disponible";
        }
            as.modifier(new Chambre(Integer.parseInt(id.getText()),num.getText(),cap.getText(),prix.getText(),etat));
            JOptionPane.showMessageDialog(null, "activite Modifiée");

            col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
            col_num.setCellValueFactory(new PropertyValueFactory<>("num "));
            col_cap.setCellValueFactory(new PropertyValueFactory<>("capacite"));
            col_prix.setCellValueFactory(new PropertyValueFactory<>("prix"));
            col_etat.setCellValueFactory(new PropertyValueFactory<>("etat"));
            ObservableList<Chambre> list = as.getChambreList();
            tab_ch.setItems(list);
            id.setText(null);
            num.setText(null);
            cap.setText(null);
            prix.setText(null);
             disponibleRadio.setSelected(false);
nonDispoRadio.setSelected(false);
        }
    }
}

    


    @FXML
    private void getselected(MouseEvent event) {
        int index = tab_ch.getSelectionModel().getSelectedIndex();
            if (index <= -1) {

                return;
            }
         // idprop.setText(col_idprop.getCellData(index).toString());
            id.setText(col_id.getCellData(index).toString());
          // comidpromp.promptTextProperty(col_idprop.getCellData.toString());
          num.setText(col_num.getCellData(index).toString());
          cap.setText(col_cap.getCellData(index).toString());
          prix.setText(col_prix.getCellData(index).toString());
           String selectedEtat = col_etat.getCellData(index).toString();
    if(selectedEtat.equals("Disponible")){
        disponibleRadio.setSelected(true);
    } else if(selectedEtat.equals("Non disponible")){
        nonDispoRadio.setSelected(true);
    }
    }

    @FXML
    private void supprimerchambre(javafx.event.ActionEvent mouseEvent)throws SQLException  {
        
         
          if (mouseEvent.getSource() == suppch) {
        ChambreService ChambreService = new ChambreService();
        Chambre selectedService = tab_ch.getSelectionModel().getSelectedItem();
        if (selectedService != null) {
            Alert alert = new Alert(AlertType.CONFIRMATION, "Êtes-vous sûr de vouloir supprimer cette chambre ?");
            alert.setHeaderText(null);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                ChambreService.supprimer(new Chambre(selectedService.getId(), "", "", "",""));
                JOptionPane.showMessageDialog(null, "Service supprimé !");
                ObservableList<Chambre> list = ChambreService.getChambreList();
                tab_ch.setItems(list);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Veuillez sélectionner une chambre à supprimer !");
        }
    }
           
            

        
        
    }

   

    @FXML
    private void numvalid(KeyEvent event) {
         if (num.getText().isEmpty()) {
        num.setStyle("-fx-border-color:red ; -fx-border-width:2px;");
        new animatefx.animation.Shake(num).play();
        errornum.setText("Le champ ne doit pas être vide.");
    } else if ((num.getText().matches("[A-Za-z]"))) {
        num.setStyle("-fx-border-color:red ; -fx-border-width:2px;");
        errornum.setText("Le numero de la chambre doit être un entier.");
    } else if ((num.getText().matches("[0-9]+"))) {
        num.setStyle(null);
        errornum.setText(null);
    }
    }

    @FXML
    private void capvalide(KeyEvent event) {
         
     if ((cap.getText().matches("[A-Za-z]"))) {
        cap.setStyle("-fx-border-color:red ; -fx-border-width:2px;");
        new animatefx.animation.Shake(cap).play();
        errorcap.setText("La capacite de la chambre doit être un entier.");
    } else if ((cap.getText().matches("[0-9]+"))) {
        cap.setStyle(null);
        errorcap.setText(null);
    }
    }

    @FXML
    private void prixvalide(KeyEvent event) {
         if (prix.getText().isEmpty()) {
        prix.setStyle("-fx-border-color:red ; -fx-border-width:2px;");
         new animatefx.animation.Shake(prix).play();
        errorprix.setText("Le prix ne doit pas être vide.");
    } else if ((prix.getText().matches("[A-Za-z]"))) {
        prix.setStyle("-fx-border-color:red ; -fx-border-width:2px;");
        errorprix.setText("Le prix de la chambre doit être un entier.");
    } else if ((prix.getText().matches("[0-9]+"))) {
        cap.setStyle(null);
        errorprix.setText(null);
    }
    }

    private void etatvalid(KeyEvent event) {
          if (etat.getText().isEmpty()) {
        etat.setStyle("-fx-border-color:red ; -fx-border-width:2px;");
         new animatefx.animation.Shake(etat).play();
        erroretat.setText("Le prix ne doit pas être vide.");
    } else if ((etat.getText().matches("[A-Za-z]"))) {
        etat.setStyle("-fx-border-color:red ; -fx-border-width:2px;");
        errorprix.setText("Le prix de la chambre doit être un entier.");
    } else if ((etat.getText().matches("[0-9]+"))) {
        etat.setStyle(null);
        erroretat.setText(null);
    }
    }

    @FXML
    private void annulerch(ActionEvent event) {
          id.setText(null);
            
             
            num.setText(null);
            cap.setText(null);
             prix.setText(null);
             etat.setText(null);
            

    }

    @FXML
    private void ajoutccheck(ActionEvent event) {
         labelch.setText(" Ajouter  ");
        labelch.setLayoutX(120);
        id.setEditable(true);
         
        num.setEditable(false);
        cap.setEditable(false);
        prix.setEditable(false);
         etat.setEditable(false);
       
       
        
        ajoutch.setDisable(false);
        modch.setDisable(true);
        suppch.setDisable(true);
        annulerch.setDisable(false);
        radiocconsulter.setSelected(false);
       
        radiomodif.setSelected(false);
        radioajoutc.setSelected(false);
    id.setText(null);
           
            
           
            num.setText(null);
            prix.setText(null);
            cap.setText(null);
             etat.setText(null);
           
    }

    @FXML
    private void modifccheck(ActionEvent event) {
                labelch.setText(" Modifier  ");
        labelch.setLayoutX(120);
        id.setEditable(true);
         
        num.setEditable(false);
        cap.setEditable(false);
        prix.setEditable(false);
         etat.setEditable(false);
       
       
        
        ajoutch.setDisable(true);
        modch.setDisable(false);
        suppch.setDisable(true);
        annulerch.setDisable(false);
        radiocconsulter.setSelected(false);
       
        radiomodif.setSelected(false);
        radioajoutc.setSelected(false);
    id.setText(null);
           
            
           
            num.setText(null);
            prix.setText(null);
            cap.setText(null);
             etat.setText(null);
    }

    @FXML
    private void suppccheck(ActionEvent event) {
           labelch.setText(" Supprimer  ");
        labelch.setLayoutX(120);
        id.setEditable(true);
         
        num.setEditable(false);
        cap.setEditable(false);
        prix.setEditable(false);
         etat.setEditable(false);
       
       
        
        ajoutch.setDisable(true);
        modch.setDisable(true);
        suppch.setDisable(false);
        annulerch.setDisable(true);
        radiocconsulter.setSelected(false);
       
        radiomodif.setSelected(false);
        radioajoutc.setSelected(false);
    id.setText(null);
           
            
           
            num.setText(null);
            prix.setText(null);
            cap.setText(null);
             etat.setText(null);
    }

    @FXML
    private void consulterccheck(ActionEvent event) {
           labelch.setText(" Consulter  ");
        labelch.setLayoutX(120);
        id.setEditable(true);
         
        num.setEditable(false);
        cap.setEditable(false);
        prix.setEditable(false);
         etat.setEditable(false);
       
       
        
        ajoutch.setDisable(true);
        modch.setDisable(true);
        suppch.setDisable(true);
        annulerch.setDisable(true);
        radiocconsulter.setSelected(false);
       
        radiomodif.setSelected(false);
        radioajoutc.setSelected(false);
    id.setText(null);
           
            
           
            num.setText(null);
            prix.setText(null);
            cap.setText(null);
             etat.setText(null);
    }

    @FXML
    private void rechercherchambre()  throws IOException, SQLException{
         ChambreService sa = new ChambreService();
      col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_num.setCellValueFactory(new PropertyValueFactory<>("num"));
        col_cap.setCellValueFactory(new PropertyValueFactory<>("capacite"));
        col_prix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        col_etat.setCellValueFactory(new PropertyValueFactory<>("etat"));
          
       
            ObservableList<Chambre> list = sa.getChambreList();
            tab_ch.setItems(list);
        //ObservableList<Article> list = FXCollections.observableArrayList();

        // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Chambre> filteredData = new FilteredList<>(list, b -> true);
        // 2. Set the filter Predicate whenever the filter changes.
        recham.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate((Chambre reglement) -> {
                // If filter text is empty, display all persons.

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(reglement.getNum()).indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches libelle
                } 
               
                else {
                    return false; // Does not match.
                }
            });
        });
        // 3. Wrap the FilteredList in a SortedList. 
        SortedList<Chambre> sortedData = new SortedList<>(filteredData);
        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(tab_ch.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        tab_ch.setItems(sortedData);
    }
}



    