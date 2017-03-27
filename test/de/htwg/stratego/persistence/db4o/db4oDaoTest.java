package de.htwg.stratego.persistence.db4o;

import de.htwg.stratego.model.IGame;
import de.htwg.stratego.model.impl.Game;
import de.htwg.stratego.persistence.IDao;
import org.junit.BeforeClass;
import org.junit.Test;

import junit.framework.TestCase;

public class db4oDaoTest extends TestCase{
    private db4oDao dao;
    private IGame game;

    @BeforeClass
    public void setUp() throws Exception {
        super.setUp();
        this.dao = new db4oDao();
        this.game = new Game(0,null,null,null);
    }

    public void testCreateGame() throws Exception {
        // TODO
        shutDownDb();
    }

    public void testReadGame() throws Exception {
        IGame iGame = dao.readGame();
        shutDownDb();
        assertEquals(game.getCurrentPlayer(), iGame.getCurrentPlayer());
    }

    public void testUpdateGame() throws Exception {
        dao.updateGame(game);
        shutDownDb();
        // TODO
    }

    public void testDeleteGame() throws Exception {
        IGame iGame = dao.readGame();
        if (iGame != null) {
            dao.deleteGame(iGame);
        }
        shutDownDb();
        // TODO
    }

    public void testCloseDb() throws Exception {
        // TODO
    }

    private void shutDownDb() {
        dao.closeDb();
    }
}