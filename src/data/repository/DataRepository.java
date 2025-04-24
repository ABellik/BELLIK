package data.repository;

import data.loader.IndividualDataLoader;
import data.loader.MediaDataLoader;
import data.loader.OrganizationDataLoader;
import data.loader.OwnershipDataLoader;
import model.actors.Individual;
import model.actors.Mentionable;
import model.actors.Organization;
import model.actors.Owner;
import model.media.Media;
import model.ownership.Appropriable;
import model.ownership.Ownership;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public abstract class DataRepository {
    private static final List<Individual> individuals = new ArrayList<>();
    private static final List<Organization> organizations = new ArrayList<>();
    private static final List<Media> medias = new ArrayList<>();
    private static final List<Ownership> ownerships = new ArrayList<>();

    public static void initialize(){
        individuals.addAll(new IndividualDataLoader().load());
        organizations.addAll(new OrganizationDataLoader().load());
        medias.addAll(new MediaDataLoader().load());
        ownerships.addAll(new OwnershipDataLoader().load());
    }

    public static List<Individual> getIndividuals() {
        return individuals;
    }

    public static List<Organization> getOrganizations() {
        return organizations;
    }

    public static List<Media> getMedias() {
        return medias;
    }

    public static List<Ownership> getOwnerships(){
        return ownerships;
    }

    public static Individual searchIndividual(String name) throws NoSuchElementException {
        for(Individual i : individuals){
            if(i.getName().equals(name)) return i;
        }
        throw new NoSuchElementException("L'individu \""+name+"\" est introuvable");
    }

    public static Organization searchOrganization(String name) throws NoSuchElementException {
        for(Organization o : organizations){
            if(o.getName().equals(name)) return o;
        }
        throw new NoSuchElementException("L'organisation \""+name+"\" est introuvable");
    }

    public static Media searchMedia(String name) throws NoSuchElementException {
        for(Media m : medias){
            if(m.getName().equals(name)) return m;
        }
        throw new NoSuchElementException("Le média \""+name+"\" est introuvable");
    }

    public static Owner searchOwner(String name) throws NoSuchElementException {
        Owner o;
        try {
            o = searchIndividual(name);
            return o;
        }
        catch(NoSuchElementException e){
            try {
                o = searchOrganization(name);
                return o;
            }
            catch(NoSuchElementException e2){
                throw new NoSuchElementException("Le propriétaire \""+name+"\" est introuvable");
            }
        }
    }

    public static Appropriable searchAppropriable(String name) throws NoSuchElementException {
        Appropriable a;
        try {
            a = searchOrganization(name);
            return a;
        }
        catch(NoSuchElementException e){
            try {
                a = searchMedia(name);
                return a;
            }
            catch(NoSuchElementException e2){
                throw new NoSuchElementException("Le possédable \""+name+"\" est introuvable");
            }
        }
    }

    public static Mentionable searchMentionable(String name) throws NoSuchElementException{
        Mentionable m;
        try {
            m = searchOrganization(name);
            return m;
        }
        catch(NoSuchElementException e){
            try {
                m = searchMedia(name);
                return m;
            }
            catch(NoSuchElementException e2){
                try{
                    m = searchIndividual(name);
                    return m;
                }
                catch(NoSuchElementException e3){
                    throw new NoSuchElementException("Le possédable \""+name+"\" est introuvable");
                }
            }
        }
    }

    public static Ownership searchOwnership(String ownerName, String propertyName) throws NoSuchElementException{
        Owner own = DataRepository.searchOwner(ownerName);
        Appropriable property = DataRepository.searchAppropriable(propertyName);
        for(Ownership o : ownerships){
            if(o.getOrigin().equals(own) && o.getProperty().equals(property)){
                return o;
            }
        }
        throw new NoSuchElementException("La part demandée est introuvable. Il est fort probable que "+ownerName+" ne possède pas\n "+propertyName);
    }

    public static Ownership searchOwnership(Owner owner, Appropriable property) throws NoSuchElementException{
        for(Ownership o : ownerships){
            if(o.getOrigin().equals(owner) && o.getProperty().equals(property)){
                return o;
            }
        }
        throw new NoSuchElementException("La part demandée est introuvable. Il est fort probable que "+owner.getName()+" ne possède pas :\n "+property.toString());
    }
}
