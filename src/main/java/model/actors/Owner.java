package model.actors;

import model.ownership.Ownership;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Classe abstraite représentant un propriétaire
 * qui peut détenir une ou plusieurs possessions ({@code Ownership}).
 *
 * <p>Cette classe implémente {@code Mentionable} et hérite de {@code Observable}
 * afin de notifier les observateurs lors de modifications d'état.</p>
 *
 * @author [Adam BELLIK]
 */
abstract public class Owner extends Observable implements Mentionable {
    /** Le nom du propriétaire. */
    private final String name;

    /** La liste des possessions (ownerships) détenues par ce propriétaire. */
    private final List<Ownership> ownerships = new ArrayList<>();

    /**
     * Constructeur d'un propriétaire avec un nom donné.
     *
     * @param name Le nom du propriétaire
     */
    public Owner(String name){
        this.name=name;
    }

    /**
     * Retourne le nom du propriétaire.
     *
     * @return Le nom du propriétaire
     */
    public String getName() {
        return name;
    }

    /**
     * Retourne une représentation textuelle du propriétaire.
     *
     * @return Une chaîne contenant le nom du propriétaire
     */
    @Override
    public String toString(){
        return "Nom du propriétaire : " + name;
    }

    /**
     * Ajoute une possession (Ownership) à la liste de celles détenues par ce propriétaire.
     *
     * @param o La possession à ajouter
     */
    public void addOwnership(Ownership o){
        ownerships.add(o);
    }

    /**
     * Retire une possession (Ownership) à la liste de celles détenues par ce propriétaire.
     *
     * @param o La possession à retirer
     */
    public void removeOwnership(Ownership o){
        ownerships.remove(o);
    }

    /**
     * Retourne la liste des possessions détenues par ce propriétaire.
     *
     * @return La liste des objets {@code Ownership} associés à ce propriétaire
     */
    public List<Ownership> getOwnerships(){
        return ownerships;
    }
}
