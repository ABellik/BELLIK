package model.actors;

import model.ownership.Appropriable;
import model.ownership.Ownership;

import java.util.ArrayList;
import java.util.List;

public class Organization extends Owner implements Appropriable {
    private final String comment;
    private List<Ownership> shares = new ArrayList<>();

    public Organization(String name, String comment){
        super(name);
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public List<Ownership> getShares(){
        return shares;
    }

    @Override
    public void addShare(Ownership o){
        shares.add(o);
    }

    @Override
    public void removeShare(Ownership o){
        shares.remove(o);
    }

    @Override
    public String toString(){
        return super.toString()+"\n\tCommentaire : "+comment+"\n";
    }
}
