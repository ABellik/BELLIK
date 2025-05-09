package data.loader;

import data.repository.DataRepository;
import model.ownership.Ownership;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public class OwnershipDataLoader extends DataLoader<Ownership>{
    public List<Ownership> load() {

        /*String[] urlFichiers = {
                "https://raw.githubusercontent.com/mdiplo/Medias_francais/master/personne-media.tsv",
                "https://raw.githubusercontent.com/mdiplo/Medias_francais/master/personne-organisation.tsv",
                "https://raw.githubusercontent.com/mdiplo/Medias_francais/master/organisation-organisation.tsv",
                "https://raw.githubusercontent.com/mdiplo/Medias_francais/master/organisation-media.tsv"
        };*/

        String[] cheminsFichiers = {
                "/files/personne-media.tsv",
                "/files/personne-organisation.tsv",
                "/files/organisation-organisation.tsv",
                "/files/organisation-media.tsv"
        };

        List<Ownership> ownerships = new ArrayList<>();

        for (String s : cheminsFichiers) {
            try (InputStream is = getClass().getResourceAsStream(s);
                 BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(is)))) {
                String ligne = br.readLine();

                while ((ligne = br.readLine()) != null) {
                    // Les valeurs sont séparées par une tabulation (\t)
                    String[] colonnes = ligne.split("\t");

                    //int p1 = Integer.parseInt(colonnes[0]);
                    String p2 = colonnes[1];
                    String p3 = colonnes[2];
                    double p4 = (colonnes.length > 3 && Objects.equals(p3, "égal à")) ? Double.parseDouble(colonnes[3].substring(0,colonnes[3].length() - 1)) : 0.0;
                    String p5 = colonnes[4];
                    try {
                        Ownership o = new Ownership(DataRepository.searchOwner(p2), DataRepository.searchAppropriable(p5), p4);
                        ownerships.add(o);
                        DataRepository.searchOwner(p2).addOwnership(o);
                        DataRepository.searchAppropriable(p5).addShare(o);
                    } catch (NoSuchElementException e) {
                        System.err.println("⚠\uFE0F Warning ! "+p2+" ou "+p5+" semblent être inconnu");
                    }
                }

            } catch (IOException e) {
                System.err.println("Erreur de lecture du fichier : " + e.getMessage());
            }
        }
        return ownerships;
    }
}
