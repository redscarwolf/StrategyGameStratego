package de.htwg.stratego.persistence.hibernate;

import de.htwg.stratego.model.IGame;
import de.htwg.stratego.persistence.IDao;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class HibernateDao implements IDao {

    @Override
    public void createGame(IGame game) {

    }

    @Override
    public IGame readGame() {
        // TODO
        return null;
    }

    public List<TransferCharacter> getTransferCharacter() {
        Transaction transaction = null;
        Session session = null;
        List<TransferCharacter> transferCharacter;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            transferCharacter = queryTransferCharacter(session);

            transaction.commit();
        } catch (HibernateException ex) {
            if (transaction != null)
                transaction.rollback();
            throw new RuntimeException(ex.getMessage());
        }

        return transferCharacter;
    }



    @SuppressWarnings({ "unchecked" })
    private List<TransferCharacter> queryTransferCharacter(Session session) {
        List result = session.createQuery( "from TransferCharacter" ).list();
        return (List<TransferCharacter>) result;
    }


    @Override
    public void updateGame(IGame game) {
        Transaction transaction = null;
        Session session = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();

            transaction = session.beginTransaction();

            TransferCharacter character = createCharacter();
            // TODO
            session.save(character);

            transaction.commit();
        } catch (HibernateException ex) {
            if (transaction != null)
                transaction.rollback();
            throw new RuntimeException(ex.getMessage());
        }
    }

    private TransferCharacter createCharacter() {
        // TODO
        return new TransferCharacter(12, true);
    }

    private TransferGame copyGame(IGame game) {
        // TODO
        TransferGame transferGame = new TransferGame();

        return transferGame;
    }

    @Override
    public void deleteGame(IGame game) {

    }

    @Override
    public void closeDb() {

    }
}
