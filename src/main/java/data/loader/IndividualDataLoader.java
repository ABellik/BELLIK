package data.loader;

import model.actors.Individual;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class IndividualDataLoader extends DataLoader<Individual>{
    public List<Individual> load(){
        //String urlFichier = "https://raw.githubusercontent.com/mdiplo/Medias_francais/master/personnes.tsv";
        String cheminFichier = "/files/personnes.tsv";
        List<Individual> individuals = new ArrayList<>();

        try (InputStream is = getClass().getResourceAsStream(cheminFichier);
             BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(is)))) {
        //try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/fichiers/personnes.tsv")))) {

            String ligne = br.readLine();

            while ((ligne = br.readLine()) != null) {
                // Les valeurs sont séparées par une tabulation (\t)
                String[] colonnes = ligne.split("\t");

                String p1 = colonnes[0];
                int p2 = (colonnes.length > 1 && !Objects.equals(colonnes[1], "")) ? Integer.parseInt(colonnes[1]) : 0;
                int p3 = (colonnes.length > 2 && !Objects.equals(colonnes[2], "")) ? Integer.parseInt(colonnes[2]) : 0;

                individuals.add(new Individual(p1,p2,p3));


            }

        }
        catch (IOException e) {
            System.err.println("Erreur de lecture du fichier : " + e.getMessage());
        }
        return individuals;

    }
}
