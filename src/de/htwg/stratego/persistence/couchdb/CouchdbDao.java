package de.htwg.stratego.persistence.couchdb;

import de.htwg.stratego.model.*;
import de.htwg.stratego.model.impl.*;
import de.htwg.stratego.persistence.IDao;
import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.ViewQuery;
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
        db.create(copyGame(game));
    }

    @Override
    public IGame readGame(int gameId) {
        ViewQuery query = new ViewQuery().allDocs().includeDocs(true);

        List<PersistenceGame> games = db.queryView(query, PersistenceGame.class);
        if (games.isEmpty()) {
            return null;
        }
        return copyGame(games.get(0));
    }

    @Override
    public void updateGame(IGame game) {
        ViewQuery query = new ViewQuery().allDocs().includeDocs(true);

        List<PersistenceGame> games = db.queryView(query, PersistenceGame.class);
        if (!games.isEmpty()) {
            db.delete(games.get(0));
        }
        createGame(game);
    }

    @Override
    public void deleteGame(IGame game) {

    }

    @Override
    public void closeDb() {

    }

    // copy to persistence

    public PersistenceGame copyGame(IGame game) {
        List<PersistencePlayer> persistencePlayers = new ArrayList<>();

        for (IPlayer player : game.getPlayer()) {
            persistencePlayers.add(copyPlayer(player));
        }

        return new PersistenceGame(game.getCurrentPlayer(), persistencePlayers, game.getGameState(),
                copyField(game.getField()));
    }

    public PersistenceField copyField(IField field) {
        PersistenceField persistenceField = new PersistenceField(field.getWidth(), field.getHeight());

        for (int y = 0; y < field.getHeight(); y++) {
            for (int x = 0; x < field.getWidth(); x++) {
                persistenceField.setCell(x, y, copyCell(field.getCell(x, y)));
            }
        }

        return persistenceField;
    }

    public PersistenceCell copyCell(ICell cell) {
        return new PersistenceCell(cell.getX(), cell.getY(), cell.isPassable(),
                copyCharacter(cell.getCharacter()));
    }

    public PersistenceCharacter copyCharacter(ICharacter character) {
        if (character != null) {
            return new PersistenceCharacter(character.getRank(),
                    character.isMoveable(), character.isVisible(), copyPlayer(character.getPlayer()));
        }
        return null;
    }

    public PersistencePlayer copyPlayer(IPlayer player) {
        List<Integer> persistenceCharacters = new ArrayList<>();

        for (ICharacter character : player.getCharacterList()) {
            persistenceCharacters.add(character.getRank());
        }

        return new PersistencePlayer(persistenceCharacters, player.getSymbol(),
                player.getName(), player.hasSetupFinished());
}

    // copy from persistence

    public IGame copyGame(PersistenceGame persistenceGame) {
        List<PersistencePlayer> persistencePlayers = persistenceGame.getPlayer();
        IPlayer[] players = new IPlayer[persistencePlayers.size()];

        for (int i = 0; i < persistencePlayers.size(); i++) {
            players[i] = copyPlayer(persistencePlayers.get(i));
        }

        PersistenceField persistenceField = persistenceGame.getField();
        IField field = new Field(persistenceField.getWidth(), persistenceField.getHeight());

        for (int y = 0; y < field.getHeight(); y++) {
            for (int x = 0; x < field.getWidth(); x++) {
                ICell cell = field.getCell(x, y);
                PersistenceCell persistenceCell = persistenceField.getCell(x, y);

                PersistenceCharacter persistenceCharacter = persistenceCell.getCharacter();
                if (persistenceCharacter != null) {
                    IPlayer player = null;
                    for (IPlayer p : players) {
                        if (p.getName().equals(persistenceCharacter.getPlayer().getName())) {
                            player = p;
                        }
                    }

                    ICharacter character = CharacterFactory.create(persistenceCharacter.getRank(), player);
                    character.setVisible(persistenceCharacter.isVisible());
                    cell.setCharacter(character);
                }

                cell.setPassable(persistenceCell.isPassable());
            }
        }

        // TODO implement ID for CouchDB
        int notImplementedID = 1;
        return new Game(notImplementedID,persistenceGame.getCurrentPlayer(), players, persistenceGame.getGameState(), field);
    }

    public IPlayer copyPlayer(PersistencePlayer persistencePlayer) {
        IPlayer player = new Player(persistencePlayer.getName(), persistencePlayer.getSymbol());

        for (int rank : persistencePlayer.getCharacterList()) {
            player.addCharacter(CharacterFactory.create(rank, player));
        }

        return player;
    }

}
