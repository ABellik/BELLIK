package model.ownership;

import data.repository.DataRepository;
import exceptions.IllegalPercentageException;
import model.actors.Organization;
import model.actors.Owner;
import model.media.Media;

import java.util.HashSet;
import java.util.Set;

/**
 * Classe utilitaire pour gérer les opérations sur les possessions (Ownership).
 *
 * <p>Contient des méthodes de rachat, d’analyse de propriété, et de récupération de médias possédés.</p>
 *
 * @author [Adam BELLIK]
 */
public final class OwnershipManager {
    /**
     * Permet à un acheteur de racheter une part d'une possession existante.
     *
     * @param o          La possession à racheter.
     * @param buyer      Le nouveau propriétaire souhaité.
     * @param percentage Le pourcentage que le buyer souhaite acquérir.
     * @throws IllegalPercentageException si le pourcentage demandé est supérieur à celui de la possession.
     * @throws IllegalArgumentException   si la possession a un pourcentage inconnu (0).
     */
    public static void buyOutOwnership(Ownership o, Owner buyer, double percentage) throws IllegalPercentageException {
        if(o.getPercentage() == 0){
            throw new IllegalArgumentException(o.getOrigin().getName()+" ne peux pas vendre cette part car nous n'avons pas la connaissance de la réelle part possédée !");
        }
        else if (o.getPercentage() < percentage) {
            throw new IllegalPercentageException("Le pourcentage d'achat est plus élevé que celui de la part !");
        }
        else if (o.getPercentage() == percentage) {
            if(OwnershipManager.ownsPropertyDirectly(buyer,o.getProperty())){
                DataRepository.searchOwnership(buyer,o.getProperty()).setPercentage(DataRepository.searchOwnership(buyer,o.getProperty()).getPercentage()+percentage);
                o.getOrigin().removeOwnership(o);
            }
            else{
                o.getOrigin().removeOwnership(o);
                o.setOrigin(buyer);
                o.getOrigin().addOwnership(o);
                if(o.getProperty() instanceof Media property){
                    property.notification(o);
                }
            }
        }
        else {
            o.setPercentage(o.getPercentage() - percentage);
            if(OwnershipManager.ownsPropertyDirectly(buyer,o.getProperty())){
                System.out.println(buyer.getOwnerships()+"\n\n\n"+o.getProperty().getShares());

                Ownership ownership = DataRepository.searchOwnership(buyer,o.getProperty());
                ownership.setPercentage(ownership.getPercentage()+percentage);
            }
            else {
                Ownership o2 = new Ownership(buyer, o.getProperty(), percentage);
                buyer.addOwnership(o2);
                o.getProperty().addShare(o2);
                if (o.getProperty() instanceof Media property) {
                    property.notification(o2);
                }
            }

        }
    }

    /**
     * Récupère l'ensemble des médias directement ou indirectement possédés par un propriétaire.
     *
     * @param owner Le propriétaire concerné.
     * @return Un ensemble de médias (Media) qu'il détient.
     */
    public static Set<Media> getAllOwnedMedia(Owner owner) {
        Set<Media> result = new HashSet<>();
        Set<Appropriable> visited = new HashSet<>();
        getAllOwnedMediaRecursive(owner, result, visited);
        return result;
    }

    /**
     * Méthode récursive interne pour construire l'ensemble des médias possédés par un Owner,
     * en prenant en compte les détentions via des organisations intermédiaires.
     *
     * @param owner   Le propriétaire à analyser.
     * @param result  L’ensemble des médias trouvés.
     * @param visited Les biens déjà visités pour éviter les boucles.
     */
    private static void getAllOwnedMediaRecursive(Owner owner, Set<Media> result, Set<Appropriable> visited) {
        for (Ownership o : owner.getOwnerships()) {
            Appropriable property = o.getProperty();
            if (visited.contains(property)) continue;
            visited.add(property);

            if (property instanceof Media media) {
                result.add(media);
            } else if (property instanceof Organization org) {
                getAllOwnedMediaRecursive(org, result, visited);
            }
        }
    }

    /**
     * Vérifie si un propriétaire détient un média (directement ou via des entités intermédiaires).
     *
     * @param owner Le propriétaire.
     * @param media Le média à vérifier.
     * @return true s'il le possède, false sinon.
     */
    public static boolean ownsMedia(Owner owner, Media media) {
        return getAllOwnedMedia(owner).contains(media);
    }

    /**
     * Vérifie si un propriétaire détient directement un bien (sans passer par une organisation).
     *
     * @param owner  Le propriétaire.
     * @param target Le bien ciblé.
     * @return true si le bien est possédé directement, false sinon.
     */
    public static boolean ownsPropertyDirectly(Owner owner, Appropriable target) {
        for (Ownership o : owner.getOwnerships()) {
            if (o.getProperty().equals(target)) {
                return true;
            }
        }
        return false;
    }
}
