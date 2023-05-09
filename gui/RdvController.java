/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connexion3a41.gui;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import edu.connexion3a41.entities.Rdv;
import edu.connexion3a41.interfaces.EntityCRUD;
import edu.connexion3a41.services.RdvCRUD;
import edu.connexion3a41.tools.MyDB;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.Properties;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
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
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.controlsfx.control.Notifications;
/**
 * FXML Controller class
 *
 * @author tha3leb
 */
public class RdvController implements Initializable {

    @FXML
    private TextField tnompatient;
    @FXML
    private TextField tadr;
    @FXML
    private TextField tnumtel;
    @FXML
    private TextField tmessage;
    @FXML
    private DatePicker tdate;
    @FXML
    private Button btnajout;
    @FXML
    private Button btnsupp;
    @FXML
    private Button btnmodif;
    @FXML
    private ComboBox<String> tid_medecin;
    @FXML
    private TableColumn<Rdv, String> col_nompatient;
    @FXML
    private TableColumn<Rdv, Date> col_date;
    @FXML
    private TableColumn<Rdv, String> col_numtel;
    @FXML
    private TableColumn<Rdv, String> col_adr;
    @FXML
    private TableColumn<Rdv, Integer> col_medecin;
    @FXML
    private TableColumn<Rdv, String> col_message;
    @FXML
    private TableView<Rdv> tabrdv;
    @FXML
    private TableColumn<Rdv, Integer> col_id;
    @FXML
    private TextField tidrdv;
    @FXML
    private Button btnannuler;
    @FXML
    private TextField trecherche;
    @FXML
    private Button ttrie;
    @FXML
    private Label errortnompatient;
    @FXML
    private Label errormsg;
    @FXML
    private Label errornumtel;
    @FXML
    private Label errormail;
    @FXML
    private Label errordate;
    @FXML
    private RadioButton btnajoutcheck;
    @FXML
    private Label label;
    @FXML
    private RadioButton btnmodifcheck;
    @FXML
    private RadioButton btnsuppcheck;
    @FXML
    private Label label1;
    @FXML
    private Button btngvisite;
    @FXML
    private Button envmail;
    @FXML
    private Button stat;
    @FXML
    private Button sms;

