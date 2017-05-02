package de.htwg.stratego.persistence.hibernate;

import de.htwg.stratego.model.*;
import de.htwg.stratego.model.impl.Field;
import de.htwg.stratego.model.impl.Game;
import de.htwg.stratego.model.impl.Player;
import de.htwg.stratego.model.impl.Rank;
import de.htwg.stratego.model.impl.CharacterFactory;
import de.htwg.stratego.persistence.IDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class HibernateDaoTest {

    public static final int GAME_ID = 999;
    private IDao dao;
    private HibernateDao daoImpl;

    private IGame game;
    private IPlayer playerOne;
    private IPlayer playerTwo;

    @Before
    public void setUp() throws Exception {
        this.dao = new HibernateDao();
        this.daoImpl = new HibernateDao();
        this.game = setUpGame(GAME_ID);
    }

    private IGame setUpGame(int gameId) {
        IField field = new Field(3, 2);
        playerOne = new Player("PlayerOne", "#");
        playerTwo = new Player("PlayerTwo", "!");
        IPlayer players[] = {playerOne, playerTwo};
        ICharacter character1 = CharacterFactory.create(Rank.SERGEANT, playerOne);
        ICharacter character2 = CharacterFactory.create(Rank.SERGEANT, playerTwo);
        ICharacter character3 = CharacterFactory.create(Rank.FLAG, playerTwo);
        ICharacter characterNotJetSet = CharacterFactory.create(Rank.FLAG, playerTwo);

        playerOne.addCharacter(characterNotJetSet);

        field.getCell(0, 0).setCharacter(character1);
        field.getCell(1, 1).setCharacter(character2);
        field.getCell(0, 1).setCharacter(character3);

        int currentPlayer = 0;

        return new Game(gameId, currentPlayer, players, EGameState.PLAYER_START, field);
    }

    @After
    public void afterEachTest() {
        List<IGame> allGames = daoImpl.getAllGames();
        for (IGame g :
                allGames) {
            dao.deleteGame(g);
            checkIfGameIsDeletedFromDB(g);
        }
    }

    private void checkIfGameIsDeletedFromDB(IGame game) {
        SessionFactory sessionFactory;
        sessionFactory = new Configuration()
                .configure() // configures settings from hibernate.cfg.xml
                .buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        TransferGame transferGameFromDB = session.get(TransferGame.class, game.getId());
        assertNull(transferGameFromDB);
        tx.commit();
        session.close();
    }

    // Tests -----------------------------------------------------------------------------------------------------

    @Test
    public void createGame_And_ReadGame() throws Exception {
        dao.createGame(game);
        assertEqual_Game_With_GameFromDB(game);
    }

    private void assertEqual_Game_With_GameFromDB(IGame game) {
        IGame gameFromDB = dao.readGame(game.getId());
        assertEquals(game.getId(), gameFromDB.getId());
        assertEquals(game.getCurrentPlayer(), gameFromDB.getCurrentPlayer());

        IPlayer[] player = game.getPlayer();
        IPlayer[] gameFromDBPlayer = gameFromDB.getPlayer();
        assertEquals(player.length, gameFromDBPlayer.length);

        for (int i = 0; i < player.length; i++) {
            assertEquals(player[i].toString(), gameFromDBPlayer[i].toString());
        }

        assertEquals(game.getGameState(), gameFromDB.getGameState());
        assertEquals(game.getField().toString(), gameFromDB.getField().toString());
    }

    @Test
    public void updateGame_gameAlreadyInDB_isUpdated() throws Exception {
        dao.createGame(game);
        int width = 2;
        int height = 1;
        game.getField().getCell(width, height).setCharacter(CharacterFactory.create(Rank.BOMB, playerOne));
        dao.updateGame(game);
        assertEqual_Game_With_GameFromDB(game);
    }

    @Test
    public void updateGame_NoGameInDB_isCreated() throws Exception {
        dao.updateGame(game);
        assertEqual_Game_With_GameFromDB(game);
    }

    @Test
    public void getAllGames() throws Exception {
        int gameOne_ID = GAME_ID;
        int gameTwo_ID = 1000;
        int gameThree_ID = 1001;

        // createGame
        IGame gameOne = setUpGame(gameOne_ID);
        IGame gameTwo = setUpGame(gameTwo_ID);
        IGame gameThree = setUpGame(gameThree_ID);
        dao.createGame(gameOne);
        dao.createGame(gameTwo);
        dao.createGame(gameThree);

        // getAllGames
        List<IGame> allGames = daoImpl.getAllGames();
        for (IGame g :
                allGames) {
            assertEqual_Game_With_GameFromDB(g);
        }
    }
}