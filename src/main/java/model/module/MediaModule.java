package model.module;


import model.actors.Mentionable;
import model.actors.Owner;
import model.media.Media;
import model.ownership.Ownership;
import model.publication.Publication;

import java.util.*;

/**
 * Classe représentant un module associé à un {@code Media}.
 *
 * <p>
 * Cette classe hérite de {@code Module} afin d'observer les modifications
 * d'état sur certains objets et de notifier les observateurs en conséquence.
 * </p>
 *
 * <p>Ce module doit :</p>
 * <ul>
 *   <li>Maintenir une liste du nombre de mentions par {@code Mentionable} ;</li>
 *   <li>Conserver un historique :
 *     <ul>
 *       <li>des rachats de parts du {@code Media},</li>
 *       <li>des propriétaires successifs.</li>
 *     </ul>
 *   </li>
 *   <li>Alerter la vigie dans les cas suivants :
 *     <ul>
 *       <li>un {@code Mentionable} est mentionné trop de fois (au-delà d’un seuil exprimé en pourcentage),</li>
 *       <li>un nouveau {@code Owner} rachète des parts.</li>
 *     </ul>
 *   </li>
 * </ul>
 */

public class MediaModule extends Module {
    /**
     * Seuil de mentions au-delà duquel un {@code Mentionable} est considéré comme trop cité
     * (ici fixé à 20 % de l’ensemble des mentions).
     */
    private static final double seuil = 0.2;

    /**
     * Table associant chaque {@code Mentionable} au nombre de fois où il a été mentionné
     * dans les publications du média observé.
     */
    private final HashMap<Mentionable, Integer> mentionsMap = new HashMap<>();

    /**
     * Référence vers le média observé par ce module.
     */
    private final Media media;

    /**
     * Historique des parts de propriété détenues dans le média.
     */
    private final List<Ownership> sharesHistory = new ArrayList<>();

    /**
     * Ensemble des propriétaires (directs) qui ont détenu au moins une part du média.
     */
    private final Set<Owner> owners = new HashSet<>();


    /**
     * Crée un module d’observation pour un média donné.
     * Initialise l’historique des parts et les propriétaires initiaux.
     *
     * @param media le média à surveiller
     */
    public MediaModule(Media media){
        super();
        this.media = media;
        sharesHistory.addAll(media.getShares());
        for(Ownership o : media.getShares()){
            owners.add(o.getOrigin());
        }
    }


    /**
     * Méthode appelée lorsqu'une mise à jour est reçue de l’objet observé.
     * <ul>
     *   <li>Si l’objet notifié est une {@code Publication}, les mentions sont enregistrées.
     *       Si une mention dépasse le {@code seuil}, elle est notifiée aux observateurs.</li>
     *   <li>Si l’objet notifié est une {@code Ownership}, l'historique est mis à jour.
     *       Si le propriétaire est nouveau, il est ajouté à la liste et notifié aux observateurs.</li>
     * </ul>
     *
     * @param o   l’objet observé
     * @param arg l’objet transmis lors de la notification (Publication ou Ownership)
     */
    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Publication p){
            for(Mentionable m : p.getMentions()){
                mentionsMap.put(m, mentionsMap.get(m) != null ? mentionsMap.get(m)+1 : 1);
            }
            for(Mentionable m2 : p.getMentions()){
                int somme = 0;
                for(Integer i: mentionsMap.values()){
                    somme+=i;
                }
                if(((double) mentionsMap.get(m2) /somme)>seuil){
                    setChanged();
                    notifyObservers(m2);
                }
            }
        }
        else{
            Ownership own = (Ownership) arg;
            sharesHistory.add(own);
            if(!(owners.contains(own.getOrigin()))){
                owners.add(own.getOrigin());
                setChanged();
                notifyObservers(own);
            }
        }
    }

    /**
     * Retourne le média observé par ce module.
     *
     * @return le média surveillé
     */
    public Media getMedia() {
        return media;
    }
}
