/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connexion3a41.gui;

import edu.connexion3a41.entities.User;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import edu.connexion3a41.services.UserService;
import edu.connexion3a41.tools.Session;

/**
 * FXML Controller class
 *
 * @author MEGA PC
 */
public class userlistController implements Initializable {

    @FXML
    private TableView<User> table;
    @FXML
    private TableColumn<User, String> colFirstname;
    @FXML
    private TableColumn<User, String> colLastname;
    @FXML
    private TableColumn<User, String> colEmail;

    @FXML
    private TableColumn<User, String> colPhone;
    
    @FXML
    private ImageView userImg;
             @FXML
    private TextField filterField;
     UserService sp = new UserService();
     User user = new User();
     ObservableList<User> list = FXCollections.observableArrayList();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
      user = sp.DisplayById(Session.current_user.getId());
        
         try {
                  InputStream stream = new FileInputStream("C:\\xampp\\htdocs\\pictures\\imagesuser/");
                   Image image = new Image(stream);
             
                    userImg.setImage(image);

              } catch (FileNotFoundException ex) {
                  System.out.println(ex.getMessage());
              }
         table.setItems(refresh());
          
    
        searsh();
        
    } 
   
    public  ObservableList<User> refresh()
    {

        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colFirstname.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colLastname.setCellValueFactory(new PropertyValueFactory<>("lastname"));
         list = sp.getAll();
        return list;
    }
    
    
    @FXML
 private void logout(ActionEvent event) {
     Session.current_user = null;
                 try {
                Parent page = FXMLLoader.load(getClass().getResource("Login.fxml"));
                Scene scene = new Scene(page);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
     
 }
     @FXML
 private void delete(ActionEvent event) {

 
sp.supprimer(table.getSelectionModel().getSelectedItem().getId());
    
       table.setItems(refresh());
        
 searsh();
 }
      @FXML
 private void goevenement(MouseEvent event) {

                  try {
                Parent page = FXMLLoader.load(getClass().getResource("evenement.fxml"));
                Scene scene = new Scene(page);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

        

 }
  @FXML
  private void edit(ActionEvent event){
       try {
                Parent page = FXMLLoader.load(getClass().getResource("FXMLprofil.fxml"));
                Scene scene = new Scene(page);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            } 
     
}
public void searsh(){
            FilteredList<User> filteredData = new FilteredList<>(refresh(), b -> true);
		
		// 2. Set the filter Predicate whenever the filter changes.
		filterField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(user -> {
				// If filter text is empty, display all persons.
								
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (user.getEmail().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches Email.
				} else if (user.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches firstname.
				}else if (user.getLastname().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches lastname.
				} else if (String.valueOf(user.getPhone()).indexOf(lowerCaseFilter)!=-1)
				     return true;
				     else  
				    	 return false; // Does not match.
			});
		});
		// 3. Wrap the FilteredList in a SortedList. 
		SortedList<User> sortedData = new SortedList<>(filteredData);
		
		// 4. Bind the SortedList comparator to the TableView comparator.
		// 	  Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(table.comparatorProperty());
		
		// 5. Add sorted (and filtered) data to the table.
		table.setItems(sortedData);
       }
       

 

    
          

}
