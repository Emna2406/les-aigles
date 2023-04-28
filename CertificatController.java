/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connexion3a41.gui;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import edu.connexion3a41.entities.Certificat;
import edu.connexion3a41.entities.Consultation;
import edu.connexion3a41.entities.Ordonnance;
import edu.connexion3a41.services.CertificatCRUD;
import edu.connexion3a41.services.ConsultationCRUD;
import edu.connexion3a41.services.OrdonnanceCRUD;
import edu.connexion3a41.tools.MyDB;
import java.awt.Desktop;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import static javafx.scene.control.ContentDisplay.CENTER;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javax.management.Notification;
import javax.swing.JOptionPane;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;


/**
 * FXML Controller class
 *
 * @author aouadh
 */
public class CertificatController implements Initializable {

    @FXML
    private TextField idcert;
    @FXML
    private TextField patcert;
    @FXML
    private TextField medcert;
    @FXML
    private DatePicker ddcert;
    @FXML
    private DatePicker dfcert;
    @FXML
    private TextField imgcert;
    @FXML
    private TableView<Certificat> tablecert;
    @FXML
    private TableColumn<Certificat, Integer> colidpcert;
    @FXML
    private TableColumn<Certificat, Integer> colidmcert;
    @FXML
    private TableColumn<Certificat, String> colddcert;
    @FXML
    private TableColumn<Certificat, String> coldfcert;
    @FXML
    private TableColumn<Certificat, String> colimgcert;
    @FXML
    private TableColumn<Certificat, Integer> colidcert;
    @FXML
    private Button ajtcert;
    @FXML
    private Button anncert;
    @FXML
    private Button modcert;
    @FXML
    private Button suppcert;
    @FXML
    private Button test;
    private DatePicker test2;
    @FXML
    private Label erreur2;
    @FXML
    private Label erreur1;
    @FXML
    private Label label1;
    @FXML
    private RadioButton adce;
    @FXML
    private RadioButton sdce;
    @FXML
    private RadioButton mdce;
    @FXML
    private TextField trechh;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
            // partie mte tableau affichage
          try {
            System.out.println("hello") ;
        CertificatCRUD sa = new  CertificatCRUD();
        colidcert.setCellValueFactory(new PropertyValueFactory<>("id"));
        colidpcert.setCellValueFactory(new PropertyValueFactory<>("patient_id"));
            colidmcert.setCellValueFactory(new PropertyValueFactory<>("medecin_id"));
            colddcert.setCellValueFactory(new PropertyValueFactory<>("datedeb"));
            coldfcert.setCellValueFactory(new PropertyValueFactory<>("datefin"));
            colimgcert.setCellValueFactory(new PropertyValueFactory<>("image"));
              // Ajouter la cell factory pour la colonne d'image
       colimgcert.setCellFactory(column -> {
    return new TableCell<Certificat, String>() {
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
            
                     
           ObservableList<Certificat> list = sa.getCertificatList();
            
             tablecert.setItems(list);
             // partie affichage mte combobox yani kol mthel page yjiblk affichage fi tableau w fi combo
         } catch (SQLException ex) {
         }
          
            
           
    } 
    
    @FXML
     private void ajoutcert(javafx.event.ActionEvent mouseEvent)throws SQLException {
        if ((mouseEvent.getSource() ==ajtcert)&&(!patcert.getText().isEmpty())&&(!medcert.getText().isEmpty())){
            String date=ddcert.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String datee=dfcert.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        File file = fileChooser.showOpenDialog(null);
        String imagePath = file.getAbsolutePath();
         
            CertificatCRUD as = new  CertificatCRUD();
            as.ajouter(new Certificat(Integer.parseInt(patcert.getText()),Integer.parseInt(medcert.getText()),date,datee,imagePath));
           
       
            //notifier
    JOptionPane.showMessageDialog(null, " Certficat ajouter ");
     
     Notifications notificationBuilder = Notifications.create()
     .title("Certificat ajouter")
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
   
       
   
           
     
             
           colidcert.setCellValueFactory(new PropertyValueFactory<>("id"));
        colidpcert.setCellValueFactory(new PropertyValueFactory<>("patient_id"));
            colidmcert.setCellValueFactory(new PropertyValueFactory<>("medecin_id"));
            colddcert.setCellValueFactory(new PropertyValueFactory<>("datedeb"));
            coldfcert.setCellValueFactory(new PropertyValueFactory<>("datefin"));
            colimgcert.setCellValueFactory(new PropertyValueFactory<>("image"));
           
           
       
             ObservableList<Certificat> list = as.getCertificatList();
             tablecert.setItems(list);
         
           
           
idcert.setText(null);
          patcert.setText(null);
          

           
           
            medcert.setText(null);
            ddcert.setValue(null);
            dfcert.setValue(null);
     imgcert.setText(null);
                        
           
        } 
        else {
           JOptionPane.showMessageDialog(null, " y a un ou des champs n'est pas valide ");
        }
        }
        @FXML
    private void supprimer_cert(javafx.event.ActionEvent mouseEvent) throws SQLException {
         if (mouseEvent.getSource() == suppcert) {
           
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
    CertificatCRUD as = new CertificatCRUD();
             as.supprimer(new Certificat(Integer.parseInt(idcert.getText())));
            JOptionPane.showMessageDialog(null, "Certificat Supprimée");
           
            colidcert.setCellValueFactory(new PropertyValueFactory<>("id"));
        colidpcert.setCellValueFactory(new PropertyValueFactory<>("patient_id"));
            colidmcert.setCellValueFactory(new PropertyValueFactory<>("medecin_id"));
            colddcert.setCellValueFactory(new PropertyValueFactory<>("datedeb"));
            coldfcert.setCellValueFactory(new PropertyValueFactory<>("datefin"));
            colimgcert.setCellValueFactory(new PropertyValueFactory<>("image"));
       
             ObservableList<Certificat> list = as.getCertificatList();
             tablecert.setItems(list);
         
           
           

        idcert.setText(null);
          patcert.setText(null);
          

           
           
            medcert.setText(null);
            ddcert.setValue(null);
            dfcert.setValue(null);
     imgcert.setText(null);
    
} else {
    colidcert.setCellValueFactory(new PropertyValueFactory<>("id"));
        colidpcert.setCellValueFactory(new PropertyValueFactory<>("patient_id"));
            colidmcert.setCellValueFactory(new PropertyValueFactory<>("medecin_id"));
            colddcert.setCellValueFactory(new PropertyValueFactory<>("datedeb"));
            coldfcert.setCellValueFactory(new PropertyValueFactory<>("datefin"));
            colimgcert.setCellValueFactory(new PropertyValueFactory<>("image"));
       
           
}

           
           

        }
    }
   

    @FXML
    private void modifiercert(javafx.event.ActionEvent mouseEvent) throws SQLException {
       
   
         if (mouseEvent.getSource() == modcert) {
         
 String date=ddcert.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String datee=dfcert.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        File file = fileChooser.showOpenDialog(null);
        String imagePath = file.getAbsolutePath();
            CertificatCRUD as = new  CertificatCRUD();
            as.modifier(new Certificat(Integer.parseInt(idcert.getText()),Integer.parseInt(patcert.getText()),Integer.parseInt(medcert.getText()),date,datee,imagePath));
           
        System.out.println("ttttttttt");
         
       
   
           
     JOptionPane.showMessageDialog(null, " Certificat modifier ");
             
colidcert.setCellValueFactory(new PropertyValueFactory<>("id"));
        colidpcert.setCellValueFactory(new PropertyValueFactory<>("patient_id"));
            colidmcert.setCellValueFactory(new PropertyValueFactory<>("medecin_id"));
            colddcert.setCellValueFactory(new PropertyValueFactory<>("datedeb"));
            coldfcert.setCellValueFactory(new PropertyValueFactory<>("datefin"));
            colimgcert.setCellValueFactory(new PropertyValueFactory<>("image"));
         
           
           ObservableList<Certificat> list = as.getCertificatList();
             tablecert.setItems(list);

         idcert.setText(null);
          patcert.setText(null);
          

           
           
            medcert.setText(null);
            ddcert.setValue(null);
            dfcert.setValue(null);
     imgcert.setText(null);
                        
           
           }
    
    
}

    @FXML
    private void med(javafx.scene.input.MouseEvent event) {
         int index = tablecert.getSelectionModel().getSelectedIndex();
            if (index <= -1) {

                return;
            }
         // idprop.setText(col_idprop.getCellData(index).toString());
            idcert.setText(colidcert.getCellData(index).toString());
          // comidpromp.promptTextProperty(col_idprop.getCellData.toString());
          patcert.setText(colidpcert.getCellData(index).toString());
          medcert.setText(colidmcert.getCellData(index).toString());
          ddcert.setPromptText(colddcert.getCellData(index));
          dfcert.setPromptText(coldfcert.getCellData(index));
          imgcert.setText(colimgcert.getCellData(index));
          
    }
    
   /*  @FXML
    private void messagevalid(KeyEvent event) {
       
       
if ((patcert .getText().matches("[0-9]")) || (patcert .getText().length() == 0)) {
           patcert .setStyle("-fx-border-color:red ; -fx-border-width:2px;");
            new animatefx.animation.Shake(patcert ).play();
           erreur1.setText("Le mesage doit etre non null et chaine  ");

        } else if ((patcert .getText().matches("[A-Z,a-z]"))) {
            patcert .setStyle(null);
            erreur1.setText(null);
        }
   
    }*/

    @FXML
    private void testnum1(javafx.scene.input.KeyEvent event) {
        if ((medcert .getText().matches("[0-9]")) ) {
            medcert .setStyle(null);
            erreur2.setText(null);

        }
         else if ((medcert .getText().matches("[A-Z,a-z]")|| (medcert .getText().length() == 0))) {
       
            medcert .setStyle("-fx-border-color:red ; -fx-border-width:2px;");
            new animatefx.animation.Shake(medcert ).play();
           erreur2.setText("Le numero doit etre numerique et egale 8 chiffres  ");
        }
    }

    @FXML
    private void testnum2(javafx.scene.input.KeyEvent event) {
        if ((patcert .getText().matches("[0-9]")) ) {
            patcert .setStyle(null);
            erreur1.setText(null);

        }
         else if ((patcert .getText().matches("[A-Z,a-z]")|| (patcert .getText().length() == 0))) {
       
            patcert .setStyle("-fx-border-color:red ; -fx-border-width:2px;");
            new animatefx.animation.Shake(medcert ).play();
           erreur1.setText("Le numero doit etre numerique et egale 8 chiffres  ");
        }
    }

    @FXML
    private void checkajouter(ActionEvent event) {
          label1.setText("Ajout   ");

        label1.setLayoutX(120);
          FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), label1);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
         fadeTransition.setCycleCount(Animation.INDEFINITE);
        fadeTransition.play();
        idcert.setEditable(false);
         patcert.setEditable(true);
        medcert.setEditable(true);
         ddcert.setEditable(true);
          dfcert.setEditable(true);
        imgcert.setEditable(true);
       
       
        anncert.setDisable(true);
       ajtcert.setDisable(false);
        modcert.setDisable(true);
        suppcert.setDisable(true);
        adce.setSelected(true);
       mdce.setSelected(false);
        sdce.setSelected(false);
      /*  radiosupp.setSelected(false);
       
        radioconsult.setSelected(false);
        radiomodif.setSelected(false);*/
  idcert.setText(null);
            patcert.setText(null);

           
            medcert.setText(null);
            ddcert.setValue(null);
            dfcert.setValue(null);
     imgcert.setText(null);
                        
                       
                     
    }

    @FXML
    private void checksuppr(ActionEvent event) {
       label1.setText("supprimer   ");

        label1.setLayoutX(120);
          FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), label1);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
         fadeTransition.setCycleCount(Animation.INDEFINITE);
        fadeTransition.play();
        idcert.setEditable(false);
         patcert.setEditable(true);
        medcert.setEditable(true);
         ddcert.setEditable(true);
          dfcert.setEditable(true);
        imgcert.setEditable(true);
       
       
        anncert.setDisable(true);
       ajtcert.setDisable(true);
        modcert.setDisable(true);
        suppcert.setDisable(false);
         adce.setSelected(false);
       mdce.setSelected(false);
        sdce.setSelected(true);
      /*  radiosupp.setSelected(false);
       
        radioconsult.setSelected(false);
        radiomodif.setSelected(false);*/
  idcert.setText(null);
            patcert.setText(null);

           
            medcert.setText(null);
            ddcert.setValue(null);
            dfcert.setValue(null);
     imgcert.setText(null);

                        
    }

    @FXML
    private void checkmodif(ActionEvent event) {
        label1.setText("modifier   ");

        label1.setLayoutX(120);
          FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), label1);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
         fadeTransition.setCycleCount(Animation.INDEFINITE);
        fadeTransition.play();
        idcert.setEditable(false);
         patcert.setEditable(true);
        medcert.setEditable(true);
         ddcert.setEditable(true);
          dfcert.setEditable(true);
        imgcert.setEditable(true);
       
       
        anncert.setDisable(true);
       ajtcert.setDisable(true);
        modcert.setDisable(false);
        suppcert.setDisable(true);
         adce.setSelected(false);
       mdce.setSelected(true);
        sdce.setSelected(false);
      /*  radiosupp.setSelected(false);
       
        radioconsult.setSelected(false);
        radiomodif.setSelected(false);*/
  idcert.setText(null);
            patcert.setText(null);

           
            medcert.setText(null);
            ddcert.setValue(null);
            dfcert.setValue(null);
     imgcert.setText(null);
                        
    }

    @FXML
    private void annuler3(ActionEvent event) {
         idcert.setText(null);
            patcert.setText(null);

           
            medcert.setText(null);
            ddcert.setValue(null);
            dfcert.setValue(null);
     imgcert.setText(null);
    }

    @FXML
    private void Performed(ActionEvent event) throws IOException {
        Document doc=new Document();
         Connection cnx = MyDB.getInstance().getConnexion();
        String sql ="Select * from certificat";
        
        
        try{
             PreparedStatement pst = cnx.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        PdfWriter.getInstance(doc,new FileOutputStream("C:\\Users\\bejao\\Desktop\\testt.pdf"));
        doc.open();
       // Image img = new Image("C:\\Users\\bejao\\Desktop\\testt.pdf");
      // img.scaleAbsoluteWidth(600);
       // img.scaleAbsoluteHeight(92);
       // img.setAligment(Image.ALIGN_CENTRE);
       
       
        doc.add(new Paragraph(" "));
        doc.add(new Paragraph("---------------------------------------------------"));
        doc.add(new Paragraph("Liste des Certificats : "));
        doc.add(new Paragraph("---------------------------------------------------"));
        doc.add(new Paragraph(" "));
         doc.add(new Paragraph(" "));
          doc.add(new Paragraph(" "));
           doc.add(new Paragraph(" "));
        
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        PdfPCell cell ;
        
        
        cell=new PdfPCell (new Phrase ("id_patient", FontFactory.getFont("comic Sans MS",12)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(BaseColor.GRAY);
        table.addCell(cell);
        cell=new PdfPCell (new Phrase ("id_medecin", FontFactory.getFont("comic Sans MS",12)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(BaseColor.GRAY);
        table.addCell(cell);
        cell=new PdfPCell (new Phrase ("date_debut", FontFactory.getFont("comic Sans MS",12)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(BaseColor.GRAY);
        table.addCell(cell);
        cell=new PdfPCell (new Phrase ("date_fin", FontFactory.getFont("comic Sans MS",12)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(BaseColor.GRAY);
        table.addCell(cell);
        cell=new PdfPCell (new Phrase ("image", FontFactory.getFont("comic Sans MS",12)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(BaseColor.GRAY);
        table.addCell(cell);
        cell=new PdfPCell (new Phrase ("id", FontFactory.getFont("comic Sans MS",12)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(BaseColor.GRAY);
        table.addCell(cell);
        ////////////////////////////////////////////////////////////////////////////
        while (rs.next()){
         cell=new PdfPCell (new Phrase (rs.getString("patient_id").toString(), FontFactory.getFont("Arial",11)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        
        table.addCell(cell);
        cell=new PdfPCell (new Phrase (rs.getString("medecin_id").toString(), FontFactory.getFont("Arial",11)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        
        table.addCell(cell);
        cell=new PdfPCell (new Phrase (rs.getString("datedeb").toString(), FontFactory.getFont("Arial",11)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        
        table.addCell(cell);
        cell=new PdfPCell (new Phrase (rs.getString("datefin").toString(), FontFactory.getFont("Arial",11)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        
        table.addCell(cell);
        cell=new PdfPCell (new Phrase (rs.getString("image").toString(), FontFactory.getFont("Arial",11)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        
        table.addCell(cell);
        cell=new PdfPCell (new Phrase (rs.getString("id").toString(), FontFactory.getFont("Arial",11)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
       
        table.addCell(cell);}
        
        doc.add(table);
        
        doc.close();
        Desktop.getDesktop().open(new File ("C:\\Users\\bejao\\Desktop\\testt.pdf"));
    }   catch (FileNotFoundException ex) {
            Logger.getLogger(CertificatController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(CertificatController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CertificatController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      
    @FXML
    private void recherche() throws SQLException {
          CertificatCRUD sa = new CertificatCRUD();
     
       colidcert.setCellValueFactory(new PropertyValueFactory<>("id"));
        colidpcert.setCellValueFactory(new PropertyValueFactory<>("patient_id"));
            colidmcert.setCellValueFactory(new PropertyValueFactory<>("medecin_id"));
            colddcert.setCellValueFactory(new PropertyValueFactory<>("datedeb"));
            coldfcert.setCellValueFactory(new PropertyValueFactory<>("datefin"));
            colimgcert.setCellValueFactory(new PropertyValueFactory<>("image"));
         
            
           ObservableList<Certificat> list = sa.getCertificatList();
             tablecert.setItems(list);
        //ObservableList<Article> list = FXCollections.observableArrayList();

        // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Certificat> filteredData = new FilteredList<>(list, b -> true);
        // 2. Set the filter Predicate whenever the filter changes.
        trechh.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate((Certificat rdv) -> {
                // If filter text is empty, display all persons.

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(rdv.getPatient_id()).indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches libelle
                }
                
                else  if (String.valueOf(rdv.getId()).indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches libelle
                } 
                
              /*  else if (String.valueOf(rdv.getDatedeb()).indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches libelle
                } */
               
                else {
                    return false; // Does not match.
                }
            });
        });
        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Certificat> sortedData = new SortedList<>(filteredData);
        // 4. Bind the SortedList comparator to the TableView comparator.
        //  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(tablecert.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        tablecert.setItems(sortedData);
    }
    
    
    
    
    }

  

  


