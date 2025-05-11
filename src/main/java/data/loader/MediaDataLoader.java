package data.loader;

import model.media.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Classe responsable du chargement des données concernant les médias depuis un fichier TSV.
 * <p>
 * Hérite de {@link DataLoader} avec le type générique {@link Media}.
 * Le fichier est attendu à l'emplacement : {@code /files/medias.tsv} dans le classpath.
 * Chaque ligne représente un média avec les colonnes suivantes :
 * <ul>
 *     <li>Nom (String)</li>
 *     <li>Echelle (String)</li>
 *     <li>Prix (String)</li>
 *     <li>disparu (boolean)</li>
 *     <li>Périodicité (String facultatif)</li>
 * </ul>
 * </p>
 *
 * @author [Adam BELLIK]
 */
public class MediaDataLoader extends DataLoader<Media>{
    /**
     * Charge la liste des médias à partir du fichier {@code medias.tsv}.
     *
     * @return une liste d'objets {@link Media} construits à partir du fichier
     */
    @Override
    public List<Media> load(){

        String cheminFichier = "/files/medias.tsv";

        List<Media> medias = new ArrayList<>();

        try (InputStream is = getClass().getResourceAsStream(cheminFichier);
             BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(is)))) {
            String ligne = br.readLine();

            while ((ligne = br.readLine()) != null) {
                // Les valeurs sont séparées par une tabulation (\t)
                String[] colonnes = ligne.split("\t");

                String p1 = colonnes[0];
                String p2 = (colonnes.length > 1 && !Objects.equals(colonnes[1], "")) ? colonnes[1] : "";
                if(p2.equals("Télévision")){
                    String p4 = (colonnes.length > 3 && !Objects.equals(colonnes[3], "")) ? colonnes[3] : "";
                    String p5 = (colonnes.length > 4 && !Objects.equals(colonnes[4], "")) ? colonnes[4] : "";
                    boolean p6 = Boolean.parseBoolean((colonnes.length > 5 && !Objects.equals(colonnes[5], "")) ? colonnes[5] : "");
                    medias.add(new Television(p1,p4,p5,p6));
                }
                else if(p2.contains("Presse")){
                    String p3 = (colonnes.length > 2 && !Objects.equals(colonnes[2], "")) ? colonnes[2] : "";
                    String p4 = (colonnes.length > 3 && !Objects.equals(colonnes[3], "")) ? colonnes[3] : "";
                    String p5 = (colonnes.length > 4 && !Objects.equals(colonnes[4], "")) ? colonnes[4] : "";
                    boolean p6 = Boolean.parseBoolean((colonnes.length > 5 && !Objects.equals(colonnes[5], "")) ? colonnes[5] : "");
                    medias.add(new PrintMedia(p1,p4,p5,p6,p3));
                }
                else if(p2.equals("Radio")){
                    String p4 = (colonnes.length > 3 && !Objects.equals(colonnes[3], "")) ? colonnes[3] : "";
                    String p5 = (colonnes.length > 4 && !Objects.equals(colonnes[4], "")) ? colonnes[4] : "";
                    boolean p6 = Boolean.parseBoolean((colonnes.length > 5 && !Objects.equals(colonnes[5], "")) ? colonnes[5] : "");
                    medias.add(new Radio(p1,p4,p5,p6));
                }
                else{
                    System.err.println("⚠\uFE0F " + colonnes[0]+" n'a pas été retenu car n'est ni une Presse, ni une télévision, ni une radio");
                }
            }
        }
        catch (IOException e) {
            System.err.println("Erreur de lecture du fichier : " + e.getMessage());
        }
        return medias;
    }
}
