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

import java.util.*;
import java.util.function.UnaryOperator;

import data.repository.DataRepository;
import javafx.util.StringConverter;
import model.actors.Individual;
import model.actors.Mentionable;
import model.actors.Organization;
import model.actors.Owner;
import model.media.Media;
import model.media.PrintMedia;
import model.media.Radio;
import model.media.Television;
import model.module.Module;
import model.ownership.Appropriable;
import model.ownership.Ownership;
import model.ownership.OwnershipManager;

/**
 * Contrôleur principal de l'application JavaFX gérant les interactions utilisateur
 * pour les fonctionnalités suivantes :
 * <ul>
 *     <li>Consultation des entités (Personne, Organisation, Média)</li>
 *     <li>Publication de contenus médiatiques (Articles, Reportages, Interviews)</li>
 *     <li>Rachats de parts de propriété entre entités</li>
 *     <li>Affichage des alertes générées par les modules de surveillance</li>
 * </ul>
 * Cette classe utilise l'annotation {@code @FXML} pour lier les composants définis dans
 * le fichier FXML correspondant à leurs éléments Java.
 *
 * @author [Adam BELLIK]
 */
public class Controller {

    /** Liste des types d'entités sélectionnables dans l'interface. */
    private final ObservableList<String> entityElements = FXCollections.observableArrayList("...","Personne", "Organisation", "Média");

    /** Liste des types de publications possibles. */
    private final ObservableList<String> publicationTypes = FXCollections.observableArrayList("...","Article", "Reportage", "Interview");

    /** Bouton permettant de quitter l'application. */
    @FXML
    private Button exitButton;


    // ----------- Onglet 1 : Renseignements -----------
    @FXML
    private ComboBox<String> entityComboBox;
    @FXML
    private ComboBox<String> nameComboBox;
    @FXML
    private TextArea informationsText;
    @FXML
    private TextArea ownershipsText;

    // ----------- Onglet 2 : Simulation de publication -----------
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

    // ----------- Onglet 3 : Simulation de rachat de parts -----------
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

    // ----------- Onglet 4 : Historique des alertes -----------
    @FXML
    private ListView<String> listViewAlerts;

