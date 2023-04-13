/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktop.interfaces;



import desktop.entities.Offre;
import desktop.services.OffreCRUD;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class GestionOffreController {

    OffreCRUD su=new OffreCRUD();
      ObservableList<Offre> data=FXCollections.observableArrayList();
      
    @FXML
    private Button Addbtn;

    @FXML
    private Button deletebtn;

    @FXML
    private TextField descField;

    @FXML
    private TableColumn<Offre, String> descriptionCol;

    @FXML
    private TableColumn<Offre, Integer> nbrCol;

    @FXML
    private Spinner<Integer> nbrField;

    @FXML
    private TableView<Offre> offreTab;

    @FXML
    private TableColumn<Offre, Integer> serviceCol;

    @FXML
    private Spinner<Integer> serviceField;

    @FXML
    private Button updatebtn;

    private Offre of;
    
    public void initialize(URL url, ResourceBundle rb) { 
         SpinnerValueFactory<Integer> valueFactory_s = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 999999, 1, 1);
         valueFactory_s.setValue(0);
        serviceField.setValueFactory(valueFactory_s);
        nbrField.setValueFactory(valueFactory_s);
        
        serviceField.increment(1);
        nbrField.increment(1);
    }
       public void refreshList(){
        data.clear();
        data=FXCollections.observableArrayList(su.display());
       
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        nbrCol.setCellValueFactory(new PropertyValueFactory<>("nbrplaces"));
        serviceCol.setCellValueFactory(new PropertyValueFactory<>("service"));
       
        offreTab.setItems(data);
    }
       
    @FXML
    void AddEntity(ActionEvent event) {

        OffreCRUD pc = new OffreCRUD();
        String description = descField.getText();
        int nbrplaces = nbrField.getValue();
        int idservice = serviceField.getValue();
       
        pc.AddEntity(new Offre(nbrplaces, description, idservice));
        
        /*  if (description.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("'Reference' must be inputed");
            alert.setTitle("Problem");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
         else
             if (description.isEmpty()) 
         {
              Alert alert = new Alert(Alert.AlertType.ERROR);
              alert.setContentText("'Reference' must be inputed");
              alert.setTitle("Problem");
              alert.setHeaderText(null);
              alert.showAndWait();
          }
         else 
          {
              Offre p = new Offre( );
              pc.AddEntity(p);
              Alert alert = new Alert(Alert.AlertType.INFORMATION);
              alert.setTitle("Success");
              alert.setContentText("Added .");
              alert.setHeaderText(null);
              alert.show();
              //redirectToListProduit();
          }  */
          
        }

    @FXML
    private void updateEntity(ActionEvent event) {
        OffreCRUD pc = new OffreCRUD();
        String description = descField.getText();
        Integer nbrplaces =nbrField.getValue();
        Integer idservice = serviceField.getValue();
        pc.update(of.getId(), new Offre(nbrplaces, description, idservice));
        refreshList();
    }
    

    @FXML
    private void deleteEntity(ActionEvent event) {
        OffreCRUD pc = new OffreCRUD();
        pc.delete(of);
        refreshList();
    }
    
   

}
