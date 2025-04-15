package model.publication;

import model.actors.Mentionable;
import model.media.Media;

import java.util.ArrayList;
import java.util.Date;

public class Article extends Publication {
    public Article(Date date, String title, Media source, ArrayList<Mentionable> mentions){
        super(date, title, source, mentions);
    }
}
