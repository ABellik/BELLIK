package model.publication;

import model.actors.Mentionable;
import model.media.Media;

import java.util.*;

/**
 * Classe abstraite représentant une publication émise par un média.
 * Une publication contient une date, un titre, une source (le média d'origine),
 * ainsi qu'un ensemble d'entités mentionnées (mentionnables).
 *
 * <p>Les sous-classes peuvent spécialiser le type de publication si nécessaire.</p>
 * @author [Adam BELLIK]
 */
public abstract class Publication{
    /** Date de parution de la publication */
    private final Date date;

    /** Titre de la publication */
    private final String title;

    /** Média source de la publication */
    private final Media source;

    /** Ensemble des entités mentionnées dans la publication */
    private final Set<Mentionable> mentions = new HashSet<>();

    /**
     * Constructeur de publication.
     *
     * @param date     la date de parution de la publication
     * @param title    le titre de la publication
     * @param source   le média ayant publié cette publication
     * @param mentions l’ensemble des entités (individus ou organisations) mentionnées dans la publication
     */
    public Publication(Date date, String title, Media source, Set<Mentionable> mentions){
        this.date   = date;
        this.title  = title;
        this.source = source;
        this.mentions.addAll(mentions);
    }

    /**
     * Retourne la date de parution de la publication.
     *
     * @return la date de publication
     */
    public Date getDate() {return date;}

    /**
     * Retourne le titre de la publication.
     *
     * @return le titre
     */
    public String getTitle() {
        return title;
    }

    /**
     * Retourne le média ayant publié cette publication.
     *
     * @return le média source
     */
    public Media getSource() {
        return source;
    }

    /**
     * Retourne l'ensemble des entités mentionnées dans cette publication.
     *
     * @return un ensemble d'entités {@link Mentionable} mentionnées
     */
    public Set<Mentionable> getMentions(){
        return mentions;
    }
}
