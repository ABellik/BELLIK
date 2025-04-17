package model.publication;

import model.actors.Mentionable;
import model.media.Media;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public abstract class Publication{
    private final Date date;
    private final String title;
    private final Media source;
    private final List<Mentionable> mentions = new ArrayList<>();



    public Publication(Date date, String title, Media source, List<Mentionable> mentions){
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
}
