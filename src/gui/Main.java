/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import services.UserService;
import utils.Session;

/**
 *
 * @author mega pc
 */
public class Main extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("locationadmin.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
     ///alter table reservation add CONSTRAINT fk_id_hotel FOREIGN KEY (ism colone ili inti samitou) REFERENCES hotel (id mte3ou fi tableau hotel) ;
        

        launch(args);
        
        
        
        
      
       
    }

}
