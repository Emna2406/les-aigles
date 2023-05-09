/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connexion3a41.gui;

import edu.connexion3a41.entities.Partenaire;
import edu.connexion3a41.services.PartenaireCRUD;
import edu.connexion3a41.tools.MailAPI;
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
public class GestionPartenaireController implements Initializable {

    @FXML
    private TextField emailField;
    @FXML
    private TextField nomField;
    @FXML
    private Button btnadd;
    @FXML
    private Button btnupdate;
    @FXML
    private Button btndelete;
    @FXML
    private TableView<Partenaire> partenaireTable;
    @FXML
    private TableColumn<Partenaire, Integer> idcol;
    @FXML
    private TableColumn<Partenaire, String> nomcol;
    @FXML
    private TableColumn<Partenaire, String> emailcol;

    private Partenaire part;

    private List<Partenaire> list_categorie;
    PartenaireCRUD su = new PartenaireCRUD();
    ObservableList<Partenaire> data;
    @FXML
    private TextField trecherche;
    @FXML
    private Button MenuPrincipal;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setBtn();
        data = FXCollections.observableList(su.display());

        idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomcol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        emailcol.setCellValueFactory(new PropertyValueFactory<>("email"));

        partenaireTable.setItems(data);

        partenaireTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                part = newSelection;
                setBtn();
            }

        });

    }

    public void refreshList() {
        data.clear();
        data = FXCollections.observableList(su.display());

        idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomcol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        emailcol.setCellValueFactory(new PropertyValueFactory<>("email"));

        partenaireTable.setItems(data);

    }

    private void setBtn() {
        if (part == null) {
            btnupdate.setDisable(true);
            btndelete.setDisable(true);
        } else {
            btnupdate.setDisable(false);
            btndelete.setDisable(false);
        }
    }

    @FXML
    private void addPartenairee(ActionEvent event) {
        {
            try {
                PartenaireCRUD pc = new PartenaireCRUD();
                String nom = nomField.getText();
                String email = emailField.getText();

                if (nom.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("'Nom' must be inputed");
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
                    Partenaire p = new Partenaire(nom, email);
                    pc.AddEntity(p);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setContentText("Added .");
                    alert.setHeaderText(null);
                    alert.show();
                    //redirectToListProduit();
                }
                String message = "Bravo, le partenaire " + nom +  " a été ajouté avec succès , Bienvenue !!! ";

                MailAPI.sendMail("emna.baccar@esprit.tn", "Partenaire Ajouté", message);
            } catch (MessagingException ex) {
                Logger.getLogger(GestionPartenaireController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        refreshList();

    }

    @FXML
    private void updatePartenaire(ActionEvent event) {
        PartenaireCRUD pc = new PartenaireCRUD();
        String nom = nomField.getText();
        String email = emailField.getText();
        pc.update(part.getId(), new Partenaire(nom, email));
        refreshList();
    }

    @FXML
    private void deletePartenaire(ActionEvent event) {
        PartenaireCRUD pc = new PartenaireCRUD();
        pc.delete(part);
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

    @FXML
    private void recherche(ActionEvent event) throws SQLException {
           PartenaireCRUD sa = new PartenaireCRUD();

        idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomcol.setCellValueFactory(new PropertyValueFactory<>("nom"));

        emailcol.setCellValueFactory(new PropertyValueFactory<>("email"));

        //appeler fx de recherche
        ObservableList<Partenaire> list = sa.listerPartenaires();
        partenaireTable.setItems(list);

        //ObservableList<Article> list = FXCollections.observableArrayList();
        // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Partenaire> filteredData = new FilteredList<>(list, b -> true);
        // 2. Set the filter Predicate whenever the filter changes.
        trecherche.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate((Partenaire part) -> {
                // If filter text is empty, display all persons.

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(part.getNom()).indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches libelle
                } else {
                    return false; // Does not match.
                }
            });
        });
        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Partenaire> sortedData = new SortedList<>(filteredData);
        // 4. Bind the SortedList comparator to the TableView comparator.
        //  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(partenaireTable.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        partenaireTable.setItems(sortedData);
     


      
    }
      /*  trecherche.textProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != null && (newValue.length() < oldValue.length())) {
                  partenaireTable.setItems(data);
            }
            String value = newValue.toLowerCase();
            ObservableList<Partenaire> subentries = FXCollections.observableArrayList();
            for (Partenaire entry : partenaireTable.getItems()) {
                boolean match = true;
                
                String nom = entry.getNom();
            
                String email = entry.getEmail();

                if ( !nom.toLowerCase().contains(value)
                        && !email.toLowerCase().contains(value)
                       ) {
                    match = false;
                }
                if (match) {
                    subentries.add(entry);
                }
            }
              partenaireTable.setItems(subentries);
        });
    
 */
}
