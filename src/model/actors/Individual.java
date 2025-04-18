package model.actors;

import model.module.IndividualModule;

import java.util.Observer;

public class Individual extends Owner {
    private final int rankChallenges2024;
    private final int billionaireForbes2024;
    private final IndividualModule mod = new IndividualModule(this);

    public Individual(String name, int rankChallenges2024, int billionaireForbes2024){
        super(name);
        this.rankChallenges2024 = rankChallenges2024;
        this.billionaireForbes2024 = billionaireForbes2024;
        this.addObserver(this.getMod());
    }

    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
    }

    @Override
    public synchronized void deleteObserver(Observer o) {
        super.deleteObserver(o);
    }

    public IndividualModule getMod() {
        return mod;
    }

    @Override
    public String toString(){
        return super.toString()
                +"\n\tClassement Challenges en 2024 : "    + (rankChallenges2024!=0 ? Integer.toString(rankChallenges2024)       : "")
                +"\n\tRang Milliardaire Forbes en 2024 : " + (billionaireForbes2024!=0 ? Integer.toString(billionaireForbes2024) : "")
                +"\n";
    }
}
