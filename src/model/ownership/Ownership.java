package model.ownership;

import model.actors.Owner;

import java.util.Observable;
import java.util.Observer;

public class Ownership extends Observable {
    private final int id;
    private Owner origin;
    private final Appropriable property;
    private double percentage;


    public Ownership(int id, Owner origin, Appropriable property, double percentage) {
        this.id = id;
        this.origin = origin;
        this.property = property;
        this.percentage = percentage;
    }

    public void setOrigin(Owner origin) {
        this.origin = origin;
        notifyObservers();
    }

    public int getId() {
        return id;
    }

    public Appropriable getProperty() {
        return property;
    }

    public Owner getOrigin() {
        return origin;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
        notifyObservers();
    }

    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
    }
}
