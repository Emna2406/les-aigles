/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktop.interfaces.eng;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import desktop.entities.Candidat;
import desktop.services.CandidatCRUD;
import desktop.tools.MailAPI;
import java.awt.Desktop;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.mail.MessagingException;

/**
 * FXML Controller class
 *
 * @author msi
 */
public class GestionCandidatController implements Initializable {

    @FXML
    private TextField nomField;
    @FXML
    private TextField prenomField;
    @FXML
    private TextField emailField;
    private Label fileLbl;

    private ObservableList<Candidat> candidatList;
   
    private List<Candidat> list_categorie;
    CandidatCRUD su = new CandidatCRUD();
    File cv;
    
    
    ObservableList<Candidat> data;
    private Candidat cand;
    @FXML
    private Button btnupdate;
    @FXML
    private Button btndelete;
    @FXML
    private Button btnadd;
    @FXML
    private Button btnCv ;
    @FXML
    private TableColumn<Candidat, String> nomCol;
    @FXML
    private TableColumn<Candidat, String> prenomCol;
    @FXML
    private TableColumn<Candidat, String> emailCol;
    @FXML
    private TableColumn<Candidat, Integer> idcCol;
    @FXML
    private TableView<Candidat> candidatTable;
    @FXML
    private TextField trecherche;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setBtn();
        data = FXCollections.observableList(su.display());

