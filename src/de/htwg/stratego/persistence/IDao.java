package de.htwg.stratego.persistence;

import de.htwg.stratego.model.IGame;

public interface IDao {

    void createGame(IGame game);
    IGame readGame();
    void updateGame(IGame game);
    void deleteGame(IGame game);

    void closeDb();
}
