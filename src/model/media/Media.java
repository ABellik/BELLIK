package model.media;

import model.actors.Mentionable;
import model.module.MediaModule;
import model.ownership.Appropriable;
import model.ownership.Ownership;
import model.publication.Publication;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public abstract class Media extends Observable implements Mentionable, Appropriable {
    private final String name;
    private final String scale;
    private final String price;
    private boolean disappeared;

    private final List<Publication> publications = new ArrayList<>();
    private final List<Ownership> ownerships = new ArrayList<>();

    private final MediaModule mod = new MediaModule(this);

    public Media(String name, String scale, String price, boolean disappeared){
        this.name = name;
        this.scale = scale;
        this.price = price;
        this.disappeared = disappeared;
        this.addObserver(this.getMod());
    }

    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
    }

    @Override
    public synchronized void deleteObserver(Observer o) {
        super.deleteObserver(o);
    }

    @Override
    public String toString(){
        return "Média : "+name+"\n\tEchelle : "+scale+"\n\tPrix : "+price+(disappeared ? "\n\tCe média n'existe plus\n" : "\n");
    }

    public abstract void publish(String title, List<Mentionable> mentions, String type);

    public List<Publication> getPublications() {
        return publications;
    }

    public void setDisappeared(boolean disappeared) {
        this.disappeared = disappeared;
    }

    public void addOwnership(Ownership o){
        ownerships.add(o);
    }

    public MediaModule getMod() {
        return mod;
    }

    public String getName() {
        return name;
    }
}
