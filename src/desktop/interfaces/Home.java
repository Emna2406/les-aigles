/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktop.interfaces;

import desktop.entities.Offre;
import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author USER
 */
public class Home extends Application {
    
    public static Offre of;
    public static int lang = 1;
    
    @Override
    public void start(Stage primaryStage) {
        
        try {
           //Parent root = FXMLLoader.load(getClass().getResource("desktop/interfaces/eng/GestionOffre.fxml"));
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/desktop/interfaces/eng/GestionCandidat.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            
            primaryStage.setTitle("Main application!");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
   
  
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
    
