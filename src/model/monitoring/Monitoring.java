package model.monitoring;

import model.actors.Mentionable;
import model.actors.Owner;
import model.module.IndividualModule;
import model.module.MediaModule;
import model.ownership.Ownership;
import model.publication.Publication;

import java.util.Observable;
import java.util.Observer;

public class Monitoring implements Observer {


    public void publishAlert(String message){
        System.out.println(message);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof IndividualModule im){
            Publication p = (Publication) arg;

            publishAlert("\n------------------------  \u001B[1mALERTE !\u001B[0m  ------------------------\n"+im.getIndividual().getName()+" a été mentionné par "+p.getSource().getName()+" qu'il possède !"+"\nLe titre de la publication concernée : "+p.getTitle()+"\n");
        }
        else{
            MediaModule mm = (MediaModule) o;
            if(arg instanceof Mentionable m){
                publishAlert("\n------------------------  \u001B[1mALERTE !\u001B[0m  ------------------------\n"+m.getName()+" a été mentionné trop de fois par "+mm.getMedia().getName()+"!");
            }
            else{
                Ownership own = (Ownership) arg;
                publishAlert("\n------------------------  \u001B[1mALERTE !\u001B[0m  ------------------------\nUne part de "+mm.getMedia().getName()+" a été racheté par "+own.getOrigin().getName()+" qui est un nouveau propriétaire !");
            }
        }
    }
}
