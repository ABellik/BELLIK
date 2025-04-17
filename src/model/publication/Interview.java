package model.publication;

import model.actors.Mentionable;
import model.media.Media;

import java.util.Date;
import java.util.List;

public class Interview extends Publication {
    public Interview(Date date, String title, Media source, List<Mentionable> mentions){
        super(date, title, source, mentions);
    }
}
