package edu.connexion3a41.gui;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import com.jfoenix.controls.JFXButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import edu.connexion3a41.tools.MyDB;
import edu.connexion3a41.services.UserService;
import edu.connexion3a41.entities.User;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import edu.connexion3a41.tools.Session;
public class LoginController implements Initializable {

    @FXML
    private Label lblErrors;

    @FXML
    private TextField txtUsername,txt_email,txt_verifcode;

    @FXML
    private TextField txtPassword,txt_password,txt_password2;

    @FXML
    private Button btnSignin;
    @FXML
    private Button btnSignup,btnSignup1;
    
    @FXML
    private Pane pane1,pane2;  
    public int id;
    /// -- 
    Connection con = null;
    PreparedStatement preparedStatement = null;
    PreparedStatement preparedStatement2 = null;
    ResultSet resultSet = null;
    ResultSet resultSet2 = null;
    ResultSet resultSet3 = null;
     UserService sp = new UserService();

  
     User L = new User();
     
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
           

        pane2.setVisible(false);
        

        
        
        if (con == null) {
            lblErrors.setTextFill(Color.TOMATO);
            lblErrors.setText("Server Error : Check");
        } else {
            lblErrors.setTextFill(Color.GREEN);
            lblErrors.setText("Server is up : Good to go");
        }
    }

    public LoginController() {
        con = MyDB.getInstance().getConnexion();

    }

    public boolean logIn() {

        String username = txtUsername.getText();
        String password = txtPassword.getText();
        boolean bool = false;

        
 
        
         
        if (L.signup(username,password)) {
           
                System.out.println("It matches");

                id = sp.getId(username);
                Session.current_user = new User();
                Session.current_user.setId(id);
                System.out.println("yoooo");
                bool = true;
           
            
            
      
        } else {
            lblErrors.setTextFill(Color.TOMATO);
            lblErrors.setText("Enter Correct Email/Password");
            System.err.println("Wrong Logins --///");
        }

        return bool;

    }

    @FXML
    public void handleButtonAction(MouseEvent event) {

    
       /* if (event.getSource() == btnSignin) {
            if (logIn() == true) {
            if (logIn() == true) {
                try {
                    if (sp.isAdmin(Session.current_user.getId()) == true) {
                Parent page = FXMLLoader.load(getClass().getResource("Home.fxml"));
                Scene scene = new Scene(page);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();              
                    }else{             
                Parent page = FXMLLoader.load(getClass().getResource("Home.fxml"));
                Scene scene = new Scene(page);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setResizable(false);                  
                    }

                } catch (IOException ex) {
            
                 Logger.getLogger(FXMLprofilController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

            }
        }*/
       if (event.getSource() == btnSignin) {
    if (logIn()) {
        try {
            Parent page = FXMLLoader.load(getClass().getResource("Home.fxml"));
            Scene scene = new Scene(page);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);

            if (sp.isAdmin(Session.current_user.getId())) {
                // Do admin-specific actions
            } else {
                // Do non-admin actions
            }

            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLprofilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}


        if (event.getSource() == btnSignup || event.getSource() == btnSignup1 ) {

            try {
                Parent page = FXMLLoader.load(getClass().getResource("Register.fxml"));
                Scene scene = new Scene(page);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            } catch (IOException ex) {
            }

        }

    }
@FXML
 private void forgotpassword(ActionEvent event)  {
  pane1.setVisible(false);
 pane2.setVisible(true);
 }
 @FXML
 private void Signin2(ActionEvent event)  {
 pane1.setVisible(true);
 pane2.setVisible(false);
 }
 
   
 


     
   private  int code=L.rondomcode();

    public int getCode() {
        return code;
    }

    public void setCode() {
       this.code=L.rondomcode();
    }

 
 
 
 
 @FXML
 private void send_code(ActionEvent event)  {
     
           String email = txt_email.getText().trim();
         Pattern pattern = Pattern.compile("([a-z0-9_\\-\\.])+\\@([a-z0-9_\\-\\.])+\\.([a-z]{2,4})$");
        if (email.isEmpty()) {
            Notifications notification = pushNotify("Empty Field", "Please fill in the field");
            notification.showError();
           txt_email.requestFocus();
        } else if(!pattern.matcher(email).matches()) {
            Notifications notification = pushNotify("Invalid Email", "Please Provide a valid email address");
            notification.showError();
            txt_email.requestFocus();
        }else { 
         if (sp.checkEmail(email).contains(email))
     {
   
     Image img = new Image(getClass().getResourceAsStream("images/ok.png"));
     Notifications notification = pushNotify("A code was sent to your email", "Check your email");
     notification.graphic(new ImageView(img));
     notification.show();
    }
     else
     {
          Notifications notification = pushNotify(email +" was not found in our database", "try again");
          notification.showError();
     }
 }
}
    

@FXML
    public void submit(ActionEvent event) {
        
     
       String email = txt_email.getText().trim();
        String text = txt_verifcode.getText().trim();
        String password = txt_password.getText();
        String password2 = txt_password2.getText();
         Pattern pattern = Pattern.compile("([a-z0-9_\\-\\.])+\\@([a-z0-9_\\-\\.])+\\.([a-z]{2,4})$");
                 if (email.isEmpty()) {
            Notifications notification = pushNotify("Empty Field", "Please fill in the field");
            notification.showError();
           txt_email.requestFocus();
        } else  if (text.isEmpty() && password.isEmpty()&& password2.isEmpty()) {
            Notifications notification = pushNotify("Empty Fields", "Please fill in the fields");
            notification.showError();
           txt_verifcode.requestFocus();
//            txt_password.setStyle("-fx-border-color: red;");
//            txt_username.setStyle("-fx-border-color: red;");
        } else
            {
    if (getCode() == Integer.parseInt(text))
  {
      if (password.equals(password2))
     {
        if(!sp.Updatepassword(password,email))
     {
     System.out.println("passwoed updated");
     Image img = new Image(getClass().getResourceAsStream("images/ok.png"));
     Notifications notification = pushNotify("passwoed updated", "back to sign in");
     notification.graphic(new ImageView(img));
     notification.show();
     clear();
     setCode();
 pane1.setVisible(true);
 pane2.setVisible(false);
     }
     else
     {
         System.out.println("passwoed wasn't updated");
         Notifications notification = pushNotify("passwoed wasn't updated", "try again");
          notification.showError();
     }
      }else
      {
         Notifications notification = pushNotify("passwoeds don't match", "try again");
         notification.showError();
      }

      
  }
  else
  {
        System.out.println("Rong code");
        Notifications notification = pushNotify("verifcode don't match", "try again");
        notification.showError();
  }

}



        }




   public void clear() {
        txt_verifcode.clear();
        txt_password.clear();
        txt_password2.clear();
        txt_email.clear();
    }











 public Notifications pushNotify(String title, String text) {
        Notifications notification = Notifications.create()
                .title(title)
                .text(text)
                .hideAfter(Duration.seconds(7))
                .position(Pos.TOP_RIGHT)
                .onAction((ActionEvent e) -> {
                    System.out.println("clicked on notification");
                });
        return notification;
    }

}
