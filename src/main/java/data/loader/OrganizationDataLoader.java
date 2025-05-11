package data.loader;

import model.actors.Organization;
import model.media.Media;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Classe responsable du chargement des données concernant les organisations depuis un fichier TSV.
 * <p>
 * Hérite de {@link DataLoader} avec le type générique {@link Organization}.
 * Le fichier est attendu à l'emplacement : {@code /files/organisations.tsv} dans le classpath.
 * Chaque ligne représente une organisation avec les colonnes suivantes :
 * <ul>
 *     <li>Nom (String)</li>
 *     <li>Commentaire (String facultatif)</li>
 * </ul>
 * </p>
 *
 * @author [Adam BELLIK]
 */
public class OrganizationDataLoader extends DataLoader<Organization> {
    /**
     * Charge la liste des organisations à partir du fichier {@code organisations.tsv}.
     *
     * @return une liste d'objets {@link Organization} construits à partir du fichier
     */
    @Override
    public List<Organization> load(){

        String cheminFichier = "/files/organisations.tsv";

        List<Organization> organizations = new ArrayList<>();

        try (InputStream is = getClass().getResourceAsStream(cheminFichier);
             BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(is)))) {

            String ligne = br.readLine();


            while ((ligne = br.readLine()) != null) {
                // Les valeurs sont séparées par une tabulation (\t)
                String[] colonnes = ligne.split("\t");

                String p1 = colonnes[0];
                String p2 = (colonnes.length > 1 && !Objects.equals(colonnes[1], "")) ? colonnes[1] : "";

                organizations.add(new Organization(p1,p2));


            }

        }
        catch (IOException e) {
            System.err.println("Erreur de lecture du fichier : " + e.getMessage());
        }
        return organizations;
    }
}
