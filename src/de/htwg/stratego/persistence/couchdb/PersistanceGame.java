package de.htwg.stratego.persistence.couchdb;

import org.ektorp.support.CouchDbDocument;

public class PersistanceGame extends CouchDbDocument {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
