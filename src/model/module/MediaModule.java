package model.module;

import model.actors.Mentionable;
import model.actors.Owner;
import model.media.Media;
import model.ownership.Ownership;
import model.publication.Publication;

import java.util.*;

public class MediaModule extends Module {
    private static final double seuil = 0.1;
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


    private final HashMap<Mentionable, Integer> mentionsMap = new HashMap<>();
    private final Media media;
    private final List<Ownership> sharesHistory = new ArrayList<>();
    private final Set<Owner> owners = new HashSet<>();

    public MediaModule(Media media){
        this.media = media;
        sharesHistory.addAll(media.getShares());
        for(Ownership o : media.getShares()){
            owners.add(o.getOrigin());
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Publication p){
            for(Mentionable m : p.getMentions()){
                mentionsMap.put(m, mentionsMap.get(m) != null ? mentionsMap.get(m)+1 : 1);
                int somme = 0;
                for(Integer i: mentionsMap.values()){
                    somme+=i;
                }
                if(((double) mentionsMap.get(m) /somme)>seuil){
                    notifyObservers(m);
                }
            }
        }
        else{
            Ownership own = (Ownership) arg;
            sharesHistory.add(own);
            if(!(owners.contains(own.getOrigin()))){
                owners.add(own.getOrigin());
                notifyObservers(own);
            }
        }
    }

    public Media getMedia() {
        return media;
    }

    public double getSeuil() {
        return seuil;
    }

    public List<Ownership> getSharesHistory(){
        return sharesHistory;
    }


}
