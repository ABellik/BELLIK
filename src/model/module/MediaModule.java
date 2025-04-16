package model.module;

import model.actors.Mentionable;

import java.util.HashMap;
import java.util.Observable;

public class MediaModule extends Module {
    private HashMap<Integer, Mentionable> mentionsList = new HashMap<Integer, Mentionable>();

    @Override
    public void update(Observable o, Object arg) {

    }
}
