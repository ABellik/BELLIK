package main;


import data.repository.DataRepository;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

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
                    \t1. Afficher les individus\
                    
                    \t2. Afficher les organisations\
                    
                    \t3. Afficher les médias\
                    
                    \t4. Afficher les possessions\
                    
                    \t5. Afficher les possessions\
                    
                    
                    Veuillez saisir votre choix :\s""");

            int choix = scanner.nextInt();
            System.out.println("Ok ! Vous avez choisi " + choix);
            if(choix == 1){
                System.out.println(DataRepository.getIndividuals());
            }
            else if(choix == 2){
                System.out.println(DataRepository.getOrganizations());
            }
            else if(choix == 3){
                System.out.println(DataRepository.getMedias());
            }
            else if(choix == 4){
                System.out.println(DataRepository.getOwnerships());
            }
            else if (choix == 5) System.exit(0);
        }
    }
}