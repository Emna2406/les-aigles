/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connexion3a41.gui;

import edu.connexion3a41.entities.Certificat;
import edu.connexion3a41.entities.Consultation;
import edu.connexion3a41.entities.Ordonnance;
import edu.connexion3a41.services.ConsultationCRUD;
import edu.connexion3a41.services.OrdonnanceCRUD;
import edu.connexion3a41.tools.MyDB;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.util.Duration;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Properties;
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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author aouadh
 */
public class ConsultationController implements Initializable {

    @FXML
    private DatePicker datec;
    @FXML
    private ComboBox<String> patientc;
    @FXML
    private ComboBox<String> medecinc;
    @FXML
    private TextField imgc;
    @FXML
    private TableView<Consultation> tablec;
    @FXML
    private TableColumn<Consultation, Integer> colpc;
    @FXML
    private TableColumn<Consultation, Integer> colmc;
    @FXML
    private TableColumn<Consultation, String> coldc;
    @FXML
    private TableColumn<Consultation, String> colimgc;
    @FXML
    private TableColumn<Consultation, Integer> colidc;
    @FXML
    private TextField idc;
    @FXML
    private Button ajoutc;
    @FXML
    private Button annulec;
    @FXML
    private Button modifc;
    @FXML
    private Button suppc;
    private ComboBox<String> test;
    @FXML
    private Label label2;
    @FXML
    private RadioButton rdc;
    @FXML
    private RadioButton mdc;
    @FXML
    private RadioButton sdc;
    @FXML
    private Button smis;
    @FXML
    private TextField ttrech;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // TODO
        try {
            System.out.println("hello") ;
        ConsultationCRUD sa = new  ConsultationCRUD();
        colidc.setCellValueFactory(new PropertyValueFactory<>("id"));
        colpc.setCellValueFactory(new PropertyValueFactory<>("patient_id"));
            colmc.setCellValueFactory(new PropertyValueFactory<>("medecin_id"));
            coldc.setCellValueFactory(new PropertyValueFactory<>("datedeconsultation"));
            colimgc.setCellValueFactory(new PropertyValueFactory<>("image"));
             // Ajouter la cell factory pour la colonne d'image
       colimgc.setCellFactory(column -> {
    return new TableCell<Consultation, String>() {
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
            
           
                    
           ObservableList<Consultation> list = sa.getConsultationList();
             tablec.setItems(list);
             // partie affichage mte combobox yani kol mthel page yjiblk affichage fi tableau w fi combo
        }catch (SQLException ex) 
            {
            Logger.getLogger(ConsultationCRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
         
         
        
         
         PreparedStatement pstt = null;
         Connection cnx = MyDB.getInstance().getConnexion();
            ObservableList<String> liste_medecin = FXCollections.observableArrayList();
        String reqq = "SELECT nom from medecin";
     
         
       
        try {
            pstt = cnx.prepareStatement(reqq);
        } catch (SQLException ex) {
            Logger.getLogger(ConsultationController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        ResultSet rss = null;
       
       
        try {
            rss = pstt.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(ConsultationController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       
        try {
            while (rss.next()) {
               
                liste_medecin .add(rss.getString("nom"));
               
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConsultationController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       
        medecinc.setItems(liste_medecin);
    
    
 
         
            PreparedStatement pst = null;
       
        ObservableList<String> liste_patient = FXCollections.observableArrayList();
        String req = "SELECT nom from patient";
     
         
       
        try {
            pst = cnx.prepareStatement(req);
        } catch (SQLException ex) {
            Logger.getLogger(ConsultationController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        ResultSet rs = null;
       
       
        try {
            rs = pst.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(ConsultationController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       
        try {
            while (rs.next()) {
               
                liste_patient .add(rs.getString("nom"));
               
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConsultationController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       
        patientc.setItems(liste_patient );
       
    }    

    

    @FXML
     private void ajoutcons(javafx.event.ActionEvent mouseEvent)throws SQLException {
        if (mouseEvent.getSource() ==ajoutc){
            String date=datec.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        File file = fileChooser.showOpenDialog(null);
        String imagePath = file.getAbsolutePath();
         
         
            ConsultationCRUD as = new  ConsultationCRUD();
            as.ajouter(new Consultation(patientc.getSelectionModel().getSelectedIndex(),medecinc.getSelectionModel().getSelectedIndex(),date,imagePath));
           
       
         
       
   
           
     JOptionPane.showMessageDialog(null, " Consultation ajouter ");
     
      Notifications notificationBuilder = Notifications.create()
     .title("Consultation ajouter")
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
             
            colidc.setCellValueFactory(new PropertyValueFactory<>("id"));
            colpc.setCellValueFactory(new PropertyValueFactory<>("patient_id"));
            colmc.setCellValueFactory(new PropertyValueFactory<>("medecin_id"));
            coldc.setCellValueFactory(new PropertyValueFactory<>("datedeconsultation"));
            colimgc.setCellValueFactory(new PropertyValueFactory<>("image"));
           
       
             ObservableList<Consultation> list = as.getConsultationList();
             tablec.setItems(list);
         
           
           
    idc.setText(null);
     patientc.setPromptText(null);
          

           
           
     medecinc.setPromptText(null);

     datec.setValue(null);
     imgc.setText(null);
     
                        
           
        }   
        }
        @FXML
    private void supprimer_rdv(javafx.event.ActionEvent mouseEvent) throws SQLException, MessagingException {
         if (mouseEvent.getSource() == suppc) {
           
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
    ConsultationCRUD as = new ConsultationCRUD();
             as.supprimer(new Consultation(Integer.parseInt(idc.getText())));
            JOptionPane.showMessageDialog(null, "consultation Supprimée");
           
           colidc.setCellValueFactory(new PropertyValueFactory<>("id"));
            colpc.setCellValueFactory(new PropertyValueFactory<>("patient_id"));
            colmc.setCellValueFactory(new PropertyValueFactory<>("medecin_id"));
            coldc.setCellValueFactory(new PropertyValueFactory<>("datedeconsultation"));
            colimgc.setCellValueFactory(new PropertyValueFactory<>("image"));
       
             ObservableList<Consultation> list = as.getConsultationList();
             tablec.setItems(list);
            int id=Integer.parseInt(idc.getText());
            envoy(id);
           
           

         idc.setText(null);
          patientc.setPromptText(null);
          

           
           
              medecinc.setPromptText(null);

            datec.setValue(null);
            imgc.setText(null);
         
    
} else {
   colidc.setCellValueFactory(new PropertyValueFactory<>("id"));
            colpc.setCellValueFactory(new PropertyValueFactory<>("patient_id"));
            colmc.setCellValueFactory(new PropertyValueFactory<>("medecin_id"));
            coldc.setCellValueFactory(new PropertyValueFactory<>("datedeconsultation"));
            colimgc.setCellValueFactory(new PropertyValueFactory<>("image"));
       
           
}

          
           

        }
    }
   
   

    @FXML
    private void modifier(javafx.event.ActionEvent mouseEvent) throws SQLException {
       
   
         if (mouseEvent.getSource() == modifc) {
         String date=datec.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
          FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        File file = fileChooser.showOpenDialog(null);
        String imagePath = file.getAbsolutePath();

            ConsultationCRUD as = new  ConsultationCRUD();
            as.modifier(new Consultation(Integer.parseInt(idc.getText()),patientc.getSelectionModel().getSelectedIndex(),medecinc.getSelectionModel().getSelectedIndex(),date,imagePath));
           
        System.out.println("ttttttttt");
         
       
   
           
     JOptionPane.showMessageDialog(null, " Consultation modifier ");
             
colidc.setCellValueFactory(new PropertyValueFactory<>("id"));
            colpc.setCellValueFactory(new PropertyValueFactory<>("patient_id"));
            colmc.setCellValueFactory(new PropertyValueFactory<>("medecin_id"));
            coldc.setCellValueFactory(new PropertyValueFactory<>("datedeconsultation"));
            colimgc.setCellValueFactory(new PropertyValueFactory<>("image"));
             ObservableList<Consultation> list = as.getConsultationList();
             tablec.setItems(list);
         
           
           

          idc.setText(null);

           
            patientc.setPromptText(null);
            medecinc.setPromptText(null);
            datec.setValue(null);
     imgc.setText(null);
                        
           
           }
    
}

    @FXML
    private void med(javafx.scene.input.MouseEvent event) {
        int index = tablec.getSelectionModel().getSelectedIndex();
            if (index <= -1) {

                return;
            }
         // idprop.setText(col_idprop.getCellData(index).toString());
            idc.setText(colidc.getCellData(index).toString());
          // comidpromp.promptTextProperty(col_idprop.getCellData.toString());
          patientc.setPromptText(colmc.getCellData(index).toString());
          medecinc.setPromptText(colmc.getCellData(index).toString());
          datec.setPromptText(coldc.getCellData(index));
          imgc.setText(colimgc.getCellData(index));
    }

    @FXML
    private void checkajouter1(ActionEvent event) {
                  label2.setText("Ajout   ");

        label2.setLayoutX(120);
          FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), label2);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
         fadeTransition.setCycleCount(Animation.INDEFINITE);
        fadeTransition.play();
        idc.setEditable(false);
         patientc.setEditable(true);
        medecinc.setEditable(true);
         datec.setEditable(true);
          imgc.setEditable(true);
        
       
       
        annulec.setDisable(true);
       ajoutc.setDisable(false);
        modifc.setDisable(true);
        suppc.setDisable(true);
      /*  radiosupp.setSelected(false);
       
        radioconsult.setSelected(false);
        radiomodif.setSelected(false);*/
       rdc.setSelected(true);
       sdc.setSelected(false);
        mdc.setSelected(false);
  idc.setText(null);
            patientc.setPromptText(null);

           
            medecinc.setPromptText(null);
            datec.setValue(null);
           
     imgc.setText(null);
    }

    @FXML
    private void checkmodif1(ActionEvent event) {
                    label2.setText("modifier   ");

        label2.setLayoutX(120);
         FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), label2);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setCycleCount(Animation.INDEFINITE);
        fadeTransition.play();
        idc.setEditable(false);
         patientc.setEditable(true);
        medecinc.setEditable(true);
         datec.setEditable(true);
          imgc.setEditable(true);
        
       
       
        annulec.setDisable(true);
       ajoutc.setDisable(true);
        modifc.setDisable(false);
        suppc.setDisable(true);
      /*  radiosupp.setSelected(false);
       
        radioconsult.setSelected(false);
        radiomodif.setSelected(false);*/
       rdc.setSelected(false);
       sdc.setSelected(false);
        mdc.setSelected(true);
  idc.setText(null);
            patientc.setPromptText(null);

           
            medecinc.setPromptText(null);
            datec.setValue(null);
           
     imgc.setText(null);
    }

    @FXML
    private void checksuppr1(ActionEvent event) {
                    label2.setText("Supprimer   ");

        label2.setLayoutX(120);
          FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), label2);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
         fadeTransition.setCycleCount(Animation.INDEFINITE);
        fadeTransition.play();
        idc.setEditable(false);
         patientc.setEditable(true);
        medecinc.setEditable(true);
         datec.setEditable(true);
          imgc.setEditable(true);
        
       
       
        annulec.setDisable(true);
       ajoutc.setDisable(true);
        modifc.setDisable(true);
        suppc.setDisable(false);
         rdc.setSelected(false);
       sdc.setSelected(true);
        mdc.setSelected(false);
       
  idc.setText(null);
            patientc.setPromptText(null);

           
            medecinc.setPromptText(null);
            datec.setValue(null);
           
     imgc.setText(null);
    }

    @FXML
    private void Jump(ActionEvent event)throws IOException {
        
   
        Parent root = FXMLLoader.load(getClass().getResource("Ordonnance.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Ordonnance");
        stage.show();
    }

    @FXML
    private void annuler1(ActionEvent event) {
    }

    @FXML
    private void gotostat(ActionEvent event) throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("Stats.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("STATS");
        stage.show();
    }
    
    
     private void envoy(int id) throws AddressException, MessagingException, javax.mail.MessagingException {
         String from="mohamedabdelatif.aouadh@esprit.tn";
       String password="201JMT3405";
        String to="mohamedabdelatif.aouadh@esprit.tn";
          String sujet="Suppression";
            String message="la Consultation "+id+" a été supprimer";
       String host="smtp.gmail.com";
       
       Properties props=System.getProperties();
       props.put("mail.smtp.auth", true);
       props.put("mail.smtp.starttls.enable", true);
       props.put("mail.smtp.host", host);
       props.put("mail.smtp.port", "587");
           //Create a session with account credentials
        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });
       
           MimeMessage m=new MimeMessage(session);
            m.setFrom(new InternetAddress(from));
            m.addRecipient(MimeMessage.RecipientType.TO,new InternetAddress(to));
            m.setSubject(sujet);
            m.setText(message);
           
           
            Transport.send(m);
         
             /* Message m=new MimeMessage(session);
                m.setFrom(new InternetAddress(from));
            m.addRecipient(MimeMessage.RecipientType.TO,new InternetAddress(to));
            m.setSubject(subject.getText());
            m.setText(msg.getText());
            Transport.send(m);
          */
            //sentBoolValue.setVisible(true);
            System.out.println("message envoyée");
               JOptionPane.showMessageDialog(null, "Mail envoyée");
    }

    @FXML
    private void jumpii(ActionEvent event) throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("Mail.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Ordonnance");
        stage.show();
    }

    @FXML
    private void gotosms(ActionEvent event) throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("Sms.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Sms");
        stage.show();
    }
    
    @FXML
    private void recherche() throws SQLException {
          ConsultationCRUD sa = new ConsultationCRUD();
     
       colidc.setCellValueFactory(new PropertyValueFactory<>("id"));
            colpc.setCellValueFactory(new PropertyValueFactory<>("patient_id"));
            colmc.setCellValueFactory(new PropertyValueFactory<>("medecin_id"));
            coldc.setCellValueFactory(new PropertyValueFactory<>("datedeconsultation"));
            colimgc.setCellValueFactory(new PropertyValueFactory<>("image"));
            
           ObservableList<Consultation> list = sa.getConsultationList();
             tablec.setItems(list);
        //ObservableList<Article> list = FXCollections.observableArrayList();

        // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Consultation> filteredData = new FilteredList<>(list, b -> true);
        // 2. Set the filter Predicate whenever the filter changes.
        ttrech.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate((Consultation rdv) -> {
                // If filter text is empty, display all persons.

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(rdv.getId()).indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches libelle
                }
                
                else  if (String.valueOf(rdv.getPatient_id()).indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches libelle
                } 
                
               /* else if (String.valueOf(rdv.getDatedeconsultation()).indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches libelle
                } */
               
                else {
                    return false; // Does not match.
                }
            });
        });
        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Consultation> sortedData = new SortedList<>(filteredData);
        // 4. Bind the SortedList comparator to the TableView comparator.
        //  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(tablec.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        tablec.setItems(sortedData);
    }
     
     
    }

