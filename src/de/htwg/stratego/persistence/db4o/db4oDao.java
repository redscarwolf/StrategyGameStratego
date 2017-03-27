package de.htwg.stratego.persistence.db4o;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectSet;
import de.htwg.stratego.controller.state.GameState;
import de.htwg.stratego.model.IGame;
import de.htwg.stratego.model.impl.Game;
import de.htwg.stratego.model.impl.Player;
import de.htwg.stratego.persistence.IDao;

import com.db4o.ObjectContainer;

import java.util.List;

public class db4oDao implements IDao{

    public static final String DATABASE_FILE_NAME = "gameDB";
    private ObjectContainer db;

    public db4oDao() {
        this.db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(),
                DATABASE_FILE_NAME);
    }

    @Override
    public void createGame(IGame game) {
        db.store(game);
    }

    @Override
    public IGame readGame() {
        ObjectSet<IGame> query = db.query(IGame.class);
        if (query.isEmpty()) {
            return null;
        }
        return query.next();
    }

    @Override
    public void updateGame(IGame game) {
        ObjectSet<IGame> iGameSet = db.queryByExample(game);
        System.out.println(iGameSet);
        if (!iGameSet.isEmpty()) {
            deleteGame(iGameSet.next());
        }
        createGame(game);
    }

    @Override
    public void deleteGame(IGame game) {
        db.delete(game);
    }

    @Override
    public void closeDb() {
        db.close();
    }
}
