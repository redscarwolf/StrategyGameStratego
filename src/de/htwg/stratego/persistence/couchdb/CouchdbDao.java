package de.htwg.stratego.persistence.couchdb;

import de.htwg.stratego.model.*;
import de.htwg.stratego.model.impl.Rank;
import de.htwg.stratego.persistence.IDao;
import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbInstance;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class CouchdbDao implements IDao {

    private CouchDbConnector db;

    public CouchdbDao() {
        HttpClient client = null;
        try {
            client = new StdHttpClient.Builder().url("http://lenny2.in.htwg-konstanz.de:5984").build();
        } catch (MalformedURLException e) {
            System.out.println("Malformed URL: " + e);
        }
        CouchDbInstance dbInstance = new StdCouchDbInstance(client);
        db = dbInstance.createConnector("stratego", true);
    }

    @Override
    public void createGame(IGame game) {
        db.create(copyGameToPersistence(game));
    }

    @Override
    public IGame readGame() {
        return null;
    }

    @Override
    public void updateGame(IGame game) {
        createGame(game);
    }

    @Override
    public void deleteGame(IGame game) {

    }

    @Override
    public void closeDb() {

    }

    // copy to persistence

    public PersistenceGame copyGameToPersistence(IGame game) {
        List<PersistencePlayer> persistencePlayers = new ArrayList<>();

        for (IPlayer player : game.getPlayer()) {
            persistencePlayers.add(copyPlayerToPersistence(player));
        }

        return new PersistenceGame(game.getCurrentPlayer(), persistencePlayers, game.getGameState(),
                copyFieldToPersistence(game.getField()));
    }

    public PersistenceField copyFieldToPersistence(IField field) {
        PersistenceField persistenceField = new PersistenceField(field.getWidth(), field.getHeight());

        for (int y = 0; y < field.getHeight(); y++) {
            for (int x = 0; x < field.getWidth(); x++) {
                persistenceField.setPersistanceCell(x, y, copyCellToPersistence(field.getCell(x, y)));
            }
        }

        return persistenceField;
    }

    public PersistenceCell copyCellToPersistence(ICell cell) {
        return new PersistenceCell(cell.getX(), cell.getY(), cell.isPassable(),
                copyCharacterToPersistence(cell.getCharacter()));
    }

    public PersistenceCharacter copyCharacterToPersistence(ICharacter character) {
        if (character != null) {
            return new PersistenceCharacter(character.getRank(),
                    character.isMoveable(), character.isVisible(), copyPlayerToPersistence(character.getPlayer()));
        }
        return null;
    }

    public PersistencePlayer copyPlayerToPersistence(IPlayer player) {
        List<Integer> persistenceCharacters = new ArrayList<>();

        for (ICharacter character : player.getCharacterList()) {
            persistenceCharacters.add(character.getRank());
        }

        return new PersistencePlayer(persistenceCharacters, player.getSymbol(),
                player.getName(), player.hasSetupFinished());
}

    // copy from persistence

    public IGame copyPersistanceToGame(PersistenceGame persistenceGame) {
        return null;
    }

}
