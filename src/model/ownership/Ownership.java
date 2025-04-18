package model.ownership;

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
    }

    @Override
    public String toString(){
        return "Possession N°"+Integer.toString(id)+"\n\t"+origin.toString()+"\n\t"+property.toString()+"\n\tPourcentage: "+Double.toString(percentage)+"\n";
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
