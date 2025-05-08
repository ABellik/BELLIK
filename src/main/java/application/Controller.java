package application;

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

public class Controller {

    private final ObservableList<String> entityElements = FXCollections.observableArrayList("...","Personne", "Organisation", "Média");

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

        entityComboBox.setItems(entityElements);

        nameComboBox.setValue("...");
        nameComboBox.setDisable(true);

        entityComboBox.setOnAction(event -> {
            String entityComboBoxValue = entityComboBox.getValue();
            ObservableList<String> names;
            switch (entityComboBoxValue) {
                case "Personne" -> {
                    nameComboBox.setDisable(false);
                    names = getIndividualNames();
                }
                case "Organisation" -> {
                    nameComboBox.setDisable(false);
                    names = getOrganizationNames();
                }
                case "Média" -> {
                    nameComboBox.setDisable(false);
                    names = getMediaNames();
                }
                default -> {
                    nameComboBox.setDisable(true);
                    names = FXCollections.observableArrayList(new ArrayList<>());
                }
            }
            names.addFirst("...");
            nameComboBox.setValue(names.getFirst());
            informationsText.clear();
            ownershipsText.clear();
            nameComboBox.setItems(names);
        });

        nameComboBox.setOnAction(event -> {
            String nameComboBoxValue = nameComboBox.getValue();
            String entityComboBoxValue = entityComboBox.getValue();

            if (nameComboBoxValue == null || nameComboBoxValue.equals("...")) {
                informationsText.clear();
                ownershipsText.clear();
                return; // on quitte la méthode ou le bloc d'action ici
            }

            switch (entityComboBoxValue) {
                case "Personne" -> {
                    Individual individual = DataRepository.searchIndividual(nameComboBoxValue);
                    informationsText.setText(individual.toString());
                    ownershipsText.setText("Liste de ses propriétés : \n" + individual.getOwnerships());
                }
                case "Organisation" -> {
                    Organization organization = DataRepository.searchOrganization(nameComboBoxValue);
                    informationsText.setText(organization.toString());
                    ownershipsText.setText("Liste de ses propriétés : \n" + organization.getOwnerships());
                }
                case "Média" -> {
                    Media media = DataRepository.searchMedia(nameComboBoxValue);
                    informationsText.setText(media.toString());
                    ownershipsText.setText("Liste de ses parts : \n" + media.getShares());
                }
                default -> {
                    informationsText.clear();
                    ownershipsText.clear();
                }
            }

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
