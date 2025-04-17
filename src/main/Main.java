package main;


import data.IndividualDataLoader;
import data.MediaDataLoader;
import data.OrganizationDataLoader;
import model.actors.Individual;
import model.actors.Organization;
import model.media.Media;



import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        List<Individual> individuals = new ArrayList<>(new IndividualDataLoader().load());
        List<Organization> organizations = new ArrayList<>(new OrganizationDataLoader().load());
        List<Media> medias = new ArrayList<>(new MediaDataLoader().load());

        Scanner scanner = new Scanner(System.in);

        System.out.println("Bienvenue sur la vigie des médias !");
        System.out.print("""
                Que souhaitez-vous faire :
                \t1. Obtenir un renseignement\
                
                \t2. Simuler une publication\
                
                \t3. Simuler un rachat de part de propriétés\
                
                \t4. Quitter\
                
                
                Veuillez saisir votre choix :\s""");
        int choix = scanner.nextInt();
        System.out.println("Ok ! Vous avez choisi "+choix);
        if(choix==4) System.exit(0);


    }
}