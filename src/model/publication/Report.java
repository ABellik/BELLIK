package model.publication;

import model.actors.Mentionable;
import model.media.Media;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class Report extends Publication {
    public Report(Date date, String title, Media source, Set<Mentionable> mentions){
        super(date, title, source, mentions);
    }
}
