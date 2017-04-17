package de.htwg.stratego.persistence.hibernate;

import de.htwg.stratego.model.*;
import de.htwg.stratego.model.impl.*;
import de.htwg.stratego.model.impl.character.*;
import de.htwg.stratego.persistence.IDao;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HibernateDao implements IDao {

    @Override
    public void createGame(IGame game) {
        Transaction transaction = null;
        Session session;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            TransferGame transferGame = copyGame(game);
            saveEachTransferObjectInDB(session, transferGame);

            transaction.commit();
        } catch (HibernateException ex) {
            if (transaction != null)
                transaction.rollback();
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public IGame readGame(int gameId) {
        IGame game;
        Transaction transaction = null;
        Session session;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            TransferGame transferGameFromDB = session.get(TransferGame.class, gameId);
            game = copyGameFromDB(transferGameFromDB);

            transaction.commit();
        } catch (HibernateException ex) {
            if (transaction != null)
                transaction.rollback();
            throw new RuntimeException(ex.getMessage());
        }
        return game;
    }

    @Override
    public void updateGame(IGame game) {
        Transaction transaction = null;
        Session session;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            TransferGame transferGameFromDB = session.get(TransferGame.class, game.getId());
            if (transferGameFromDB != null) {
                deleteEachTransferObjectInDB(session, transferGameFromDB);
            }

            TransferGame transferGame = copyGame(game);
            saveEachTransferObjectInDB(session, transferGame);

            transaction.commit();
        } catch (HibernateException ex) {
            if (transaction != null)
                transaction.rollback();
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public void deleteGame(IGame game) {
        Transaction transaction = null;
        Session session;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            TransferGame transferGameFromDB = session.get(TransferGame.class, game.getId());
            if (transferGameFromDB != null) {
                deleteEachTransferObjectInDB(session, transferGameFromDB);
            }

            transaction.commit();
        } catch (HibernateException ex) {
            if (transaction != null)
                transaction.rollback();
            throw new RuntimeException(ex.getMessage());
        }
    }

    private void saveEachTransferObjectInDB(Session session, TransferGame transferGame) {
        for (TransferPlayer transferPlayer: transferGame.getPlayer()) {
            session.save(transferPlayer);
            for (TransferCharacter character: transferPlayer.getCharacterList()) {
                session.save(character);
            }
        }

        TransferField field = transferGame.getField();
        session.save(field);

        for (TransferCell cell : field.getCells()) {
            session.save(cell);
            session.save(cell.getCharacter());
        }
        session.save(transferGame);
    }

    private void deleteEachTransferObjectInDB(Session session, TransferGame transferGame) {
        for (TransferPlayer transferPlayer: transferGame.getPlayer()) {
            session.delete(transferPlayer);
            for (TransferCharacter character: transferPlayer.getCharacterList()) {
                session.delete(character);
            }
        }

        TransferField field = transferGame.getField();
        session.delete(field);

        for (TransferCell cell : field.getCells()) {
            session.delete(cell);
            session.delete(cell.getCharacter());
        }
        session.delete(transferGame);
    }

    @Override
    public void closeDb() {
        throw new NotImplementedException();
    }

    private IGame copyGameFromDB(TransferGame transferGame){
        HashMap<TransferPlayer, IPlayer> playerMap = new HashMap<>();
        List<IPlayer> playerList = new ArrayList<>();
        for (TransferPlayer transferPlayer: transferGame.getPlayer()) {
            Player player = new Player(transferPlayer.getName(), transferPlayer.getSymbol());
            playerMap.put(transferPlayer,player);
            playerList.add(player);
            for (TransferCharacter transferCharacter: transferPlayer.getCharacterList()) {
                ICharacter character = createCharacterByRank(transferCharacter, player);
                player.addCharacter(character);
            }
        }

        TransferField transferField = transferGame.getField();
        Field field = new Field(transferField.getWidth(), transferField.getHeight());

        for (TransferPlayer transferPlayer: transferGame.getPlayer()) {
            for (TransferCell transferCell : transferField.getAllCellsFrom(transferPlayer)) {
                ICharacter character = createCharacterByRank(transferCell.getCharacter(), playerMap.get(transferPlayer));
                ICell cell = new Cell(transferCell.getX(), transferCell.getY(), transferCell.getPassable());
                cell.setCharacter(character);
                field.setCell(cell);
            }
        }
        return new Game(
                transferGame.getId(),
                transferGame.getCurrentPlayer(),
                playerList.toArray(new IPlayer[playerList.size()]),
                transferGame.getGameState(),
                field);
    }

    private ICharacter createCharacterByRank(TransferCharacter transferCharacter, IPlayer player) {
        ICharacter character;
        switch (transferCharacter.getRank()) {
            case Rank.FLAG :
                character = new Flag(player);
                break;
            case Rank.SPY :
                character = new Spy(player);
                break;
            case Rank.SCOUT :
                character = new Scout(player);
                break;
            case Rank.MINER :
                character = new Miner(player);
                break;
            case Rank.SERGEANT :
                character = new Sergeant(player);
                break;
            case Rank.LIEUTENANT :
                character = new Lieutenant(player);
                break;
            case Rank.CAPTAIN :
                character = new Captain(player);
                break;
            case Rank.MAJOR :
                character = new Major(player);
                break;
            case Rank.COLONEL :
                character = new Colonel(player);
                break;
            case Rank.GENERAL :
                character = new General(player);
                break;
            case Rank.MARSHAL :
                character = new Marshal(player);
                break;
            case Rank.BOMB :
                character = new Bomb(player);
                break;
            default:
                throw new RuntimeException("Couldn't createCharacterByRank because Rank: " + transferCharacter.getRank().toString() + " ,is not captured.");
        }
        return character;
    }

    private TransferGame copyGame(IGame game) {
        HibernateUtilTransferPlayerMap transferPlayerMap = new HibernateUtilTransferPlayerMap(game);
        return new TransferGame(
                game.getId(),
                game.getGameState(),
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

    public List<IGame> getAllGames(){
        List<IGame> gameList = new ArrayList<>();

        Transaction transaction = null;
        Session session;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            List<TransferGame> transferGameList = queryTransferGame(session);
            for (TransferGame transferGame :
                    transferGameList) {
                gameList.add(copyGameFromDB(transferGame));
            }

            transaction.commit();
        } catch (HibernateException ex) {
            if (transaction != null)
                transaction.rollback();
            throw new RuntimeException(ex.getMessage());
        }

        return gameList;
    }

    @SuppressWarnings({ "unchecked" })
    private List<TransferGame> queryTransferGame(Session session) {
        return (List<TransferGame>) session.createQuery( "from TransferGame" ).list();
    }
}
