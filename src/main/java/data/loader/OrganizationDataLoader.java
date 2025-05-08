package data.loader;

import model.actors.Organization;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrganizationDataLoader extends DataLoader<Organization> {
    @Override
    public List<Organization> load(){

        String urlFichier = "https://raw.githubusercontent.com/mdiplo/Medias_francais/master/organisations.tsv";
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
