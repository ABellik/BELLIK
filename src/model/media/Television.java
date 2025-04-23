package model.media;

import exceptions.PublicationTypeException;
import model.actors.Individual;
import model.actors.Mentionable;
import model.publication.Interview;
import model.publication.Publication;
import model.publication.Report;

import java.util.Date;
import java.util.Set;

public class Television extends Media{
    public Television(String name, String scale, String price, boolean disappeared){
        super(name, scale, price, disappeared);
    }

    @Override
    public String toString() {
        return super.toString()+"\tType : Télévision\n";
    }

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
            throw new PublicationTypeException("Le type mentionné ("+type+") ne peut pas être publié par une chaîne télévisée !\nTypes autorisés:\n\t- Reportage\n\t- Interview");
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