    /**
     * Initializes the controller class.
     */
    
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // partie mte tableau affichage
          try {
      
               btnajout.setDisable(true);
       btnmodif.setDisable(true);
        btnsupp.setDisable(true);
        btnannuler.setDisable(true);
            System.out.println("helllloa") ;
        RdvCRUD sa = new  RdvCRUD();
        col_id.setCellValueFactory(new PropertyValueFactory<>("idrdv"));
        col_nompatient.setCellValueFactory(new PropertyValueFactory<>("nompatient"));
            col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
            col_numtel.setCellValueFactory(new PropertyValueFactory<>("numtel"));
            col_message.setCellValueFactory(new PropertyValueFactory<>("message"));
            col_adr.setCellValueFactory(new PropertyValueFactory<>("adr"));
            
                     col_medecin.setCellValueFactory(new PropertyValueFactory<>("medecin_id"));
           ObservableList<Rdv> list = sa.getRdvList();
             tabrdv.setItems(list);
             // partie affichage mte combobox yani kol mthel page yjiblk affichage fi tableau w fi combo
         } catch (SQLException ex) {
            Logger.getLogger(RdvCRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
          
            PreparedStatement pst = null;
        Connection cnx = MyDB.getInstance().getConnexion();
        ObservableList<String> liste_medecin = FXCollections.observableArrayList();
        String req = "SELECT nom from medecin";
      
         
        
        try {
            pst = cnx.prepareStatement(req);
        } catch (SQLException ex) {
            Logger.getLogger(RdvController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ResultSet rs = null;
       
        
        try {
            rs = pst.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(RdvController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       
        try {
            while (rs.next()) {
                
                liste_medecin .add(rs.getString("nom"));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(RdvController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
        tid_medecin.setItems(liste_medecin );
        
    } 
     public boolean estRedondant(String nompatient) {
    Connection connexion = null;
    PreparedStatement statement = null;
    ResultSet resultat = null;
    boolean estRedondant = false;

    try {
       
        statement = connexion.prepareStatement("SELECT COUNT(*) FROM rdv  WHERE nompatient = ? ");
        statement.setString(1, nompatient);
      

        if (resultat.next()) {
            int count = resultat.getInt(1);
            if (count > 0) {
                estRedondant = true;
            }
        }
    } catch (SQLException e) {
        /* gérer l'exception */
    } finally {
        /* fermer la connexion, le statement et le resultSet */
    }

    return estRedondant;
}
    
    @FXML
    private void ajoutrdv(javafx.event.ActionEvent mouseEvent)throws SQLException {
       /* if (estRedondant(tnompatient.getText())==true){
              JOptionPane.showMessageDialog(null, " Rdv est deja reservée ");
        }
        else*/if ((mouseEvent.getSource() == btnajout)&&(!tnompatient.getText().isEmpty())&&(!tnumtel.getText().isEmpty())&&(!tmessage.getText().isEmpty())&&(!tadr.getText().isEmpty())) {
          

            RdvCRUD as = new  RdvCRUD();
            as.ajouter(new Rdv(tnompatient.getText(),Date.valueOf(tdate.getValue()),tnumtel.getText(),tmessage.getText(),tadr.getText(),tid_medecin.getSelectionModel().getSelectedIndex()));
           
       
          
        
   
           //notifier
     JOptionPane.showMessageDialog(null, " Rdv ajouter ");
     
     Notifications notificationBuilder = Notifications.create()
     .title("rdv ajouter")
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
    
             
             
             

            col_id.setCellValueFactory(new PropertyValueFactory<>("idrdv"));
          col_nompatient.setCellValueFactory(new PropertyValueFactory<>("nompatient"));
            col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
            col_numtel.setCellValueFactory(new PropertyValueFactory<>("numtel"));
            col_message.setCellValueFactory(new PropertyValueFactory<>("message"));
            col_adr.setCellValueFactory(new PropertyValueFactory<>("adr"));
            
                     col_medecin.setCellValueFactory(new PropertyValueFactory<>("id_medecin"));
       
             ObservableList<Rdv> list = as.getRdvList();
             tabrdv.setItems(list);
          
            
            

          tnompatient.setText(null);

            
            tdate.setValue(null);
            tnumtel.setText(null);
            tmessage.setText(null);
     tadr.setText(null);
                        tid_medecin.setPromptText(null);
            
           }else {
           JOptionPane.showMessageDialog(null, " y a un ou des champs n'est pas valide ");
        }
        
    }

    @FXML
    private void supprimer_rdv(javafx.event.ActionEvent mouseEvent) throws SQLException, AddressException, MessagingException {
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
    RdvCRUD as = new RdvCRUD();
             as.supprimer(new Rdv(Integer.parseInt(tidrdv.getText())));
            JOptionPane.showMessageDialog(null, "rdv Supprimée");
            //mail
              String from="amen.bejaoui@esprit.tn";
       String password="22202227**";
       String to =tadr.getText();
       String sujet="rdv supprimer docteur ";
        SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
       String message="bonjour docteur votre rdv a été suuprimer de M/Mme  "+tnompatient.getText();
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
            //sentBoolValue.setVisible(true);
            System.out.println(" mail mouchard activer");
               JOptionPane.showMessageDialog(null, "mail mouchard activer");
            
            
            
            //fin mail
           
            col_id.setCellValueFactory(new PropertyValueFactory<>("idrdv"));
          col_nompatient.setCellValueFactory(new PropertyValueFactory<>("nompatient"));
            col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
            col_numtel.setCellValueFactory(new PropertyValueFactory<>("numtel"));
            col_message.setCellValueFactory(new PropertyValueFactory<>("message"));
            col_adr.setCellValueFactory(new PropertyValueFactory<>("adr"));
            
                     col_medecin.setCellValueFactory(new PropertyValueFactory<>("id_medecin"));
       
             ObservableList<Rdv> list = as.getRdvList();
             tabrdv.setItems(list);
          
            
            

          tnompatient.setText(null);

            
            tdate.setValue(null);
            tnumtel.setText(null);
            tmessage.setText(null);
     tadr.setText(null);
                        tid_medecin.setPromptText(null);
} else {
    col_id.setCellValueFactory(new PropertyValueFactory<>("idrdv"));
          col_nompatient.setCellValueFactory(new PropertyValueFactory<>("nompatient"));
            col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
            col_numtel.setCellValueFactory(new PropertyValueFactory<>("numtel"));
            col_message.setCellValueFactory(new PropertyValueFactory<>("message"));
            col_adr.setCellValueFactory(new PropertyValueFactory<>("adr"));
            
                     col_medecin.setCellValueFactory(new PropertyValueFactory<>("id_medecin"));
       
            
}

           
            

        }
    }
    
    @FXML
     void getSelected(MouseEvent event) {
         int index = tabrdv.getSelectionModel().getSelectedIndex();
            if (index <= -1) {

                return;
            }
         // idprop.setText(col_idprop.getCellData(index).toString());
            tidrdv.setText(col_id.getCellData(index).toString());
          // comidpromp.promptTextProperty(col_idprop.getCellData.toString());
          tnompatient.setText(col_nompatient.getCellData(index).toString());
          tnumtel.setText(col_numtel.getCellData(index).toString());
          tmessage.setText(col_message.getCellData(index).toString());
          tadr.setText(col_adr.getCellData(index).toString());
          tid_medecin.setPromptText(col_medecin.getCellData(index).toString());
           tdate.setPromptText(col_date.getCellData(index).toLocalDate().toString());
     }  

    @FXML
    private void modifier(javafx.event.ActionEvent mouseEvent) throws SQLException {
       
   
         if (mouseEvent.getSource() == btnmodif) {
          

            RdvCRUD as = new  RdvCRUD();
            as.modifier(new Rdv(Integer.parseInt(tidrdv.getText()),tnompatient.getText(),Date.valueOf(tdate.getValue()),tnumtel.getText(),tmessage.getText(),tadr.getText(),tid_medecin.getSelectionModel().getSelectedIndex()));
           
        System.out.println("ttttttttt");
          
        
   
           
     JOptionPane.showMessageDialog(null, " Rdv modifier ");
             

            col_id.setCellValueFactory(new PropertyValueFactory<>("idrdv"));
          col_nompatient.setCellValueFactory(new PropertyValueFactory<>("nompatient"));
            col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
            col_numtel.setCellValueFactory(new PropertyValueFactory<>("numtel"));
            col_message.setCellValueFactory(new PropertyValueFactory<>("message"));
            col_adr.setCellValueFactory(new PropertyValueFactory<>("adr"));
            
                     col_medecin.setCellValueFactory(new PropertyValueFactory<>("id_medecin"));
       
             ObservableList<Rdv> list = as.getRdvList();
             
             tabrdv.setItems(list);
          
            
            

          tnompatient.setText(null);

            
            tdate.setValue(null);
            tnumtel.setText(null);
            tmessage.setText(null);
     tadr.setText(null);
                        tid_medecin.setPromptText(null);
            
           }
    
    }

    @FXML
    private void annuler(ActionEvent event) {
        
         try {
            System.out.println("helllloa") ;
        RdvCRUD sa = new  RdvCRUD();
        col_id.setCellValueFactory(new PropertyValueFactory<>("idrdv"));
        col_nompatient.setCellValueFactory(new PropertyValueFactory<>("nompatient"));
            col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
            col_numtel.setCellValueFactory(new PropertyValueFactory<>("numtel"));
            col_message.setCellValueFactory(new PropertyValueFactory<>("message"));
            col_adr.setCellValueFactory(new PropertyValueFactory<>("adr"));
            
                     col_medecin.setCellValueFactory(new PropertyValueFactory<>("medecin_id"));
           ObservableList<Rdv> list = sa.getRdvList();
             tabrdv.setItems(list);
             
           ;
           tadr .setStyle("-fx-border-color:white ; -fx-border-width:2px;");
            tnompatient .setStyle("-fx-border-color:white ; -fx-border-width:2px;");
             tnumtel .setStyle("-fx-border-color:white ; -fx-border-width:2px;");
              tmessage .setStyle("-fx-border-color:white ; -fx-border-width:2px;");
          
            
            

          tnompatient.setText(null);

            
            tdate.setValue(null);
            tnumtel.setText(null);
            tmessage.setText(null);
     tadr.setText(null);
                        tid_medecin.setPromptText(null);
         } catch (SQLException ex) {
            Logger.getLogger(RdvCRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
          
            PreparedStatement pst = null;
        Connection cnx = MyDB.getInstance().getConnexion();
        ObservableList<String> liste_medecin = FXCollections.observableArrayList();
        String req = "SELECT nom from medecin";
      
         
        
        try {
            pst = cnx.prepareStatement(req);
        } catch (SQLException ex) {
            Logger.getLogger(RdvController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ResultSet rs = null;
       
        
        try {
            rs = pst.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(RdvController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       
        try {
            while (rs.next()) {
                
                liste_medecin .add(rs.getString("nom"));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(RdvController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
        tid_medecin.setItems(liste_medecin );
    }

    @FXML
    private void recherche() throws SQLException {
          RdvCRUD sa = new RdvCRUD();
     
      col_id.setCellValueFactory(new PropertyValueFactory<>("idrdv"));
        col_nompatient.setCellValueFactory(new PropertyValueFactory<>("nompatient"));
            col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
            col_numtel.setCellValueFactory(new PropertyValueFactory<>("numtel"));
            col_message.setCellValueFactory(new PropertyValueFactory<>("message"));
            col_adr.setCellValueFactory(new PropertyValueFactory<>("adr"));
            
                     col_medecin.setCellValueFactory(new PropertyValueFactory<>("medecin_id"));
                     //appeler fx de recherche 
           ObservableList<Rdv> list = sa.getRdvList();
             tabrdv.setItems(list);
        //ObservableList<Article> list = FXCollections.observableArrayList();

        // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Rdv> filteredData = new FilteredList<>(list, b -> true);
        // 2. Set the filter Predicate whenever the filter changes.
        trecherche.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate((Rdv rdv) -> {
                // If filter text is empty, display all persons.

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(rdv.getNompatient()).indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches libelle
                } 
               
                else {
                    return false; // Does not match.
                }
            });
        });
        // 3. Wrap the FilteredList in a SortedList. 
        SortedList<Rdv> sortedData = new SortedList<>(filteredData);
        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(tabrdv.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        tabrdv.setItems(sortedData);
    }

    @FXML
    private void trie(javafx.event.ActionEvent mouseEvent) throws SQLException {
         if (mouseEvent.getSource() == ttrie) {
           
           RdvCRUD sa = new  RdvCRUD();
   
      col_id.setCellValueFactory(new PropertyValueFactory<>("idrdv"));
        col_nompatient.setCellValueFactory(new PropertyValueFactory<>("nompatient"));
            col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
            col_numtel.setCellValueFactory(new PropertyValueFactory<>("numtel"));
            col_message.setCellValueFactory(new PropertyValueFactory<>("message"));
            col_adr.setCellValueFactory(new PropertyValueFactory<>("adr"));
            
                     col_medecin.setCellValueFactory(new PropertyValueFactory<>("medecin_id"));
                     //appeler fx trie
           ObservableList<Rdv> list = sa.triedate();
             tabrdv.setItems(list);
        }
    }
  
    @FXML
    void tnompatient (KeyEvent event) {
        
if ((tnompatient.getText().matches("[0-9]")) || (tnompatient.getText().length() == 0)) {
            tnompatient.setStyle("-fx-border-color:red ; -fx-border-width:2px;");
            new animatefx.animation.Shake(tnompatient).play();
            errortnompatient.setText("nom de patient doit etre non null et  chaine  ");

        } else if ((tnompatient.getText().matches("[A-Z,a-z]"))) {
            tnompatient.setStyle(null);
            errortnompatient.setText(null);
             tnompatient.setStyle("-fx-border-color:green ; -fx-border-width:2px;");
        }
    }

    @FXML
    private void messagevalid(KeyEvent event) {
        
        
if ((tmessage .getText().matches("[0-9]")) || (tmessage .getText().length() == 0)) {
           tmessage .setStyle("-fx-border-color:red ; -fx-border-width:2px;");
            new animatefx.animation.Shake(tmessage ).play();
           errormsg.setText("Le mesage doit etre non null et chaine  ");

        } else if ((tmessage .getText().matches("[A-Z,a-z]"))) {
            tmessage .setStyle(null);
            errormsg.setText(null);
              tmessage.setStyle("-fx-border-color:green ; -fx-border-width:2px;");
        }
    
    }

    @FXML
    private void numerovalid(KeyEvent event) {
        if ((tnumtel .getText().matches("[0-9]")) ) {
            tnumtel .setStyle(null);
            errornumtel.setText(null);
              tnumtel.setStyle("-fx-border-color:green ; -fx-border-width:2px;");

        } 
         else if ((tnumtel .getText().matches("[A-Z,a-z]")|| (tnumtel .getText().length() == 0))) {
       
            tnumtel .setStyle("-fx-border-color:red ; -fx-border-width:2px;");
            new animatefx.animation.Shake(tnumtel ).play();
           errornumtel.setText("Le numero doit etre numerique et egale 8 chiffres  ");
        }
    }

    @FXML
    private void mailvalid(KeyEvent event) {
        
   
            // Vérifier que le nouvel input est une suppression ou que le nouveau texte correspond à une adresse e-mail valide
            if (isValidEmail(tadr.getText())) {
 
                   errormail.setText("mail valide ");
                    tadr .setStyle("-fx-border-color:green ; -fx-border-width:2px;");
            }else{errormail.setText("mail Invalide ");
            tadr .setStyle("-fx-border-color:red ; -fx-border-width:2px;");
            new animatefx.animation.Shake(tadr ).play();
            
            }
            // Si le nouveau texte n'est pas valide, ignorer le changement
          
           
        
   
    
    
    }
       private boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
        return pattern.matcher(email).matches();
    }
       
         public static boolean isValidDate(String dateString, String dateFormat) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
        try {
            LocalDate.parse(dateString, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
    @FXML
    private void datevalid(ActionEvent event) {
      

// Validation de base pour s'assurer que la date est valide
tdate.focusedProperty().addListener((observable, oldValue, newValue) -> {
    if (!newValue) {
        if (!isValidDate(tdate.getValue())) {
            errordate.setText("Date invalide");
             new animatefx.animation.Shake(tdate ).play();
            tdate .setStyle("-fx-border-color:red ; -fx-border-width:2px;");
        } else {
            errordate.setText("");
             tdate .setStyle("-fx-border-color:white ; -fx-border-width:2px;");
        }
    }
});

// Vérification de la validité de la date

    }

    @FXML
    private void checkajout(ActionEvent event) {
           label.setText("Ajout   ");

        label.setLayoutX(120);
          FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), label);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
         fadeTransition.setCycleCount(Animation.INDEFINITE);
        fadeTransition.play();
        tidrdv.setEditable(false);
         tnompatient.setEditable(true);
        tdate.setEditable(true);
        tadr.setEditable(true);
        tmessage.setEditable(true);
        tid_medecin.setEditable(true);
       
        
        btnajout.setDisable(false);
       btnmodif.setDisable(true);
        btnsupp.setDisable(true);
        btnannuler.setDisable(false);
      /*  radiosupp.setSelected(false);
       
        radioconsult.setSelected(false);
        radiomodif.setSelected(false);*/
  tidrdv.setText(null);
            tnompatient.setText(null);

            
            tdate.setValue(null);
            tnumtel.setText(null);
            tmessage.setText(null);
     tadr.setText(null);
                        tid_medecin.setPromptText(null);
                                   tadr .setStyle("-fx-border-color:white ; -fx-border-width:2px;");
            tnompatient .setStyle("-fx-border-color:white ; -fx-border-width:2px;");
             tnumtel .setStyle("-fx-border-color:white ; -fx-border-width:2px;");
              tmessage .setStyle("-fx-border-color:white ; -fx-border-width:2px;");
                        
                      
        
    }

    @FXML
    private void checkmodif(ActionEvent event) {
         label.setText("Modifier   ");
        label.setLayoutX(120);
         label.setLayoutX(120);
           FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), label);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
         fadeTransition.setCycleCount(Animation.INDEFINITE);
        fadeTransition.play();
        tidrdv.setEditable(false);
         tnompatient.setEditable(true);
        tdate.setEditable(true);
        tadr.setEditable(true);
        tmessage.setEditable(true);
        tid_medecin.setEditable(true);
       
        
        btnajout.setDisable(true);
       btnmodif.setDisable(false);
        btnsupp.setDisable(true);
        btnannuler.setDisable(false);
      /*  radiosupp.setSelected(false);
       
        radioconsult.setSelected(false);
        radiomodif.setSelected(false);*/
  tidrdv.setText(null);
            tnompatient.setText(null);

            
            tdate.setValue(null);
            tnumtel.setText(null);
            tmessage.setText(null);
     tadr.setText(null);
                        tid_medecin.setPromptText(null);
                                   tadr .setStyle("-fx-border-color:white ; -fx-border-width:2px;");
            tnompatient .setStyle("-fx-border-color:white ; -fx-border-width:2px;");
             tnumtel .setStyle("-fx-border-color:white ; -fx-border-width:2px;");
              tmessage .setStyle("-fx-border-color:white ; -fx-border-width:2px;");
    }

    @FXML
    private void checksupp(ActionEvent event) {
          label.setText("Supprimer   ");
         label.setLayoutX(120);
           FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), label);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
         fadeTransition.setCycleCount(Animation.INDEFINITE);
        fadeTransition.play();
        tidrdv.setEditable(false);
         tnompatient.setEditable(true);
        tdate.setEditable(true);
        tadr.setEditable(true);
        tmessage.setEditable(true);
        tid_medecin.setEditable(true);
       
        
        btnajout.setDisable(true);
       btnmodif.setDisable(true);
        btnsupp.setDisable(false);
        btnannuler.setDisable(false);
      /*  radiosupp.setSelected(false);
       
        radioconsult.setSelected(false);
        radiomodif.setSelected(false);*/
  tidrdv.setText(null);
            tnompatient.setText(null);

            
            tdate.setValue(null);
            tnumtel.setText(null);
            tmessage.setText(null);
     tadr.setText(null);
                        tid_medecin.setPromptText(null);
                                   tadr .setStyle("-fx-border-color:white ; -fx-border-width:2px;");
            tnompatient .setStyle("-fx-border-color:white ; -fx-border-width:2px;");
             tnumtel .setStyle("-fx-border-color:white ; -fx-border-width:2px;");
              tmessage .setStyle("-fx-border-color:white ; -fx-border-width:2px;");
    }

    @FXML
    private void gogestionvisite(ActionEvent event) throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("visite.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("clinique");
        stage.show();
    }
   
   
    private boolean isValidDate(LocalDate value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @FXML
    private void envomail(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("mailing.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("clinique");
        stage.show();
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
    private void sms(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("sms.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("clinique");
        stage.show();
    }

  

   
   
   

    
    
     

   

   
}
