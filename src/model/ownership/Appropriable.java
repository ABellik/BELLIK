package model.ownership;

import model.actors.Owner;

import java.util.ArrayList;

public interface Appropriable {
    public ArrayList<Owner> getOwners();
}
