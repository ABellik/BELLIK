package model.actors;

public class Individual extends Owner {
    private final int rankChallenges2024;
    private final int billionaireForbes2024;

    public Individual(String name, int rankChallenges2024, int billionaireForbes2024){
        super(name);
        this.rankChallenges2024 = rankChallenges2024;
        this.billionaireForbes2024 = billionaireForbes2024;
    }

    @Override
    public String toString(){
        return super.toString()
                +"\n\tClassement Challenges en 2024 : "    + (rankChallenges2024!=0 ? Integer.toString(rankChallenges2024)       : "")
                +"\n\tRang Milliardaire Forbes en 2024 : " + (billionaireForbes2024!=0 ? Integer.toString(billionaireForbes2024) : "")
                +"\n";
    }
}
