package application;

import data.loader.DataLoader;
import data.loader.IndividualDataLoader;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.util.*;

import data.repository.DataRepository;
import model.actors.Individual;
import model.actors.Organization;
import model.media.Media;

import javax.xml.crypto.Data;

public class Controller {

    private final ObservableList<String> entityElements = FXCollections.observableArrayList("Personne", "Organisation", "Média");
    private ObservableList<String> nameElements = FXCollections.observableArrayList(new ArrayList<>());

    @FXML
    private Button exitButton;

    @FXML
    private ComboBox<String> entityComboBox;

    @FXML
    private ComboBox<String> nameComboBox;

    @FXML
    private TextArea informationsText;

    @FXML
    private TextArea ownershipsText;

    @FXML
    public void initialize(){

        System.out.println("Chargement des données en cours...");
        try {
            Thread.sleep(1000); // Pause de 2000 millisecondes (2 secondes)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        DataRepository.initialize();
        System.out.println("Données chargées avec succès !");

        try {
            Thread.sleep(1000); // Pause optionnelle avant de tout effacer
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }


        exitButton.setOnAction(event -> {
            // Récupère la fenêtre (Stage) actuelle via le bouton
            Stage stage = (Stage) exitButton.getScene().getWindow();
            stage.close(); // Ferme la fenêtre
        });

        nameElements = FXCollections.observableArrayList(getIndividualNames());


        entityComboBox.setItems(entityElements);

        nameComboBox.setItems(nameElements);

        entityComboBox.setOnAction(event -> {
            String newVal = entityComboBox.getValue();
            if (newVal.equals("Personne")) {
                nameElements = FXCollections.observableArrayList(getIndividualNames());
            }
            else if (newVal.equals("Organisation")) {
                nameElements = FXCollections.observableArrayList(getOrganizationNames());
            }
            else if (newVal.equals("Média")) {
                nameElements = FXCollections.observableArrayList(getMediaNames());
            }
            nameComboBox.setItems(nameElements);
        });
    }

    public ObservableList<String> getIndividualNames(){
        ArrayList<String> names = new ArrayList<>();
        List<Individual> individuals = DataRepository.getIndividuals();
        for(Individual i : individuals ){
            names.add(i.getName());
        }
        return FXCollections.observableArrayList(names);
    }

    public ObservableList<String> getOrganizationNames(){
        ArrayList<String> names = new ArrayList<>();
        List<Organization> organizations = DataRepository.getOrganizations();
        for(Organization i : organizations ){
            names.add(i.getName());
        }
        return FXCollections.observableArrayList(names);
    }

    public ObservableList<String> getMediaNames(){
        ArrayList<String> names = new ArrayList<>();
        List<Media> medias = DataRepository.getMedias();
        for(Media i : medias ){
            names.add(i.getName());
        }
        return FXCollections.observableArrayList(names);
    }
}
