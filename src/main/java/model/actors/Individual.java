package model.actors;

import model.module.IndividualModule;

import java.util.Observer;

/**
 * Classe représentant une personne
 *
 * <p>Cette classe hérite de {@code Owner}</p>
 *
 * @author [Adam BELLIK]
 */
public class Individual extends Owner {
    /** Le classement Challenges 2024 de la personne. */
    private final int rankChallenges2024;

    /** Le classement milliardaire Forbes 2024 de la personne. */
    private final int billionaireForbes2024;

    /** Le module surveillant la personne. */
    private final IndividualModule mod = new IndividualModule(this);

    /**
     * Construit une personne.
     *
     * @param name Le nom du propriétaire
     * @param rankChallenges2024 Le classement Challenges 2024
     * @param billionaireForbes2024 Le classement Forbes 2024
     */
    public Individual(String name, int rankChallenges2024, int billionaireForbes2024){
        super(name);
        this.rankChallenges2024 = rankChallenges2024;
        this.billionaireForbes2024 = billionaireForbes2024;
        //this.addObserver(this.getMod());
    }

    /**
     * Ajoute un observateur pour cette personne
     *
     * @param o L'observateur
     */
    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
    }

    /**
     * Supprime un observateur pour cette personne
     *
     * @param o L'observateur
     */
    @Override
    public synchronized void deleteObserver(Observer o) {
        super.deleteObserver(o);
    }

    /**
     * Retourner le module surveillant cette personne
     *
     * @return le module surveillant cette personne
     */
    public IndividualModule getMod() {
        return mod;
    }

    /**
     * Retourne une représentation textuelle de la personne
     *
     * @return Une chaîne contenant les informations sur la personne
     */
    @Override
    public String toString(){
        return super.toString()
                +"\n\tClassement Challenges en 2024 : "    + (rankChallenges2024!=0 ? Integer.toString(rankChallenges2024)       : "")
                +"\n\tRang Milliardaire Forbes en 2024 : " + (billionaireForbes2024!=0 ? Integer.toString(billionaireForbes2024) : "")
                +"\n";
    }
}
