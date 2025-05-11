package model.media;

import model.actors.Mentionable;
import model.module.MediaModule;
import model.ownership.Appropriable;
import model.ownership.Ownership;
import model.publication.Publication;

import java.util.*;

/**
 * Classe abstraite représentant un média.
 * <p>
 * Un média est une entité mentionnable et appropriable, pouvant publier des contenus
 * et être la propriété de différents propriétaires via des {@code Ownership}.
 * Il est observable et possède un module de traitement spécifique ({@code MediaModule}).
 * </p>
 *
 * <p>Chaque média est caractérisé par un nom, une échelle (locale, nationale, etc.),
 * un prix, et un état de disparition éventuel.</p>
 *
 * @author [Adam BELLIK]
 */
public abstract class Media extends Observable implements Mentionable, Appropriable {
    /** Le nom du média. */
    private final String name;

    /** L'échelle (locale, nationale...) à laquelle s'adresse le média. */
    private final String scale;

    /** Le prix du média. */
    private final String price;

    /** Indique si le média a disparu (true) ou est encore actif (false). */
    private final boolean disappeared;

    /** Liste des publications produites par ce média. */
    private final List<Publication> publications = new ArrayList<>();

    /** Liste des parts de propriété (Ownership) liées à ce média. */
    private final List<Ownership> shares = new ArrayList<>();

    /** Module associé au média, pour traitements spécifiques. */
    private final MediaModule mod = new MediaModule(this);

    /**
     * Constructeur d'un média.
     *
     * @param name Le nom du média
     * @param scale L'échelle du média (locale, nationale, etc.)
     * @param price Le prix associé au média
     * @param disappeared Indique si le média a disparu
     */
    public Media(String name, String scale, String price, boolean disappeared){
        this.name = name;
        this.scale = scale;
        this.price = price;
        this.disappeared = disappeared;
        this.addObserver(this.getMod());
    }

    /**
     * Ajoute un observateur à ce média.
     *
     * @param o L'observateur à ajouter
     */
    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
    }

    /**
     * Supprime un observateur de ce média.
     *
     * @param o L'observateur à supprimer
     */
    @Override
    public synchronized void deleteObserver(Observer o) {
        super.deleteObserver(o);
    }

    /**
     * Retourne une représentation textuelle du média.
     *
     * @return Une chaîne décrivant le média
     */
    @Override
    public String toString(){
        return "Média : "+name+"\n\tEchelle : "+scale+"\n\tPrix : "+price+(disappeared ? "\n\tCe média n'existe plus\n" : "\n");
    }

    /**
     * Méthode abstraite de publication.
     * Doit être implémentée par les sous-classes pour publier un contenu.
     *
     * @param title Le titre de la publication
     * @param mentions Les entités mentionnées dans la publication
     * @param type Le type de la publication
     */
    public abstract void publish(String title, Set<Mentionable> mentions, String type);

    /**
     * Retourne la liste des publications produites par ce média.
     *
     * @return La liste des {@code Publication}
     */
    public List<Publication> getPublications() {
        return publications;
    }

    /**
     * Retourne la liste des parts de propriété du média.
     *
     * @return La liste des {@code Ownership}
     */
    @Override
    public List<Ownership> getShares(){
        return shares;
    }

    /**
     * Ajoute une part de propriété au média.
     *
     * @param o La part de propriété à ajouter
     */
    @Override
    public void addShare(Ownership o){
        shares.add(o);

    }

    /**
     * Supprime une part de propriété du média.
     *
     * @param o La part de propriété à supprimer
     */
    @Override
    public void removeShare(Ownership o){
        shares.remove(o);
    }

    /**
     * Retourne le module de gestion associé au média.
     *
     * @return Le {@code MediaModule} du média
     */
    public MediaModule getMod() {
        return mod;
    }

    /**
     * Retourne le nom du média.
     *
     * @return Le nom du média
     */
    public String getName() {
        return name;
    }

    /**
     * Notifie les observateurs d’un changement avec un argument donné.
     *
     * @param arg L’objet représentant le changement
     */
    public void notification(Object arg){
        setChanged();
        notifyObservers(arg);
    }
}
