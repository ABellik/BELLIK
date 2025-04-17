package model.module;

import model.actors.Owner;
import model.media.Media;
import model.publication.Publication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

public class OwnerModule extends Module {

    private final ArrayList<Publication> mentions = new ArrayList<>();
    private final HashMap<Media, Double> percentages = new HashMap<>();
    private final Owner owner;

    public OwnerModule(Owner owner){
        this.owner = owner;
    }

    @Override
    public void update(Observable o, Object arg) {



    }

    public Owner getOwner() {
        return owner;
    }
}
