package model.actors;

import model.ownership.Appropriable;

public class Organization extends Owner implements Appropriable {
    private final String comment;

    public Organization(String name, String comment){
        super(name);
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public String toString(){
        return super.toString()+"\n\tCommentaire : "+comment+"\n";
    }
}
