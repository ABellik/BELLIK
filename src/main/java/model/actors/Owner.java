package model.actors;

import model.ownership.Ownership;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

abstract public class Owner extends Observable implements Mentionable {
    private final String name;
    private final List<Ownership> ownerships = new ArrayList<>();

    public Owner(String name){
        this.name=name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString(){
        return "Nom du propriétaire : " + name;
    }

    public void addOwnership(Ownership o){
        ownerships.add(o);
    }

    public void removeOwnership(Ownership o){
        ownerships.remove(o);
    }

    public List<Ownership> getOwnerships(){
        return ownerships;
    }
}
