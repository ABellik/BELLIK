package model.module;

import model.actors.Individual;
import model.media.Media;
import model.ownership.OwnershipManager;
import model.publication.Publication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

/**
 * Module associé à un {@code Individual}, permettant de surveiller les publications
 * dans lesquelles cet individu est mentionné.
 *
 * <p>Ce module a pour objectifs de :</p>
 * <ul>
 *   <li>Maintenir un historique des publications mentionnant l'individu,</li>
 *   <li>Calculer la proportion de mentions par média,</li>
 *   <li>Notifier la vigie lorsqu'un individu est mentionné dans un média qu'il possède.</li>
 * </ul>
 */
public class IndividualModule extends Module {
    /**
     * Liste des publications où l’individu est mentionné.
     */
    private final ArrayList<Publication> mentions = new ArrayList<>();

    /**
     * Nombre de fois où l’individu a été mentionné par chaque média.
     */
    private final HashMap<Media, Integer> mentionsMap = new HashMap<>();

    /**
     * Référence vers l’individu surveillé.
     */
    private final Individual individual;

    /**
     * Construit un module associé à un {@code Individual}.
     *
     * @param individual l’individu à surveiller
     */
    public IndividualModule(Individual individual){
        super();
        this.individual = individual;
    }

    /**
     * Méthode appelée lorsqu’une publication mentionne cet individu.
     * <ul>
     *   <li>Ajoute la publication à l’historique,</li>
     *   <li>Met à jour le nombre de mentions pour le média concerné,</li>
     *   <li>Déclenche une alerte si l’individu est mentionné dans un média qu’il possède.</li>
     * </ul>
     *
     * @param o   l’objet observé (en général un média)
     * @param arg l’objet transmis lors de la notification (doit être une {@code Publication})
     */
    @Override
    public void update(Observable o, Object arg) {
        Publication p = (Publication) arg;
        mentions.add(p);
        mentionsMap.put(p.getSource(), mentionsMap.get(p.getSource()) != null ? mentionsMap.get(p.getSource())+1 : 1);

        if (OwnershipManager.ownsMedia(this.individual,p.getSource())) {
            setChanged();
            notifyObservers(p);
        }
    }

    /**
     * Retourne l’individu associé à ce module.
     *
     * @return l’individu surveillé
     */
    public Individual getIndividual() {
        return individual;
    }
}
