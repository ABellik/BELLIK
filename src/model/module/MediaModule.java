package model.module;

import model.actors.Mentionable;
import model.media.Media;

import java.util.HashMap;
import java.util.Observable;

public class MediaModule extends Module {
    /*
    Chaque instance de ce module est associé à un "Media".
    Ce module doit :
        - Avoir une liste du nombre de mentions par "Mentionable"
        - Avoir un historique :
            - des rachats de parts du "Media"
            - des "Propriétaire"
        - Alerter la vigie si :
            - Un "Mentionable" est mentionné trop de fois (% > Seuil)
            - Un nouveau "Propriétaire" rachète des parts
     */


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
