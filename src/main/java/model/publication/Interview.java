package model.publication;

import model.actors.Mentionable;
import model.media.Media;

import java.util.Date;
import java.util.Set;

public class Interview extends Publication {
    public Interview(Date date, String title, Media source, Set<Mentionable> mentions){
        super(date, title, source, mentions);
    }
}
