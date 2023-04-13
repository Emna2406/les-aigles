/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktop.interfaces;

import desktop.entities.Candidat;
import desktop.services.CandidatCRUD;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
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

    private ObservableList<Candidat> candidatList;

    private List<Candidat> list_categorie;
    CandidatCRUD su = new CandidatCRUD();
    ObservableList<Candidat> data;
    private Candidat cand;
    @FXML
    private Button btnupdate;
    @FXML
    private Button btndelete;
    @FXML
    private Button btnadd;
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

        candidatTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                cand = newSelection;
                setBtn();
            }

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

    private void addCandidat(ActionEvent event) {
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

        refreshList();
    }

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
