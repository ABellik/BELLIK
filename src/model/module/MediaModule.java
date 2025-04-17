package model.module;

import model.actors.Mentionable;
import model.media.Media;

import java.util.HashMap;
import java.util.Observable;

public class MediaModule extends Module {
    private final HashMap<Mentionable, Integer> mentionsList = new HashMap<>();
    private final Media media;

    public MediaModule(Media media){
        this.media = media;
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    public Media getMedia() {
        return media;
    }
}
