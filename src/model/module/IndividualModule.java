package model.module;

import model.actors.Individual;
import model.media.Media;
import model.ownership.Ownership;
import model.ownership.OwnershipManager;
import model.publication.Publication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

public class IndividualModule extends Module {
    /*
    Chaque instance de ce module est associé à un "Individual".
    Ce module doit :
        - Avoir un historique des publications où le "Individual" est mentionné
        - Donner le % de mentions par média
        - Alerter la vigie si le "Individual" est mentionné dans un média qu'il possède
     */


    private final ArrayList<Publication> mentions = new ArrayList<>();
    private final HashMap<Media, Integer> mentionsMap = new HashMap<>();
    private final Individual individual;

    public IndividualModule(Individual individual){
        super();
        this.individual = individual;
    }

    @Override
    public void update(Observable o, Object arg) {
        Publication p = (Publication) arg;
        mentions.add(p);
        mentionsMap.put(p.getSource(), mentionsMap.get(p.getSource()) != null ? mentionsMap.get(p.getSource())+1 : 1);

        if (OwnershipManager.ownsMedia(this.individual,p.getSource())) {
            setChanged();
            notifyObservers(p);
        }
    }

    public Individual getIndividual() {
        return individual;
    }

    public double getPercentage(Media m){
        return (double) mentionsMap.get(m) /mentions.size();
    }

}
