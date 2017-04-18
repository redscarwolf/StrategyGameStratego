package de.htwg.stratego.model;

import de.htwg.stratego.controller.state.GameState;

public interface IGame {

    int getId();
    int getCurrentPlayer();
    IPlayer[] getPlayer();
    EGameState getGameState();
    IField getField();

}
