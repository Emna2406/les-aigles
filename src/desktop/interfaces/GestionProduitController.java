/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktop.interfaces;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;
import desktop.entities.Partenaire;
import desktop.entities.Produit;
import desktop.services.PartenaireCRUD;
import desktop.services.ProduitCRUD;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
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
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
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
public class GestionProduitController implements Initializable {

    private TextField nomField;

    private TextField stockField;

    @FXML
    private TableView<Produit> produitTable;

    private ObservableList<Produit> produitList;
    @FXML
    private TextField nomfield;
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
    @FXML
    private Spinner<Integer> stockspin;
    private Produit prod;

    private List<Produit> list_categorie;
    ProduitCRUD su = new ProduitCRUD();
    ObservableList<Produit> data;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setBtn();
        data = FXCollections.observableList(su.display());
        SpinnerValueFactory<Integer> valueFactory_s = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99999999, 1, 1);
        valueFactory_s.setValue(1);
        

        idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomcol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        stockspin.setValueFactory(valueFactory_s);
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

    @FXML
    private void addProduit(ActionEvent event) {
        ProduitCRUD pc = new ProduitCRUD();
        String nom = nomField.getText();
        Integer stock = stockspin.getValue();

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
            //redirectToListProduit();
        }

        refreshList();
    }

    @FXML
    private void updateProduit(ActionEvent event) {
        ProduitCRUD pc = new ProduitCRUD();
        String nom = nomField.getText();
        Integer stock = stockspin.getValue();

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

}
