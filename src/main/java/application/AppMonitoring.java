package application;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.monitoring.Monitoring;

/**
 * Classe principale de l'application JavaFX "Vigie des médias".
 * <p>
 * Cette classe hérite de {@link javafx.application.Application} et sert de point d'entrée
 * pour le lancement de l'interface graphique de l'application. Elle initialise le mode graphique,
 * charge la vue principale à partir du fichier FXML et configure la fenêtre principale.
 * </p>
 */
public class AppMonitoring extends Application {
    /**
     * Méthode principale de l'application.
     * <p>
     * Elle appelle la méthode {@code launch(args)} de JavaFX, qui déclenche le cycle de vie de l'application.
     * </p>
     *
     * @param args les arguments de la ligne de commande (non utilisés dans cette application).
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Point d'entrée de l'interface graphique JavaFX.
     * <p>
     * Cette méthode configure la fenêtre principale (titre, icône, scène, redimensionnement)
     * et charge la vue depuis le fichier FXML {@code main-view.fxml}.
     * Elle active également le mode graphique de la classe {@code Monitoring}.
     * </p>
     *
     * @param primaryStage la fenêtre principale fournie par JavaFX.
     * @throws Exception si le fichier FXML ou les ressources associées ne peuvent pas être chargés.
     */
    @Override
    public void start(Stage primaryStage) throws Exception{

        Monitoring.setGUI_mode(true);
        Parent root = FXMLLoader.load(getClass().getResource("/main-view.fxml"));
        primaryStage.setTitle("Vigie des médias");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icone.png")));
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
