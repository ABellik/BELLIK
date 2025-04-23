package model.module;

import model.monitoring.Monitoring;

import java.util.Observable;
import java.util.Observer;

public abstract class Module extends Observable implements Observer {
    private static final Monitoring monitoring = new Monitoring();

    public Module(){
        this.addObserver(monitoring);
    }

    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
    }

}
