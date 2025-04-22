package main;


import data.repository.DataRepository;
import model.publication.Publication;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        int choix;

        System.out.println("Chargement des données en cours...");
        try {
            Thread.sleep(1000); // Pause de 2000 millisecondes (2 secondes)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        DataRepository.initialize();
        System.out.println("Données chargées avec succès !");
        try {
            Thread.sleep(1000); // Pause de 2000 millisecondes (2 secondes)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Bienvenue sur la vigie des médias !");
        /*System.out.print("""
                Que souhaitez-vous faire :
                \t1. Obtenir un renseignement\
                
                \t2. Simuler une publication\
                
                \t3. Simuler un rachat de part de propriétés\
                
                \t4. Quitter\
                
                
                Veuillez saisir votre choix :\s""");*/

        while(true) {
            System.out.print("""
                    Que souhaitez-vous faire :
                    \t1. Avoir des renseignements\
                    
                    \t2. Simuler une publication\
                    
                    \t3. Simuler un rachat de part\
                    
                    \t4. Quitter\
                    
                    Veuillez saisir votre choix :\s""");

            choix = scanner.nextInt();
            System.out.println("Ok ! Vous avez choisi " + choix);
            if(choix == 1){
                System.out.print("""
                    Avoir des renseignements/\n
                    Que souhaitez-vous faire :
                    \t1. Afficher les propriétaires\
                    
                    \t2. Afficher les organisations\
                    
                    \t3. Afficher les médias\
                    
                    \tAutre. Revenir\
                    
                    Veuillez saisir votre choix :\s""");

                choix = scanner.nextInt();
                if (choix == 1){
                    System.out.println(DataRepository.getIndividuals().get(10).getOwnerships());
                }
                else if(choix == 2){
                    System.out.println(DataRepository.getOrganizations());
                }
                else if(choix == 3){
                    System.out.println(DataRepository.getMedias());
                }
                else if(choix == 4) System.exit(0);
            }
            else if(choix == 2){
                String title;
                System.out.print("Entrez le titre de la publication : ");
                title = scanner.next();
                System.out.print("Entrez les noms des mentionnés (Personne, Organisation ou Média) : ");
                //Implémenter cela en premier (Version IA intéressante)


            }
            else if(choix == 3){
                System.out.println(DataRepository.getMedias());
            }
            else if(choix == 4) System.exit(0);
            else System.out.println("Choix invalide !");
        }
    }
}