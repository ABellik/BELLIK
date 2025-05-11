package application;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.monitoring.Monitoring;





public class AppMonitoring extends Application {

    public static void main(String[] args) {
        launch(args);
    }

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
