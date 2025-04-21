package model.ownership;

import data.repository.DataRepository;
import exceptions.IllegalPercentageException;
import model.actors.Owner;

public class OwnershipManager {
    public static void buyOutOwnership(Ownership o, Owner buyer, double percentage) {
        if (o.getPercentage() < percentage) {
            throw new IllegalPercentageException("Le pourcentage d'achat est plus élevé que celui de la part !");
        } else if (o.getPercentage() == percentage) {
            o.getOrigin().removeOwnership(o);
            o.setOrigin(buyer);
        } else {
            o.setPercentage(o.getPercentage() - percentage);
            Ownership o2 = new Ownership(1000,buyer,o.getProperty(),percentage);
            buyer.addOwnership(o2);
            o.getProperty().addShare(o2);
        }
    }
}
//Il reste à modifier les observateurs pour conserver une trace des rachats.