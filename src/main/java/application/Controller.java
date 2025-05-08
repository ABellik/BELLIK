package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.*;

import data.repository.DataRepository;
import model.actors.Individual;
import model.actors.Organization;
import model.media.Media;
import model.ownership.Ownership;

public class Controller {

    private final ObservableList<String> entityElements = FXCollections.observableArrayList("...","Personne", "Organisation", "Média");
    private final ObservableList<String> publicationTypes = FXCollections.observableArrayList("...","Article", "Reportage", "Interview");

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
    private TextField textFieldTitle;
    @FXML
    private ComboBox<String> comboBoxMedia;
    @FXML
    private ComboBox<String> comboBoxPublicationType;
    @FXML
    private ListView<String> mentionsListView;
    @FXML
    private Button addMentionsButton;
    @FXML
    private Button removeMentionsButton;
    @FXML
    private Button publishButton;

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

        /**********************************************RENSEIGNEMENTS***********************************************/
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

            List<Ownership> ownerships;
            StringBuilder text = new StringBuilder();
            String buf;

            switch (entityComboBoxValue) {
                case "Personne" -> {
                    Individual individual = DataRepository.searchIndividual(nameComboBoxValue);
                    informationsText.setText(individual.toString());
                    ownerships = individual.getOwnerships();
                    for(Ownership o : ownerships){
                        buf = "n°" + o.getId() + " | " + o.getProperty().getName() + " | " + o.getPercentage() + "\n";
                        text.append(buf);
                    }
                    ownershipsText.setText(text.toString());
                }
                case "Organisation" -> {
                    Organization organization = DataRepository.searchOrganization(nameComboBoxValue);
                    informationsText.setText(organization.toString());
                    ownerships = organization.getOwnerships();
                    for(Ownership o : ownerships){
                        buf = "n°" + o.getId() + " | " + o.getProperty().getName() + " | " + o.getPercentage() + "\n";
                        text.append(buf);
                    }
                    ownershipsText.setText(text.toString());
                }
                case "Média" -> {
                    Media media = DataRepository.searchMedia(nameComboBoxValue);
                    informationsText.setText(media.toString());
                    ownerships = media.getShares();
                    for(Ownership o : ownerships){
                        buf = "n°" + o.getId() + " | " + o.getOrigin().getName() + " | " + o.getPercentage() + "\n";
                        text.append(buf);
                    }
                    ownershipsText.setText(text.toString());
                }
                default -> {
                    informationsText.clear();
                    ownershipsText.clear();
                }
            }
        });

        /**********************************************PUBLICATIONS***********************************************/
        comboBoxPublicationType.setValue(publicationTypes.getFirst());
        comboBoxPublicationType.setItems(publicationTypes);

        ObservableList<String> names = getMediaNames();
        names.addFirst("...");
        comboBoxMedia.setValue(names.getFirst());
        comboBoxMedia.setItems(names);



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
