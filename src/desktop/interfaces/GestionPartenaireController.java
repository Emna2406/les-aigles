/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktop.interfaces;

import desktop.entities.Partenaire;
import desktop.services.PartenaireCRUD;
import desktop.tools.MailAPI;
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
            
                MailAPI.sendMail("emna.baccar@esprit.tn", "Partenaire Ajouté", "Bravo, l'ajout du partenaire a été réussi ");
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

}
