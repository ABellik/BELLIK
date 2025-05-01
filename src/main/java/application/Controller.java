package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Controller {
    @FXML
    private Button exitButton;

    @FXML
    public void initialize(){
        exitButton.setOnAction(event -> {
            // Récupère la fenêtre (Stage) actuelle via le bouton
            Stage stage = (Stage) exitButton.getScene().getWindow();
            stage.close(); // Ferme la fenêtre
        });
    }
}
