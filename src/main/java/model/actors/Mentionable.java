package model.actors;

/**
 * Interface représentant une entité qui peut être mentionnée par son nom.
 * <p>
 * Elle définit une méthode pour accéder au nom de l'entité.
 * </p>
 *
 * @author [Adam BELLIK]
 */
public interface Mentionable {
    /**
     * Retourne le nom de l'entité mentionnable.
     *
     * @return Le nom de l'entité
     */
    public String getName();
}
