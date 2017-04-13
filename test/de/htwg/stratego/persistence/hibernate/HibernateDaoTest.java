package de.htwg.stratego.persistence.hibernate;

import de.htwg.stratego.model.IGame;
import de.htwg.stratego.model.impl.Game;
import de.htwg.stratego.persistence.IDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class HibernateDaoTest {
    private IDao dao;
    private HibernateDao daoImpl;
    private IGame game;

    @Before
    public void setUp() throws Exception {
        this.dao = new createTCharacterList();
        this.daoImpl = new createTCharacterList();
        this.game = new Game(1,null,null,null);
    }

    @After
    public void deleteGame() throws Exception {
        // TODO irgenwas um nach test aufzuräumen, wird nach JEDEM test ausgeführt
    }

    @Test
    public void updateGame_startWithEmptyDatabase_savesPassedGame() throws Exception {
        dao.updateGame(game);
        IGame iGame = dao.readGame();
        assertEquals(game.getCurrentPlayer(),iGame.getCurrentPlayer());
    }
    
    // TODO ubergangstest delete
    @Test
    public void getTransferCharacter() throws Exception {
        List<TransferCharacter> transferCharacterList = daoImpl.getTransferCharacter();
        for ( TransferCharacter transChar : transferCharacterList ) {
            System.out.println( "TransferCharacter (" + transChar.getId() +
                    ") : " + transChar.getRank() + " " + transChar.getMoveable() );
        }
    }

    // TODO delete
    @Test
    public void uebergangsTest_QueryList() throws Exception {
        SessionFactory sessionFactory;
        sessionFactory = new Configuration()
                .configure() // configures settings from hibernate.cfg.xml
                .buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        
        commandDatabaseQueryList(session);

        tx.commit();
        session.close();
    }

    @SuppressWarnings({ "unchecked" })
    private void commandDatabaseQueryList(Session session) {
        List result = session.createQuery( "from TransferCharacter" ).list();
        for ( TransferCharacter transChar : (List<TransferCharacter>) result ) {
            System.out.println( "Event (" + transChar.getId() +
                    ") : " + transChar.getRank() );
        }
    }

    // TODO delete
    @Test
    public void uebergangstest_ManyToOne() throws Exception {
        SessionFactory sessionFactory;
        sessionFactory = new Configuration()
                .configure() // configures settings from hibernate.cfg.xml
                .buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        TransferCharacter transferCharacter = new TransferCharacter(12, false);

        List<TransferCharacter> transferCharacterList = new ArrayList<>();
        transferCharacterList.add(transferCharacter);

        TransferPlayer transferPlayer = new TransferPlayer("Hugo2", "!!", false);

        transferCharacter.setPlayer(transferPlayer);
        transferPlayer.setCharacterList(transferCharacterList);

        session.save(transferPlayer);
        session.save(transferCharacter);

        tx.commit();
        session.close();
    }
}