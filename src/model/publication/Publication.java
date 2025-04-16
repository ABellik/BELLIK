package model.publication;

import model.actors.Mentionable;
import model.media.Media;
import model.module.OwnerModule;

import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;

public abstract class Publication{
    private Date date;
    private String title;
    private Media source;
    private ArrayList<Mentionable> mentions;



    public Publication(Date date, String title, Media source, ArrayList<Mentionable> mentions){
        this.date   = date;
        this.title  = title;
        this.source = source;
        this.mentions.addAll(mentions);

    }
}
