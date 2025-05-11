package model.ownership;

import data.repository.DataRepository;
import model.actors.Owner;

/**
 * Classe représentant une relation de propriété entre un propriétaire (Owner) et un bien possédable (Appropriable).
 *
 * <p>
 * Une possession est caractérisée par un identifiant unique, un propriétaire {@code Owner}, une propriété {@code Appropriable}, et un pourcentage de possession.
 * </p>
 *
 * @author [Adam BELLIK]
 */
public class Ownership {
    private static int last_id = 0;
    private final int id;
    private Owner origin;
    private final Appropriable property;
    private double percentage;

    /**
     * Construit une nouvelle possession avec un propriétaire, une propriété, et un pourcentage donné.
     * L'objet est automatiquement ajouté au référentiel de données.
     *
     * @param origin     Le propriétaire initial de la possession.
     * @param property   La propriété concernée par la possession.
     * @param percentage Le pourcentage de possession.
     */
    public Ownership(Owner origin, Appropriable property, double percentage) {
        this.id = last_id++;
        this.origin = origin;
        this.property = property;
        this.percentage = percentage;
        DataRepository.getOwnerships().add(this);
    }

    /**
     * Renvoie une représentation textuelle de la possession.
     *
     * @return Chaîne descriptive de la possession.
     */
    @Override
    public String toString(){
        return "Possession N°"+id+"\n\tPropriétaire :\n\t"+origin.toString()+"\n\tPossession :\n\t"+property.toString()+"\n\tPourcentage: "+(percentage != 0 ? percentage : "inconnu/incertain")+"\n";
    }

    /** @return l'identifiant unique de la possession. */
    public int getId() {
        return id;
    }

    /** @return Le bien possédé. */
    public Appropriable getProperty() {
        return property;
    }

    /** @return Le propriétaire actuel de la possession. */
    public Owner getOrigin() {return origin;}

    /** @return Le pourcentage de possession. */
    public double getPercentage() {
        return percentage;
    }

    /**
     * Modifie le propriétaire de la possession.
     *
     * @param origin Le nouveau propriétaire.
     */
    public void setOrigin(Owner origin) {
        this.origin = origin;
    }

    /**
     * Modifie le pourcentage de possession.
     *
     * @param percentage Le nouveau pourcentage.
     */
    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
}
