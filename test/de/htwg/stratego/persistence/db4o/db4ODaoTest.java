package de.htwg.stratego.persistence.db4o;

import de.htwg.stratego.model.IGame;
import de.htwg.stratego.model.impl.Game;
import org.junit.BeforeClass;

import junit.framework.TestCase;

public class db4ODaoTest extends TestCase{
    private Db4oDao dao;
    private IGame game;

    @BeforeClass
    public void setUp() throws Exception {
        super.setUp();
        this.dao = new Db4oDao();
        this.game = new Game(1,null,null,null);
    }

    public void testCreateGame() throws Exception {
        // TODO
        shutDownDbAndDelete();
    }

    public void testUpdateGame_startWithEmptyDatabase_savesPassedGame() throws Exception {
        dao.updateGame(game);
        IGame iGame = dao.readGame();
        assertEquals(game.getCurrentPlayer(),iGame.getCurrentPlayer());
        shutDownDbAndDelete();
    }

    public void testUpdateGame_startWithFilledDatabase_deleteOldAndSavePassedGame() throws Exception {
        dao.createGame(new Game(2, null, null, null));
        dao.updateGame(game);
        IGame iGame = dao.readGame();
        assertEquals(game.getCurrentPlayer(),iGame.getCurrentPlayer());
        shutDownDbAndDelete();
    }

    public void testDeleteGame_haveOneGameInDatabase_deleteOldGame() throws Exception {
        dao.createGame(game);
        IGame iGame = dao.readGame();
        dao.deleteGame(iGame);
        shutDown();
    }

    private void shutDownDbAndDelete() {
        dao.deleteGame(game);
        shutDown();
    }
    private void shutDown() {
        dao.closeDb();
    }
}