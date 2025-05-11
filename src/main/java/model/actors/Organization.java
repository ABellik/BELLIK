package model.actors;

import model.ownership.Appropriable;
import model.ownership.Ownership;

import java.util.ArrayList;
import java.util.List;

public class Organization extends Owner implements Appropriable {
    /** Le commentaire sur l'organisation*/
    private final String comment;

    /** La liste des parts (ownerships) partageant l'organisation. */
    private List<Ownership> shares = new ArrayList<>();

    /**
     * Construit une organisation.
     *
     * @param name Le nom du propriétaire
     * @param comment le commentaire sur l'organisation
     */
    public Organization(String name, String comment){
        super(name);
        this.comment = comment;
    }

    /**
     * Retourne le commentaire.
     *
     * @return Le commentaire
     */
    public String getComment() {
        return comment;
    }

    /**
     * Retourne La liste des parts (ownerships) partageant l'organisation.
     *
     * @return La liste des parts (ownerships) partageant l'organisation.
     */
    @Override
    public List<Ownership> getShares(){
        return shares;
    }

    /**
     * Ajoute une part (Ownership) à la liste de celles partageant l'organisation.
     *
     * @param o La part à ajouter
     */
    @Override
    public void addShare(Ownership o){
        shares.add(o);
    }

    /**
     * Supprime une part (Ownership) dans la liste de celles partageant l'organisation.
     *
     * @param o La part à supprimer
     */
    @Override
    public void removeShare(Ownership o){
        shares.remove(o);
    }

    /**
     * Retourne une représentation textuelle de l'organisation.
     *
     * @return Une chaîne contenant le nom de l'organisation
     */
    @Override
    public String toString(){
        return super.toString()+"\n\tCommentaire : "+getComment()+"\n";
    }
}
