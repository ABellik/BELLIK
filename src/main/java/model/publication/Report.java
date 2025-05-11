package model.publication;

import model.actors.Mentionable;
import model.media.Media;

import java.util.Date;
import java.util.Set;

/**
 * Classe représentant un reportage émis par un média.
 * <p>Un reportage hérite d'une {@code Publication}</p>
 *
 * @author [Adam BELLIK]
 */
public class Report extends Publication {
    /**
     * Construit une nouvelle instance de {@code Report}.
     *
     * @param date     la date de parution de la publication
     * @param title    le titre de la publication
     * @param source   le média ayant publié cette publication
     * @param mentions l’ensemble des entités (individus ou organisations) mentionnées dans la publication
     */
    public Report(Date date, String title, Media source, Set<Mentionable> mentions){
        super(date, title, source, mentions);
    }
}
