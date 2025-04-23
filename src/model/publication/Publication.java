package model.publication;

import model.actors.Mentionable;
import model.media.Media;


import java.util.*;


public abstract class Publication{
    private final Date date;
    private final String title;
    private final Media source;
    private final Set<Mentionable> mentions = new HashSet<>();



    public Publication(Date date, String title, Media source, Set<Mentionable> mentions){
        this.date   = date;
        this.title  = title;
        this.source = source;
        this.mentions.addAll(mentions);

    }

    public Date getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public Media getSource() {
        return source;
    }

    public Set<Mentionable> getMentions(){
        return mentions;
    }
}
