package de.htwg.stratego.persistence.hibernate;

import de.htwg.stratego.model.*;
import de.htwg.stratego.persistence.IDao;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class HibernateDao implements IDao {

    @Override
    public void createGame(IGame game) {
        Transaction transaction = null;
        Session session = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();

            transaction = session.beginTransaction();

            TransferGame transferGame = copyGame(game);
            session.save(transferGame);

            transaction.commit();
        } catch (HibernateException ex) {
            if (transaction != null)
                transaction.rollback();
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public void updateGame(IGame game) {
        Transaction transaction = null;
        Session session = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();

            transaction = session.beginTransaction();

            TransferGame transferGame = copyGame(game);
            session.saveOrUpdate(transferGame);

            transaction.commit();
        } catch (HibernateException ex) {
            if (transaction != null)
                transaction.rollback();
            throw new RuntimeException(ex.getMessage());
        }
    }


    @Override
    public IGame readGame() {
        // TODO
        return null;
    }



    @Override
    public void deleteGame(IGame game) {

    }

    @Override
    public void closeDb() {

    }


    @SuppressWarnings({ "unchecked" })
    private List<TransferCharacter> queryTransferCharacter(Session session) {
        List result = session.createQuery( "from TransferCharacter" ).list();
        return (List<TransferCharacter>) result;
    }

    // TODO löschen? für was noch behalten?
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

    // TODO private setzen
    public TransferGame copyGame(IGame game) {
        HibernateUtilTransferPlayerMap transferPlayerMap = new HibernateUtilTransferPlayerMap(game);
        return new TransferGame(
                null,
                copyField(game.getField(),game.getPlayer(), transferPlayerMap),
                game.getCurrentPlayer(),
                transferPlayerMap.getAllTransferPlayer());
    }

    private TransferField copyField (IField field, IPlayer[] player, HibernateUtilTransferPlayerMap transferPlayerMap) {
        TransferField transferField = new TransferField(field.getWidth(), field.getHeight());

        List<TransferCell> transferCellList = createTransferCells(field, player, transferPlayerMap);
        transferField.setCells(transferCellList);
        return transferField;
    }

    private List<TransferCell> createTransferCells(IField field, IPlayer[] player, HibernateUtilTransferPlayerMap transferPlayerMap) {
        List<TransferCell> transferCellList = new ArrayList<>();

        for (IPlayer aPlayer : player) {
            TransferPlayer transferPlayer = transferPlayerMap.getTransferPlayer(aPlayer);
            transferCellList.addAll(copyCellsForPlayer(transferPlayer, field.getAllCellsFrom(aPlayer)));

        }
        return transferCellList;
    }

    private List<TransferCell> copyCellsForPlayer(TransferPlayer transferPlayer, List<ICell> allCellsFromPlayer) {
        List<TransferCell> tCellList = new ArrayList<>();
        for (ICell oldCell :
                allCellsFromPlayer) {

            TransferCharacter transferCharacter = new TransferCharacter(oldCell.getCharacter(), transferPlayer);

            // copyCell
            TransferCell transferCell = new TransferCell(oldCell.getX(), oldCell.getY(), oldCell.isPassable());
            transferCell.setCharacter(transferCharacter);


            tCellList.add(transferCell);
        }
        return tCellList;
    }
}
