/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connexion3a41.gui;

import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import java.net.Authenticator;

//import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.shape.Rectangle;
import javax.mail.PasswordAuthentication;
import javax.mail.internet.*;

import javax.mail.Session;
import javax.mail.Transport;
import javax.swing.JOptionPane;
import javax.xml.validation.Validator;





/**
 * FXML Controller class
 *
 * @author YOSR
 */
public class MailingController implements Initializable {

    @FXML
    private TextField sendto;
   
    @FXML
    private TextArea msg;
    @FXML
    private Button envoyer;
    @FXML
    private Button annuler;
    @FXML
    private Label error;
@FXML
private ComboBox<String> subject;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> subjects = FXCollections.observableArrayList("Reclamation", "Question", "Demande devis");
subject.getItems().addAll(subjects);

          final Tooltip tooltipButton = new Tooltip();
        tooltipButton.setText("Mail du partenaire");
        sendto.setTooltip(tooltipButton); 
        final Tooltip tooltipButton1 = new Tooltip();
        tooltipButton1.setText("Sujet du mail");
        subject.setTooltip(tooltipButton1); 
        final Tooltip tooltipButton2 = new Tooltip();
        tooltipButton2.setText("Message");
        msg.setTooltip(tooltipButton2); 
        final Tooltip tooltipButton3 = new Tooltip();
        tooltipButton3.setText("Envoyée mail");
        envoyer.setTooltip(tooltipButton3); 
        final Tooltip tooltipButton4 = new Tooltip();
        tooltipButton4.setText("Annuler mail");
        annuler.setTooltip(tooltipButton4);
        // TODO
    }    

    @FXML
    private void mailvalid(ActionEvent event) {
      /* validationSupport.registerValidator(sendto, Validator.createEmptyValidator("Veuillez saisir un mail valide SVP"));
        if (!(sendto.getText().matches("[A-Z,a-z]")) || (sendto.getText().length() == 0)) {
            sendto.setStyle("-fx-border-color:red ; -fx-border-width:2px;");
            new animatefx.animation.Shake(sendto).play();
            error.setText("Veuillez saisir un mail valide SVP ");

        } else if (!(sendto.getText().matches("[0-9]"))) {
            sendto.setStyle(null);
            error.setText(null);
        }*/
    }

    @FXML
    private void envoy(ActionEvent event)throws AddressException, MessagingException, javax.mail.MessagingException {
         String from="yosr.teffaha@esprit.tn";
       String password="familleteffaha415417";
       String to =sendto.getText();
       String sujet = subject.getValue();

       String message=msg.getText();
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
            m.setSubject(subject.getValue());
            m.setText(msg.getText());
            
            Transport.send(m);
            //sentBoolValue.setVisible(true);
            System.out.println("message envoyée");
               JOptionPane.showMessageDialog(null, "Mail envoyée");
    }

    @FXML
    private void annul(ActionEvent event) {
            msg.setText(null);
       sendto.setText(null);
       subject.setValue(null); 
       sendto.setStyle(null);
       error.setText(null);
    }

    
    
}
