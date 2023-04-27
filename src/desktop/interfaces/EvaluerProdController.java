package desktop.interfaces;
import desktop.entities.Produit;
import desktop.services.RatingService;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;

public class EvaluerProdController implements Initializable {
    private Produit produit;
    @FXML
    private TextField rate;

    // Inject the "GestionProduitController" to access its methods and fields
    private GestionProduitController gestionProduitController;

    // Method to set the "GestionProduitController"
    public void setGestionProduitController(GestionProduitController gestionProduitController) {
        this.gestionProduitController = gestionProduitController;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    public void rateProduct(javafx.event.ActionEvent actionEvent) {
        Dialog<Integer> dialog = new Dialog<>();
        dialog.setTitle("Rate Product");

        // Create the rating control
        desktop.entities.Rating rating = new desktop.entities.Rating();
        rating.setRating(Integer.parseInt(rate.getText())); // Get the rating value from the textField

        // Add the rating control to the dialog
        dialog.getDialogPane().setContent(rate);

        // Add the OK button to the dialog
        ButtonType buttonTypeOk = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        // Show the dialog and wait for user response
        Optional<Integer> result = dialog.showAndWait();

        // If the user clicked OK, save the rating value to the database
        if (result.isPresent()) {
            // Get the selected product
            Produit produit = this.produit;

            // Call the rateProduit method to store the rating value in the database
            RatingService rs = new RatingService();
            rs.rateProduit(produit.getId(), rating.getRating());

            // Refresh the product list in GestionProduitController
            gestionProduitController.refreshList();

            // Close the dialog
            dialog.close();
        }
    }
}
