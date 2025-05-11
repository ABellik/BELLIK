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

/**
 * Classe utilitaire centralisant l'accès et la recherche des entités du modèle :
 * {@link Individual}, {@link Organization}, {@link Media}, {@link Ownership}, etc.
 * <p>
 * Elle agit comme un dépôt en mémoire, peuplé à partir de chargeurs de données spécialisés.
 * Toutes les données sont stockées sous forme de listes statiques accessibles globalement.
 * </p>
 * @author [Adam BELLIK]
 */
public abstract class DataRepository {
    private static final List<Individual> individuals = new ArrayList<>();
    private static final List<Organization> organizations = new ArrayList<>();
    private static final List<Media> medias = new ArrayList<>();
    private static final List<Ownership> ownerships = new ArrayList<>();

    /**
     * Initialise le dépôt en important les données depuis les fichiers sources.
     * <p>
     * Les données sont lues via les classes :
     * {@link IndividualDataLoader}, {@link OrganizationDataLoader}, {@link MediaDataLoader}, {@link OwnershipDataLoader}.
     * </p>
     */
    public static void initialize(){
        individuals.addAll(new IndividualDataLoader().load());
        organizations.addAll(new OrganizationDataLoader().load());
        medias.addAll(new MediaDataLoader().load());
        ownerships.addAll(new OwnershipDataLoader().load());
    }

    /**
     * @return la liste des individus connus.
     */
    public static List<Individual> getIndividuals() {
        return individuals;
    }

    /**
     * @return la liste des organisations connues.
     */
    public static List<Organization> getOrganizations() {
        return organizations;
    }

    /**
     * @return la liste des médias connus.
     */
    public static List<Media> getMedias() {
        return medias;
    }

    /**
     * @return la liste des entités mentionnables (individus, organisations, médias).
     */
    public static List<Mentionable> getMentionables() {
        List<Mentionable> mentionables = new ArrayList<>();
        mentionables.addAll(getIndividuals());
        mentionables.addAll(getOrganizations());
        mentionables.addAll(getMedias());
        return mentionables;
    }

    /**
     * @return la liste des entités propriétaires (individus et organisations).
     */
    public static List<Owner> getOwners() {
        List<Owner> owners = new ArrayList<>();
        owners.addAll(getIndividuals());
        owners.addAll(getOrganizations());
        return owners;
    }

    /**
     * @return la liste des possessions.
     */
    public static List<Ownership> getOwnerships(){
        return ownerships;
    }

    /**
     * Recherche un individu par son nom.
     *
     * @param name nom de l'individu
     * @return l'individu correspondant
     * @throws NoSuchElementException si aucun individu ne correspond
     */
    public static Individual searchIndividual(String name) throws NoSuchElementException {
        for(Individual i : individuals){
            if(i.getName().equals(name)) return i;
        }
        throw new NoSuchElementException("L'individu \""+name+"\" est introuvable");
    }

    /**
     * Recherche une organisation par son nom.
     *
     * @param name nom de l'organisation
     * @return l'organisation correspondante
     * @throws NoSuchElementException si aucune organisation ne correspond
     */
    public static Organization searchOrganization(String name) throws NoSuchElementException {
        for(Organization o : organizations){
            if(o.getName().equals(name)) return o;
        }
        throw new NoSuchElementException("L'organisation \""+name+"\" est introuvable");
    }

    /**
     * Recherche un média par son nom.
     *
     * @param name nom du média
     * @return le média correspondant
     * @throws NoSuchElementException si aucun média ne correspond
     */
    public static Media searchMedia(String name) throws NoSuchElementException {
        for(Media m : medias){
            if(m.getName().equals(name)) return m;
        }
        throw new NoSuchElementException("Le média \""+name+"\" est introuvable");
    }

    /**
     * Recherche un propriétaire par son nom (individu ou organisation).
     *
     * @param name nom du propriétaire
     * @return l'entité propriétaire
     * @throws NoSuchElementException si aucun propriétaire ne correspond
     */
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

    /**
     * Recherche une entité possédable par son nom (organisation ou média).
     *
     * @param name nom du possédable
     * @return l'entité {@link Appropriable} correspondante
     * @throws NoSuchElementException si aucun possédable ne correspond
     */
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

    /**
     * Recherche une entité mentionnable par son nom (organisation, média, individu).
     *
     * @param name nom de l'entité
     * @return l'entité {@link Mentionable} correspondante
     * @throws NoSuchElementException si aucune entité ne correspond
     */
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

    /**
     * Recherche une relation de propriété à partir des noms d'un propriétaire et d'un bien.
     *
     * @param ownerName    nom du propriétaire
     * @param propertyName nom de l'entité possédée
     * @return l'objet {@link Ownership} correspondant
     * @throws NoSuchElementException si aucun lien de propriété ne correspond
     */
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

    /**
     * Recherche une relation de propriété à partir des objets propriétaires et biens.
     *
     * @param owner    propriétaire
     * @param property entité possédée
     * @return l'objet {@link Ownership} correspondant
     * @throws NoSuchElementException si aucun lien de propriété ne correspond
     */
    public static Ownership searchOwnership(Owner owner, Appropriable property) throws NoSuchElementException{
        for(Ownership o : ownerships){
            if(o.getOrigin().equals(owner) && o.getProperty().equals(property)){
                return o;
            }
        }
        throw new NoSuchElementException("La part demandée est introuvable. Il est fort probable que "+owner.getName()+" ne possède pas :\n "+property.toString());
    }
}
