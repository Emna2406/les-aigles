/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connexion3a41.gui;

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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static java.util.Collections.list;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author aouadh
 */
public class OrdonnanceController implements Initializable {

    @FXML
    private TextField ido;
    @FXML
    private ComboBox<String> conso;
    @FXML
    private DatePicker dateo;
    @FXML
    private TextField desco;
    @FXML
    private TextField imgo;
    @FXML
    private TableView<Ordonnance> tableo;
    @FXML
    private TableColumn<Ordonnance, Integer> colidco;
    @FXML
    private TableColumn<Ordonnance, String> coldo;
    @FXML
    private TableColumn<Ordonnance, String> coldeso;
    @FXML
    private TableColumn<Ordonnance, String> colimgo;
    @FXML
    private TableColumn<Ordonnance, Integer> colido;
    @FXML
    private Button ajto;
    @FXML
    private Button anno;
    @FXML
    private Button modo;
    @FXML
    private Button suppo;
    private ComboBox<String> test;
    @FXML
    private Label label3;
    @FXML
    private RadioButton ado;
    @FXML
    private RadioButton mdo;
    @FXML
    private RadioButton sdo;
    @FXML
    private Label erreur3;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         // TODO
        try {
            System.out.println("hello") ;
        OrdonnanceCRUD sa = new  OrdonnanceCRUD();
        colido.setCellValueFactory(new PropertyValueFactory<>("id"));
        colidco.setCellValueFactory(new PropertyValueFactory<>("consultation_id"));
            coldo.setCellValueFactory(new PropertyValueFactory<>("date"));
            coldeso.setCellValueFactory(new PropertyValueFactory<>("description"));
            colimgo.setCellValueFactory(new PropertyValueFactory<>("image"));
             // Ajouter la cell factory pour la colonne d'image
       colimgo.setCellFactory(column -> {
    return new TableCell<Ordonnance, String>() {
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
            
           
                    
           ObservableList<Ordonnance> list = sa.getOrdonnanceList();
             tableo.setItems(list);
             // partie affichage mte combobox yani kol mthel page yjiblk affichage fi tableau w fi combo
        }catch (SQLException ex) {
             Logger.getLogger(ConsultationCRUD.class.getName()).log(Level.SEVERE, null, ex);
            }
        
         PreparedStatement pstt = null;
         Connection cnx = MyDB.getInstance().getConnexion();
            ObservableList<String> liste_consultation = FXCollections.observableArrayList();
        String reqq = "SELECT id from consultation";
     
         
       
        try {
            pstt = cnx.prepareStatement(reqq);
        } catch (SQLException ex) {
            Logger.getLogger(OrdonnanceController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        ResultSet rss = null;
       
       
        try {
            rss = pstt.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(OrdonnanceController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       
        try {
            while (rss.next()) {
               
                liste_consultation .add(rss.getString("id"));
               
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrdonnanceController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       
        conso.setItems(liste_consultation);
    
    
    
}
    @FXML
     private void ajoutord(javafx.event.ActionEvent mouseEvent)throws SQLException {
        if (mouseEvent.getSource() ==ajto){
            String date=dateo.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
             FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        File file = fileChooser.showOpenDialog(null);
        String imagePath = file.getAbsolutePath();
            
            OrdonnanceCRUD as = new  OrdonnanceCRUD();
            as.ajouter(new Ordonnance(conso.getSelectionModel().getSelectedIndex(),date,desco.getText(),imagePath));
           
       
         
       
   
           
     JOptionPane.showMessageDialog(null, " ordonnance ajouter ");
             
         colido.setCellValueFactory(new PropertyValueFactory<>("id"));
        colidco.setCellValueFactory(new PropertyValueFactory<>("consultation_id"));
            coldo.setCellValueFactory(new PropertyValueFactory<>("date"));
            coldeso.setCellValueFactory(new PropertyValueFactory<>("description"));
            colimgo.setCellValueFactory(new PropertyValueFactory<>("image"));
            
           
       
             ObservableList<Ordonnance> list = as.getOrdonnanceList();
           
             //tableo.setItems(list);
            tableo.setItems(list);
           
           
ido.setText(null);
          conso.setPromptText(null);
          

           
           
            dateo.setValue(null);
            desco.setText(null);
     imgo.setText(null);
                        
           
        }   
        }
        @FXML
    private void supprimer_ord(javafx.event.ActionEvent mouseEvent) throws SQLException {
         if (mouseEvent.getSource() == suppo) {
           
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
    OrdonnanceCRUD as = new OrdonnanceCRUD();
             as.supprimer(new Ordonnance(Integer.parseInt(ido.getText())));
            JOptionPane.showMessageDialog(null, "Ordonnance Supprimée");
           
          colido.setCellValueFactory(new PropertyValueFactory<>("id"));
        colidco.setCellValueFactory(new PropertyValueFactory<>("consultation_id"));
            coldo.setCellValueFactory(new PropertyValueFactory<>("date"));
            coldeso.setCellValueFactory(new PropertyValueFactory<>("description"));
            colimgo.setCellValueFactory(new PropertyValueFactory<>("image"));
       
            
              ObservableList<Ordonnance> list = as.getOrdonnanceList();
             tableo.setItems(list);
         
           
           

         ido.setText(null);
          conso.setPromptText(null);
          

           
           
            dateo.setValue(null);
            desco.setText(null);
     imgo.setText(null);
    
} else {
   colido.setCellValueFactory(new PropertyValueFactory<>("id"));
        colidco.setCellValueFactory(new PropertyValueFactory<>("consultation_id"));
            coldo.setCellValueFactory(new PropertyValueFactory<>("date"));
            coldeso.setCellValueFactory(new PropertyValueFactory<>("description"));
            colimgo.setCellValueFactory(new PropertyValueFactory<>("image"));
       
           
}

           
           

        }
    }
   
  

    @FXML
    private void modifierord(javafx.event.ActionEvent mouseEvent) throws SQLException {
       
   
         if (mouseEvent.getSource() == modo) {
          String date=dateo.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
          FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        File file = fileChooser.showOpenDialog(null);
        String imagePath = file.getAbsolutePath();

            OrdonnanceCRUD as = new  OrdonnanceCRUD();
            as.modifier(new Ordonnance(Integer.parseInt(ido.getText()),conso.getSelectionModel().getSelectedIndex(),date,desco.getText(),imagePath));
           
        System.out.println("ttttttttt");
         
       
   
           
     JOptionPane.showMessageDialog(null, " Ordonnance modifier ");
             
 colido.setCellValueFactory(new PropertyValueFactory<>("id"));
        colidco.setCellValueFactory(new PropertyValueFactory<>("consultation_id"));
            coldo.setCellValueFactory(new PropertyValueFactory<>("date"));
            coldeso.setCellValueFactory(new PropertyValueFactory<>("description"));
            colimgo.setCellValueFactory(new PropertyValueFactory<>("image"));
            ObservableList<Ordonnance> list = as.getOrdonnanceList();
             tableo.setItems(list);
         
           
           

          ido.setText(null);

           
            conso.setPromptText(null);
            dateo.setValue(null);
            desco.setText(null);
     imgo.setText(null);
                        
           
           }
    
}

    @FXML
    private void med(javafx.scene.input.MouseEvent event) {
        
           int index = tableo.getSelectionModel().getSelectedIndex();
            if (index <= -1) {

                return;
            }
         // idprop.setText(col_idprop.getCellData(index).toString());
            ido.setText(colido.getCellData(index).toString());
          // comidpromp.promptTextProperty(col_idprop.getCellData.toString());
         
           conso.setPromptText(colidco.getCellData(index).toString());
         
           dateo.setPromptText(coldo.getCellData(index));
          desco.setText(coldeso.getCellData(index));
          imgo.setText(colimgo.getCellData(index));}

    @FXML
    private void checkajouter3(ActionEvent event) {
         label3.setText("Ajout   ");

        label3.setLayoutX(120);
         // FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), label1);
       // fadeTransition.setFromValue(1.0);
       // fadeTransition.setToValue(0.0);
       //  fadeTransition.setCycleCount(Animation.INDEFINITE);
        //fadeTransition.play();
        ido.setEditable(false);
         conso.setEditable(true);
        dateo.setEditable(true);
         desco.setEditable(true);
          imgo.setEditable(true);
        
       
       
        anno.setDisable(true);
       ajto.setDisable(false);
        modo.setDisable(true);
        suppo.setDisable(true);
      /*  radiosupp.setSelected(false);
       
        radioconsult.setSelected(false);
        radiomodif.setSelected(false);*/
       ado.setSelected(true);
       sdo.setSelected(false);
        mdo.setSelected(false);
  ido.setText(null);
            conso.setPromptText(null);

           
            dateo.setValue(null);
             desco.setText(null);
           
     imgo.setText(null);
    }

    @FXML
    private void checkmodif3(ActionEvent event) {
         label3.setText("Modifier   ");

        label3.setLayoutX(120);
         // FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), label1);
       // fadeTransition.setFromValue(1.0);
       // fadeTransition.setToValue(0.0);
       //  fadeTransition.setCycleCount(Animation.INDEFINITE);
        //fadeTransition.play();
        ido.setEditable(false);
         conso.setEditable(true);
        dateo.setEditable(true);
         desco.setEditable(true);
          imgo.setEditable(true);
        
       
       
        anno.setDisable(true);
       ajto.setDisable(true);
        modo.setDisable(false);
        suppo.setDisable(true);
      /*  radiosupp.setSelected(false);
       
        radioconsult.setSelected(false);
        radiomodif.setSelected(false);*/
       ado.setSelected(false);
       sdo.setSelected(false);
        mdo.setSelected(true);
  ido.setText(null);
            conso.setPromptText(null);

           
            dateo.setValue(null);
             desco.setText(null);
           
     imgo.setText(null);
    }

    @FXML
    private void checksuppr3(ActionEvent event) {
         label3.setText("Supprimer   ");

        label3.setLayoutX(120);
         // FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), label1);
       // fadeTransition.setFromValue(1.0);
       // fadeTransition.setToValue(0.0);
       //  fadeTransition.setCycleCount(Animation.INDEFINITE);
        //fadeTransition.play();
        ido.setEditable(false);
         conso.setEditable(true);
        dateo.setEditable(true);
         desco.setEditable(true);
          imgo.setEditable(true);
        
       
       
        anno.setDisable(true);
       ajto.setDisable(true);
        modo.setDisable(true);
        suppo.setDisable(false);
      /*  radiosupp.setSelected(false);
       
        radioconsult.setSelected(false);
        radiomodif.setSelected(false);*/
       ado.setSelected(false);
       sdo.setSelected(true);
        mdo.setSelected(false);
  ido.setText(null);
            conso.setPromptText(null);

           
            dateo.setValue(null);
             desco.setText(null);
           
     imgo.setText(null);
    }

    @FXML
    private void testchaine(KeyEvent event) {
      
       
       
if ((desco .getText().matches("[0-9]")) || (desco .getText().length() == 0)) {
           desco .setStyle("-fx-border-color:red ; -fx-border-width:2px;");
            new animatefx.animation.Shake(desco ).play();
           erreur3.setText("Le mesage doit etre non null et chaine  ");

        } else if ((desco .getText().matches("[A-Z,a-z]"))) {
            desco .setStyle(null);
            erreur3.setText(null);
        }
   
    }

    @FXML
    private void jump1(ActionEvent event) throws IOException{
         Parent root = FXMLLoader.load(getClass().getResource("Certificat.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Certificat");
        stage.show();
    }

    @FXML
    private void annuler2(ActionEvent event) {
    }
    }
    
       
    

