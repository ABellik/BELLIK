package model.publication;

import model.actors.Mentionable;
import model.media.Media;

import java.util.Date;
import java.util.Set;

/**
 * Classe représentant un article émis par un média.
 * <p>Un article hérite d'une {@code Publication}</p>
 *
 * @author [Adam BELLIK]
 */
public class Article extends Publication {
    /**
     * Construit une nouvelle instance de {@code Article}.
     *
     * @param date     la date de parution de la publication
     * @param title    le titre de la publication
     * @param source   le média ayant publié cette publication
     * @param mentions l’ensemble des entités (individus ou organisations) mentionnées dans la publication
     */
    public Article(Date date, String title, Media source, Set<Mentionable> mentions){
        super(date, title, source, mentions);
    }
}
