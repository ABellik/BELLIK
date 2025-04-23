package main;


import data.repository.DataRepository;
import exceptions.PublicationTypeException;
import model.actors.Mentionable;
import model.actors.Owner;
import model.media.Media;
import model.ownership.Appropriable;
import model.ownership.Ownership;
import model.ownership.OwnershipManager;
import model.publication.Publication;

import javax.xml.crypto.Data;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;

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
            Thread.sleep(1000); // Pause optionnelle avant de tout effacer
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("\n\n############################  \u001B[1mBienvenue sur la vigie des médias !\u001B[0m  ############################\n");

        while(true) {
            System.out.print("""
                    Que souhaitez-vous faire :
                    \t1. Avoir des renseignements\
                    
                    \t2. Simuler une publication\
                    
                    \t3. Simuler un rachat de part\
                    
                    \t4. Quitter\
                    
                    \nVeuillez saisir votre choix :\s""");

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
                    System.out.println(DataRepository.getIndividuals());
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
                scanner.nextLine();
                System.out.print("Entrez le titre de la publication : ");
                title = scanner.nextLine();

                Set<Mentionable> mentions = new HashSet<>();
                System.out.println("Entrez les noms des mentionnés (Personne, Organisation ou Média). Tapez 'fin' pour terminer : ");

                String mentionName = scanner.next();
                while (!(mentionName.equals("fin"))) {
                    // On recherche un Mentionable (Personne, Organisation, Media...) par nom
                    try {
                        Mentionable mention = DataRepository.searchMentionable(mentionName);
                        mentions.add(mention);
                        System.out.println("Vous avez mentionné "+mention.getName()+"\n");
                    } catch (NoSuchElementException e) {
                        System.out.println("Aucun mentionable trouvé avec le nom \"" + mentionName + "\".");
                    }

                    mentionName = scanner.next();
                }

                String mediaName;
                Media m;
                scanner.nextLine();
                while(true) {
                    System.out.print("Entrez le nom du média qui publie : ");
                    mediaName = scanner.nextLine();
                    try {
                        m = DataRepository.searchMedia(mediaName);
                        break;
                    } catch (NoSuchElementException e) {
                        System.out.println(mediaName + " est introuvable");
                    }
                }
                System.out.println(m);


                String publicationType;
                System.out.println("Enfin, quel est le type de la publication ? (Article, Reportage, ou Interview) : ");
                publicationType = scanner.next();


                System.out.println("En cours de publication...");
                try {
                    Thread.sleep(1000); // Pause de 2000 millisecondes (2 secondes)
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                try{
                    m.publish(title,mentions,publicationType);
                    System.out.println("Publication terminée !\n");
                }
                catch(PublicationTypeException e){
                    System.out.println(e.getMessage()+"\nVous êtes prié de recommencer\n");
                }

            }


            else if(choix == 3){
                String sellerName;
                scanner.nextLine();
                System.out.print("Entrez le nom du vendeur de la part : ");
                sellerName = scanner.nextLine();

                String buyerName;
                System.out.print("Entrez le nom de l'acheteur de la part : ");
                buyerName = scanner.nextLine();

                String propertyName;
                System.out.print("Quelle propriété est rachetée : ");
                propertyName = scanner.nextLine();

                double percentage;
                System.out.println("Quel pourcentage de "+propertyName+" est racheté par "+buyerName+" ?");
                percentage = scanner.nextDouble();

                try {
                    Ownership ownership = DataRepository.searchOwnership(sellerName, propertyName);
                    OwnershipManager.buyOutOwnership(ownership,DataRepository.searchOwner(buyerName),percentage);
                    System.out.println("Rachat de part réalisé avec succès !\n");
                } catch (NoSuchElementException e) {
                    System.out.println("Rachat de part annulé : " + e.getMessage());
                }

                //Bug si un nouveau propriétaire ayant acheté, achète une nouvelle part
            }

            else if(choix == 4) System.exit(0);

            else System.out.println("Choix invalide !");
        }
    }
}