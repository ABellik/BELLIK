package main;


import data.repository.DataRepository;
import exceptions.IllegalPercentageException;
import exceptions.PublicationTypeException;
import model.actors.Individual;
import model.actors.Mentionable;
import model.actors.Organization;
import model.media.Media;
import model.ownership.Ownership;
import model.ownership.OwnershipManager;
import model.module.Module;

import java.util.*;

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
                    \nQue souhaitez-vous faire :
                    \t1. Avoir des renseignements\
                    
                    \t2. Simuler une publication\
                    
                    \t3. Simuler un rachat de part\
                    
                    \t4. Quitter\
                    
                    \nVeuillez saisir votre choix :\s""");

            try {
                choix = scanner.nextInt();
                System.out.println("Ok ! Vous avez choisi " + choix);
            }
            catch (InputMismatchException e){
                scanner.nextLine();
                System.out.println("Entrée invalide");
                continue;
            }

            if(choix == 1){
                System.out.print("""
                    \nQue souhaitez-vous faire :
                    \t1. Rechercher une personne\
                    
                    \t2. Rechercher une organisation\
                    
                    \t3. Rechercher un média\
                    
                    \t4. Afficher l'historique des alertes\
                    
                    \nVeuillez saisir votre choix :\s""");

                try {
                    choix = scanner.nextInt();
                }
                catch (InputMismatchException e){
                    System.out.println("Entrée invalide");
                    scanner.nextLine();
                    continue;
                }
                scanner.nextLine();
                if (choix == 1){
                    String name;
                    System.out.print("Entrez son nom complet : ");
                    name = scanner.nextLine();

                    try {
                        Individual individual = DataRepository.searchIndividual(name);
                        System.out.println(individual);
                        System.out.println("Liste de ses propriétés : \n"+individual.getOwnerships());
                    } catch (NoSuchElementException e) {
                        System.out.println(e.getMessage());
                    }

                }
                else if(choix == 2){
                    String name;
                    System.out.print("Entrez son nom complet : ");
                    name = scanner.nextLine();

                    try {
                        Organization organization = DataRepository.searchOrganization(name);
                        System.out.println(organization);
                        System.out.println("Liste de ses propriétés : \n"+organization.getOwnerships());
                        System.out.println("Liste de ses parts : \n"+organization.getShares());
                    } catch (NoSuchElementException e) {
                        System.out.println(e.getMessage());
                    }
                }
                else if(choix == 3){
                    String name;
                    System.out.print("Entrez son nom complet : ");
                    name = scanner.nextLine();

                    try {
                        Media media = DataRepository.searchMedia(name);
                        System.out.println(media);
                        System.out.println("Liste de ses parts : \n"+media.getShares());
                    } catch (NoSuchElementException e) {
                        System.out.println(e.getMessage());
                    }
                }
                else if(choix == 4){
                    System.out.println(Module.getMonitoring().getAlertList());
                }

            }


            else if(choix == 2){
                String title;
                scanner.nextLine();
                System.out.print("Entrez le titre de la publication : ");
                title = scanner.nextLine();

                Set<Mentionable> mentions = new HashSet<>();
                System.out.println("Entrez les noms des mentionnés (Personne, Organisation ou Média). Tapez 'fin' pour terminer : ");

                String mentionName = scanner.nextLine();
                while (!(mentionName.equals("fin"))) {
                    // On recherche un Mentionable (Personne, Organisation, Media...) par nom
                    try {
                        Mentionable mention = DataRepository.searchMentionable(mentionName);
                        mentions.add(mention);
                        System.out.println("Vous avez mentionné "+mention.getName()+"\n");
                    } catch (NoSuchElementException e) {
                        System.out.println("Aucun mentionable trouvé avec le nom \"" + mentionName + "\".");
                    }

                    mentionName = scanner.nextLine();
                }

                String mediaName;
                Media m;
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
                System.out.print("Quel pourcentage de "+propertyName+" est racheté par "+buyerName+" : ");
                try {
                    percentage = scanner.nextDouble();
                }
                catch (InputMismatchException e){
                    System.out.println("Pourcentage invalide");
                    scanner.nextLine();
                    continue;
                }
                try {
                    Ownership ownership = DataRepository.searchOwnership(sellerName, propertyName);
                    OwnershipManager.buyOutOwnership(ownership,DataRepository.searchOwner(buyerName),percentage);
                    System.out.println("Rachat de part réalisé avec succès !\n");
                } catch (NoSuchElementException | IllegalArgumentException e) {
                    System.out.println("Rachat de part annulé : " + e.getMessage());
                }
            }

            else if(choix == 4) System.exit(0);

            else System.out.println("Choix invalide !");
        }

        //À AJOUTER, Si propriétaire contrôle et ne possède pas, alors annuler rachat
    }
}