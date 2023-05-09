/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connexion3a41.gui;

import edu.connexion3a41.entities.User;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.ResourceBundle;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import edu.connexion3a41.tools.Session;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

import edu.connexion3a41.services.UserService;

/**
 * FXML Controller class
 *
 * @author MEGA PC
 */
public class FXMLprofilController implements Initializable {

        @FXML
    private TextField txtFirstname;
    @FXML
    private TextField txtLastname;
    @FXML
    private TextField txtEmail;
    @FXML
    private Button btnSave;
         @FXML
    private TextField txt_tel;

                  @FXML
    private ImageView userImg;
         
                
    
       @FXML           
private Button btnImage;
              UserService udao = new UserService();
               
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
setDis();
      User user = new User();
      user = udao.DisplayById(Session.current_user.getId());
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d"); 
       txtFirstname.setText(user.getName());
       txtLastname.setText(user.getLastname());
       txtEmail.setText(user.getEmail());
       txt_tel.setText(user.getPhone());
       


      
           
    }   


    
    
@FXML
 private void save(ActionEvent event)  {
   

     
     if("Modifier".equals(btnSave.getText())){
              btnSave.setText("Save");
        txtFirstname.setDisable(false);
  txtLastname.setDisable(false);
txtEmail.setDisable(false);
txt_tel.setDisable(false);

     }
     else
     {
         if (!txtEmail.getText().matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")) {
            
            //    lblStatus.setText("Enter valid Email");
                txtEmail.setStyle("-fx-border-clor: red; -fx-text-box-border: red ; -fx-focus-color: red ; -fx-text-fill: red; -fx-font-size: 16px;");

            } else if (!txt_tel.getText().matches("[0-9]+")) {
            
            //    lblStatus.setText("Enter valid phone number");
                txt_tel.setStyle("-fx-border-clor: red; -fx-text-box-border: red ; -fx-focus-color: red ; -fx-text-fill: red; -fx-font-size: 16px;");

            } else { 
             User u = new User();
            u.setName(txtFirstname.getText());
            u.setLastname(txtLastname.getText());
            u.setEmail(txtEmail.getText());
            u.setPhone(txt_tel.getText());
            udao.modifier(u,Session.current_user.getId());
setDis();
            
            
            }
        
         
         
         
     }
 }

public void setDis(){
 btnSave.setText("Modifier");
txtFirstname.setDisable(true);
txtLastname.setDisable(true);
txtEmail.setDisable(true);
txt_tel.setDisable(true);
}
@FXML
 private void logout(ActionEvent event) {
     Session.current_user = null;
                 try {
                Parent page = FXMLLoader.load(getClass().getResource("Login.fxml"));
                Scene scene = new Scene(page);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
     
 }

   
  @FXML
 private void gohome(MouseEvent event) {
                 try {
                Parent page = FXMLLoader.load(getClass().getResource("FXMLuserlist.fxml"));
                Scene scene = new Scene(page);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
     
 }    
 }
