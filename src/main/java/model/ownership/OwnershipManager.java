package model.ownership;

import data.repository.DataRepository;
import exceptions.IllegalPercentageException;
import model.actors.Organization;
import model.actors.Owner;
import model.media.Media;

import java.util.HashSet;
import java.util.Set;

public class OwnershipManager {
    public static void buyOutOwnership(Ownership o, Owner buyer, double percentage) throws IllegalPercentageException {
        if(o.getPercentage() == 0){
            throw new IllegalArgumentException(o.getOrigin().getName()+" ne peux pas vendre cette part car il la contrôle !");
        }
        else if (o.getPercentage() < percentage) {
            throw new IllegalPercentageException("Le pourcentage d'achat est plus élevé que celui de la part !");
        }
        else if (o.getPercentage() == percentage) {
            System.out.println("Je suis là 3");
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
            System.out.println("Je suis là");
            o.setPercentage(o.getPercentage() - percentage);
            if(OwnershipManager.ownsPropertyDirectly(buyer,o.getProperty())){
                System.out.println("Je suis là 2");
                System.out.println(buyer.getOwnerships()+"\n\n\n"+o.getProperty().getShares());

                Ownership ownership = DataRepository.searchOwnership(buyer,o.getProperty());
                ownership.setPercentage(ownership.getPercentage()+percentage);
            }
            else {
                Ownership o2 = new Ownership(1000, buyer, o.getProperty(), percentage);
                buyer.addOwnership(o2);
                o.getProperty().addShare(o2);
                if (o.getProperty() instanceof Media property) {
                    property.notification(o2);
                }
            }

        }
    }

    public static Set<Media> getAllOwnedMedia(Owner owner) {
        Set<Media> result = new HashSet<>();
        Set<Appropriable> visited = new HashSet<>();
        getAllOwnedMediaRecursive(owner, result, visited);
        return result;
    }

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

    public static boolean ownsMedia(Owner owner, Media media) {
        return getAllOwnedMedia(owner).contains(media);
    }

    public static boolean ownsPropertyDirectly(Owner owner, Appropriable target) {
        for (Ownership o : owner.getOwnerships()) {
            if (o.getProperty().equals(target)) {
                return true;
            }
        }
        return false;
    }
}
