package model.media;

import exceptions.MediaTypeException;
import model.actors.Mentionable;


import java.util.List;

public class OtherMedia extends Media{
    public OtherMedia(String name, String scale, String price, boolean disappeared){
        super(name,scale,price,disappeared);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public void publish(String title, List<Mentionable> mentions, String type) throws MediaTypeException {
        throw new MediaTypeException("Ce type de média ne peut momentanément pas publier");
    }
}
