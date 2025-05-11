package model.ownership;

import java.util.List;


/**
 * Interface représentant une entité pouvant être possédée, comme un média ou une organisation.
 * <p>
 * Les objets implémentant cette interface peuvent avoir des parts de propriété (ownerships).
 * </p>
 *
 * @author [Adam BELLIK]
 */
public interface Appropriable {
    /**
     * Ajoute une part de propriété à cette entité.
     *
     * @param o la part de propriété à ajouter (association avec un propriétaire)
     */
    public void addShare(Ownership o);

    /**
     * Supprime une part de propriété de cette entité.
     *
     * @param o la part de propriété à supprimer
     */
    public void removeShare(Ownership o);

    /**
     * Retourne la liste de toutes les parts de propriété associées à cette entité.
     *
     * @return une liste d'objets {@link Ownership} représentant les parts de propriété
     */
    public List<Ownership> getShares();

    /**
     * Retourne le nom de l'entité possédée.
     *
     * @return le nom de l'entité
     */
    public String getName();
}
