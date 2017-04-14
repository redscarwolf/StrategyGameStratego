package de.htwg.stratego.persistence.couchdb;

import de.htwg.stratego.model.IGame;
import de.htwg.stratego.persistence.IDao;
import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbInstance;

import java.net.MalformedURLException;

public class CouchdbDao implements IDao {

    private CouchDbConnector db;

    public CouchdbDao() {
        HttpClient client = null;
        try {
            client = new StdHttpClient.Builder().url(
                    "http://lenny2.in.htwg-konstanz.de:5984").build();

        } catch (MalformedURLException e) {
            System.out.println("Malformed URL: " + e);
        }
        CouchDbInstance dbInstance = new StdCouchDbInstance(client);
        db = dbInstance.createConnector("stratego", true);

        PersistanceGame game = new PersistanceGame();
        game.setName("hallo welt");
        db.create(game);
    }

    @Override
    public void createGame(IGame game) {

    }

    @Override
    public IGame readGame() {
        return null;
    }

    @Override
    public void updateGame(IGame game) {

    }

    @Override
    public void deleteGame(IGame game) {

    }

    @Override
    public void closeDb() {

    }
}
