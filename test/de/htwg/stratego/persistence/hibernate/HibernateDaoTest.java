package de.htwg.stratego.persistence.hibernate;

import de.htwg.stratego.model.ICharacter;
import de.htwg.stratego.model.IField;
import de.htwg.stratego.model.IGame;
import de.htwg.stratego.model.IPlayer;
import de.htwg.stratego.model.impl.Field;
import de.htwg.stratego.model.impl.Game;
import de.htwg.stratego.model.impl.Player;
import de.htwg.stratego.model.impl.character.Bomb;
import de.htwg.stratego.model.impl.character.Flag;
import de.htwg.stratego.model.impl.character.Sergeant;
import de.htwg.stratego.persistence.IDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
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
        this.game = setUpGame();
    }

    private IGame setUpGame() {
        IField field = new Field(3, 2);
        playerOne = new Player("PlayerOne", "#");
        playerTwo = new Player("PlayerTwo", "!");
        IPlayer players[] = {playerOne, playerTwo};
        ICharacter character1 = new Sergeant(playerOne);
        ICharacter character2 = new Sergeant(playerTwo);
        ICharacter character3 = new Flag(playerTwo);
        ICharacter characterNotJetSet = new Flag(playerTwo);

        playerOne.addCharacter(characterNotJetSet);

        field.getCell(0, 0).setCharacter(character1);
        field.getCell(1, 1).setCharacter(character2);
        field.getCell(0, 1).setCharacter(character3);

        int currentPlayer = 0;
        return new Game(GAME_ID, currentPlayer, players, null, field);
    }

    @After
    public void afterEachTest() {
        dao.deleteGame(game);
        checkIfGameIsDeletedFromDB();
    }

    private void checkIfGameIsDeletedFromDB() {
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
        assertEqual_Game_With_GameFromDB();
    }

    private void assertEqual_Game_With_GameFromDB() {
        IGame gameFromDB = dao.readGame(GAME_ID);
        assertEquals(game.getId(), gameFromDB.getId());
        assertEquals(game.getCurrentPlayer(), gameFromDB.getCurrentPlayer());

        // TODO Array vs List falsche Reihenfolge in Array
//        assertArrayEquals(game.getPlayer(), gameFromDB.getPlayer());

        assertEquals(game.getGameState(), gameFromDB.getGameState());
        assertEquals(game.getField().toString(), gameFromDB.getField().toString());
    }

    @Test
    public void updateGame_gameAlreadyInDB_isUpdated() throws Exception {
        dao.createGame(game);
        int width = 2;
        int height = 1;
        game.getField().getCell(width, height).setCharacter(new Bomb(playerOne));
        dao.updateGame(game);
        assertEqual_Game_With_GameFromDB();
    }

    @Test
    public void updateGame_NoGameInDB_isCreated() throws Exception {
        dao.updateGame(game);
        assertEqual_Game_With_GameFromDB();
    }

    // TODO List<IGame> getGameList() method, hier ein bsp Code anhand von Transfercharacter
//    @Test
//    public void uebergangsTest_QueryList() throws Exception {
//        SessionFactory sessionFactory;
//        sessionFactory = new Configuration()
//                .configure() // configures settings from hibernate.cfg.xml
//                .buildSessionFactory();
//        Session session = sessionFactory.openSession();
//        Transaction tx = session.beginTransaction();
//
//        commandDatabaseQueryList(session);
//
//        tx.commit();
//        session.close();
//    }
//
//    @SuppressWarnings({ "unchecked" })
//    private void commandDatabaseQueryList(Session session) {
//        List result = session.createQuery( "from TransferCharacter" ).list();
//        for ( TransferCharacter transChar : (List<TransferCharacter>) result ) {
//            System.out.println( "Event (" + transChar.getId() +
//                    ") : " + transChar.getRank() );
//        }
//    }
}