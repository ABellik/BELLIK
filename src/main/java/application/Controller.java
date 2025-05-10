package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.beans.binding.Bindings;

import java.text.ParseException;
import java.util.*;
import java.util.function.UnaryOperator;

import data.repository.DataRepository;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;
import model.actors.Individual;
import model.actors.Mentionable;
import model.actors.Organization;
import model.actors.Owner;
import model.media.Media;
import model.media.PrintMedia;
import model.media.Radio;
import model.media.Television;
import model.module.Module;
import model.monitoring.Monitoring;
import model.ownership.Appropriable;
import model.ownership.Ownership;
import model.ownership.OwnershipManager;

public class Controller {

    private final ObservableList<String> entityElements = FXCollections.observableArrayList("...","Personne", "Organisation", "Média");
    private final ObservableList<String> publicationTypes = FXCollections.observableArrayList("...","Article", "Reportage", "Interview");

    //Composants de la fenêtre
    @FXML
    private Button exitButton;


    //Composants du premier onglet
    @FXML
    private ComboBox<String> entityComboBox;
    @FXML
    private ComboBox<String> nameComboBox;
    @FXML
    private TextArea informationsText;
    @FXML
    private TextArea ownershipsText;

    //Composants du deuxième onglet
    @FXML
    private TextField textFieldTitle;
    @FXML
    private ComboBox<String> comboBoxMedia;
    @FXML
    private ComboBox<String> comboBoxPublicationType;
    @FXML
    private ListView<String> mentionsListView;
    @FXML
    private TextField searchMentionsTextField;
    @FXML
    private ListView<String> mentionablesListView;
    @FXML
    private Button addMentionsButton;
    @FXML
    private Button removeMentionsButton;
    @FXML
    private Button publishButton;

    //Composants du troisième onglet
    @FXML
    private ComboBox<String> comboBoxSeller;

    @FXML
    private ComboBox<String> comboBoxBuyer;

    @FXML
    private ComboBox<String> comboBoxOwnerships;

    @FXML
    private Spinner<Double> spinnerPercentage;

    @FXML
    private Button buyoutButton;

    //Composants du quatrième onglet
    @FXML
    private ListView<String> listViewAlerts;


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
            //names.add(0,"...");
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
        comboBoxPublicationType.setDisable(true);

        ObservableList<String> mentionables = getMentionablesNames();

        FilteredList<String> filteredMentionables = new FilteredList<>(mentionables, s -> true);

        // Lie la recherche à la liste filtrée
        searchMentionsTextField.textProperty().addListener((obs, oldVal, newVal) -> {
            String lowerCaseFilter = newVal.toLowerCase();
            filteredMentionables.setPredicate(name -> {
                if (lowerCaseFilter.isEmpty()) return true;
                return name.toLowerCase().contains(lowerCaseFilter);
            });
        });

        // Trie alphabétique
        SortedList<String> sortedMentionables = new SortedList<>(filteredMentionables);
        sortedMentionables.setComparator(String::compareToIgnoreCase);

        // Lie à la ListView
        mentionablesListView.setItems(sortedMentionables);

        comboBoxPublicationType.setValue(publicationTypes.getFirst());
        comboBoxPublicationType.setItems(publicationTypes);

        ObservableList<String> names = getMediaNames();
        names.addFirst("...");
        comboBoxMedia.setValue(names.getFirst());
        comboBoxMedia.setItems(names);

        comboBoxMedia.setOnAction(event -> {
            String selected = comboBoxMedia.getSelectionModel().getSelectedItem();
            if (selected != null && !selected.equals("...")){
                comboBoxPublicationType.setDisable(false);
                Media m = DataRepository.searchMedia(selected);
                switch (m) {
                    case PrintMedia printMedia -> publicationTypes.setAll(new String[]{"...", "Article", "Interview"});
                    case Television television -> publicationTypes.setAll(new String[]{"...", "Reportage", "Interview"});
                    case Radio radio -> publicationTypes.setAll(new String[]{"...", "Reportage", "Interview"});
                    default -> {
                    }
                }
            }
            else{
                comboBoxPublicationType.setDisable(true);
            }

        });

        publishButton.setOnAction(event -> {
            String title = textFieldTitle.getText();

            Media media = DataRepository.searchMedia(comboBoxMedia.getValue());

            ObservableList<String> mentionsList = mentionsListView.getItems();

            Set<Mentionable> mentions = new HashSet<>();
            for(String s : mentionsList){
                mentions.add(DataRepository.searchMentionable(s));
            }

            String publicationType = comboBoxPublicationType.getValue();

            media.publish(title,mentions,publicationType);
            listViewAlerts.setItems(FXCollections.observableArrayList(Module.getMonitoring().getAlertList()));
            showInformationAlert("Publication réalisée avec succès !");
        });

