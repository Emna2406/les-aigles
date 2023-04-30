/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 /*package desktop.interfaces;






/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktop.interfaces.eng;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import desktop.entities.Offre;
import desktop.interfaces.Home;
import desktop.services.OffreCRUD;
import desktop.tools.MailAPI;
import java.awt.Desktop;
import java.awt.Font;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.mail.MessagingException;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class GestionOffreController implements Initializable {

    @FXML
    private TextField descField;
    private TextField serviceField;
    @FXML
    private TextField TfNbr_places;

    private ObservableList<Offre> offreList;

    private List<Offre> list_categorie;
    OffreCRUD off = new OffreCRUD();
    ObservableList<Offre> data;
    private Offre of;
    
    
    @FXML
    private TableColumn<Offre, Integer> serviceCol;
    @FXML
    private TableColumn<Offre, Integer> nbrCol;
    @FXML
    private Button updatebtn;
    @FXML
    private Button deletebtn;
    @FXML
    private TableView<Offre> offreTab;
    @FXML
    private TableColumn<Offre, String> descriptionCol;
    @FXML
    private Button Addbtn;
    @FXML
    private TextField TfService;
    @FXML
    private Button detailBtn;
    @FXML
    private TextField search;
    @FXML
    private Button Exporter;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setBtn();
        data = FXCollections.observableList(off.display());

        nbrCol.setCellValueFactory(new PropertyValueFactory<>("nbrplaces"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        serviceCol.setCellValueFactory(new PropertyValueFactory<>("idService"));
      //  voirPlusBtn.setCellValueFactory(new PropertyValueFactory<>("plusBtn"));

        offreTab.setItems(data);
        search.textProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != null && (newValue.length() < oldValue.length())) {
                offreTab.setItems(data);
            }
            String value = newValue.toLowerCase();
            ObservableList<Offre> subentries = FXCollections.observableArrayList();
            for (Offre entry : offreTab.getItems()) {
                boolean match = true;
                
                String description = entry.getDescription();
                

                if (!description.toLowerCase().contains(value))
                         {
                    match = false;
                }
                if (match) {
                    subentries.add(entry);
                    
                }
                
            } 
             offreTab.setItems(data);
                 });
        
         


        offreTab.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                of = newSelection;
                Home.of = of;
                setBtn();
            }
        
             
            
        });
    
    
       }
    @FXML
    public void refreshList1() {
        data.clear();
        data = FXCollections.observableList(off.display());

        nbrCol.setCellValueFactory(new PropertyValueFactory<>("nbrplaces"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        serviceCol.setCellValueFactory(new PropertyValueFactory<>("idService"));

        offreTab.setItems(data);

    }

    private void setBtn() {
        if (of == null) {
            updatebtn.setDisable(true);
            deletebtn.setDisable(true);
        } else {
            updatebtn.setDisable(false);
            deletebtn.setDisable(false);
        }
    }

    @FXML
    private void AddEntity(ActionEvent event) {
        OffreCRUD pc = new OffreCRUD();
        String description = descField.getText();
        int nbrplaces = Integer.parseInt(TfNbr_places.getText());
        int idService = Integer.parseInt(serviceField.getText());

        if (description.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("'Description' must be inputted");
            alert.setTitle("Problem");
            alert.setHeaderText(null);
            alert.showAndWait();
        } else if (TfNbr_places.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("'Number of places' must be inputted");
            alert.setTitle("Problem");
            alert.setHeaderText(null);
            alert.showAndWait();
        } else if (serviceField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("'Service ID' must be inputted");
            alert.setTitle("Problem");
            alert.setHeaderText(null);
            alert.showAndWait();
        } else {
            Offre offre = new Offre(description, nbrplaces, idService);
            pc.AddEntity(of);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Added.");
            alert.setHeaderText(null);
            alert.show();
        }
        

        refreshList1();
    }

    @FXML

    private void updateEntity(ActionEvent event) {
        {
        try {
        OffreCRUD pc = new OffreCRUD();
        String description = descField.getText();
        int idService = Integer.parseInt(serviceField.getText());
        int nbrplaces = Integer.parseInt(TfNbr_places.getText());
        pc.update(of.getId(), new Offre(description, idService, nbrplaces));
        MailAPI.sendMail("nouha.ouertani@esprit.tn", "Candidat modifié", "Bravo, la modification a été réussie ");
            } catch (MessagingException ex) {
                Logger.getLogger(GestionOffreController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        refreshList1();
    }

    @FXML

    private void deleteEntity(ActionEvent event) {
        OffreCRUD pc = new OffreCRUD();
        pc.delete(of);
        refreshList1();
    }
    
    @FXML
    private void plusDetail()
    {
        
         try {
            Parent root = FXMLLoader.load(getClass().getResource("DetailOffre.fxml"));

            Stage st = new Stage();
            Scene scene = new Scene(root);
            
            st.setTitle("Detail Offre");
            st.setScene(scene);
            st.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void redirectToHomePage(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage() + ex.getStackTrace());
        }
    }
    
    private String getPath() {
        String userHome = System.getProperty("user.home");
        String fileSeparator = System.getProperty("file.separator");
        String documentsPath = userHome + fileSeparator + "Documents";
        System.out.println("User's documents path: " + documentsPath);
        return documentsPath;
    }

    @FXML
    private void Exporter(ActionEvent event) {
         try {
            //the file path
//            File file = new File("C:\\Users\\user\\Desktop\\image\\file.csv");
                        File file = new File(getPath() + "\\file.csv");

            //if the file not exist create one
            if (!file.exists()) {
                file.createNewFile();
            } else {
                file.delete();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write("description;Nbr-places;ID-service;");
            bw.newLine();
            //loop for jtable rows
            for (int i = 0; i < offreTab.getItems().size(); i++) {
                //loop for jtable column
                for (int j = 0; j < offreTab.getColumns().size(); j++) {
                    bw.write(offreTab.getColumns().get(j).getCellData(i) + ";");
                }
                bw.newLine();
            }
            //close BufferedWriter
            bw.close();
            //close FileWriter 
            fw.close();
            JOptionPane.showMessageDialog(null, "Data Exported");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
   
}
