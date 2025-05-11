package model.media;

import exceptions.PublicationTypeException;
import model.actors.Individual;
import model.actors.Mentionable;
import model.publication.Article;
import model.publication.Interview;
import model.publication.Publication;

import java.util.Date;
import java.util.Set;

/**
 * Représente une presse écrite, tel qu'un journal ou un magazine.
 * <p>
 * Ce type de média peut publier uniquement des {@code Article} ou des {@code Interview}, avec une certaine périodicité.
 * Il hérite de la classe abstraite {@code Media} et ajoute un attribut spécifique : la fréquence de parution.
 * </p>
 *
 * <p>Lors de la publication, il notifie les observateurs associés à tout {@code Individual} mentionnée.</p>
 *
 * @author [Adam BELLIK]
 */
public class PrintMedia extends Media{
    /** La fréquence de publication (quotidien, hebdomadaire, mensuel, etc.). */
    private final String frequency;

    /**
     * Construit une nouvelle presse écrite.
     *
     * @param name         Le nom du média
     * @param scale        L'échelle du média (locale, nationale, etc.)
     * @param price        Le prix associé au média
     * @param disappeared  Indique si le média a disparu
     * @param frequency    La fréquence de publication du média
     */
    public PrintMedia(String name, String scale, String price, boolean disappeared, String frequency){
        super(name, scale, price, disappeared);
        this.frequency = frequency;
    }

    /**
     * Retourne une représentation textuelle de la presse écrite,
     * incluant les informations de base et la périodicité.
     *
     * @return Une chaîne représentant le média
     */
    @Override
    public String toString() {
        return super.toString()+"\tType : Presse écrite\n"+"\tPériodicité : "+frequency+"\n";
    }

    /**
     * Publie un contenu dans le média, si le type est autorisé.
     * <p>
     * Seuls les types suivants sont autorisés : {@code Article} et {@code Interview}.
     * En cas de type invalide, une {@code PublicationTypeException} est levée.
     * </p>
     *
     * @param title    Le titre de la publication
     * @param mentions Les entités mentionnées dans la publication
     * @param type     Le type de la publication ("Article" ou "Interview")
     * @throws PublicationTypeException si le type de publication n'est pas compatible avec une presse écrite
     */
    @Override
    public void publish(String title, Set<Mentionable> mentions, String type) throws PublicationTypeException {
        Publication pub;
        if(type.equals("Article")){
            pub = new Article(new Date(),title,this,mentions);
        }
        else if(type.equals("Interview")){
            pub = new Interview(new Date(),title,this,mentions);
        }
        else{
            throw new PublicationTypeException("Le type mentionné ("+type+") ne peut pas être publié par une presse écrite !\nTypes autorisés:\n\t- Article\n\t- Interview");
        }

        for(Mentionable m : mentions) {
            if(m instanceof Individual){
                this.addObserver(((Individual) m).getMod());
            }
        }

        this.getPublications().add(pub);
        setChanged();
        notifyObservers(pub);

        for(Mentionable m : mentions) {
            if(m instanceof Individual){
                this.deleteObserver(((Individual) m).getMod());
            }
        }
    }


}
