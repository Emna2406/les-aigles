/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connexion3a41.gui;

//import com.mysql.cj.Session;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import java.net.Authenticator;
//import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javax.mail.internet.*;

import javax.mail.Session;
import javax.mail.Transport;



//import javafx.mail.PasswordAuthentication;

import java.util.Properties;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javax.mail.PasswordAuthentication;
import javax.swing.JOptionPane;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
//import sun.plugin2.message.transport.Transport;


/*import javafx.scene.control.Tooltip;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.InternetAddress;*/


/**
 * FXML Controller class
 *
 * @author thebejaoui
 */
public class MailingController implements Initializable {

    @FXML
    private TextField sendto;
    @FXML
    private TextField subject;
    @FXML
    private TextArea msg;
    @FXML
    private Button envoyer;
    @FXML
    private Button annuler;
     @FXML
    private Label error;
         ValidationSupport validationSupport = new ValidationSupport();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
    }    

    @FXML
    private void mailvalid(ActionEvent event) {
          validationSupport.registerValidator(sendto, Validator.createEmptyValidator("Veuillez saisir un mail valide SVP"));
        if (!(sendto.getText().matches("[A-Z,a-z]")) || (sendto.getText().length() == 0)) {
            sendto.setStyle("-fx-border-color:red ; -fx-border-width:2px;");
            new animatefx.animation.Shake(sendto).play();
            error.setText("Veuillez saisir un mail valide SVP ");

        } else if (!(sendto.getText().matches("[0-9]"))) {
            sendto.setStyle(null);
            error.setText(null);
        }
    }

    @FXML
    private void envoy(ActionEvent event)  throws AddressException, MessagingException, javax.mail.MessagingException {
         String from="amen.bejaoui@esprit.tn";
       String password="22202227**";
       String to =sendto.getText();
       String sujet=subject.getText();
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
            m.setSubject(subject.getText());
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
       subject.setText(null); 
       sendto.setStyle(null);
       error.setText(null);
    }
    
}
