/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connexion3a41.gui;

import edu.connexion3a41.tools.MailAPI;
import edu.connexion3a41.gui.GestionPartenaireController;
import edu.connexion3a41.entities.Produit;
import edu.connexion3a41.services.ProduitCRUD;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.mail.MessagingException;

/**
 * FXML Controller class
 *
 * @author msi
 */
public class GestionProduitController implements Initializable {

    @FXML
    private TextField stockField;

    @FXML
    private TableView<Produit> produitTable;

    private ObservableList<Produit> produitList;
    @FXML
    private TextField nomField;
    @FXML
    private Button btnadd;
    @FXML
    private Button btndelete;
    @FXML
    private Button btnupdate;
    @FXML
    private TableColumn<Produit, Integer> idcol;
    @FXML
    private TableColumn<Produit, String> nomcol;
    @FXML
    private TableColumn<Produit, Integer> stockcol;

    private Produit prod;

    private List<Produit> list_categorie;
    ProduitCRUD su = new ProduitCRUD();
    ObservableList<Produit> data;
    private TableColumn<Produit, Double> rateCol;
    @FXML
    private TextField trecherche;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setBtn();
        data = FXCollections.observableList(su.display());

        idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomcol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        stockcol.setCellValueFactory(new PropertyValueFactory<>("stock"));

        produitTable.setItems(data);

        produitTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                prod = newSelection;
                setBtn();
            }

        });
    }

    public void refreshList() {
        data.clear();
        data = FXCollections.observableList(su.display());

        idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomcol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        stockcol.setCellValueFactory(new PropertyValueFactory<>("stock"));

        produitTable.setItems(data);

    }

    private void setBtn() {
        if (prod == null) {
            btnupdate.setDisable(true);
            btndelete.setDisable(true);
        } else {
            btnupdate.setDisable(false);
            btndelete.setDisable(false);
        }
    }

    /*@FXML
    private void addProduit(ActionEvent event) {
        try {
            ProduitCRUD pc = new ProduitCRUD();
            String nom = nomField.getText();
            int stock = Integer.valueOf(stockField.getText());

            if (nom.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("'Nom' must be inputed");
                alert.setTitle("Problem");
                alert.setHeaderText(null);
                alert.showAndWait();
            } else {
                Produit p = new Produit(nom, stock);
                pc.AddEntity(p);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Added .");
                alert.setHeaderText(null);
                alert.show();
                URI file;

                // customize email message with product name and stock
                String htmlContent = new String(Files.readAllBytes(Paths.get("C:\\Users\\msi\\Desktop\\Desktop\\src\\desktop\\interfaces\\email-template.html")));

                htmlContent = htmlContent.replace("{{PRODUCT_NAME}}", nom);
                htmlContent = htmlContent.replace("{{PRODUCT_STOCK}}", String.valueOf(stock));
                MailAPI.sendMail("emna.baccar@esprit.tn", "Product Added", htmlContent);
            }

        } catch (MessagingException | IOException ex) {
            Logger.getLogger(GestionPartenaireController.class.getName()).log(Level.SEVERE, null, ex);
        }

        refreshList();
    }*/


  
      @FXML
    private void addProduit(ActionEvent event) {
        try {
            ProduitCRUD pc = new ProduitCRUD();
            String nom = nomField.getText();
            int stock = Integer.valueOf(stockField.getText());

            if (nom.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("'Nom' must be inputed");
                alert.setTitle("Problem");
                alert.setHeaderText(null);
                alert.showAndWait();
            } else {
                Produit p = new Produit(nom, stock);
                pc.AddEntity(p);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Added .");
                alert.setHeaderText(null);
                alert.show();

                // customize email message with product name and stock
                String message = "Bravo, le produit " + nom + " avec un stock de " + stock + " a été ajouté avec succès.";
                MailAPI.sendMail("emna.baccar@esprit.tn", "Produit Ajouté", message);
            }

        } catch (MessagingException ex) {
            Logger.getLogger(GestionPartenaireController.class.getName()).log(Level.SEVERE, null, ex);
        }

        refreshList();
    }
     
    @FXML
    private void updateProduit(ActionEvent event) {
        ProduitCRUD pc = new ProduitCRUD();
        String nom = nomField.getText();
        int stock = Integer.valueOf(stockField.getText());

        pc.update(prod.getId(), new Produit(nom, stock));

        refreshList();
    }

    @FXML
    private void deleteProduit(ActionEvent event) {
        ProduitCRUD pc = new ProduitCRUD();
        pc.delete(prod);
        refreshList();
    }

    @FXML
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

    public void consulterStat(ActionEvent event) {
        /*  try {
            Parent root = FXMLLoader.load(getClass().getResource("statistique.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage() + ex.getStackTrace());
        }*/
    }

     @FXML
    private void recherche(ActionEvent event) throws SQLException {
           ProduitCRUD sa = new ProduitCRUD();

        idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomcol.setCellValueFactory(new PropertyValueFactory<>("nom"));

       stockcol.setCellValueFactory(new PropertyValueFactory<>("stock"));

        //appeler fx de recherche
        ObservableList<Produit> list = sa.listerProduits();
        produitTable.setItems(list);

        //ObservableList<Article> list = FXCollections.observableArrayList();
        // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Produit> filteredData = new FilteredList<>(list, b -> true);
        // 2. Set the filter Predicate whenever the filter changes.
        trecherche.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate((Produit prod) -> {
                // If filter text is empty, display all persons.

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(prod.getNom()).indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches libelle
                } else {
                    return false; // Does not match.
                }
            });
        });
        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Produit> sortedData = new SortedList<>(filteredData);
        // 4. Bind the SortedList comparator to the TableView comparator.
        //  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(produitTable.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        produitTable.setItems(sortedData);
     


      
    }
}
