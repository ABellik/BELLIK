package model.media;

import model.actors.Mentionable;
import model.ownership.Appropriable;

public abstract class Media implements Mentionable, Appropriable {
    private String name;
    private String scale;
    private String price;
    private boolean disappeared;

    @Override
    public String toString(){
        StringBuilder SB = new StringBuilder();
        SB.append("Média : ").append(name);
        SB.append("Echelle : ").append(scale);
        SB.append("Prix : ").append(price);
        if (disappeared) SB.append("Ce média n'existe plus");
        return SB.toString();
    }
}
