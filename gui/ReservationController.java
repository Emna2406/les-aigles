/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connexion3a41.gui;

import edu.connexion3a41.entities.Chambre;
import edu.connexion3a41.entities.Reservation;
import edu.connexion3a41.entities.Service;
import edu.connexion3a41.entities.pdfR;
import edu.connexion3a41.services.ChambreService;
import edu.connexion3a41.services.ReservationService;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import javax.swing.JOptionPane;
import org.controlsfx.control.Notifications;

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
   
    private RadioButton radioajout;
    @FXML
    private RadioButton radiomodr;
    @FXML
    private RadioButton radiosuppr;
    private RadioButton radioconsultr;
    @FXML
    private Button btngservice;
    @FXML
    private TableColumn<?, ?> col_prix;
    @FXML
    private TextField rechres;
    @FXML
    private Button stat;
    @FXML
    private Button mail;
    @FXML
    private Button details;
    private ComboBox<?> ExporterListe;
    @FXML
    private Button pdf;
    @FXML
    private Button imprimer;

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
        col_prix.setCellValueFactory(new PropertyValueFactory<>("prix"));
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
            JOptionPane.showMessageDialog(null, " Reservation Ajouté");
               Notifications notificationBuilder = Notifications.create()
     .title("reservation ajouter")
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

    private void gotoservice(ActionEvent event) throws IOException {
       
        
       
                Parent root = FXMLLoader.load(getClass().getResource("chambre.fxml"));
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("clinique");
                stage.show();

            
    
    }

    @FXML
    private void recherchereservation() throws IOException, SQLException {
        ReservationService sa = new ReservationService();
         col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
            col_idc.setCellValueFactory(new PropertyValueFactory<>("id_chambre"));
          col_idm.setCellValueFactory(new PropertyValueFactory<>("id_medecin"));
            col_ipd.setCellValueFactory(new PropertyValueFactory<>("id_patient"));
            col_datedeb.setCellValueFactory(new PropertyValueFactory<>("date_deb"));
            
            ObservableList<Reservation> list = sa.getReservationList();
            tab_res.setItems(list);
        //ObservableList<Article> list = FXCollections.observableArrayList();

        // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Reservation> filteredData = new FilteredList<>(list, b -> true);
        // 2. Set the filter Predicate whenever the filter changes.
        rechres.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate((Reservation reglement) -> {
                // If filter text is empty, display all persons.

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(reglement.getId_patient()).indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches libelle
                } 
               
                else {
                    return false; // Does not match.
                }
            });
        });
        // 3. Wrap the FilteredList in a SortedList. 
        SortedList<Reservation> sortedData = new SortedList<>(filteredData);
        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(tab_res.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        tab_res.setItems(sortedData);
        
    }

    @FXML
    private void stat(ActionEvent event) throws IOException {
        
                Parent root = FXMLLoader.load(getClass().getResource("stat.fxml"));
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("clinique");
                stage.show();

            
    }

    @FXML
    private void mail(ActionEvent event) throws IOException {
        
                Parent root = FXMLLoader.load(getClass().getResource("mailing.fxml"));
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("clinique");
                stage.show();

            
    }
     private void PDF(javafx.scene.input.MouseEvent event) {
                            Reservation voy = tab_res.getSelectionModel().getSelectedItem();

        pdfR pd=new pdfR();
        try{
                    pd.GeneratePdf("MesInformations",voy,voy.getId());

            System.out.println("impression done");
        } catch (Exception ex) {
            Logger.getLogger(ReservationService.class.getName()).log(Level.SEVERE, null, ex);
            }
    }



@FXML
private void Detail(ActionEvent event) {
    System.out.println("Extraction de la date de début...");
    int index = tab_res.getSelectionModel().getSelectedIndex();
    if (index <= -1) {
        return;
    }
    Reservation reservation = tab_res.getItems().get(index);
    LocalDate debut = reservation.getDate_deb().toLocalDate();
    System.out.println("Date de début : " + debut); // débogage
    LocalDate fin = LocalDate.now(); // la date actuelle
    long nbJours = ChronoUnit.DAYS.between(debut, fin);
    // Afficher le nombre de jours dans une boîte de dialogue
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Durée de la réservation");
    alert.setHeaderText(null);
    alert.setContentText("La durée de la réservation est de " + nbJours + " jours.");
    alert.showAndWait();
}

       
    public static void printNode(final Node node) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Printer printer = Printer.getDefaultPrinter();
        PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.LANDSCAPE, Printer.MarginType.DEFAULT);
        PrinterAttributes attr = printer.getPrinterAttributes();
        PrinterJob job = PrinterJob.createPrinterJob();
        double scaleX = pageLayout.getPrintableWidth() / node.getBoundsInParent().getWidth();
        double scaleY = pageLayout.getPrintableHeight() / node.getBoundsInParent().getHeight();
        Scale scale = new Scale(scaleX, scaleY);
        node.getTransforms().add(scale);
        
        if (job != null && job.showPrintDialog(node.getScene().getWindow())) {
            boolean success = job.printPage(pageLayout, node);
            if (success) {
                job.endJob();
                
            }
        }
        node.getTransforms().remove(scale);
        
    }
    
      private void ImprimerAction(ActionEvent event) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
                printNode(tab_res);
    }
    
 

    @FXML
    private void pdf(ActionEvent event) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
         Reservation voy = tab_res.getSelectionModel().getSelectedItem();
         pdfR pd=new pdfR();
        try{
                    pd.GeneratePdf(""+voy.getId_chambre()+"",voy,voy.getId());
                    Alert alert= new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("PDF");
                    alert.setHeaderText(null);
                    alert.setContentText("!!!PDF exported!!!");
                    alert.showAndWait();
            System.out.println("impression done");
        } catch (Exception ex) {
            Logger.getLogger(ReservationService.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert= new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Alert");
                    alert.setHeaderText(null);
                    alert.setContentText("!!!Selectioner une Reservation!!!");
                    alert.showAndWait();
            }
           printNode(tab_res);
    }

    @FXML
    private void imprimer(ActionEvent event) throws NoSuchMethodException, InvocationTargetException, InstantiationException {
        try {
            printNode(tab_res);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ReservationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void gotochambre(ActionEvent event) {
    }

   
  


    
    
    
    

}


   



   
    

   
     

    

