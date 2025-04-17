package model.actors;

import model.module.OwnerModule;
import model.ownership.Ownership;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

abstract public class Owner extends Observable implements Mentionable {
    private final String name;
    private final List<Ownership> ownerships = new ArrayList<>();
    private final OwnerModule mod = new OwnerModule(this);

    public Owner(String name){
        this.name=name;
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

    public String getName() {
        return name;
    }

    public OwnerModule getMod() {
        return mod;
    }

    @Override
    public String toString(){
        return "Nom du propriétaire : " + name;
    }

    public void addOwnership(Ownership o){
        ownerships.add(o);
    }
}
