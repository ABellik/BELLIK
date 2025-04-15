package model.actors;

abstract public class Owner implements Mentionable {
    private String nom;

    public String getNom() {
        return nom;
    }

    @Override
    public String toString(){
        StringBuilder SB = new StringBuilder();
        SB.append("Nom du propriétaire : ").append(nom);
        return SB.toString();
    }
}
