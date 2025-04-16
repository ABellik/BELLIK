package model.media;

import model.actors.Mentionable;
import model.module.OwnerModule;
import model.ownership.Appropriable;
import model.publication.Publication;

import java.util.Observable;

public abstract class Media extends Observable implements Mentionable, Appropriable {
    private String name;
    private String scale;
    private String price;
    private boolean disappeared;

    static private OwnerModule mod = new OwnerModule();

    @Override
    public String toString(){
        StringBuilder SB = new StringBuilder();
        SB.append("Média : ").append(name);
        SB.append("Echelle : ").append(scale);
        SB.append("Prix : ").append(price);
        if (disappeared) SB.append("Ce média n'existe plus");
        return SB.toString();
    }

    public void publish(Publication pub){
        notifyObservers(pub);
    }

}