        addMentionsButton.setOnAction(event -> {
            String selectedItem = mentionablesListView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                mentionsListView.getItems().add(selectedItem);
                mentionables.remove(selectedItem); // on retire depuis la source
                mentionsListView.getItems().sort(null);
            }
        });


        removeMentionsButton.setOnAction(event -> {
            String selectedItem = mentionsListView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                mentionables.add(selectedItem);
                mentionsListView.getItems().remove(selectedItem);
                mentionables.sort(null);
            }
        });

        publishButton.disableProperty().bind(
                textFieldTitle.textProperty().isEmpty()
                        .or(comboBoxMedia.valueProperty().isNull().or(comboBoxMedia.valueProperty().isEqualTo("...")))
                        .or(comboBoxPublicationType.valueProperty().isNull().or(comboBoxPublicationType.valueProperty().isEqualTo("...")))
        );

        /**********************************************RACHAT DE PARTS***********************************************/
        comboBoxOwnerships.setDisable(true);
        spinnerPercentage.setDisable(true);

        SpinnerValueFactory.DoubleSpinnerValueFactory valueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 100.0, 0.0, 0.5);
        spinnerPercentage.setValueFactory(valueFactory);

        StringConverter<Double> converter = new StringConverter<>() {
            @Override
            public String toString(Double value) {
                if (value == null) return "";
                return String.format("%.2f", value).replace('.', ',');
            }

            @Override
            public Double fromString(String text) {
                if (text == null || text.isEmpty()) return 0.0;
                // Le point est déjà interdit, donc on peut remplacer par sécurité
                return Double.parseDouble(text.replace(',', '.'));
            }
        };


        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            // Autorise seulement les virgules comme séparateur décimal
            return newText.matches("\\d{1,3}(,\\d{0,2})?") ? change : null;
        };


        TextFormatter<Double> textFormatter = new TextFormatter<>(converter, 0.0, filter);
        spinnerPercentage.getEditor().setTextFormatter(textFormatter);

        textFormatter.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && newVal >= 0 && newVal <= 100) {
                spinnerPercentage.getValueFactory().setValue(newVal);
            }
        });




        ObservableList<String> owners = getOwnersNames();
        owners.addFirst("...");
        owners.sort(null);
        comboBoxSeller.setValue(owners.getFirst());
        comboBoxSeller.setItems(owners);
        comboBoxBuyer.setValue(owners.getFirst());
        comboBoxBuyer.setItems(owners);

        comboBoxSeller.setOnAction(event -> {
            String selected = comboBoxSeller.getSelectionModel().getSelectedItem();
            if(selected != null && !selected.equals("...")){
                comboBoxOwnerships.setDisable(false);
                Owner o = DataRepository.searchOwner(selected);
                ObservableList<String> ownershipsNames = getOwnershipsNames(o);
                ownershipsNames.addFirst("...");

                comboBoxOwnerships.setItems(ownershipsNames);
                comboBoxOwnerships.setValue(comboBoxOwnerships.getItems().getFirst());
            }
            else{
                comboBoxOwnerships.setDisable(true);
                comboBoxOwnerships.setValue(comboBoxOwnerships.getItems().getFirst());
            }
        });

        comboBoxOwnerships.setOnAction(event -> {
            String selected = comboBoxOwnerships.getSelectionModel().getSelectedItem();
            if(selected != null && !selected.equals("...")){
                spinnerPercentage.setDisable(false);
                Owner o = DataRepository.searchOwner(comboBoxSeller.getSelectionModel().getSelectedItem());
                Appropriable a = DataRepository.searchAppropriable(comboBoxOwnerships.getSelectionModel().getSelectedItem());
                Ownership ownership = DataRepository.searchOwnership(o,a);
                double percentage = ownership.getPercentage();
                if(percentage == 0.0){
                    spinnerPercentage.setDisable(true);
                    spinnerPercentage.setPromptText("Propriété impossible à racheter");
                }
                else{
                    spinnerPercentage.setEditable(true);
                    valueFactory.setMax(percentage);
                    valueFactory.setValue(percentage);
                }
            }
            else{
                spinnerPercentage.setDisable(true);
            }
        });

        buyoutButton.setOnAction(event -> {
            Owner seller = DataRepository.searchOwner(comboBoxSeller.getValue());

            Owner buyer = DataRepository.searchOwner(comboBoxBuyer.getValue());

            Appropriable property = DataRepository.searchAppropriable(comboBoxOwnerships.getValue());

            Ownership o = DataRepository.searchOwnership(seller,property);

            double percentage = valueFactory.getValue();

            OwnershipManager.buyOutOwnership(o,buyer,percentage);
            comboBoxSeller.setValue(comboBoxSeller.getItems().getFirst());
            comboBoxBuyer.setValue(comboBoxSeller.getItems().getFirst());
            valueFactory.setValue(0.0);
            spinnerPercentage.setDisable(true);
            listViewAlerts.setItems(FXCollections.observableArrayList(Module.getMonitoring().getAlertList()));
            showInformationAlert("Rachat de part réalisé avec succès !");

        });

        /**********************************************HISTORIQUE DES ALERTES***********************************************/
        listViewAlerts.setItems(FXCollections.observableArrayList(Module.getMonitoring().getAlertList()));


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

    public ObservableList<String> getMentionablesNames(){
        ArrayList<String> names = new ArrayList<>();
        List<Mentionable> mentionableList = DataRepository.getMentionables();
        for(Mentionable i : mentionableList ){
            names.add(i.getName());
        }
        return FXCollections.observableArrayList(names);
    }

    public ObservableList<String> getOwnersNames(){
        ArrayList<String> names = new ArrayList<>();
        List<Owner> ownerList = DataRepository.getOwners();
        for(Owner i : ownerList ){
            names.add(i.getName());
        }
        return FXCollections.observableArrayList(names);
    }

    public ObservableList<String> getOwnershipsNames(Owner o){
        ArrayList<String> names = new ArrayList<>();
        List<Ownership> ownerships = o.getOwnerships();
        for(Ownership i : ownerships ){
            names.add(i.getProperty().getName());
        }
        return FXCollections.observableArrayList(names);
    }

    public static void showInformationAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Publication validée");
        alert.setHeaderText(null); // ou mets un en-tête si tu veux
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void showWarningAlert(String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Alerte de la vigie");
        alert.setHeaderText(null); // ou mets un en-tête si tu veux
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void showErrorAlert(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null); // ou mets un en-tête si tu veux
        alert.setContentText(message);
        alert.showAndWait();
    }
}
