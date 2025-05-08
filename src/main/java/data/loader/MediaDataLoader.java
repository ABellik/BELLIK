package data.loader;

import model.media.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MediaDataLoader extends DataLoader<Media>{
    @Override
    public List<Media> load(){

        String urlFichier = "https://raw.githubusercontent.com/mdiplo/Medias_francais/master/medias.tsv";
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
                /*else if(p2.equals("Site")){
                    String p4 = (colonnes.length > 3 && !Objects.equals(colonnes[3], "")) ? colonnes[3] : "";
                    String p5 = (colonnes.length > 4 && !Objects.equals(colonnes[4], "")) ? colonnes[4] : "";
                    boolean p6 = Boolean.parseBoolean((colonnes.length > 5 && !Objects.equals(colonnes[5], "")) ? colonnes[5] : "");
                    medias.add(new Site(p1,p4,p5,p6));
                }*/
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
                    /*String p4 = (colonnes.length > 3 && !Objects.equals(colonnes[3], "")) ? colonnes[3] : "";
                    String p5 = (colonnes.length > 4 && !Objects.equals(colonnes[4], "")) ? colonnes[4] : "";
                    boolean p6 = Boolean.parseBoolean((colonnes.length > 5 && !Objects.equals(colonnes[5], "")) ? colonnes[5] : "");
                    medias.add(new OtherMedia(p1,p4,p5,p6));*/
                    System.out.println(colonnes[0]+" n'a pas été retenu car n'est ni une Presse, ni une télévision, ni une radio");
                }
            }

        }
        catch (IOException e) {
            System.err.println("Erreur de lecture du fichier : " + e.getMessage());
        }
        return medias;
    }
}
