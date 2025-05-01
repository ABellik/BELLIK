package model.ownership;

import data.repository.DataRepository;
import model.actors.Owner;

public class Ownership {
    private final int id;
    private Owner origin;
    private final Appropriable property;
    private double percentage;

    public Ownership(int id, Owner origin, Appropriable property, double percentage) {
        this.id = id;
        this.origin = origin;
        this.property = property;
        this.percentage = percentage;
        DataRepository.getOwnerships().add(this);
    }

    @Override
    public String toString(){
        return "Possession N°"+id+"\n\tPropriétaire :\n\t"+origin.toString()+"\n\tPossession :\n\t"+property.toString()+"\n\tPourcentage: "+(percentage != 0 ? percentage : "inconnu/incertain")+"\n";
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

    public void setOrigin(Owner origin) {
        this.origin = origin;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
}
