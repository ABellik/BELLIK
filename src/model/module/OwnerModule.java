package model.module;

import model.publication.Publication;

import java.util.ArrayList;
import java.util.Observable;

public class OwnerModule extends Module {

    private final ArrayList<Publication> mentions = new ArrayList<Publication>();

    @Override
    public void update(Observable o, Object arg) {


    }
}
