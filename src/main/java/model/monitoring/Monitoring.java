package model.monitoring;

import application.Controller;
import model.actors.Mentionable;
import model.module.IndividualModule;
import model.module.MediaModule;
import model.ownership.Ownership;
import model.publication.Publication;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Classe {@code Monitoring} représentant le module de surveillance principal (la "vigie").
 *
 * <p>Cette classe observe les modules {@code IndividualModule} et {@code MediaModule}
 * afin de détecter et traiter les situations potentiellement conflictuelles ou sensibles
 * dans les publications médiatiques.</p>
 *
 * <p>Elle est responsable de :</p>
 * <ul>
 *   <li>Détecter si un individu est mentionné dans un média qu'il possède,</li>
 *   <li>Détecter si une entité est trop souvent mentionnée par un même média (dépassement de seuil),</li>
 *   <li>Détecter les changements de propriété sur un média (nouveaux propriétaires),</li>
 *   <li>Publier les alertes soit sur la console, soit via une interface graphique,</li>
 *   <li>Conserver un historique des alertes publiées.</li>
 * </ul>
 */
public class Monitoring implements Observer {

    /**
     * Liste contenant l'historique des alertes publiées.
     */
    private final List<String> alertList = new ArrayList<>();


    /**
     * Indique si le mode interface graphique est activé.
     * Si {@code false}, les alertes sont affichées dans la console.
     */
    private static boolean GUI_mode = false;

    /**
     * Méthode interne qui publie une alerte à l'utilisateur.
     *
     * @param message le message d'alerte à afficher
     */
    public void publishAlert(String message){
        if(!GUI_mode) System.out.println(message);
        else Controller.showWarningAlert(message.substring(70));
    }

    /**
     * Méthode appelée automatiquement lorsqu'un module observé notifie une mise à jour.
     * Cette méthode gère différents cas :
     * <ul>
     *   <li>Un {@code Individual} est mentionné dans un média qu'il possède,</li>
     *   <li>Un {@code Mentionable} est trop souvent mentionné,</li>
     *   <li>Un nouveau {@code Owner} acquiert une part dans un média.</li>
     * </ul>
     *
     * @param o   le module observé (soit {@code IndividualModule}, soit {@code MediaModule})
     * @param arg l’objet transmis lors de la notification (soit une {@code Publication}, un {@code Mentionable}, ou une {@code Ownership})
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof IndividualModule im){
            Publication p = (Publication) arg;
            String alert = "\n------------------------  \u001B[1mALERTE !\u001B[0m  ------------------------\n"+im.getIndividual().getName()+" a été mentionné par "+p.getSource().getName()+" qu'il possède !"+"\nLe titre de la publication concernée : "+p.getTitle()+"\n";
            publishAlert(alert);
            alertList.add(alert.substring(70));
        }
        else{
            MediaModule mm = (MediaModule) o;
            if(arg instanceof Mentionable m){
                String alert = "\n------------------------  \u001B[1mALERTE !\u001B[0m  ------------------------\n"+m.getName()+" a été mentionné trop de fois par "+mm.getMedia().getName()+"!";
                publishAlert(alert);
                alertList.add(alert.substring(70));

            }
            else{
                Ownership own = (Ownership) arg;
                String alert = "\n------------------------  \u001B[1mALERTE !\u001B[0m  ------------------------\nUne part de "+mm.getMedia().getName()+" a été racheté par "+own.getOrigin().getName()+" qui est un nouveau propriétaire !";
                publishAlert(alert);
                alertList.add(alert.substring(70));
            }
        }
    }

    /**
     * Active ou désactive le mode graphique pour l'affichage des alertes.
     *
     * @param GUI_mode {@code true} pour activer le mode GUI, {@code false} pour afficher dans la console
     */
    public static void setGUI_mode(boolean GUI_mode) {
        Monitoring.GUI_mode = GUI_mode;
    }

    /**
     * Retourne l'historique des messages d’alerte publiés.
     *
     * @return une liste contenant tous les messages d’alerte (tronqués)
     */
    public List<String> getAlertList() {
        return alertList;
    }
}
