package de.htwg.stratego.model;

import de.htwg.stratego.controller.state.GameState;

public interface IGame {

    int getCurrentPlayer();
    IPlayer[] getPlayer();
    GameState getGameState();
    IField getField();

}
