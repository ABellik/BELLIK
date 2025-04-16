package model.actors;

import model.module.OwnerModule;
import model.publication.Publication;

import java.util.ArrayList;

abstract public class Owner implements Mentionable {
    private String nom;

    public String getNom() {
        return nom;
    }

    private final OwnerModule mod = new OwnerModule();

    @Override
    public String toString(){
        return "Nom du propriétaire : " + nom;
    }

}
