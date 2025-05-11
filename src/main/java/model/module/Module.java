package model.module;

import model.monitoring.Monitoring;

import java.util.Observable;
import java.util.Observer;


/**
 * Classe abstraite représentant un module, surveillé par une vigie, et qui surveille l'état d'une entité particulière
 *
 * <p>Cette classe implémente {@code Observer} et hérite de {@code Observable}
 * afin d'observer des modifications d'états sur certains objets
 * et de notifier les observateurs lors de modifications d'état</p>
 *
 * @author [Adam BELLIK]
 */
public abstract class Module extends Observable implements Observer {
    /**Observateur statique associé à tous les modules créés*/
    private static final Monitoring monitoring = new Monitoring();

    /**
     * Constructeur d'un module ajoutant la vigie monitoring aux observateurs du module
     */
    public Module(){
        this.addObserver(monitoring);
    }

    /**
     * Ajoute un observateur (Observer) à la liste de celles de ce module.
     *
     * @param o L'observateur à ajouter
     */
    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
    }

    /**
     * Retourne la vigie qui surveille les modules
     *
     * @return la vigie {@code Monitoring}
     */
    public static Monitoring getMonitoring() {
        return monitoring;
    }
}