        idcCol.setCellValueFactory(new PropertyValueFactory<>("idCand"));
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));

        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        candidatTable.setItems(data);
        
         /*trecherche.textProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != null && (newValue.length() < oldValue.length())) {
                candidatTable.setItems(data);
            }
            String value = newValue.toLowerCase();
            ObservableList<Candidat> subentries = FXCollections.observableArrayList();
            for (Candidat entry : candidatTable.getItems()) {
                boolean match = true;
                
                String name = entry.getNom();
                String firstname = entry.getPrenom();
                

                if (!name.toLowerCase().contains(value)&&
                        !firstname.toLowerCase().contains(value))
                         {
                    match = false;
                }
                if (match) {
                    subentries.add(entry);
                    
                }
                candidatTable.setItems(data);
            } 
                
                 });*/

        candidatTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
               cand = newSelection;
               setBtn();
           }
            

       // });
        
        /*trecherche.textProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != null && (newValue.length() < oldValue.length())) {
                candidatTable.setItems(data);
            }
            String value = newValue.toLowerCase();
            ObservableList<Candidat> subentries = FXCollections.observableArrayList();
            
            for (Candidat entry : candidatTable.getItems()) {
                boolean match = true;
                
                String name = entry.getNom();
                String firstname = entry.getPrenom();
                
                

                if (!name.toLowerCase().contains(value)&&
                        !firstname.toLowerCase().contains(value))
                         {
                    match = false;
                }
                if (match) {
                    subentries.add(entry);
                    
                }
                
            } 
            candidatTable.setItems(data);*/
             
                 }); 

    }
    
    

    public void refreshList() {
        data.clear();
        data = FXCollections.observableList(su.display());

        idcCol.setCellValueFactory(new PropertyValueFactory<>("idCand"));
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        candidatTable.setItems(data);

    }

    private void setBtn() {
        if (cand == null) {
            btnupdate.setDisable(true);
            btndelete.setDisable(true);
        } else {
            btnupdate.setDisable(false);
            btndelete.setDisable(false);
        }
    }

    @FXML
    private void addCandidat(ActionEvent event) {
        {
            try {
        CandidatCRUD pc = new CandidatCRUD();
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String email = emailField.getText();

        if (nom.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("'Nom' must be inputed");
            alert.setTitle("Problem");
            alert.setHeaderText(null);
            alert.showAndWait();
        } else if (prenom.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("'prenom' must be inputed");
            alert.setTitle("Problem");
            alert.setHeaderText(null);
            alert.showAndWait();
        } else if (email.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("'email' must be inputed");
            alert.setTitle("Problem");
            alert.setHeaderText(null);
            alert.showAndWait();
        } else {
            Candidat p = new Candidat(nom, prenom, email);
            pc.AddEntity(p);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Added .");
            alert.setHeaderText(null);
            alert.show();
            
        }
       String message = "Bravo, le candidat " + nom + " " + prenom + " a été ajouté avec succès.";
                MailAPI.sendMail("nouha.ouertani@esprit.tn", "Candidat Ajouté", message);
            } catch (MessagingException ex) {
                Logger.getLogger(GestionCandidatController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        refreshList();
    }

    @FXML
    private void updateCandidat(ActionEvent event) {
        CandidatCRUD pc = new CandidatCRUD();
        String nom = nomField.getText();
        String prenom = prenomField.getText();
  

        String email = emailField.getText();
        pc.update(cand.getIdCand(), new Candidat(nom, prenom, email));
        refreshList();
    }

    @FXML
    private void deleteCandidat(ActionEvent event) {
        CandidatCRUD pc = new CandidatCRUD();
        pc.delete(cand);
        refreshList();
    }

    @FXML
    private void redirectToHomePage(ActionEvent event) {
        try {
            String path = "/desktop/interfaces/";
            Parent root = FXMLLoader.load(getClass().getResource("/desktop/interfaces/Home.fxml"));
            //Parent root = FXMLLoader.load(getClass().getResource("GestionCandidat.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage() + ex.getStackTrace());
        }
    }

@FXML
    private void uploadbtn(ActionEvent event) {
        try {
           // byte[] pdfBytes = generatePDF();
            FileChooser fileChooser = new FileChooser();
            cv = fileChooser.showOpenDialog(btnCv.getScene().getWindow());
            fileLbl.setText(cv.getName());
            //cv.renameTo(new File(cv.getParent()+"/"));
            
            
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /*  
    private byte[] generatePDF() throws Exception {
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);
        document.open();

        // Define font styles
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.BLACK);
        
        Paragraph title = new Paragraph("Offre List");
        title.setFont(FontFactory.getFont(FontFactory.TIMES_BOLD, 24, BaseColor.RED));
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);
        // Add table headers
        PdfPTable table = new PdfPTable(3);
        table.addCell(new Phrase("ID", headerFont));
        table.addCell(new Phrase("Name", headerFont));
        table.addCell(new Phrase("Category", headerFont));

        // Define font styles for table data
        Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK);

        // Add table data
        for (Academy academy : academyList) {
            table.addCell(new Phrase(String.valueOf(academy.getId()), dataFont));
            table.addCell(new Phrase(academy.getName(), dataFont));
            table.addCell(new Phrase(academy.getCategory(), dataFont));
        }

        // Add style to table
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        document.add(table);
        document.close();

        return baos.toByteArray();
    }
    
    public void generateCategoryChart(List<Academy> academyList) {
        if (academyList == null || academyList.isEmpty()) {
            throw new IllegalArgumentException("Academy list cannot be null or empty");
        }
        // Create a map to store the count of academies in each category
        Map<String, Integer> categoryCount = new HashMap<>();
        for (Academy academy : academyList) {
            String category = academy.getCategory();
            categoryCount.put(category, categoryCount.getOrDefault(category, 0) + 1);
        }
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (String category : categoryCount.keySet()) {
            dataset.addValue(categoryCount.get(category), "Academies", category);
        }
        JFreeChart chart = ChartFactory.createBarChart(
                "Number of Academies by Category",
                "Category",
                "Number of Academies",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false);
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        CategoryItemRenderer renderer = plot.getRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(500, 270));
        JFrame frame = new JFrame("Category Chart");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(chartPanel);
        frame.pack();
        frame.setVisible(true);
    }*/

    @FXML
    private void rechercher(ActionEvent event)throws SQLException  {
         
         CandidatCRUD sa = new CandidatCRUD();
       

        idcCol.setCellValueFactory(new PropertyValueFactory<>("idCand"));
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        candidatTable.setItems(data);

       
        //appeler fx de recherche
            ObservableList<Candidat> list = sa.listerCandidats();
            candidatTable.setItems(list);

        //ObservableList<Article> list = FXCollections.observableArrayList();
        // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Candidat> filteredData = new FilteredList<>(list,  b -> true);
        // 2. Set the filter Predicate whenever the filter changes.
        trecherche.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate((Candidat part) -> {
                // If filter text is empty, display all persons.

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Compare first name and last name of every person with filter text.
               String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(part.getNom()).indexOf(lowerCaseFilter) != -1) {                    
                  return true; // Filter matches prénom
                    } else {
                  return false; // Does not match.
                    }

            });
        });
        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Candidat> sortedData = new SortedList<>(filteredData);
        // 4. Bind the SortedList comparator to the TableView comparator.
        //  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(candidatTable.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        candidatTable.setItems(sortedData);
    }
}
