package model.media;

import exceptions.PublicationTypeException;
import model.actors.Individual;
import model.actors.Mentionable;
import model.publication.Interview;
import model.publication.Publication;
import model.publication.Report;

import java.util.Date;
import java.util.Set;

/**
 * Représente une radio.
 * <p>
 * Ce type de média peut publier uniquement des {@code Reportage} ou des {@code Interview}.
 * Il hérite de la classe abstraite {@code Media}.
 * </p>
 *
 * <p>Lors de la publication, il notifie les observateurs associés à tout {@code Individual} mentionnée.</p>
 *
 * @author [Adam BELLIK]
 */
public class Radio extends Media{
    /**
     * Construit une nouvelle radio.
     *
     * @param name         Le nom du média
     * @param scale        L'échelle du média (locale, nationale, etc.)
     * @param price        Le prix associé au média
     * @param disappeared  Indique si le média a disparu
     */
    public Radio(String name, String scale, String price, boolean disappeared){
        super(name, scale, price, disappeared);
    }

    /**
     * Retourne une représentation textuelle de la radio,
     * incluant les informations de base.
     *
     * @return Une chaîne représentant le média
     */
    @Override
    public String toString() {
        return super.toString()+"\tType : Radio\n";
    }

    /**
     * Publie un contenu dans le média, si le type est autorisé.
     * <p>
     * Seuls les types suivants sont autorisés : {@code Reportage} et {@code Interview}.
     * En cas de type invalide, une {@code PublicationTypeException} est levée.
     * </p>
     *
     * @param title    Le titre de la publication
     * @param mentions Les entités mentionnées dans la publication
     * @param type     Le type de la publication ("Reportage" ou "Interview")
     * @throws PublicationTypeException si le type de publication n'est pas compatible avec une presse écrite
     */
    @Override
    public void publish(String title, Set<Mentionable> mentions, String type) throws PublicationTypeException {
        Publication pub;
        if(type.equals("Reportage")){
            pub = new Report(new Date(),title,this,mentions);
        }
        else if(type.equals("Interview")){
            pub = new Interview(new Date(),title,this,mentions);
        }
        else{
            throw new PublicationTypeException("Le type mentionné ("+type+") ne peut pas être publié par une radio !\nTypes autorisés:\n\t- Reportage\n\t- Interview");
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
