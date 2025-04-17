package model.actors;

import model.ownership.Appropriable;

public class AppropriableOrganization extends Organization implements Appropriable {
    public AppropriableOrganization(String name, String comment) {
        super(name, comment);
    }
}
