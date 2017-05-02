package de.htwg.stratego.persistence.db4o;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectSet;
import de.htwg.stratego.controller.state.GameState;
import de.htwg.stratego.model.*;
import de.htwg.stratego.model.impl.CharacterFactory;
import de.htwg.stratego.model.impl.Field;
import de.htwg.stratego.model.impl.Game;
import de.htwg.stratego.model.impl.Player;
import de.htwg.stratego.persistence.IDao;

import com.db4o.ObjectContainer;
import de.htwg.stratego.persistence.couchdb.*;
import de.htwg.stratego.persistence.couchdb.PersistenceCell;
import de.htwg.stratego.persistence.couchdb.PersistenceCharacter;
import de.htwg.stratego.persistence.couchdb.PersistenceField;
import de.htwg.stratego.persistence.couchdb.PersistenceGame;
import de.htwg.stratego.persistence.couchdb.PersistencePlayer;

import java.util.ArrayList;
import java.util.List;

public class Db4oDao implements IDao{

    public static final String DATABASE_FILE_NAME = "StrategoDb4o";
    private ObjectContainer db;

    public Db4oDao() {
        this.db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(),
                DATABASE_FILE_NAME);
    }

    @Override
    public void createGame(IGame game) {
        db.store(copyGame(game));
    }

    @Override
    public IGame readGame(int gameId) {
        ObjectSet<PersistenceGame> query = db.query(PersistenceGame.class);
        if (query.isEmpty()) {
            return null;
        }
        return copyGame(query.next());
    }

    @Override
    public void updateGame(IGame game) {
        ObjectSet<PersistenceGame> query = db.query(PersistenceGame.class);
        if (!query.isEmpty()) {
            db.delete(query.next());
        }
        createGame(game);
    }

    @Override
    public void deleteGame(IGame game) {
    }

    @Override
    public void closeDb() {
        db.close();
    }

    // copy to persistence

    public de.htwg.stratego.persistence.couchdb.PersistenceGame copyGame(IGame game) {
        List<de.htwg.stratego.persistence.couchdb.PersistencePlayer> persistencePlayers = new ArrayList<>();

        for (IPlayer player : game.getPlayer()) {
            persistencePlayers.add(copyPlayer(player));
        }

        return new de.htwg.stratego.persistence.couchdb.PersistenceGame(game.getCurrentPlayer(), persistencePlayers, game.getGameState(),
                copyField(game.getField()));
    }

    public de.htwg.stratego.persistence.couchdb.PersistenceField copyField(IField field) {
        de.htwg.stratego.persistence.couchdb.PersistenceField persistenceField = new de.htwg.stratego.persistence.couchdb.PersistenceField(field.getWidth(), field.getHeight());

        for (int y = 0; y < field.getHeight(); y++) {
            for (int x = 0; x < field.getWidth(); x++) {
                persistenceField.setCell(x, y, copyCell(field.getCell(x, y)));
            }
        }

        return persistenceField;
    }

    public de.htwg.stratego.persistence.couchdb.PersistenceCell copyCell(ICell cell) {
        return new de.htwg.stratego.persistence.couchdb.PersistenceCell(cell.getX(), cell.getY(), cell.isPassable(),
                copyCharacter(cell.getCharacter()));
    }

    public de.htwg.stratego.persistence.couchdb.PersistenceCharacter copyCharacter(ICharacter character) {
        if (character != null) {
            return new de.htwg.stratego.persistence.couchdb.PersistenceCharacter(character.getRank(),
                    character.isMoveable(), character.isVisible(), copyPlayer(character.getPlayer()));
        }
        return null;
    }

    public de.htwg.stratego.persistence.couchdb.PersistencePlayer copyPlayer(IPlayer player) {
        List<Integer> persistenceCharacters = new ArrayList<>();

        for (ICharacter character : player.getCharacterList()) {
            persistenceCharacters.add(character.getRank());
        }

        return new de.htwg.stratego.persistence.couchdb.PersistencePlayer(persistenceCharacters, player.getSymbol(),
                player.getName(), player.hasSetupFinished());
    }

    // copy from persistence

    public IGame copyGame(de.htwg.stratego.persistence.couchdb.PersistenceGame persistenceGame) {
        List<de.htwg.stratego.persistence.couchdb.PersistencePlayer> persistencePlayers = persistenceGame.getPlayer();
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
