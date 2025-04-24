package model.monitoring;

import model.actors.Mentionable;
import model.module.IndividualModule;
import model.module.MediaModule;
import model.ownership.Ownership;
import model.publication.Publication;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Monitoring implements Observer {

    private final List<String> alertList = new ArrayList<>();


    public void publishAlert(String message){
        System.out.println(message);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof IndividualModule im){
            Publication p = (Publication) arg;
            String alert = "\n------------------------  \u001B[1mALERTE !\u001B[0m  ------------------------\n"+im.getIndividual().getName()+" a été mentionné par "+p.getSource().getName()+" qu'il possède !"+"\nLe titre de la publication concernée : "+p.getTitle()+"\n";
            publishAlert(alert);
            alertList.add(alert);
        }
        else{
            MediaModule mm = (MediaModule) o;
            if(arg instanceof Mentionable m){
                String alert = "\n------------------------  \u001B[1mALERTE !\u001B[0m  ------------------------\n"+m.getName()+" a été mentionné trop de fois par "+mm.getMedia().getName()+"!";
                publishAlert(alert);
                alertList.add(alert);

            }
            else{
                Ownership own = (Ownership) arg;
                String alert = "\n------------------------  \u001B[1mALERTE !\u001B[0m  ------------------------\nUne part de "+mm.getMedia().getName()+" a été racheté par "+own.getOrigin().getName()+" qui est un nouveau propriétaire !";
                publishAlert(alert);
                alertList.add(alert);
            }
        }
    }

    public List<String> getAlertList() {
        return alertList;
    }
}