    /**
     * Méthode appelée automatiquement après le chargement du fichier FXML.
     * Initialise les composants de l'interface et configure les événements associés.
     */
    @FXML
    public void initialize(){

        System.out.println("Chargement des données en cours...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        DataRepository.initialize();
        System.out.println("Données chargées avec succès !");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }


        exitButton.setOnAction(event -> {
            Stage stage = (Stage) exitButton.getScene().getWindow();
            stage.close();
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
                return;
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
                        buf = "n°" + o.getId() + " | " + o.getProperty().getName() + " | " + (o.getPercentage()!=0.0 ? o.getPercentage() : "Inconnu") + "\n";
                        text.append(buf);
                    }
                    ownershipsText.setText(text.toString());
                }
                case "Organisation" -> {
                    Organization organization = DataRepository.searchOrganization(nameComboBoxValue);
                    informationsText.setText(organization.toString());
                    ownerships = organization.getOwnerships();
                    for(Ownership o : ownerships){
                        buf = "n°" + o.getId() + " | " + o.getProperty().getName() + " | " + (o.getPercentage()!=0.0 ? o.getPercentage() : "Inconnu") + "\n";
                        text.append(buf);
                    }
                    ownershipsText.setText(text.toString());
                }
                case "Média" -> {
                    Media media = DataRepository.searchMedia(nameComboBoxValue);
                    informationsText.setText(media.toString());
                    ownerships = media.getShares();
                    for(Ownership o : ownerships){
                        buf = "n°" + o.getId() + " | " + o.getOrigin().getName() + " | " + (o.getPercentage()!=0.0 ? o.getPercentage() : "Inconnu") + "\n";
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

        searchMentionsTextField.textProperty().addListener((obs, oldVal, newVal) -> {
            String lowerCaseFilter = newVal.toLowerCase();
            filteredMentionables.setPredicate(name -> {
                if (lowerCaseFilter.isEmpty()) return true;
                return name.toLowerCase().contains(lowerCaseFilter);
            });
        });

        SortedList<String> sortedMentionables = new SortedList<>(filteredMentionables);
        sortedMentionables.setComparator(String::compareToIgnoreCase);

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
                mentionables.remove(selectedItem);
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
                return Double.parseDouble(text.replace(',', '.'));
            }
        };


        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
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

    /**
     * Récupère les noms de tous les individus présents dans le dépôt de données.
     *
     * @return une liste observable contenant les noms des individus.
     */
    public ObservableList<String> getIndividualNames(){
        ArrayList<String> names = new ArrayList<>();
        List<Individual> individuals = DataRepository.getIndividuals();
        for(Individual i : individuals ){
            names.add(i.getName());
        }
        return FXCollections.observableArrayList(names);
    }

    /**
     * Récupère les noms de toutes les organisations présentes dans le dépôt de données.
     *
     * @return une liste observable contenant les noms des organisations.
     */
    public ObservableList<String> getOrganizationNames(){
        ArrayList<String> names = new ArrayList<>();
        List<Organization> organizations = DataRepository.getOrganizations();
        for(Organization i : organizations ){
            names.add(i.getName());
        }
        return FXCollections.observableArrayList(names);
    }

    /**
     * Récupère les noms de tous les médias présents dans le dépôt de données.
     *
     * @return une liste observable contenant les noms des médias.
     */
    public ObservableList<String> getMediaNames(){
        ArrayList<String> names = new ArrayList<>();
        List<Media> medias = DataRepository.getMedias();
        for(Media i : medias ){
            names.add(i.getName());
        }
        return FXCollections.observableArrayList(names);
    }

    /**
     * Récupère les noms de toutes les entités mentionnables présentes dans le dépôt de données.
     * Une entité mentionnable peut être une personne, une organisation ou un média.
     *
     * @return une liste observable contenant les noms des entités mentionnables.
     */
    public ObservableList<String> getMentionablesNames(){
        ArrayList<String> names = new ArrayList<>();
        List<Mentionable> mentionableList = DataRepository.getMentionables();
        for(Mentionable i : mentionableList ){
            names.add(i.getName());
        }
        return FXCollections.observableArrayList(names);
    }

    /**
     * Récupère les noms de tous les propriétaires (individus ou organisations) présents dans le dépôt de données.
     *
     * @return une liste observable contenant les noms des propriétaires.
     */
    public ObservableList<String> getOwnersNames(){
        ArrayList<String> names = new ArrayList<>();
        List<Owner> ownerList = DataRepository.getOwners();
        for(Owner i : ownerList ){
            names.add(i.getName());
        }
        return FXCollections.observableArrayList(names);
    }

    /**
     * Récupère les noms des propriétés détenues par un propriétaire donné.
     * Chaque propriété est obtenue à partir des possessions de l'objet {@code Owner}.
     *
     * @param o le propriétaire dont on veut connaître les propriétés détenues.
     * @return une liste observable contenant les noms des propriétés.
     */
    public ObservableList<String> getOwnershipsNames(Owner o){
        ArrayList<String> names = new ArrayList<>();
        List<Ownership> ownerships = o.getOwnerships();
        for(Ownership i : ownerships ){
            names.add(i.getProperty().getName());
        }
        return FXCollections.observableArrayList(names);
    }

    /**
     * Affiche une alerte d'information avec le message fourni.
     * Utilisé pour informer l'utilisateur de la validation d'une publication ou d'une autre action réussie.
     *
     * @param message le message à afficher dans l'alerte.
     */
    public static void showInformationAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Publication validée");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Affiche une alerte d'avertissement avec le message fourni.
     * Utilisé pour avertir l'utilisateur d'un problème ou d'un comportement à risque détecté par la vigie.
     *
     * @param message le message à afficher dans l'alerte.
     */
    public static void showWarningAlert(String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Alerte de la vigie");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
