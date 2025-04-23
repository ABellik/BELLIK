package model.media;

import exceptions.PublicationTypeException;
import model.actors.Individual;
import model.actors.Mentionable;
import model.publication.Article;
import model.publication.Interview;
import model.publication.Publication;

import java.util.Date;
import java.util.Set;

public class PrintMedia extends Media{
    private final String frequency;

    public PrintMedia(String name, String scale, String price, boolean disappeared, String frequency){
        super(name, scale, price, disappeared);
        this.frequency = frequency;
    }

    public String getFrequency() {
        return frequency;
    }

    @Override
    public String toString() {
        return super.toString()+"\tType : Presse écrite\n"+"\tPériodicité : "+frequency+"\n";
    }

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
