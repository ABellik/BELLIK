package model.module;

import java.util.Observable;
import java.util.Observer;

public abstract class Module extends Observable implements Observer {
    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
    }
}
