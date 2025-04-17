package model.media;

import exceptions.MediaTypeException;
import model.actors.Mentionable;


import java.util.List;

public class Site extends Media{
    public Site(String name, String scale, String price, boolean disappeared){
        super(name, scale, price, disappeared);
    }

    @Override
    public String toString() {
        return super.toString()+"\tType : Site\n";
    }

    @Override
    public void publish(String title, List<Mentionable> mentions, String type) throws MediaTypeException {
        throw new MediaTypeException("Le type site ne peut momentanément pas publier");
    }
}
